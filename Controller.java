package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private TextArea textArea;

    private String OS = "";

    private Dialogy dialogy = new Dialogy();
    private Settings settings = new Settings();
    private Adb adb = new Adb(OS);
    private FileOperator fileOperator = new FileOperator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.OS = Main.OS;
    }

    /* handlery pro ADB */
    public void handleInstallApk(ActionEvent event) {
        try {
            adb.Install(fileOperator.open());
        } catch (NullPointerException e){
            dialogy.Error("Error", "You did not choose any file!");
        }
    }

    public void handleUninstallApk(ActionEvent event) {
        System.out.println("pica");
    }

    public void handleRebootDevice(ActionEvent event) {
        System.out.println("pica");
    }

    public void handleShutdownDevice(ActionEvent event) {
        System.out.println("pica");
    }

    public void handleAdbLogcat(ActionEvent event) {
       new Thread(adb::logCat).start();
        new Thread(() -> {
            textArea.setText(adb.getAdbLogcatOutput());
        }).start();
    }

    public void handleAdbStart(ActionEvent event) {
        adb.Start();
    }

    public void handleRestartServer(ActionEvent event) {
        adb.RestartServer();
    }

    public void handleAdbKillServer(ActionEvent event) {
        adb.KillServer();
    }

    public void handleAdbVersion(ActionEvent event) {
        dialogy.Info("Adb version", adb.Version());
    }


    /* handlery pro samotnou aplikaci */
    public void handleOpenSettings(ActionEvent event) {
        try {
            settings.start(Main.stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCloseApp(ActionEvent event) {
        ButtonType volba = dialogy.Confirm("Close", "Do you really want to leave this program?").get();
        if (volba == ButtonType.OK) {
            System.exit(0);
        } else event.consume();
    }
}
