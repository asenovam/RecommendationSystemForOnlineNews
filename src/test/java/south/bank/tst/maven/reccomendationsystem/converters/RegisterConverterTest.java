/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.converters;

import org.junit.Test;
import south.bank.tst.maven.reccomendationsystem.beans.RegisterBean;
import south.bank.tst.maven.reccomendationsystem.entities.User;

import static org.junit.Assert.*;

/**
 *
 * @author mar_9
 */
public class RegisterConverterTest {
    
    RegisterConverter register = new RegisterConverter();

    @Test
    public void isProfileSuccessfullyCreated() {
        User testUser = new User();
        RegisterBean regBean = new RegisterBean();
        regBean.setEmail("marchelaasenova@icloud.com");
        regBean.setNickname("Chela");
        regBean.setPassword("789456");
        regBean.setFirstName("Marchela");
        regBean.setLastName("Asenova");
        regBean.setGender("Female");
        regBean.setAgeGroup("25");
        
        register.convert(regBean, testUser);

        assertEquals("marchelaasenova@icloud.com", testUser.getEmail());
        assertEquals("Chela", testUser.getNickname());
        assertEquals("789456", testUser.getPassword());
        assertEquals("Marchela", testUser.getFirstName());
        assertEquals("Asenova", testUser.getLastName());
        assertEquals("Female", testUser.getGender());
        assertEquals("25", testUser.getAgeGroup());

    }

}
