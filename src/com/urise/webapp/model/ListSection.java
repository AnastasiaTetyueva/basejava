package com.urise.webapp.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> list = new ArrayList<String>();

    public ListSection() {
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String row : list) {
            str.append(row); str.append("\n");
        }
        return str.toString();
    }

    @Override
    public void doWriteData(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(getList().size());
        for (String row : getList()) {
            outputStream.writeUTF(row);
        }
    }

    public static ListSection doReadData(DataInputStream inputStream) throws IOException {
        int size = inputStream.readInt();
        ListSection result = new ListSection();
        for (int i = 0; i < size; i++) {
            String row = inputStream.readUTF();
            result.getList().add(row);
        }
        return result;
    }

}
