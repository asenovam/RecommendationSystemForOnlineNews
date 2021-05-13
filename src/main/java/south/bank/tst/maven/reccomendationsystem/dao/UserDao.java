/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import south.bank.tst.maven.reccomendationsystem.entities.User;

/**
 *
 * @author mar_9
 */
public interface UserDao extends MongoRepository<User, String> {

    public User findByNickname(String nickname);

    public User findByEmail(String email);

}
