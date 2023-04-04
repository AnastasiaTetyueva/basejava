package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон", "tel:"),
    SKYPE("Skype", "skype:"),
    EMAIL("Почта", "mailto:"),
    LINKEDIN("Профиль LinkedIn",""),
    GITHUB("Профиль GitHub", ""),
    STACKOVERFLOW("Профиль Stackoverflow", ""),
    HOMEPAGE("Домашняя страница", "");

    private final String title;
    private final String prefix;

    ContactType(String title, String prefix) {
        this.title = title;
        this.prefix = prefix;
    }

    public String getTitle() {
        return title;
    }

    public String getPrefix() {
        return prefix;
    }

}
