package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String name;

    private String website;

    List<Period> periods;

    public Organization() {
        this("", "", new ArrayList<Period>());
    }

    public Organization(String name, String website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = periods;
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
        String str = new String();
        str.concat(this.name); str.concat("\n");
        str.concat(this.website); str.concat("\n");
        for (Period period : periods) {
            str.concat(period.toString()); str.concat("\n");
        }
        return str;
    }

}
