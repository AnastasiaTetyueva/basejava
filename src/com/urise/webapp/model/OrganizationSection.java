package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection {

    private final List<Organization> organizations = new ArrayList<Organization>();

    public List<Organization> getOrganizations() {
        return organizations;
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
