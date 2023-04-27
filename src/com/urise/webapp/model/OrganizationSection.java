package com.urise.webapp.model;

import java.util.*;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    public static final OrganizationSection EMPTY = new OrganizationSection(Collections.singletonList(Organization.EMPTY));

    private List<Organization> organizations = new ArrayList<Organization>();

    public OrganizationSection() {}

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

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
        for (Organization organization : getOrganizations()) {
            str.append(organization.toString()); str.append("\n");
        }
        return str.toString();
    }

    @Override
    public boolean isEmpty() { return organizations.size() == 0; }
}
