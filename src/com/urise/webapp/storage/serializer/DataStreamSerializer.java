package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class DataStreamSerializer implements Serializer {
    @Override
    public void doWrite(OutputStream outputStream, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());

            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet())  {
                String title = entry.getKey().name();
                dos.writeUTF(title);
                doWriteData(entry.getValue(), dos);
            }
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

    public void doWriteData(Object object, DataOutputStream outputStream) throws IOException {
        if (object instanceof ListSection) {
            doWriteData_listSection((ListSection) object, outputStream);
        }
        if (object instanceof Organization) {
            doWriteData_Organization((Organization) object, outputStream);
        }
        if (object instanceof OrganizationSection) {
            doWriteData_OrganizationSection((OrganizationSection) object, outputStream);
        }
        if (object instanceof Period) {
            doWriteData_Period((Period) object, outputStream);
        }
        if (object instanceof TextSection) {
            doWriteData_TextSection((TextSection) object, outputStream);
        }
    }

    // ListSection
    public void doWriteData_listSection(ListSection object, DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(object.getList() == null ? 0 : object.getList().size());
        for (String row : object.getList()) {
            outputStream.writeUTF(row);
        }
    }

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
    public void doWriteData_Organization(Organization object, DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(Objects.requireNonNull(object.getName(), "Name must not be null"));
        outputStream.writeUTF(object.getWebsite() == null ? "" : object.getWebsite());
        outputStream.writeInt(Objects.requireNonNull(object.getPeriods(), "Periods must not be null").size());
        for (Period period : object.getPeriods()) {
            doWriteData_Period(period, outputStream);
        }
    }

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
    public void doWriteData_OrganizationSection(OrganizationSection object, DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(object.getOrganizations() == null ? 0 : object.getOrganizations().size());
        for (Organization row : object.getOrganizations()) {
            doWriteData_Organization(row, outputStream);
        }
    }

    public static OrganizationSection doReadData_OrganizationSection(DataInputStream inputStream) throws IOException {
        int size = inputStream.readInt();
        OrganizationSection result = new OrganizationSection();
        for (int i = 0; i < size; i++) {
            result.getOrganizations().add(doReadData_Organization(inputStream));
        }
        return result;
    }

    // Period
    public void doWriteData_Period(Period object, DataOutputStream outputStream) throws IOException {
        LocalDate st = Objects.requireNonNull(object.getStart(), "Start of period must not be null");
        outputStream.writeInt(st.getYear());
        outputStream.writeInt(st.getMonthValue());
        outputStream.writeInt(st.getDayOfMonth());
        if (object.getEnd() == null) {
            outputStream.writeInt(0);
            outputStream.writeInt(0);
            outputStream.writeInt(0);
        } else {
            outputStream.writeInt(object.getEnd().getYear());
            outputStream.writeInt(object.getEnd().getMonthValue());
            outputStream.writeInt(object.getEnd().getDayOfMonth());
        }
        outputStream.writeUTF(Objects.requireNonNull(object.getTitle(), "Title must be null"));
        outputStream.writeUTF(object.getDescription());
    }

    public static Period doReadData_Period(DataInputStream inputStream) throws IOException {
        int startYear = inputStream.readInt();
        int startMonth = inputStream.readInt();
        int startDay = inputStream.readInt();
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

        int endYear = inputStream.readInt();
        int endMonth = inputStream.readInt();
        int endDay = inputStream.readInt();
        LocalDate endDate;
        if (endYear == 0 && endMonth == 0 && endDay == 0) {
            endDate = null;
        } else {
            endDate = LocalDate.of(endYear, endMonth, endDay);
        }

        String title = inputStream.readUTF();
        String description = inputStream.readUTF();

        return new Period(startDate, endDate, title, description);
    }

    // TextSection
    public void doWriteData_TextSection(TextSection object, DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(object.getText() == null ? "" : object.getText());
    }

    public static TextSection doReadData_TextSection(DataInputStream inputStream) throws IOException {
        TextSection result = new TextSection();
        result.setText(inputStream.readUTF());
        return result;
    }


}
