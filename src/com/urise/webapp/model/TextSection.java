package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private String text;

    public TextSection() {
        this("");
    }

    public TextSection(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        String content = (String) o;
        return text.equals(content);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }
}
