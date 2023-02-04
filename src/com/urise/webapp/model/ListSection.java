package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {
    private List<String> list;

    public ListSection() {
        this(new ArrayList<String>());
    }

    public ListSection(List<String> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        String str = new String();
        for (String row : list) {
            str.concat(row); str.concat("\n");
        }
        return str;
    }

}
