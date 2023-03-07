/**
 * Initial resume class
 */
package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private final String uuid;

    private String fullName;

    private Map<SectionType, AbstractSection> sections;

    private Map<ContactType, String> contacts;

    public Resume() {
        this(UUID.randomUUID().toString(), "");
    }

    public Resume(String uuid) {
        this(uuid, "");
    }

    public Resume(String uuid, String fullName) {
        this(uuid, fullName, new HashMap<SectionType, AbstractSection>(), new HashMap<ContactType, String>());
    }

    public Resume(String uuid, String fullName, Map<SectionType, AbstractSection> sections, Map<ContactType, String> contacts) {
        this.uuid = uuid;
        this.fullName = fullName;
        this.sections = sections;
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.fullName); str.append("\n");
        str.append("\n");
        for (ContactType contact : ContactType.values()) {
            str.append(contact.getTitle()); str.append(": ");
            str.append(contacts.get(contact)); str.append("\n");
        }
        str.append("\n");
        for (SectionType type : SectionType.values()) {
            str.append(type.getTitle());
            str.append("\n");
            str.append(sections.get(type));
            str.append("\n\n");
        }
        return str.toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public String getSectionTitle(SectionType type) {
        return type.getTitle();
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setContact(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    public static Comparator<Resume> getResumeComparator = (r1, r2) ->
                (r1.getFullName() != null || r2.getFullName() != null || r1.getFullName().toUpperCase() == r2.getFullName().toUpperCase()) ? r1.getUuid().compareTo(r2.getUuid())
                        : r1.getFullName().toUpperCase().compareTo(r2.getFullName().toUpperCase());


}
