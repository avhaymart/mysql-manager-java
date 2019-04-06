package com.herokuapp.avhaymart.mysqlmanager.models;

import java.util.ArrayList;

public class PostRow {
    private PostRowContents data;

    PostRow(ArrayList<ArrayList<String>> fields, ArrayList<String> input, String name){
        this.data = new PostRowContents(fields, input, name);
    }


    /**
     * @return the data
     */
    public PostRowContents getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(PostRowContents data) {
        this.data = data;
    }
}
