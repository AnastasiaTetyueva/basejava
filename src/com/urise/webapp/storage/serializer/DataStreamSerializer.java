package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

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


    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

        // contacts
            List<AbstractMap.SimpleEntry<ContactType, String>> collectionC = new ArrayList<>();
            readWithException(collectionC, dis, () -> new AbstractMap.SimpleEntry<>(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            for (AbstractMap.SimpleEntry<ContactType, String> entry : collectionC) {
                resume.setContact(entry.getKey(), entry.getValue());
            }

        // sections
            List<AbstractMap.SimpleEntry<SectionType, AbstractSection>> collectionS = new ArrayList<>();
            readWithException(collectionS, dis, () -> {
                SectionType type = SectionType.valueOf(dis.readUTF());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        TextSection resultT = new TextSection();
                        resultT.setText(dis.readUTF());
                        return new AbstractMap.SimpleEntry<>(type, resultT);
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection resultL = new ListSection();
                        readWithException(resultL.getList(), dis, dis::readUTF);
                        return new AbstractMap.SimpleEntry<>(type, resultL);
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection resultO = new OrganizationSection();
                        readWithException(resultO.getOrganizations(), dis, () -> {
                            Organization result = new Organization();
                            String name = dis.readUTF();
                            String website = dis.readUTF();
                            result.setName(name);
                            result.setWebsite(website);
                            readWithException(result.getPeriods(), dis, () -> {
                                LocalDate start = readDate(dis);
                                LocalDate end = readDate(dis);
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                return new Period(start, end, title, description);
                            });
                            return result;
                        });
                        return new AbstractMap.SimpleEntry<>(type, resultO);
                    default: break;
                }
                return null;
            });
            for (AbstractMap.SimpleEntry<SectionType, AbstractSection> entry : collectionS) {
                resume.setSection(entry.getKey(), entry.getValue());
            }
            return resume;
        }
    }

    private <T> void readWithException(Collection<T> collection, DataInputStream inputStream, ThrowingSupplier<T> supplier) throws IOException {
        int size = inputStream.readInt();
        for (int i = 0; i < size; i++) {
            collection.add(supplier.get());
        }
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

}
