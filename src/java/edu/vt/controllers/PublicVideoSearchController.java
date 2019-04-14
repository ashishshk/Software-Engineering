/*
 * Created by Ashish Kotian on 2019.03.01
 * Copyright © 2019 Ashish Kotian. All rights reserved.
 */
package edu.vt.controllers;
import edu.vt.globals.Methods;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import edu.vt.EntityBeans.*;
import edu.vt.FacadeBeans.*;
import java.util.*;
import java.util.ArrayList;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.PrimeFaces;
@SessionScoped
@Named(value = "publicVideoSearchController")
/**
 *
 * @author ajitsarkaar
 */
//This file manages the search operation and search results carried out by making
//calls to the API, recieving a JSON response, parsing it, and creating objects to
//store these attributes, finally putting them in a list.

public class PublicVideoSearchController implements Serializable {
    
    private String searchString;
    private String categoryLabel;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        StringBuilder sb = new StringBuilder(searchString);
        for(int i = 0;i<searchString.length();i++){
            if(searchString.charAt(i) == ' '){
                sb.setCharAt(i, '+');
            }
        }
        this.searchString = sb.toString();
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    @EJB
    private PublicVideoFacade publicVideoFacade;

    public PublicVideoFacade getPublicVideoFacade() {
        return publicVideoFacade;
    }

    // The object that will be returned by the API call.
    public class PublicVideoObj{
        private int id;
        private String title;
        private String thumbnail;
        private String description;
        private String duration;
        private Date datePublished;
        private String category;

        

        public PublicVideoObj() {
            id = 0;
            title = "";
            thumbnail = "";
            description = "";
            duration = "";
            datePublished = new Date();
            category = "";
        }

        

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
        
    }

    public PublicVideoObj getSelected() {
        return selected;
    }
    private PublicVideoObj selected;
    public void selection(PublicVideoObj selected){
        this.selected = selected;
        
        
    }
    public List<PublicVideo> getItems() {
        return items;
    }

    public void setItems(List<PublicVideo> items) {
        this.items = items;
    }
    
    private List<PublicVideo> items = new ArrayList<>();
    
    
    // Performing the API call to search for the PublicVideo
    public void performSearch(){
        
        Methods.preserveMessages();
        try {
            // Obtain the JSON response from the searchApiUrl
            List<PublicVideo> results = getPublicVideoFacade().findPublicVideosByParams(searchString, categoryLabel);    
            
            System.out.println("Results: ");
            System.out.println(results);
            
            items = results;

        }
        
        catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The SQLprovides no data for the search query entered!");
            
            
        }
        
        
        
    }
    
    
    
    
    
    
    
    
}