package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by david on 26.7.15.
 */
public class Settings extends Application{

    private Dialogy dialogy = new Dialogy();
    private FileOperator fileOperator = new FileOperator();

    private static Stage settingsStage;

    private String ADB_PATH;
    private String FASTBOOT_PATH;

    @FXML
    private TextField adb_path_textfield;
    @FXML
    private TextField fastboot_path_textfield;

    @Override
    public void start(Stage settingsStage) throws Exception {
        Settings.settingsStage = settingsStage;
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        settingsStage.setTitle("ADC - Settings");
        settingsStage.setScene(new Scene(root));
        settingsStage.show();
        settingsStage.setOnCloseRequest(event -> {
            ButtonType volba = dialogy.Confirm("Settings", "Do you want to leave program or go back to Main Screen?").get();
            if (volba == ButtonType.OK){
                try {
                    new Main().loadMainScreen(settingsStage);
                    event.consume();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else System.exit(0);
        });
    }

    /**
     * Vybrani cesty k souboru ADB, plati pouze pro Windows a Mac
     * @param event
     */
    public void handleChooseAdbPath (ActionEvent event){
        ADB_PATH = fileOperator.open().getPath();
        adb_path_textfield.setText(ADB_PATH);
    }

    /**
     * Vybrani cesty k souboru Fastboot, plati pouze pro Windows a Mac
     * @param event
     */
    public void handleChooseFastbootPath (ActionEvent event){
        FASTBOOT_PATH = fileOperator.open().getPath();
        fastboot_path_textfield.setText(FASTBOOT_PATH);
    }

    /**
     * Ulozeni nastaveni do textoveho souboru a prepnuti Stage na Main
     * @param event
     */
    public void handleSaveAndBack(ActionEvent event){
        new Main().loadMainScreen(settingsStage);
    }
}
