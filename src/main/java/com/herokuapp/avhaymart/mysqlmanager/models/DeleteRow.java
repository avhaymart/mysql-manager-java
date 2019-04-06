package com.herokuapp.avhaymart.mysqlmanager.models;

import java.util.ArrayList;
import java.util.Arrays;

public class DeleteRow {
    private ArrayList<String> rowIndex = new ArrayList<String>();
    private String tableName;

    public DeleteRow(String[] s, String t) {
        this.rowIndex = new ArrayList<String>(Arrays.asList(t));
        this.tableName = t;
    }

    /**
     * @return the rowIndex
     */
    public ArrayList<String> getRowIndex() {
        return rowIndex;
    }

    public String getRowIndex(int i) {
        return rowIndex.get(i);
    }

    /**
     * @param rowIndex the rowIndex to set
     */
    public void setRowIndex(ArrayList<String> rowIndex) {
        this.rowIndex = rowIndex;
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
}