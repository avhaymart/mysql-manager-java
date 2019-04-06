package com.herokuapp.avhaymart.mysqlmanager.models;

public class DbItem {
    private String name;

    public DbItem(String n) {
        this.name = n;
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

    @Override
    public String toString() {
        return "{name:"+this.getName()+"}";
    }

    
}