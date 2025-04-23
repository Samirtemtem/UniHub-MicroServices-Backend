package com.example.event_ms.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Data Transfer Object for Event messages sent via RabbitMQ
 */
@NoArgsConstructor
@AllArgsConstructor
public class EventMessage implements Serializable {

    private Long idEvent;
    private String nomEvent;
    private String descriptionEvent;
    private Date dateDebEvent;
    private Date dateFinEvent;
    private String lieuEvent;
    private String imageEvent;
    private EventAction eventAction;

    /**
     * Enum representing the type of action performed on an event
     */
    public enum EventAction {
        CREATE,
        UPDATE,
        DELETE
    }

    // Getters and Setters
    public Long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Long idEvent) {
        this.idEvent = idEvent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public Date getDateDebEvent() {
        return dateDebEvent;
    }

    public void setDateDebEvent(Date dateDebEvent) {
        this.dateDebEvent = dateDebEvent;
    }

    public Date getDateFinEvent() {
        return dateFinEvent;
    }

    public void setDateFinEvent(Date dateFinEvent) {
        this.dateFinEvent = dateFinEvent;
    }

    public String getLieuEvent() {
        return lieuEvent;
    }

    public void setLieuEvent(String lieuEvent) {
        this.lieuEvent = lieuEvent;
    }

    public String getImageEvent() {
        return imageEvent;
    }

    public void setImageEvent(String imageEvent) {
        this.imageEvent = imageEvent;
    }

    public EventAction getEventAction() {
        return eventAction;
    }

    public void setEventAction(EventAction eventAction) {
        this.eventAction = eventAction;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "idEvent=" + idEvent +
                ", nomEvent='" + nomEvent + '\'' +
                ", descriptionEvent='" + descriptionEvent + '\'' +
                ", dateDebEvent=" + dateDebEvent +
                ", dateFinEvent=" + dateFinEvent +
                ", lieuEvent='" + lieuEvent + '\'' +
                ", imageEvent='" + imageEvent + '\'' +
                ", eventAction=" + eventAction +
                '}';
    }
}
