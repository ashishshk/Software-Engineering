/*
 * Created by Ashish Kotian on 2019.03.31  * 
 * Copyright © 2019 Ashish Kotian. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.PublicVideo;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.*;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.*;
import edu.vt.globals.Methods;

import java.io.Serializable;
import java.util.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("userVideoController")
@SessionScoped
public class UserVideoController implements Serializable {

    @EJB
    private edu.vt.FacadeBeans.UserVideoFacade ejbFacade;
    private List<UserVideo> items = null;
    private UserVideo selected;
    
    private String tableTitle = "My List of Favorite YouTube Videos";
    
    public String getTableTitle() {
        return tableTitle;
    }
    
    public void setTableTitle(String s) {
        tableTitle = s;
    }
    
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
    
    
    @EJB
    private UserVideoFacade userVideoFacade;

    public UserVideoFacade getUserVideoFacade() {
        return userVideoFacade;
    }
    
    public UserVideoController() {
    }

    public UserVideo getSelected() {
        return selected;
    }

    public void setSelected(UserVideo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserVideoFacade getFacade() {
        return ejbFacade;
    }

    public UserVideo prepareCreate() {
        selected = new UserVideo();
        initializeEmbeddableKey();
        
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserVideoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserVideoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserVideoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UserVideo> getItems() {
        if (items == null) {
//            items = getFacade().findAll();
            User signedInUser = (User) Methods.sessionMap().get("user");
            int user_id = signedInUser.getId();
            items = getFacade().findAll(user_id);
        }
        
        return items;
    }
    
    private Boolean isSearchResults = false;
    
    public Boolean isSearchResultsMethod() {
        return isSearchResults;
    }
    
    private List<UserVideo> oldItems;
    
    public void goBackPressed() {
        isSearchResults = false;
        items = oldItems;
        tableTitle = "My List of Favorite YouTube Videos";
    }
    
    
    public void share() {
        getPublicVideoFacade().createFavouriteEntry(this.selected); 
        JsfUtil.addSuccessMessage("Public Video was successfully created.");
    }
    
    public void setNull() {
        items = null;
    }
    
    public void performSearch(){
        
        try {
            User signedInUser = (User) Methods.sessionMap().get("user");
            int user_id = signedInUser.getId();
            // Obtain the JSON response from the searchApiUrl
            List<UserVideo> results = getUserVideoFacade().findUserVideosByParams(searchString, categoryLabel, user_id);    
            
            System.out.println("Results: ");
            System.out.println(results);
            
            oldItems = items;
            items = results;
            isSearchResults = true;
            tableTitle = "Search Results for My Favorite YouTube Videos";

        }
        
        catch (Exception e) {
            Methods.showMessage("Fatal Error", "Unrecognized Search Query!",
                    "The SQLprovides no data for the search query entered!");
            
            
        }
        
        
        
    }
    
    

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    User signedInUser = (User) Methods.sessionMap().get("user");
                    int user_id = signedInUser.getId();
                    selected.user_id = user_id;
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public UserVideo getUserVideo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<UserVideo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UserVideo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UserVideo.class)
    public static class UserVideoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserVideoController controller = (UserVideoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userVideoController");
            return controller.getUserVideo(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserVideo) {
                UserVideo o = (UserVideo) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UserVideo.class.getName()});
                return null;
            }
        }

    }

}
