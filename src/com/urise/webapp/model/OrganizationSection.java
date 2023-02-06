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
        StringBuilder str = new StringBuilder();
        for (Organization organization : organizations) {
            str.append(organization.toString()); str.append("\n");
        }
        return str.toString();
    }


}
