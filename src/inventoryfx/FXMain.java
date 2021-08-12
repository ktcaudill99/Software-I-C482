/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryfx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import inventoryfx.FXMLController;
/**
 *
 * @author Katherine Caudill
 * 
 * 
 * launch file: FXMain opens main screen

-main screen = FXML (layout) FXMLController (controls/actions of FXML)

-Add Product = AddProductFXML (layout) AddProductFXMLController (controls/actions of AddProductFXML)

-Modify Procut = ModifyProductFXML (layout) ModifyProductFXMLController (controls/actions of ModifyProductFXML)

-Add Part = AddPartFXML (layout) AddPartFXMLController (controls/actions of AddPartFXML)

-Modify Part = ModifyPartFXML (layout) FXMLController (controls/actions of ModifyPartFXML)
*
Javadoc located under inventoryfx\dist\javadoc
*
* 
 * 
 * FOR THE RUNTIME ERROR COMMENT SEE ADDPARTFAXML
 * (* (previously had runtime error
     * button would not work
     * fixed by directing it correctly
     * (left out part of where being directed)) 
     * (FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));)
     * THAT WAS THE LINE THAT DID NOT WORK. PREVIOUSLY HAD A SLASH IN THERE
     * THAT SLASH MADE IT NOT DIRECT TO THE CORRECT PLACE
     * *****FIXED**** NY TAKING SLASH OUT!!!!
     * IT NOT DIRECTS CORRECTLY)
 * 
 * A COMPATIBLE FEATURE FOR FUTURE UPDATES WOULD BE TO EDIT PART
 * THROUGH MODIFY PRODUCT
 * 
 * ANOTHER COMPATIBLE FEATURE WO8ULD BE TO BETTER THE SEARCH FEATURE
 *  FOR EXAMPLE
 *      SEARCH BY INHOUSE OR OUTSOURCED
 * 
 * 
 * 
 */
public class FXMain extends Application {
    
    // Set main window as FXML.fxml

    
    /**
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
          try {
           
         FXMLLoader fxmlLoader = new FXMLLoader(FXMLController.class.getResource("FXML.fxml"));

        Parent root = FXMLLoader.load(getClass().getResource("/inventoryfx/FXML.fxml"));
        
        Scene scene = new Scene(root);
        FXMLController mainScreen = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
