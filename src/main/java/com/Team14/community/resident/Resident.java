package com.Team14.community.resident;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Village is required")
    @Column(nullable = false)
    private String village;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must contain 10 digits")
    private String phone;
    private Integer age;
    private String gender;
    public Resident() {
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}