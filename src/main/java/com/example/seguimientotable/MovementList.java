package com.example.seguimientotable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovementList {
    private ObservableList<Movement> students = FXCollections.observableArrayList();
    private static MovementList instance = null;
    public MovementList(){}

    public static MovementList getInstance(){
        if(instance==null){
            instance = new MovementList();
        }
        return instance;
    }
    public ObservableList<Movement> getMovements(){
        return students;
    }
    public void setList(ObservableList<Movement> list){
        this.students = list;
    }
}
