package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.*;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Organization EMPTY = new Organization(Collections.singletonList(Period.EMPTY), "", "");

    private String name;

    private String website;

    private List<Period> periods = new ArrayList<Period>();

    public Organization() {
        this("");
    }

    public Organization(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public Organization(List<Period> periods, String name, String website) {
        this(name);
        this.periods = periods;
        this.website = website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.website);
        hash = 29 * hash + Objects.hashCode(this.periods);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.name); str.append("\n");
        str.append(this.website); str.append("\n");
        for (Period period : periods) {
            str.append(period.toString()); str.append("\n");
        }
        return str.toString();
    }

}
