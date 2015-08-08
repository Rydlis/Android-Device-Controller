package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    protected TextArea textArea;

    private String OS;
    private Boolean adbIsRunning;

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
            adb.InstallApk(fileOperator.open());
        } catch (NullPointerException e){
            dialogy.Error("Error", "You did not choose any file!");
        }
    }

    public void handleUninstallApk(ActionEvent event) {
        System.out.println("pica");
    }

    public void handleRebootDevice(ActionEvent event) {
        adb.RebootDevice(dialogy.Vyber(Alert.AlertType.CONFIRMATION, "vyber","vyber","vyber","vyber","vyber","vyber").get().getText());
    }

    public void handleShutdownDevice(ActionEvent event) {
        System.out.println("pica");
    }

    public void handleAdbLogcat(ActionEvent event) {
        new Thread(adb::logCat).start();
        adbIsRunning = true;
    }

    public void handleAdbStart(ActionEvent event) {
        adb.StartServer();
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

    /**
     * Funkce na vypnuti apliakce pres dialogove okno
     */
    public void handleCloseApp(ActionEvent event) {
        ButtonType volba = dialogy.Confirm("Close", "Do you really want to leave this program?").get();
        if (volba == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        } else event.consume();         // event.consume() zaruci to ze se zrusi volani na vypntui aplikace
    }
}
