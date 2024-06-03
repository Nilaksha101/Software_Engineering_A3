/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AIS_R_Initial.ModelTest;

import com.AIS_R_Initial.Model.ManagementStaff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author 12217608
 */
public class ManagementStaffTest {
   @Test
    public void testConstructorAndGetters() {
        ManagementStaff managementStaff = new ManagementStaff("John", "123 Main St", "1234567890",
                "john@gmaile.com", "Mjohn", "password", "M123",
                ManagementStaff.ManagementLevel.SENIOR_MANAGER, ManagementStaff.Branch.MELBOURNE);

        Assertions.assertEquals("John", managementStaff.getFullName());
        Assertions.assertEquals("123 Main St", managementStaff.getAddress());
        Assertions.assertEquals("1234567890", managementStaff.getPhoneNumber());
        Assertions.assertEquals("john@gmail.com", managementStaff.getEmail());
        Assertions.assertEquals("Mjohn", managementStaff.getUsername());
        Assertions.assertEquals("password", managementStaff.getPassword());
        Assertions.assertEquals("M123", managementStaff.getStaffId());
        Assertions.assertEquals(ManagementStaff.ManagementLevel.SENIOR_MANAGER, managementStaff.getManagementLevel());
        Assertions.assertEquals(ManagementStaff.Branch.MELBOURNE, managementStaff.getBranch());
    }  
}
