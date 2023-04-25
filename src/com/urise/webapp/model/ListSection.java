package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    public static final ListSection EMPTY = new ListSection();

    private final List<String> list = new ArrayList<String>();

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
        int index = 0;
        for (String row : list) {
            String goodRow = row.trim();
            if (goodRow.length() != 0) {
                str.append(goodRow);
                if (index < list.size()-1) {
                    str.append("\n");
                }
            }
            index++;
        }
        return str.toString();
    }

    @Override
    public boolean isEmpty() { return list.size() == 0; }

}
