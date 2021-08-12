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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import inventoryfx.*;

/**
 * FXML Controller class
 *
 * @author Katie-BAMF
 */
public class ModifyPartFXMLController implements Initializable {

    /**
     *
     */
    public static Part part;
    
    @FXML
    private RadioButton rbOutSorced;
    @FXML
    private RadioButton rbInHouse;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lbSource;
    @FXML
    private ToggleGroup source;
    @FXML
    private TextField tfModPartID;
    @FXML
    private TextField tfModPartName;
    @FXML
    private TextField tfModPartInv;
    @FXML
    private TextField tfModPartCost;
    @FXML
    private TextField tfModPartMax;
    @FXML
    private TextField tfModPartMachineID;
    @FXML
    private TextField tfModPartMin;
    
    /**
     * gets all part data and auto fills it
     * to prepare for edits
     */
    public void setupView() {
        

        tfModPartID.setText(String.valueOf(part.getId()));
        tfModPartName.setText(part.getName());
        tfModPartInv.setText(String.valueOf(part.getStock()));
        tfModPartCost.setText(String.valueOf(part.getPrice()));
        tfModPartMax.setText(String.valueOf(part.getMax()));
        tfModPartMin.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            rbInHouse.setSelected(true);
            lbSource.setText("Machine ID");
            tfModPartMachineID.setText(Integer.toString(((InHouse) part).getMachineID()));
        }
        else {
            rbOutSorced.setSelected(true);
            lbSource.setText("Company Name");
            tfModPartMachineID.setText(((OutSourced) part).getCompanyName());
        }

    }
    
    /**
     *
     * @param event
     * @throws IOException
     * @throws NumberFormatException
     * error check
     * saves the modified parts
     */
    @FXML
    public void saveMod(ActionEvent event) throws IOException, NumberFormatException {

        if (!Inventoryfx.isNumeric(tfModPartMax.getText())
                || !Inventoryfx.isNumeric(tfModPartMin.getText())
                || !Inventoryfx.isNumeric(tfModPartCost.getText())
                || !Inventoryfx.isNumeric(tfModPartInv.getText())) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Min, Max, Inventory, and price should all be numeric");
            alert.showAndWait();

        }

        else if (Integer.parseInt(tfModPartMin.getText()) > Integer.parseInt(tfModPartMax.getText())) {
            System.out.println("Min Max Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Part Mins cannot be greater than Maxs");
            alert.showAndWait();
        }
        else if (Integer.parseInt(tfModPartMin.getText()) > Integer.parseInt(tfModPartInv.getText()) || Integer.parseInt(tfModPartInv.getText()) > Integer.parseInt(tfModPartMax.getText())) {
            System.out.println("Inventory Error");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Inventory should be between the min and max");
            alert.showAndWait();
        }
        else {
            try {
                int oldPartIndex = part.getId();

                String name = tfModPartName.getText();
                int stock = Integer.parseInt(tfModPartInv.getText());
                double price = (Double.parseDouble(tfModPartCost.getText()));
                int min = Integer.parseInt(tfModPartMin.getText());
                int max = Integer.parseInt(tfModPartMax.getText());
                String dualText = tfModPartMachineID.getText();

                if (rbInHouse.isSelected()) {

                    if (!Inventoryfx.isNumeric(dualText)) {
                        MachineIDError();
                    }
                    else {
                        InHouse inhousePart = new InHouse(oldPartIndex, name, stock, price, min, max, Integer.parseInt(dualText));
                        Inventoryfx.updatePart(oldPartIndex, inhousePart);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventoryfx/FXML.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage mainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
                        mainScreen.setScene(scene);
                        mainScreen.show();
                    }
                }

                else if (rbOutSorced.isSelected()) {

                    if (Inventoryfx.isNumeric(dualText)) {
                        CompanyNameError();
                    }
                    else {
                        OutSourced outsourcedPart = new OutSourced(oldPartIndex, name, stock, price, min, max, dualText);
                        Inventoryfx.updatePart(oldPartIndex, outsourcedPart);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventoryfx/FXML.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        Stage mainScreen = (Stage)((Node)event.getSource()).getScene().getWindow();
                        mainScreen.setScene(scene);
                        mainScreen.show();
                    }

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
                        "\nMin, Max, Inventory: Integer" +
                        "\nMachine ID: Number " +
                        "\nCompany Name: String");
                alert.showAndWait();
            }

        }


    }

    /** Displays an alert error based on the machineID TextField. */
    public void MachineIDError() {
        System.out.println("Machine Id Error");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Machine ID should be an Integer");
        alert.showAndWait();
    }

    /** Displays an alert error based on the companyName TextField. */
    public void CompanyNameError() {
        System.out.println("Company Name Error");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Company Name should be an String");
        alert.showAndWait();
    }


    /** Updates the gui of based on Radio Buttons.*/
    @FXML
    public void radioSelect() {
        if (rbInHouse.isSelected()) {
            lbSource.setText("Machine ID");
        }
        else if (rbOutSorced.isSelected()) {
            lbSource.setText("Company Name");
        }

    };


    /** Displays MainScreen and does not save changes.
     * @throws IOException failed to read the file */
    @FXML
    void setCancelButton(ActionEvent event) throws IOException {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Press OK to discard edits.");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {

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

    }

    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setupView();
    }    
    
}
