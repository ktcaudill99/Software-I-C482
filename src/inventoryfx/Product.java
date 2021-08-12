/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Katherine Caudill
 */
public class Product {
    private int productID;
    private String productName;
    private int productLevel;
    private double productCost;
    private int productMax;
    private int productMin;

    /**
     *
     */
    public ObservableList<Part> parts = FXCollections.observableArrayList();;

    /**
     *
     */
    public Product(){
        
        this.productID = productID;
        this.productName = productName;
        this.productLevel = productLevel;
        this.productCost = productCost;
        this.productMax = productMax;
        this.productMin = productMin;
       // this.linkedParts = linkedParts;
    }

    /**
     *
     * @return
     */
    public int getProductID() {
        return productID;
    }

    /**
     *
     * @param productID
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     *
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     */
    public int getProductLevel() {
        return productLevel;
    }

    /**
     *
     * @param productLevel
     */
    public void setProductLevel(int productLevel) {
        this.productLevel = productLevel;
    }

    /**
     *
     * @return
     */
    public double getProductCost() {
        return productCost;
    }

    /**
     *
     * @param productCost
     */
    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }
    
    /**
     *
     * @return
     */
    public int getProductMax() {
        return productMax;
    }

    /**
     *
     * @param productMax
     */
    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    /**
     *
     * @return
     */
    public int getProductMin() {
        return productMin;
    }

    /**
     *
     * @param productMin
     */
    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }
    
    /**
     *
     * @return
     */
    public ObservableList getAllLinkedParts() {
        return parts;
    }

    /**
     *
     * @param parts
     */
    public void setAllLinkedParts (ObservableList<Part> parts ){
        this.parts = parts;
    }
     
}
