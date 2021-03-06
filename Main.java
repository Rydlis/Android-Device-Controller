/*
  ADC - Android Device Controller
  It's a aplication for controlling your device via USB cable, ADB need to be switched on
  This app was programmed as my graduation work
  Skeleton of this app is generated by IntelliJ Idea 14.0.3
  My big thaks is toward my teacher Mgr. Kovář, for helping me with this work and nearly everything around it :)
 */
package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application {

    protected static Stage stage;
    protected static String OS;

    private Dialogy dialogy = new Dialogy();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.stage = primaryStage;
        OS = getOsType();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ADC - Android Device Controller");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            ButtonType buttonType = dialogy.Confirm("Close", "Do you really want to leave this program?").get();
            if (buttonType == ButtonType.OK) {
                Platform.exit();
                System.exit(0);
            }
            else event.consume();
        });
    }

    public void loadMainScreen(Stage stage){
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOsType() {
        if (System.getProperty("os.name").equals("Windows")) OS = "win";
        if (System.getProperty("os.name").equals("Linux")) OS = "nix";
        if (System.getProperty("os.name").equals("Mac")) OS = "mac";
        return OS;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
