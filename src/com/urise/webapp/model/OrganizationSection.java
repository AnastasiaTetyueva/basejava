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
}
