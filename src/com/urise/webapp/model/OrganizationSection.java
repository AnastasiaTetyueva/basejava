package com.urise.webapp.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<Organization> organizations = new ArrayList<Organization>();

    public OrganizationSection() {}

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
    public void doWriteData(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(getOrganizations().size());
        for (Organization row : getOrganizations()) {
            row.doWriteData(outputStream);
        }
    }

    public static OrganizationSection doReadData(DataInputStream inputStream) throws IOException {
        int size = inputStream.readInt();
        OrganizationSection result = new OrganizationSection();
        for (int i = 0; i < size; i++) {
            result.getOrganizations().add(Organization.doReadData(inputStream));
        }
        return result;
    }


}
