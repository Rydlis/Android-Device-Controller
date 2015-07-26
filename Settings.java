package sample;

import javafx.application.Application;
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

    @Override
    public void start(Stage settingsStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        settingsStage.setTitle("Settings");
        settingsStage.setScene(new Scene(root));
        settingsStage.show();
        settingsStage.setOnCloseRequest(event -> {
            ButtonType volba = dialogy.Confirm("Settings", "Do you want to leave program or go back to kokot?").get();
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
}
