/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ais_r_enhanced.ais.r.enhanced.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 12217608
 */
public class RecruitDetails implements Serializable {
    private int id;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private Date interviewDate;
    private QualificationLevel qualificationLevel; // Enum for qualification level
    private Department department;
    private String experience;
    private String timeToLeadAndCompleteProject;
    private boolean requestForOTP;
    private String OTPCode;
    private Location location;


    // Constructor
    public RecruitDetails(String fullName, String address, String phoneNumber, String email,
                          String username, String password, Date interviewDate,
                          QualificationLevel qualificationLevel, Department department,
                          String experience, String timeToLeadAndCompleteProject, Location location) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.interviewDate = interviewDate;
        this.qualificationLevel = qualificationLevel;
        if(department!=null){
            this.department = department;
        }
        this.experience = experience;
        this.timeToLeadAndCompleteProject = timeToLeadAndCompleteProject;
        this.location = location;
    }

    public String generateDetailsString() {
        return "Full name: " + getFullName() + '\n' +
                "Address: " + getAddress() + '\n' +
                "Phone number: " + getPhoneNumber() + '\n' +
                "Email: " + getEmail() + '\n' +
                "Interview date: " + getInterviewDate() + '\n' +
                "Qualification level: " + getQualificationLevel() + '\n' +
                "Location: " + getLocation() + '\n' +
                "Department: " + getDepartment() + '\n' +
                "Experience: " + getExperience() + '\n' +
                "Time to lead and complete project: " + getTimeToLeadAndCompleteProject() + '\n';
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRequestForOTP() {
        return requestForOTP;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public QualificationLevel getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(QualificationLevel qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTimeToLeadAndCompleteProject() {
        return timeToLeadAndCompleteProject;
    }

    public void setTimeToLeadAndCompleteProject(String timeToLeadAndCompleteProject) {
        this.timeToLeadAndCompleteProject = timeToLeadAndCompleteProject;
    }

    public boolean getRequestForOTP() {
        return requestForOTP;
    }

    public void setRequestForOTP(boolean requestForOTP) {
        this.requestForOTP = requestForOTP;
    }

    public String getOTPCode() {
        return OTPCode;
    }

    public void setOTPCode(String OTPCode) {
        this.OTPCode = OTPCode;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "Recruit{"
                + "fullName='" + fullName + '\''
                + ", address='" + address + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", email='" + email + '\''
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", interviewDate=" + interviewDate
                + ", qualificationLevel=" + qualificationLevel
                + '}';
    }

    // Enum for qualification level
    public enum QualificationLevel {
        BACHELORS,
        MASTERS,
        PHD
    }

    public enum Department {
        SoftwareEngineer, AerospaceEngineer,
        MechanicalEngineer, ElectronicsEngineer
    }

    public enum Location {
        MELBOURNE,
        SYDNEY,
        BRISBANE,
        ADELAIDE
    }
}
