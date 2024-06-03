/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AIS_R_Initial.Model;

import java.io.Serializable;

/**
 *
 * @author 12217608
 */
public class ManagementStaff implements Serializable{

    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String staffId;
    private ManagementLevel managementLevel; // Enum for management level
    private Branch branch; // Enum for branch

    // Constructor
    public ManagementStaff(String fullName, String address, String phoneNumber,
            String email, String username, String password,
            String staffId, ManagementLevel managementLevel,
            Branch branch) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.staffId = staffId;
        this.managementLevel = managementLevel;
        this.branch = branch;
    }

    // Getters and setters
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

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public ManagementLevel getManagementLevel() {
        return managementLevel;
    }

    public void setManagementLevel(ManagementLevel managementLevel) {
        this.managementLevel = managementLevel;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    // Enum for management level
    public enum ManagementLevel {
        SENIOR_MANAGER,
        MID_LEVEL_MANAGER,
        SUPERVISOR
    }

    // Enum for branch
    public enum Branch {
        MELBOURNE,
        SYDNEY,
        BRISBANE,
        ADELAIDE
    }

    // Override toString() method
    @Override
    public String toString() {
        return "ManagementStaff{"
                + "fullName='" + fullName + '\''
                + ", address='" + address + '\''
                + ", phoneNumber='" + phoneNumber + '\''
                + ", email='" + email + '\''
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", staffId='" + staffId + '\''
                + ", managementLevel=" + managementLevel
                + ", branch=" + branch
                + '}';
    }
}
