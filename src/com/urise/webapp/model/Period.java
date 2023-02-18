package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate start;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate end;

    private String title;

    private String description;

    public Period() {
        this(null, null, "", "");
    }

    public Period(LocalDate start, LocalDate end, String title, String description) {
        this.start = start;
        this.end = end;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.start);
        hash = 29 * hash + Objects.hashCode(this.end);
        hash = 29 * hash + Objects.hashCode(this.title);
        hash = 29 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.start.toString()); str.append(" -- ");
        str.append(this.end.toString()); str.append("  ");
        str.append(this.title); str.append("\n");
        str.append(this.description); str.append("\n");
        return str.toString();
    }

    public void doWriteData(DataOutputStream outputStream) throws IOException {
        System.out.printf("YEAR: ", "%d", start.getYear());
        System.out.printf("MONTH: ", "%d", start.getMonthValue());
        System.out.printf("DAY: ", "%d", start.getDayOfMonth());
        outputStream.writeInt(start.getYear());
        outputStream.writeInt(start.getMonthValue());
        outputStream.writeInt(start.getDayOfMonth());
        outputStream.writeInt(end.getYear());
        outputStream.writeInt(end.getMonthValue());
        outputStream.writeInt(end.getDayOfMonth());
        outputStream.writeUTF(getTitle());
        outputStream.writeUTF(getDescription());
    }

    public static Period doReadData(DataInputStream inputStream) throws IOException {
        int startYear = inputStream.readInt();
        int startMonth = inputStream.readInt();
        int startDay = inputStream.readInt();
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);

        int endYear = inputStream.readInt();
        int endMonth = inputStream.readInt();
        int endDay = inputStream.readInt();
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);

        String title = inputStream.readUTF();
        String description = inputStream.readUTF();

        return new Period(startDate, endDate, title, description);
    }

}
