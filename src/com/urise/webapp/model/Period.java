package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Date;

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
}
