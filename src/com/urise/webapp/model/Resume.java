/**
 * Initial resume class
 */
package com.urise.webapp.model;

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

public class Resume {

    // Unique identifier
    private final String uuid;

    private String fullName;

    private Map<SectionType, AbstractSection> sections;

    private Map<ContactType, String> contacts;

    public Resume() {
        this(UUID.randomUUID().toString(), "dummy");
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
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

    public static Comparator<Resume> getResumeComparator = (r1, r2) ->
                (r1.getFullName() != null || r2.getFullName() != null || r1.getFullName().toUpperCase() == r2.getFullName().toUpperCase()) ? r1.getUuid().compareTo(r2.getUuid())
                        : r1.getFullName().toUpperCase().compareTo(r2.getFullName().toUpperCase());


}
