package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;


public class Controller {

    protected static String OS = "";

    private Dialogy dialogy = new Dialogy();
    private Settings settings = new Settings();

    protected static void getOsType(){
        if (System.getProperty("os.name").equals("Windows")) OS = "win";
        if (System.getProperty("os.name").equals("Linux")) OS = "nix";
        if (System.getProperty("os.name").equals("Mac")) OS = "mac";
    }

    public void handleOpenSettings(ActionEvent event){
        try {
            settings.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCloseApp (ActionEvent event){
        ButtonType volba = dialogy.Confirm("Close", "Do you really want to leave this program?").get();
        if (volba == ButtonType.OK){
            System.exit(0);
        } else event.consume();
    }
}
