/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryfx;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import inventoryfx.Inventoryfx;
import inventoryfx.Part;
import inventoryfx.Product;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * FXML Controller class
 *
 * @author Katherine Caudill
 */
public class AddProductFXMLController implements Initializable {

    private Stage stage;
    private Parent scene;
    private ObservableList<Part> linkedParts = FXCollections.observableArrayList();

    /**
     *
     */
    static public Product product;
    private int productID;
    
    
    @FXML
    private TextField tfAddProdID;
    @FXML
    private TextField tfAddProdName;
    @FXML
    private TextField tfAddProdInv;
    @FXML
    private TextField tfAddProdPrice;
    @FXML
    private TextField tfAddProdMax;
    @FXML
    private TextField tfAddProdMin;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<Part> tvParts;
    @FXML
    private TableColumn<Part, Integer> colPartID;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, Integer> colPartInvLavel;
    @FXML
    private TableColumn<Part, Double> colPartPrice;
    @FXML
    private TableView<Part> tvParts1;
    @FXML
    private TableColumn<Part, Integer> colPartID1;
    @FXML
    private TableColumn<Part, String> colPartName1;
    @FXML
    private TableColumn<Part, Integer> colPartInvLavel1;
    @FXML
    private TableColumn<Part, Double> colPartPrice1;
    @FXML
    private TextField tfPartSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnProductClear;
    

    /**
     * sends user to main screen
     *  does not save changes 
     */
     @FXML
    void setCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Exit and discard changes?", new ButtonType[0]);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = (Parent)FXMLLoader.load(this.getClass().getResource("/inventoryfx/FXML.fxml"));
            stage.setScene(new Scene(this.scene));
            stage.show();
        }
    }
    
    
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
     *
     * @param event
     * checks to make sure part is selected
     * adds part to linked/associated parts
     */
    @FXML
    public void addPartButton(ActionEvent event) {
        if (tvParts.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Selection");
            alert.setHeaderText("Please select a part to add");
            alert.showAndWait();
        }

        else {
         Inventoryfx.lookupPart(((Part)tvParts.getSelectionModel().getSelectedItem()).getId());
            linkedParts.add((Part)tvParts.getSelectionModel().getSelectedItem());
            tvParts1.setItems(linkedParts);
        }

    }
    
    /**
     * updates linked/assoitated part table
     */
    public void  updateTVParts1() {
        tvParts1.setItems(linkedParts);
    }

    /**
     *
     * @param event
     */
    @FXML
    public void removePartButton(ActionEvent event) {
        Part selectedItem = tvParts.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Part Association?");
        alert.setHeaderText("Remove Associated part?");
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.OK) {
            Inventoryfx.lookupPart(((Part)tvParts1.getSelectionModel().getSelectedItem()).getId());
            linkedParts.remove(tvParts1.getSelectionModel().getSelectedItem());
            tvParts1.setItems(linkedParts);
        }
        else {
            alert.close();
        }
        

    }
    
    /**
     *
     * @param event
     * @throws IOException
     * @throws NumberFormatException
     * error checks
     * saves product with linked parts attached
     */
    @FXML
    public void saveProductButton(ActionEvent event) throws IOException, NumberFormatException {
        if (tfAddProdName.getText().isEmpty()
                || tfAddProdInv.getText().isEmpty()
                || tfAddProdMin.getText().isEmpty()
                || tfAddProdMax.getText().isEmpty()
                || tfAddProdPrice.getText().isEmpty()) {

            System.out.println("Data Empty");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Error");
            alert.setHeaderText("Please enter valid data for every field");
            alert.showAndWait();
        }

        else if (!Inventoryfx.isNumeric(tfAddProdMax.getText())
                || !Inventoryfx.isNumeric(tfAddProdMin.getText())
                || !Inventoryfx.isNumeric(tfAddProdPrice.getText())
                || !Inventoryfx.isNumeric(tfAddProdInv.getText())) {


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Value Error");
            alert.setHeaderText("Min, Max, Inventory, and price should all be numeric");
            alert.showAndWait();

        }


        else if (Integer.parseInt(tfAddProdMin.getText()) > Integer.parseInt(tfAddProdMax.getText())) {
            System.out.println("Min Max Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Min Max Error");
            alert.setHeaderText("Product Mins cannot be greater than Maxs");
            alert.showAndWait();
        }
        else if (Integer.parseInt(tfAddProdMin.getText()) > Integer.parseInt(tfAddProdInv.getText()) || Integer.parseInt(tfAddProdInv.getText()) > Integer.parseInt(tfAddProdMax.getText())) {
            System.out.println("Inventory Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inventory Error");
            alert.setHeaderText("Inventory should be between the min and max");
            alert.showAndWait();
        }

        else {
            System.out.println("Data not empty");
            try {
                String name = tfAddProdName.getText();
                String inv = tfAddProdInv.getText();
                String price = tfAddProdPrice.getText();
                String min = tfAddProdMin.getText();
                String max = tfAddProdMax.getText();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add Product");
                alert.setHeaderText("Would like to save this Product to the inventory? ");
                alert.showAndWait();


                if (alert.getResult() == ButtonType.OK)  {
                Product newProduct = new Product();
                newProduct.setProductID(productID);
                newProduct.setProductName(name);
                newProduct.setProductLevel(Integer.parseInt(inv));
                newProduct.setProductCost(Double.parseDouble(price));
                newProduct.setProductMin(Integer.parseInt(min));
                newProduct.setProductMax(Integer.parseInt(max));
                newProduct.setAllLinkedParts(linkedParts);
                Inventoryfx.addProduct(newProduct);
                   
                
                    
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventoryfx/FXML.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage mainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
                mainScreen.setScene(scene);
                mainScreen.show();
                    
                }
                else {
                    alert.close();
                    
                }
            }
            catch (IOException E) {
                System.out.println(E.getLocalizedMessage());
            }
            catch ( NumberFormatException E) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Type Error");
                alert.setHeaderText("Please format your inputs like the following:" +
                        "\nName: String" +
                        "\nPrice: Double" +
                        "\nMin, Max, Inventory: Integer");
                alert.showAndWait();
            }


        }

    }
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        tvParts.setItems(Inventoryfx.getAllParts());
        tvParts1.setItems(linkedParts);
        
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvLavel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        colPartID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartInvLavel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPartPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));

       productID = Inventoryfx.getProductIDCount();
        tfAddProdID.setText("" + productID);
      
        updateTVParts1();
    }    

    public void updatedParts() {
        tvParts.setItems(Inventoryfx.getAllParts());
    }
    @FXML
    private void ClearSearchProducts(ActionEvent event) {
         updatedParts();
        tfPartSearch.setText("");
    }
    
}
