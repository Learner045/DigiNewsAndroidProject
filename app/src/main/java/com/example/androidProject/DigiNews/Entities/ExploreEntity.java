package com.example.androidProject.DigiNews.Entities;


public class ExploreEntity {
    private int ImageID; //explore imageID based on category
    private String categoryText; //categoryname

    public ExploreEntity(int imageID,String text){
        this.ImageID=imageID;
        this.categoryText=text;
    }
    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }
}
