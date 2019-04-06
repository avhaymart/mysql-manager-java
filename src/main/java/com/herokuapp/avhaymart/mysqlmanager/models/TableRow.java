package com.herokuapp.avhaymart.mysqlmanager.models;

public class TableRow {
    private boolean dataType;
    private String dataTypePretty;
    private String itemName;

    TableRow(boolean d, String p, String i) {
        this.dataType = d;
        this.dataTypePretty = p;
        this.itemName = i;
    }

    /**
     * @return the dataType
     */
    public boolean isDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(boolean dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the dataTypePretty
     */
    public String getDataTypePretty() {
        return dataTypePretty;
    }

    /**
     * @param dataTypePretty the dataTypePretty to set
     */
    public void setDataTypePretty(String dataTypePretty) {
        this.dataTypePretty = dataTypePretty;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
}