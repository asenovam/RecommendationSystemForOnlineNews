/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.converters;

import java.util.UUID;
import org.springframework.stereotype.Component;
import south.bank.tst.maven.reccomendationsystem.beans.RegisterBean;
import south.bank.tst.maven.reccomendationsystem.entities.User;

/**
 *
 * @author mar_9
 */
@Component
public class RegisterConverter {
    
    public void convert(RegisterBean registerBean, User user) {
        user.setEmail(registerBean.getEmail());
        user.setNickname(registerBean.getNickname());
        user.setPassword(registerBean.getPassword());
        user.setFirstName(registerBean.getFirstName());
        user.setLastName(registerBean.getLastName());
        user.setGender(registerBean.getGender());
        user.setAgeGroup(registerBean.getAgeGroup());
        
        long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        user.setUid(id);
    }
}
