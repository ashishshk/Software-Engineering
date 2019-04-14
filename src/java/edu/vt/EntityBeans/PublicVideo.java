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
@Table(name = "PublicVideo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicVideo.findAll", query = "SELECT p FROM PublicVideo p")
    , @NamedQuery(name = "PublicVideo.searchAll", query = "SELECT p FROM PublicVideo p WHERE (p.title LIKE :searchString OR p.description LIKE :searchString OR p.category LIKE :searchString)")
    , @NamedQuery(name = "PublicVideo.findById", query = "SELECT p FROM PublicVideo p WHERE p.id = :id")
    , @NamedQuery(name = "PublicVideo.findByTitle", query = "SELECT p FROM PublicVideo p WHERE p.title = :title")
    , @NamedQuery(name = "PublicVideo.searchTitle", query = "SELECT p FROM PublicVideo p WHERE (p.title LIKE :searchString)")
    , @NamedQuery(name = "PublicVideo.findByDescription", query = "SELECT p FROM PublicVideo p WHERE p.description = :description")
    , @NamedQuery(name = "PublicVideo.searchDescription", query = "SELECT p FROM PublicVideo p WHERE (p.description LIKE :searchString)")
    , @NamedQuery(name = "PublicVideo.findByYoutubeVideoId", query = "SELECT p FROM PublicVideo p WHERE p.youtubeVideoId = :youtubeVideoId")
    , @NamedQuery(name = "PublicVideo.findByDuration", query = "SELECT p FROM PublicVideo p WHERE p.duration = :duration")
    , @NamedQuery(name = "PublicVideo.findByDatePublished", query = "SELECT p FROM PublicVideo p WHERE p.datePublished = :datePublished")
    , @NamedQuery(name = "PublicVideo.searchCategory", query = "SELECT p FROM PublicVideo p WHERE (p.category LIKE :searchString)")
    , @NamedQuery(name = "PublicVideo.findByCategory", query = "SELECT p FROM PublicVideo p WHERE p.category = :category")})
public class PublicVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    public Integer id;
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

    public PublicVideo() {
    }

    public PublicVideo(Integer id) {
        this.id = id;
    }

    public PublicVideo(Integer id, String title, String description, String youtubeVideoId, String duration, Date datePublished, String category) {
        this.id = id;
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
        if (!(object instanceof PublicVideo)) {
            return false;
        }
        PublicVideo other = (PublicVideo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.PublicVideo[ id=" + id + " ]";
    }
    
}