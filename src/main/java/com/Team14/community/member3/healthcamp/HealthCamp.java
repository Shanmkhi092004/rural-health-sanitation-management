
package com.Team14.community.member3.healthcamp;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "health_camps")
public class HealthCamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String campName;

    @Column(nullable = false)
    private String village;

    private String location;

    private LocalDate campDate;

    private String organizer;

    private String description;

    private String status = "PLANNED";

    public HealthCamp() {
    }

    public Long getId() {
        return id;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getCampDate() {
        return campDate;
    }

    public void setCampDate(LocalDate campDate) {
        this.campDate = campDate;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


