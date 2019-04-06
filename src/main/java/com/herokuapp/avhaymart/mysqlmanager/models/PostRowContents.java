
package com.herokuapp.avhaymart.mysqlmanager.models;

import java.util.ArrayList;

public class PostRowContents {
    private ArrayList<ArrayList<String>> fields = new ArrayList<ArrayList<String>>();
    private ArrayList<String> input = new ArrayList<String>();
    private String name;

    PostRowContents(ArrayList<ArrayList<String>> fields, ArrayList<String> input, String name) {
        this.fields = fields;
        this.input = input;
        this.name = name;
    }

    /**
     * @return the fields
     */
    public ArrayList<ArrayList<String>> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(ArrayList<ArrayList<String>> fields) {
        this.fields = fields;
    }

    /**
     * @return the input
     */
    public ArrayList<String> getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(ArrayList<String> input) {
        this.input = input;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}