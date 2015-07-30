package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Created by david on 26.7.15.
 */
public class Settings extends Application{

    private Dialogy dialogy = new Dialogy();
    private static Stage settingsStage;

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

    public void handleSaveAndBack(ActionEvent event){
        new Main().loadMainScreen(settingsStage);
    }
}
