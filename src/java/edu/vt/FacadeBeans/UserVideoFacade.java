/*
 * Created by Ashish Kotian on 2019.03.31
 * Copyright © 2019 Ashish Kotian. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.PublicVideo;
import edu.vt.EntityBeans.UserVideo;
import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserVideoFacade extends AbstractFacade<UserVideo> {

    @PersistenceContext(unitName = "Videos-KotianPU")
    private EntityManager em;

    // @Override annotation indicates that the super class AbstractFacade's getEntityManager() method is overridden.
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /* 
    This constructor method invokes the parent abstract class AbstractFacade.java's
    constructor method AbstractFacade, which in turn initializes its entityClass instance
    variable with the UserVideo class object reference returned by the UserVideo.class.
     */
    public UserVideoFacade() {
        super(UserVideo.class);
    }
    
    /*
    ====================================================
    The following method is added to the generated code.
    ====================================================
     */
    /**
     * @param primaryKey is the Primary Key of the User entity in a table row in the database.
     * @return a list of photos associated with the User whose primary key is primaryKey
     */
    public List<UserVideo> findAll(Integer user_id) {

        return (List<UserVideo>) em.createNamedQuery("UserVideo.findByUserId")
                .setParameter("user_id", user_id)
                .getResultList();
    }
    
    public List<UserVideo> findUserVideosByParams(String searchString, String categoryLabel, int userId) {
        String methodToUse;
        System.out.println("Category Label: " + categoryLabel);
        if (categoryLabel.equals("All")) {
            methodToUse = "UserVideo.searchAll";
        } else {
            String[] parts = categoryLabel.split(" ");
            methodToUse = "UserVideo.search" + parts[1];
        }
        return (List<UserVideo>) em.createNamedQuery(methodToUse)
                .setParameter("searchString", '%' + searchString + '%')
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public void createFavouriteEntry(int userId, PublicVideo publicVideo) {
        int user_id = userId;
        int id = publicVideo.id;
        String title = publicVideo.title;
        String description = publicVideo.description;
        String youtubeVideoId = publicVideo.youtubeVideoId;
        String duration = publicVideo.duration;
        Date datePublished = publicVideo.datePublished;
        String category = publicVideo.category;
        
        em.createNativeQuery("INSERT INTO UserVideo (id, user_id, title, description, youtube_video_id, duration, date_published, category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
            .setParameter(2, user_id)
            .setParameter(3, title)
            .setParameter(4, description)
            .setParameter(5, youtubeVideoId)
            .setParameter(6, duration)
            .setParameter(7, datePublished)
            .setParameter(8, category)
            .executeUpdate();
        
        /*
        em.createNativeQuery("UserVideo.createEntry")
                .setParameter("id", id)
                .setParameter("user_id", user_id)
                .setParameter("title", title)
                .setParameter("description", description)
                .setParameter("youtube_video_id", youtubeVideoId)
                .setParameter("duration", duration)
                .setParameter("date_published", datePublished)
                .setParameter("category", category)
                .executeUpdate();
        */
                
    }

    /* The following methods are inherited from the parent AbstractFacade class:
    
        create
        edit
        find
        findAll
        remove
     */
    
}