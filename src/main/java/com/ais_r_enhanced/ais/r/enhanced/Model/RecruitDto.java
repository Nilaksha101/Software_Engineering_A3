package com.ais_r_enhanced.ais.r.enhanced.Model;

import java.io.Serializable;

//model for grouping by location
public class RecruitDto implements Serializable {
    private String fullName;
    private RecruitDetails.Location location;

    public RecruitDto(String fullName, RecruitDetails.Location location) {
        this.fullName = fullName;
        this.location = location;
    }

    public RecruitDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RecruitDetails.Location getLocation() {
        return location;
    }

    public void setLocation(RecruitDetails.Location location) {
        this.location = location;
    }
}
