package com.example.railway_trackerapp;

public class TrainModel {

    private String train_num, name, train_from, train_to;

    public TrainModel(String train_num, String name, String train_from, String train_to) {
        this.train_num = train_num;
        this.name = name;
        this.train_from = train_from;
        this.train_to = train_to;
    }

    public String getTrain_num() {
        return train_num;
    }

    public void setTrain_num(String train_num) {
        this.train_num = train_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrain_from() {
        return train_from;
    }

    public void setTrain_from(String train_from) {
        this.train_from = train_from;
    }

    public String getTrain_to() {
        return train_to;
    }

    public void setTrain_to(String train_to) {
        this.train_to = train_to;
    }
}
