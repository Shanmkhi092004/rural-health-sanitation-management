package com.Team14.community.healthcentre;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
public class HealthCentre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Centre name is required")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Village is required")
    @Column(nullable = false)
    private String village;
    private String address;
    private String phone;
    private String services;
    public HealthCentre() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVillage() {
        return village;
    }
    public void setVillage(String village) {
        this.village = village;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getServices() {
        return services;
    }
    public void setServices(String services) {
        this.services = services;
    }
}