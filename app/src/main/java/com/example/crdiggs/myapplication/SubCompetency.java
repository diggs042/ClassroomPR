package com.example.crdiggs.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class SubCompetency implements Serializable {

    private String competency;
    private String name;
    private ArrayList<String> definitions = new ArrayList<>();

    public SubCompetency(String competency){
        this.competency = competency;
    }

    public String getCompetency() {
        return competency;
    }

    public void setCompetency(String competency) {
        this.competency = competency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<String> definitions) {
        this.definitions = definitions;
    }
}
