package com.example.guessthenumber;

public class Player {

    private String imagePath;
    private String name;
    private int attempts;
    private int time;

    //Constructors
    public Player(){

    }

    public Player(String imagePath, String name, int attempts, int time){

        this.imagePath=imagePath;
        this.name=name;
        this.attempts=attempts;
        this.time=time;

    }

    //Getters and Setters
    public String getImagePath() {

        return imagePath;

    }

    public void setImagePath(String imagePath) {

        this.imagePath = imagePath;

    }

    public String get_name() {

        return name;

    }

    public void set_name(String _name) {

        this.name = name;

    }

    public int getAttempts() {

        return attempts;

    }

    public void setAttempts(int attempts) {

        this.attempts = attempts;

    }

    public int getTime() {

        return time;

    }

    public void setTime(int time) {

        this.time = time;

    }

}


