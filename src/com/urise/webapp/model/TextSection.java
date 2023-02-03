package com.urise.webapp.model;

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
}
