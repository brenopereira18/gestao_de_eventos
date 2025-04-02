package com.eventify.eventify.model.entity;

public enum EventCategory {
    SHOW("Show/Festa"),
    CONFERENCE("Conferência"),
    SPORT("Esporte"),
    THEATER("Teatro"),
    WORKSHOP("Workshop"),
    UNIVERSITY_EVENT("Evento Universitário"),
    STAND_UP("Stand up"),
    EXHIBITION("Exposição/Feira"),
    WEBNAR("Webnar"),
    FITNESS("Fitness"),
    CUISINE("Gastronomia"),
    FUNK("Funk"),
    RAP("Rap/Trap"),
    BACKCOUNTRY("Sertanejo"),
    ELETRONICS("Eletrônica"),
    PAGODE("Pagode"),
    CARNAVAL("Carnaval"),
    CRUISE("Cruzeiro");

    private String category;

    EventCategory(String category) {
        this.category = category;
    }

}
