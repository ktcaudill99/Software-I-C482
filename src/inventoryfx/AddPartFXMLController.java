/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryfx;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import inventoryfx.InHouse;
import inventoryfx.Inventoryfx;
import inventoryfx.OutSourced;
import inventoryfx.Part;

/**
 * FXML Controller class
 *
 * @author Katherine Caudill
 */
public class AddPartFXMLController implements Initializable {
 
    Part selectedPart;
    Boolean editData = false, 
    inHouse = true;
    private Stage stage;
    private Parent scene;
    
    @FXML
    private RadioButton rbOutSorced;
    @FXML
    private RadioButton rbInHouse;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private ToggleGroup source;
    @FXML
    private Label lbSource;
    @FXML
    private TextField tfAddPartID;
    @FXML
    private TextField tfAddPartName;
    @FXML
    private TextField tfAddPartInv;
    @FXML
    private TextField tfAddPartCost;
    @FXML
    private TextField tfAddPartMax;
    @FXML
    private TextField tfAddPartMachineID;
    @FXML
    private TextField tfAddPartMin;

    
    /**
     * sends user to main screen
     *  does not save changes 
     */
    @FXML
    void cancelPartAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit and\ndiscard changes?", new ButtonType[0]);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = (Parent)FXMLLoader.load(this.getClass().getResource("/inventoryfx/FXML.fxml"));
            stage.setScene(new Scene(this.scene));
            stage.show();
        }
    }

    /**
     *
     * @param event
     * updates gui based on radio button selected
     * 
     */
    @FXML
    public void radioSelect(ActionEvent event) {
        ToggleGroup Tgroup = new ToggleGroup();
        rbInHouse.setToggleGroup(Tgroup);
        rbOutSorced.setToggleGroup(Tgroup);

        if (rbInHouse.isSelected()) {
            lbSource.setText("Machine ID");
        }
        else if (rbOutSorced.isSelected()) {
            lbSource.setText("Company Name");
        }

    };
    
    /**
     *does error check
     * saves the new part
     * send user back to main screen
     * 
     * (previously had runtime error
     * button would not work
     * fixed by directing it correctly
     * (left out part of where being directed)) 
     * (FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));)
     * THAT WAS THE LINE THAT DID NOT WORK. PREVIOUSLY HAD A SLASH IN THERE
     * THAT SLASH MADE IT NOT DIRECT TO THE CORRECT PLACE
     * *****FIXED**** NY TAKING SLASH OUT!!!!
     * IT NOT DIRECTS CORRECTLY
     * 
     * 
     */
    
    @FXML
    void partSaveButtonAction(ActionEvent event) throws IOException {
      if (tfAddPartName.getText().isEmpty()
                || tfAddPartInv.getText().isEmpty()
                || tfAddPartMachineID.getText().isEmpty()
                || tfAddPartMin.getText().isEmpty()
                || tfAddPartMax.getText().isEmpty()
                || tfAddPartCost.getText().isEmpty()) {

            System.out.println("Data Empty");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Data Error");
            alert.setHeaderText("Please enter valid data for every field");
            alert.showAndWait();
        }

        else if (!Inventoryfx.isNumeric(tfAddPartMax.getText())
                || !Inventoryfx.isNumeric(tfAddPartMin.getText())
                || !Inventoryfx.isNumeric(tfAddPartCost.getText())
                || !Inventoryfx.isNumeric(tfAddPartInv.getText())) {


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Value Error");
            alert.setHeaderText("Min, Max, Inventory, and price should all be numeric");
            alert.showAndWait();

        }

        else if (Integer.parseInt(tfAddPartMin.getText()) > Integer.parseInt(tfAddPartMax.getText())) {
            System.out.println("Min Max Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Min Max Error");
            alert.setHeaderText("Part Mins cannot be greater than Maxs");
            alert.showAndWait();
        }

        else if (Integer.parseInt(tfAddPartMin.getText()) > Integer.parseInt(tfAddPartInv.getText()) || Integer.parseInt(tfAddPartInv.getText()) > Integer.parseInt(tfAddPartMax.getText())) {
            System.out.println("Inventory Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Inventory Error");
            alert.setHeaderText("Inventory should be between the min and max");
            alert.showAndWait();
        }


        else {

            System.out.println(!Inventoryfx.isNumeric(tfAddPartInv.getText()));

            System.out.println("Data not empty");
            try {
                int id = Integer.parseInt(tfAddPartID.getText());
                String name = tfAddPartName.getText();
                int stock = Integer.parseInt(tfAddPartInv.getText());
                double price = Double.parseDouble(tfAddPartCost.getText());
                int min = Integer.parseInt(tfAddPartMin.getText());
                int max = Integer.parseInt(tfAddPartMax.getText());
                String dualText = tfAddPartMachineID.getText();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add Part");
                alert.setHeaderText("Would like to save this part to the inventory? ");
                alert.showAndWait();


                if (alert.getResult() == ButtonType.OK)  {


                    if (rbInHouse.isSelected()) {
                        InHouse inhousePart = new InHouse(id, name, stock, price, min, max, Integer.parseInt(dualText));
                        inhousePart.setMachineID(Integer.parseInt(dualText));
                        System.out.println(inhousePart.getMachineID());
                        Inventoryfx.addPart(inhousePart);

                    }
                    else if (rbOutSorced.isSelected()) {
                        System.out.println("Saving Outsourced");
                        OutSourced outsourcedPart = new OutSourced(id, name, stock, price, min, max, dualText);
                        outsourcedPart.setCompanyName(dualText);
                        System.out.println(outsourcedPart.getCompanyName());
                        Inventoryfx.addPart(outsourcedPart);
                    }


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Type Error");
                alert.setHeaderText(E.getLocalizedMessage());
                alert.showAndWait();
            }
            catch (NumberFormatException E) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Type Error");
                alert.setHeaderText("Please format your inputs like the following:" +
                        "\nName: String" +
                        "\nPrice: Double" +
                        "\nMin, Max, Inventory: Integer" +
                        "\nMachine ID: Number " +
                        "\nCompany Name: String");
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
        
        Part lastPart = Inventoryfx.getAllParts().get(Inventoryfx.getAllParts().size() - 1);
        int lastID = lastPart.getId();
        tfAddPartID.setText(String.valueOf(++lastID));
    }    
    
}
