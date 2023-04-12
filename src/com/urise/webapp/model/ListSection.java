package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> list = new ArrayList<String>();

    public ListSection() {
    }

    public ListSection(List<String> list) {
        this.list.addAll(list);
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

}
