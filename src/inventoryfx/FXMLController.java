/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import inventoryfx.InHouse;
import inventoryfx.Inventoryfx;
import inventoryfx.OutSourced;
import inventoryfx.Part;
import inventoryfx.Product;
import inventoryfx.*;
import javafx.scene.Node;
import javafx.collections.transformation.FilteredList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Katherine Caudill
 */

// Main/First window controller class
public class FXMLController implements Initializable {

    public static int makePartId;
    public static int makeProductId;
    
    @FXML
    private TableView<Product> tvProducts = new TableView<Product>(Inventoryfx.getAllProducts());
    @FXML
    private TableColumn<Product, Integer> colProductID;
    @FXML
    private TableColumn<Product, String> colProductName;
    @FXML
    private TableColumn<Product, Integer> colProductInvLavel;
    @FXML
    private TableColumn<Product, Double> colProductPrice;
    @FXML
    private TableView<Part> tvParts = new TableView<Part>(Inventoryfx.getAllParts());
    @FXML
    private TableColumn<Part, Integer> colPartID;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, Integer> colPartInvLavel;
    @FXML
    private TableColumn<Part, Double> colPartPrice;
    @FXML
    private TextField tfProductSearch;
    @FXML
    private TextField tfPartSearch;
    @FXML
    private Button btnPartAdd;
    @FXML
    private Button btnPartMod;
    @FXML
    private Button btnPartDel;
    @FXML
    private Button btnProductAdd;
    @FXML
    private Button btnProductMod;
    @FXML
    private Button btnProductDel;
    @FXML
    private Button btnExit;

    Part partSelected = null;
    Product productSelected = null;
    @FXML
    private Button btnProductClear;
    @FXML
    private Button btnPartClear;
    
    /**
     * Initializes the controller class.
     */
   
    
    /**
     * searches part based on id or name
     * could use work
     * if part does not exist it does not populate all items
     * must hit enter to clear that
     * 
     */
     @FXML
    void searchPart(ActionEvent event) {
        
    
         
          ObservableList<Part> partFilteredList = Inventoryfx.lookupPart(tfPartSearch.getText());
        tvParts.setItems(partFilteredList);

        // Highlight if only a single row is filtered
        if (partFilteredList.size() == 1) {
            tvParts.getSelectionModel().select(partFilteredList.get(0));
        } else if (partFilteredList.size() == 0) {
            // Provide error message in UI if no results from search
           Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Parts Found");
                    alert.setHeaderText("Please Search Again");
                    alert.setContentText("Can not find part.\nPlease hit clear to refresh the table and try again.");
                    alert.showAndWait();
        } else {
            // Clear error message label if search field is simply blank
            System.out.println("Number Format Exception");
            tvParts.getSelectionModel().clearSelection();
        }
        
        }

        
    
    /**
     *
     * @param s
     * @return
     * used to help search name
     */
    public FilteredList<Part> searchParts(String s) {
        return Inventoryfx.getAllParts().filtered(p -> p.getName().toLowerCase().contains(s.toLowerCase()));
    }

    /**
     * searches part based on id or name
     * if product does not exist it does not populate all items
     * must hit enter to clear that
     */
    @FXML
    void searchProduct(ActionEvent event) {
        
          ObservableList<Product> productFilteredList = Inventoryfx.lookupProduct(tfProductSearch.getText());
        tvProducts.setItems(productFilteredList);

       
        if (productFilteredList.size() == 1) {
            tvProducts.getSelectionModel().select(productFilteredList.get(0));
        } else if (productFilteredList.size() == 0) {
            // Provide error message in UI if no results from search
           Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("No Parts Found");
                    alert.setHeaderText("Please Search Again");
                    alert.setContentText("Can not find product.\nPlease hit clear to refresh the table and try again.");
                    alert.showAndWait();
        } else {
            // Clear error message label if search field is simply blank
            System.out.println("Number Format Exception");
            tvProducts.getSelectionModel().clearSelection();
        }
        
       
    }
    
    /**
     *
     * @param s
     * @return
     * used to help search name
     */
    public FilteredList<Product> searchProducts(String s) {
        return Inventoryfx.getAllProducts().filtered(p -> p.getProductName().toLowerCase().contains(s.toLowerCase()));
    }
   
   /**
     * sends user to add part screen
     */
    @FXML
    void addPartAction(ActionEvent screenAddPart) throws IOException {
   
        Stage stage;
        Parent root;
        stage = (Stage) btnPartAdd.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AddPartFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

       
    }
    
     /**
      * checks to see if part is selected
     * if selected sends user to modify part screen
     * else thows error to select part
     */
    @FXML
    void modifyPartAction(ActionEvent screenModifyPart) throws IOException {
        if (tvParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a part to modify");
            alert.showAndWait();

        }
        else {

            Part selectedItem = tvParts.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem.getName());
            ModifyPartFXMLController.part = selectedItem;


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventoryfx/ModifyPartFXML.fxml"));
                Parent addPartScreen = loader.load();
                Scene addPartScene = new Scene(addPartScreen);
                Stage addPart = (Stage)((Node)screenModifyPart.getSource()).getScene().getWindow();
                addPart.setScene(addPartScene);
                addPart.show();
            }
            catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }
    
     /**
      * checks to see if part is selected
     * if selected deletes part after confirm
     * else thows error to select part
     */
    
    @FXML
    void deletePartAction(ActionEvent deletePart) {
        try {
            Inventoryfx.lookupPart(((Part)tvParts.getSelectionModel().getSelectedItem()).getId());
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete part?", new ButtonType[0]);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventoryfx.deletePart((Part)tvParts.getSelectionModel().getSelectedItem());
                tvParts.setItems(Inventoryfx.getAllParts());
            }
        } catch (NullPointerException var4) {
            Alert alert = new Alert(AlertType.ERROR, "Please select a part.", new ButtonType[0]);
            alert.setTitle("Error Dialog");
            alert.showAndWait();
        }

     
    }
    
    /**
     * sends user to add product screen
     */
    @FXML
    void addProductAction(ActionEvent screenAddproduct) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) btnProductAdd.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("AddProductFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
      * checks to see if Product is selected
     * if selected sends user to modify Product screen
     * else thows error to select part
     */
     @FXML
    void modifyProductAction(ActionEvent screenModifyProduct) throws IOException {
         if (tvProducts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a product to modify");
            alert.showAndWait();

        }
        else {

            Product selectedItem = tvProducts.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem.getProductName());

            ModifyProductFXMLController.product = selectedItem;

            try {
                Stage stage;
                Parent root;
                 stage = (Stage) btnProductMod.getScene().getWindow();
                 root = FXMLLoader.load(getClass().getResource("ModifyProductFXML.fxml"));
                 Scene scene = new Scene(root);
                 stage.setScene(scene);
               stage.show();
            }
            catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

    }
    
    /**
      * checks to see if Product is selected
     * if selected deletes Product after confirm
     * else thows error to select Product
     */
    @FXML
    void deleteProductAction(ActionEvent deleteProduct) {
            Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
         if (Inventoryfx.lookupProduct(((Product)this.tvProducts.getSelectionModel().getSelectedItem()).getProductID()).getAllLinkedParts().size() > 0) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Can not delete product with an associated part");
            alert.showAndWait();                
            }
         else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete product?", new ButtonType[0]);
            alert.setContentText("Delete Part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventoryfx.deleteProduct((Product)tvProducts.getSelectionModel().getSelectedItem());
                tvProducts.setItems(Inventoryfx.getAllProducts());
                tvProducts.setItems(Inventoryfx.getAllProducts());
              }
         }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("No Selection");
            alert1.setHeaderText("No Product Selected");
            alert1.setContentText("Please select a Product from the inventory.");
            alert1.showAndWait();
        }
  
    }
    
     /** exits/terminates application */
    @FXML
    void exitAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Exit application?", new ButtonType[0]);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }

    }

     /** Populates the partsTable with parts. Gets parts list from Inventory. */
    public void updatedParts() {
        tvParts.setItems(Inventoryfx.getAllParts());
    }

    /** Populates the productsTable with products. Gets product list from Inventory. */
    public void updatedProducts() {
        tvProducts.setItems(Inventoryfx.getAllProducts());
    };

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
// Setting table collumn controller variables
       tvParts.setItems(Inventoryfx.getAllParts());
       tvProducts.setItems(Inventoryfx.getAllProducts());
  
     //  updatedParts();
      //  updatedProducts();
        colProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        colProductInvLavel.setCellValueFactory(new PropertyValueFactory<>("productLevel"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("productCost"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartInvLavel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
    
    }

    @FXML
    private void ClearSearchProducts(ActionEvent event) {
        updatedProducts();
        tfProductSearch.setText("");
    }

    @FXML
    private void ClearSearchParts(ActionEvent event) {
        
        updatedParts();
        tfPartSearch.setText("");
    }

}
