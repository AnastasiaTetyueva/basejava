package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

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

}
