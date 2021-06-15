package com.example.snltech;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventData {

    private String Adult,Cost,Date,Drinks,Food,Intro,Time,Category,ID,Name,Access;
    private Boolean Music;
    public EventData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public EventData(String Adult, String Cost, String Date, String Drinks, String Food, String Intro, Boolean Music, String Name, String Time, String Category, String ID, String Access) {
        this.Adult =Adult;
        this.Cost =Cost;
        this.Date =Date;
        this.Drinks =Drinks;
        this.Food =Food;
        this.Intro =Intro;
        this.Music =Music;
        this.Name =Name;
        this.Time =Time;
        this.Category =Category;
        this.Access=Access;
        this.ID=ID;
    }

    public String getAccess() {
        return Access;
    }

    public void setAccess(String access) {
        Access = access;
    }

    public String getAdult() {
        return Adult;
    }

    public void setAdult(String adult) {
        Adult = adult;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDrinks() {
        return Drinks;
    }

    public void setDrinks(String drinks) {
        Drinks = drinks;
    }

    public String getFood() {
        return Food;
    }

    public void setFood(String food) {
        Food = food;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    public Boolean getMusic() {
        return Music;
    }

    public void setMusic(Boolean music) {
        Music = music;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String venue) {
        Category = venue;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
