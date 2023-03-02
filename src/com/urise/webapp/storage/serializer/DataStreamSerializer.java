package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class DataStreamSerializer implements Serializer {
    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

        // contacts
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(contacts.entrySet(), dos, elem -> {
                dos.writeUTF(String.valueOf(elem.getKey()));
                dos.writeUTF(elem.getValue());
            });

        // sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeWithException(sections.entrySet(), dos, elem -> {
                String title = elem.getKey().name();
                dos.writeUTF(title);
                doWriteSectionData(elem.getValue(), elem.getKey(), dos);
            });
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream outputStream, ThrowingConsumer<T> consumer) throws IOException {
         outputStream.writeInt(collection.size());
        for (T entry : collection) {
            consumer.accept(entry);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(type, doReadData_TextSection(dis));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(type, doReadData_listSection(dis));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.setSection(type, doReadData_OrganizationSection(dis));
                    break;
                    default: break;
                }
            }
            return resume;
        }
    }

    public void doWriteSectionData(AbstractSection section, SectionType type, DataOutputStream outputStream) throws IOException {
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                TextSection objectT = (TextSection) section;
                outputStream.writeUTF(objectT.getText() == null ? "" : objectT.getText());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection objectL = (ListSection) section;
                writeWithException(objectL.getList(), outputStream, outputStream::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                OrganizationSection objectO = (OrganizationSection) section;
                writeWithException(objectO.getOrganizations(), outputStream, elem -> {
                    outputStream.writeUTF(Objects.requireNonNull(elem.getName(), "Name must not be null"));
                    outputStream.writeUTF(elem.getWebsite() == null ? "" : elem.getWebsite());
                    writeWithException(elem.getPeriods(), outputStream, period -> {
                        LocalDate st = Objects.requireNonNull(period.getStart(), "Start of period must not be null");
                        writeDate(st, outputStream);
                        writeDate(period.getEnd(), outputStream);
                        outputStream.writeUTF(Objects.requireNonNull(period.getTitle(), "Title must be null"));
                        outputStream.writeUTF(period.getDescription());
                    });
                });
                break;
            default:
                break;
        }
    }

    // ListSection
    public static ListSection doReadData_listSection(DataInputStream inputStream) throws IOException {
        int size = inputStream.readInt();
        ListSection result = new ListSection();
        for (int i = 0; i < size; i++) {
            String row = inputStream.readUTF();
            result.getList().add(row);
        }
        return result;
    }

    // Organization
    public static Organization doReadData_Organization(DataInputStream inputStream) throws IOException {
        Organization result = new Organization();
        String name = inputStream.readUTF();
        String website = inputStream.readUTF();
        result.setName(name);
        result.setWebsite(website);
        int size = inputStream.readInt();
        for(int i = 0; i < size; i++) {
            result.getPeriods().add(doReadData_Period(inputStream));
        }
        return result;
    }

    // OrganizationSection
    public static OrganizationSection doReadData_OrganizationSection(DataInputStream inputStream) throws IOException {
        int size = inputStream.readInt();
        OrganizationSection result = new OrganizationSection();
        for (int i = 0; i < size; i++) {
            result.getOrganizations().add(doReadData_Organization(inputStream));
        }
        return result;
    }

    // Period
    private void writeDate(LocalDate date, DataOutputStream outputStream) throws IOException {
        if (date == null) {
            outputStream.writeInt(0);
            outputStream.writeInt(0);
            outputStream.writeInt(0);
        } else {
            outputStream.writeInt(date.getYear());
            outputStream.writeInt(date.getMonthValue());
            outputStream.writeInt(date.getDayOfMonth());
        }
    }

    public static Period doReadData_Period(DataInputStream inputStream) throws IOException {
        LocalDate start = readDate(inputStream);
        LocalDate end = readDate(inputStream);

        String title = inputStream.readUTF();
        String description = inputStream.readUTF();

        return new Period(start, end, title, description);
    }

    private static LocalDate readDate(DataInputStream inputStream) throws IOException {
        int year = inputStream.readInt();
        int month = inputStream.readInt();
        int day = inputStream.readInt();
        if (year == 0 && month == 0 && day == 0) {
            return null;
        } else {
            return LocalDate.of(year, month, day);
        }
    }

    // TextSection
    public static TextSection doReadData_TextSection(DataInputStream inputStream) throws IOException {
        TextSection result = new TextSection();
        result.setText(inputStream.readUTF());
        return result;
    }


}
