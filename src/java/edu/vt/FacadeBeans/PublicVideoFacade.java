/*
 * Created by Ashish Kotian on 2019.03.31
 * Copyright © 2019 Ashish Kotian. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.PublicVideo;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import edu.vt.EntityBeans.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PublicVideoFacade extends AbstractFacade<PublicVideo> {

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
    variable with the PublicVideo class object reference returned by the PublicVideo.class.
     */
    public PublicVideoFacade() {
        super(PublicVideo.class);
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
    public List<PublicVideo> findAll(Integer primaryKey) {

        return (List<PublicVideo>) em.createNamedQuery("PublicVideo.findAll")
                .getResultList();
    }
    
    public List<PublicVideo> findPublicVideosByParams(String searchString, String categoryLabel) {
        String methodToUse;
        System.out.println("Category Label: " + categoryLabel);
        if (categoryLabel.equals("All")) {
            methodToUse = "PublicVideo.searchAll";
        } else {
            String[] parts = categoryLabel.split(" ");
            methodToUse = "PublicVideo.search" + parts[1];
        }
        
        return (List<PublicVideo>) em.createNamedQuery(methodToUse)
                .setParameter("searchString", '%' + searchString + '%')
                .getResultList();
    }
    
    
    public void createFavouriteEntry(UserVideo userVideo) {
        int id = userVideo.id;
        String title = userVideo.title;
        String description = userVideo.description;
        String youtubeVideoId = userVideo.youtubeVideoId;
        String duration = userVideo.duration;
        Date datePublished = userVideo.datePublished;
        String category = userVideo.category;
        
        em.createNativeQuery("INSERT INTO PublicVideo (title, description, youtube_video_id, duration, date_published, category) VALUES (?, ?, ?, ?, ?, ?)")
            
            .setParameter(1, title)
            .setParameter(2, description)
            .setParameter(3, youtubeVideoId)
            .setParameter(4, duration)
            .setParameter(5, datePublished)
            .setParameter(6, category)
            .executeUpdate();
        
 
                
    }
    
    

    /* The following methods are inherited from the parent AbstractFacade class:
    
        create
        edit
        find
        findAll
        remove
     */
    
}