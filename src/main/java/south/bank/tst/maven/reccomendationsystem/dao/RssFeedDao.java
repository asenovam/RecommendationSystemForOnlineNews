/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package south.bank.tst.maven.reccomendationsystem.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import south.bank.tst.maven.reccomendationsystem.entities.RssFeed;

/**
 *
 * @author mar_9
 */
public interface RssFeedDao extends MongoRepository<RssFeed, String> {

    public List<RssFeed> findByUrl(String url);

    public List<RssFeed> findByUid(long uid);

}
