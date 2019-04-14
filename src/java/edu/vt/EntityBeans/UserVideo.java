/*
 * Created by Ashish Kotian on 2019.03.20  * 
 * Copyright © 2019 Ashish Kotian. All rights reserved. * 
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.SimpleDateFormat;

/**
 *
 * @author ashishkotian
 */
@Entity
@Table(name = "UserVideo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserVideo.findAll", query = "SELECT p FROM UserVideo p")
    , @NamedQuery(name = "UserVideo.searchAll", query = "SELECT p FROM UserVideo p WHERE p.user_id = :userId AND (p.title LIKE :searchString OR p.description LIKE :searchString OR p.category LIKE :searchString)")
    , @NamedQuery(name = "UserVideo.findById", query = "SELECT p FROM UserVideo p WHERE p.id = :id")
    , @NamedQuery(name = "UserVideo.findByUserId", query = "SELECT p FROM UserVideo p WHERE p.user_id = :user_id")
    , @NamedQuery(name = "UserVideo.findByTitle", query = "SELECT p FROM UserVideo p WHERE p.title = :title")
    , @NamedQuery(name = "UserVideo.searchTitle", query = "SELECT p FROM UserVideo p WHERE p.user_id = :userId AND (p.title LIKE :searchString)")
    , @NamedQuery(name = "UserVideo.findByDescription", query = "SELECT p FROM UserVideo p WHERE p.description = :description")
    , @NamedQuery(name = "UserVideo.searchDescription", query = "SELECT p FROM UserVideo p WHERE p.user_id = :userId AND (p.description LIKE :searchString)")
    , @NamedQuery(name = "UserVideo.findByYoutubeVideoId", query = "SELECT p FROM UserVideo p WHERE p.youtubeVideoId = :youtubeVideoId")
    , @NamedQuery(name = "UserVideo.findByDuration", query = "SELECT p FROM UserVideo p WHERE p.duration = :duration")
    , @NamedQuery(name = "UserVideo.findByDatePublished", query = "SELECT p FROM UserVideo p WHERE p.datePublished = :datePublished")
    , @NamedQuery(name = "UserVideo.searchCategory", query = "SELECT p FROM UserVideo p WHERE p.user_id = :userId AND (p.category LIKE :searchString)")
    , @NamedQuery(name = "UserVideo.findByCategory", query = "SELECT p FROM UserVideo p WHERE p.category = :category")})
public class UserVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    public Integer id;
    @Basic(optional = false)
    @Column(name = "user_id")
    public Integer user_id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "title")
    public String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "description")
    public String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "youtube_video_id")
    public String youtubeVideoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "duration")
    public String duration;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_published")
    @Temporal(TemporalType.DATE)
    public Date datePublished;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "category")
    public String category;

    public UserVideo() {
    }

    public UserVideo(Integer id) {
        this.id = id;
    }

    public UserVideo(Integer id, Integer user_id, String title, String description, String youtubeVideoId, String duration, Date datePublished, String category) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.youtubeVideoId = youtubeVideoId;
        this.duration = duration;
        this.datePublished = datePublished;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    
    public Date getDatePublished() {
        return datePublished;
    }
    
    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserVideo)) {
            return false;
        }
        UserVideo other = (UserVideo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.UserVideo[ id=" + id + " ]";
    }
    
}