package com.example.androidProject.DigiNews.Entities;

import android.os.Parcel;
import android.os.Parcelable;


public class NewsEntity implements Parcelable{
    private String urlForWebView;
    private String urlImage;
    private String title;
    private String author;
    private String source;
    private String publishedAt;
    private String desc;

    public NewsEntity(){

    }

    public NewsEntity(Parcel input){

        urlForWebView=input.readString();
        urlImage=input.readString();
        title=input.readString();
        author=input.readString();
        source=input.readString();
        publishedAt=input.readString();
        desc=input.readString();
    }

    public String getUrlForWebView() {
        return urlForWebView;
    }

    public void setUrlForWebView(String urlForWebView) {
        this.urlForWebView = urlForWebView;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        //ORDER IS IMP BECAUSE NO KEYS
        dest.writeString(urlForWebView);
        dest.writeString(urlImage);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(source);
        dest.writeString(publishedAt);
        dest.writeString(desc);
    }

    public static final Parcelable.Creator<NewsEntity> CREATOR
            = new Parcelable.Creator<NewsEntity>() {
        public NewsEntity createFromParcel(Parcel in) {
            return new NewsEntity(in);
        }

        public NewsEntity[] newArray(int size) {
            return new NewsEntity[size];
        }
    };

}
