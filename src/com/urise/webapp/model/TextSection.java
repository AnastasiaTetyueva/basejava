package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String text;

    public TextSection() {
        this("");
    }

    public TextSection(String text) {
        this.text = text;
    }

    public String getText() {
        return text.trim();
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public boolean isEmpty() { return text == null || getText().length() == 0; }

}
