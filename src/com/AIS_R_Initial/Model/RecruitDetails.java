/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AIS_R_Initial.Model;

//import java.io.Serializable;
//import java.util.Date;
//
///**
// *
// * @author 12217608
// */
//public class RecruitDetails implements Serializable{
//    
//     private static final long serialVersionUID = 1L;
//
//    private String fullName;
//    private String address;
//    private String phoneNumber;
//    private String email;
//    private String username;
//    private String password;
//    private Date interviewDate;
//    private QualificationLevel qualificationLevel; // Enum for qualification level
//
//    // Constructor
//    public RecruitDetails(String fullName, String address, String phoneNumber, String email,
//            String username, String password, Date interviewDate,
//            QualificationLevel qualificationLevel) {
//        this.fullName = fullName;
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.username = username;
//        this.password = password;
//        this.interviewDate = interviewDate;
//        this.qualificationLevel = qualificationLevel;
//    }
//
//    // Getters and setters
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Date getInterviewDate() {
//        return interviewDate;
//    }
//
//    public void setInterviewDate(Date interviewDate) {
//        this.interviewDate = interviewDate;
//    }
//
//    public QualificationLevel getQualificationLevel() {
//        return qualificationLevel;
//    }
//
//    public void setQualificationLevel(QualificationLevel qualificationLevel) {
//        this.qualificationLevel = qualificationLevel;
//    }
//
//    // Enum for qualification level
//    public enum QualificationLevel {
//        BACHELORS,
//        MASTERS,
//        PHD
//    }
//
//    // Override toString() method
//    @Override
//    public String toString() {
//        return "Recruit{"
//                + "fullName='" + fullName + '\''
//                + ", address='" + address + '\''
//                + ", phoneNumber='" + phoneNumber + '\''
//                + ", email='" + email + '\''
//                + ", username='" + username + '\''
//                + ", password='" + password + '\''
//                + ", interviewDate=" + interviewDate
//                + ", qualificationLevel=" + qualificationLevel
//                + '}';
//    }
//}



import java.io.Serializable;
import java.util.Date;

public class RecruitDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    //private int id; // New ID field
    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private Date interviewDate;
    private QualificationLevel qualificationLevel;

    public RecruitDetails(String fullName, String address, String phoneNumber, String email,
                          String username, String password, Date interviewDate, QualificationLevel qualificationLevel) {
        //this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.interviewDate = interviewDate;
        this.qualificationLevel = qualificationLevel;
    }

    // Getters and setters
//    public int getId() {
//        return id;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }

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

    public enum QualificationLevel {
        BACHELORS,
        MASTERS,
        PHD
    }
}
