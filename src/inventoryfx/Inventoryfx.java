/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryfx;

import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Katherine Caudill
 */
public class Inventoryfx {

    /**
     *
     */
    public static Part selectedPart;

    /**
     *
     */
    public static Product selectedProduct;
    private static int partIDCount = 0;
    private static int productIDCount = 0;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList(
            
     /** preloaded parts (partID (autofill), partName, Inventory/Stock level(Int(0)), cost/price(double(00.00)), --> 
            Min inventory/stock level(Int(0)), Max inventory/stock level(Int(0)), CompanyName(OutSourced)/MachineNumber(InHouse))
            */
        new OutSourced(1, "ospart1", 11, 23, 0 , 50, "os1"),
        new OutSourced(2, "ospart2", 12, 26, 0, 55, "os2"),
        new OutSourced(3, "ospart3", 13, 14, 0, 60, "os3"),
        new InHouse(4, "ihpart1", 21, 12, 0, 30,2),
        new InHouse(5, "ihpart2", 22, 8, 0, 32, 1),
        new InHouse(6, "ihpart3", 23, 2, 0, 38, 2),
        new InHouse(7, "ihpart4", 24, 6, 0, 40, 1)
);
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList(
             
         //   new Product()
            
    );

    /**
     *
     */
    public Inventoryfx() {
    }

    /**
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     *
     * @return
     */
    public static int getProductIDCount() {
        productIDCount++;
        return productIDCount;
    }

    /**
     *
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId) {
        for (int i = 0; i < Inventoryfx.getAllParts().size(); i++) {
            Part p = Inventoryfx.getAllParts().get(i);

            // Returns first match
            if (p.getId() == partId)
                return p;
        }
        return null;

    }

    /**
     *
     * @param productID
     * @return
     */
    public static Product lookupProduct(int productID) {
        for (int i = 0; i < Inventoryfx.getAllProducts().size(); i++) {
            Product p = Inventoryfx.getAllProducts().get(i);

            // Returns first match
            if (p.getProductID() == productID)
                return p;
        }
        return null;
    }

    /**
     *
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
      /*
        ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();
        for (Part p : allParts) {
            if (partName.compareTo(p.getName()) == 0) {
                allFilteredParts.add(p);
            }
        }

        return allFilteredParts;
   */
      
      
      ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();

        // Searches for matching characters, then adds to list
        for (Part p : Inventoryfx.getAllParts()) {
            if (p.getName().contains(partName))
                allFilteredParts.add(p);
        }

        // Searches for integers, then adds to list
        try {
            int idPart = Integer.parseInt(partName);
            Part p = Inventoryfx.lookupPart(idPart);
            if (p != null)
                allFilteredParts.add(p);
        } catch (NumberFormatException e) {
            // ignore exception
        }

        return allFilteredParts;
        
        
        }

    /**
     *
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
       ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();

        // Searches for matching characters, then adds to list
        for (Product p : Inventoryfx.getAllProducts()) {
            if (p.getProductName().contains(productName))
                allFilteredProducts.add(p);
        }

        // Searches for integers, then adds to list
        try {
            int idProduct = Integer.parseInt(productName);
            Product p = Inventoryfx.lookupProduct(idProduct);
            if (p != null)
                allFilteredProducts.add(p);
        } catch (NumberFormatException e) {
            // ignore exception
        }

        return allFilteredProducts;
    }

    /**
     *
     * @param oldPartIndex
     * @param selectedPart
     */
    public static void updatePart(int oldPartIndex, Part selectedPart) {
        allParts.set(oldPartIndex -1, selectedPart);
    }

    /**
     *
     * @param oldProductIndex
     * @param product
     */
    public static void updateProduct(int oldProductIndex, Product product) {
        allProducts.set(oldProductIndex -1, product);
    }

    /**
     *
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        
        int id = selectedPart.getId();
        Part lookupPart = lookupPart(id);
        return allParts.remove(lookupPart);
        
    }
    
    /**
     *
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        int id = selectedProduct.getProductID();
        Product lookupProduct = lookupProduct(id);
        return allProducts.remove(lookupProduct);
    }

    /**
     *
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    /**
     *
     * @param strNum
     * @return
     */
    public static boolean isNumeric(String strNum) {
       if (strNum == null){
           return false;
       }
       try {
           double d = Double.parseDouble(strNum);
           
       } catch (NumberFormatException nfe){
           return false;
       }
       return true;
   }
    
       
}
