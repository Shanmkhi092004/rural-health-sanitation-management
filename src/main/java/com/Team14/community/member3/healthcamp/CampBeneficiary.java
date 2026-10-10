
package com.Team14.community.member3.healthcamp;

import jakarta.persistence.*;

@Entity
@Table(name = "camp_beneficiaries")
public class CampBeneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long campId;

    @Column(nullable = false)
    private String beneficiaryName;

    private Integer age;

    private String gender;

    private String village;

    private String contactNumber;

    private String screeningNotes;

    public CampBeneficiary() {
    }

    public Long getId() {
        return id;
    }

    public Long getCampId() {
        return campId;
    }

    public void setCampId(Long campId) {
        this.campId = campId;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
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

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getScreeningNotes() {
        return screeningNotes;
    }

    public void setScreeningNotes(String screeningNotes) {
        this.screeningNotes = screeningNotes;
    }
}


