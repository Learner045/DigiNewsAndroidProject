package com.example.androidProject.DigiNews.DatabaseWork;

import java.util.Date;

import io.realm.RealmObject;


public class NewsEntityRealm extends RealmObject {
    private String urlForWebViewRealm;
    private String urlImageRealm;
    private String titleRealm;
    private String authorRealm;
    private String sourceRealm;
    private Date publishedAtRealm;
    private String descRealm;

    public String getUrlForWebViewRealm() {
        return urlForWebViewRealm;
    }

    public void setUrlForWebViewRealm(String urlForWebViewRealm) {
        this.urlForWebViewRealm = urlForWebViewRealm;
    }

    public String getUrlImageRealm() {
        return urlImageRealm;
    }

    public void setUrlImageRealm(String urlImageRealm) {
        this.urlImageRealm = urlImageRealm;
    }

    public String getTitleRealm() {
        return titleRealm;
    }

    public void setTitleRealm(String titleRealm) {
        this.titleRealm = titleRealm;
    }

    public String getAuthorRealm() {
        return authorRealm;
    }

    public void setAuthorRealm(String authorRealm) {
        this.authorRealm = authorRealm;
    }

    public String getSourceRealm() {
        return sourceRealm;
    }

    public void setSourceRealm(String sourceRealm) {
        this.sourceRealm = sourceRealm;
    }

    public Date getPublishedAtRealm() {
        return publishedAtRealm;
    }

    public void setPublishedAtRealm(Date publishedAtRealm) {
        this.publishedAtRealm = publishedAtRealm;
    }

    public String getDescRealm() {
        return descRealm;
    }

    public void setDescRealm(String descRealm) {
        this.descRealm = descRealm;
    }
}
