/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AIS_R_Initial.ModelTest;

/**
 *
 * @author 12217608
 */
import com.AIS_R_Initial.Model.AdministrationStaff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AdministrationStaffTest {
    @Test
    public void testConstructorAndGetters() {
        AdministrationStaff staff = new AdministrationStaff("John", "123 Main St", "1234567890", "john@gmail.com", "A123", "password", "A123", AdministrationStaff.PositionType.FULL_TIME);
        
        Assertions.assertEquals("John", staff.getFullName());
        Assertions.assertEquals("123 Main St", staff.getAddress());
        Assertions.assertEquals("1234567890", staff.getPhoneNumber());
        Assertions.assertEquals("john@gmail.com", staff.getEmail());
        Assertions.assertEquals("A123", staff.getUsername());
        Assertions.assertEquals("password", staff.getPassword());
        Assertions.assertEquals("A123", staff.getStaffId());
        Assertions.assertEquals(AdministrationStaff.PositionType.FULL_TIME, staff.getPositionType());
    }
}
