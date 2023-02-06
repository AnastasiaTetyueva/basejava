package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate start;

    private LocalDate end;

    private String title;

    private String description;

    public Period() {
        this(LocalDate.now(), LocalDate.now(), "", "");
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
        str.append(this.start.toString()); str.append("-");
        str.append(this.end.toString()); str.append("  ");
        str.append(this.title); str.append("\n");
        str.append(this.description); str.append("\n");
        return str.toString();
    }

}
