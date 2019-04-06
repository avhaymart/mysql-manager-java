package com.herokuapp.avhaymart.mysqlmanager.models;

import java.util.ArrayList;

public class PostTable {
    private String tableName;
    private ArrayList<TableRow> rows = new ArrayList<TableRow>();

    PostTable(String t, ArrayList<TableRow> r) {
        this.tableName = t;
        this.rows = r;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the rows
     */
    public ArrayList<TableRow> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(ArrayList<TableRow> rows) {
        this.rows = rows;
    }

    
}