package com.herokuapp.avhaymart.mysqlmanager.models;

public class Connection {
    private String user, password, database, host;

    public Connection(String u, String p, String d, String h) {
        this.user = u;
        this.password = p;
        this.database = d;
        this.host = h;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "{\"user\":\""+this.getUser()+"\",\"password\":\""+this.getPassword()+"\",\"database\":\""+this.getDatabase()+"\",\"host\":\""+this.getHost()+"\"}";
    }

    
    
}