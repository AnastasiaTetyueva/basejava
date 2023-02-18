package com.urise.webapp.model;

import java.io.*;

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
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public void doWriteData(DataOutputStream outputStream) throws IOException {
        outputStream.writeUTF(getText());
    }

    public static TextSection doReadData(DataInputStream inputStream) throws IOException {
        TextSection result = new TextSection();
        result.setText(inputStream.readUTF());
        return result;
    }
}
