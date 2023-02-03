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

}
