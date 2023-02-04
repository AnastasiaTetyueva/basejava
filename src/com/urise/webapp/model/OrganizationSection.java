package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection {

    private List<Organization> organizations;

    public OrganizationSection() {
        this(new ArrayList<Organization>());
    }

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        String str = new String();
        for (Organization organization : organizations) {
            str.concat(organization.toString()); str.concat("\n");
        }
        return str;
    }


}
