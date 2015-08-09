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
            dialogy.Error("Error", "You did not choose any file");
        }
    }

    public void handleUninstallApk(ActionEvent event) {
        System.out.println("rydlisu, delej sakra");
    }

    public void handleRebootDevice(ActionEvent event) {
        adb.RebootDevice(dialogy.Vyber(Alert.AlertType.CONFIRMATION, "vyber","vyber","vyber","vyber","vyber","vyber").get().getText());
    }

    public void handleShutdownDevice(ActionEvent event) {
        adb.ShutdownDevice();
    }

    /**
     * Handler volajici funkci Logcat() a zapnuti funkce na obnovu TextArei
     * kde by se mel vypisovat InputStream, bohuzel to tak jeste neni
     * TODO porovnavani textu at se netisknou dva stejne radky!!!
     * @param event
     */
    public void handleAdbLogcat(ActionEvent event) {
        adb.logCat();
        adbIsRunning = true;
        new Thread(() -> {
            String helpfulLine;
            String helpfulLine2 = null;
                while (adbIsRunning) {
                    helpfulLine = adb.getLine() + "\n";
                    if (!helpfulLine.equals(helpfulLine2)) textArea.appendText(helpfulLine);
                    helpfulLine2 = helpfulLine;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }).start();
    }

    /**
     * Handler na volani funkce StartServer()
     * @param event
     */
    public void handleAdbStart(ActionEvent event) {
        adb.StartServer();
    }

    /**
     * Handler na volani funkce RestartServer()
     * @param event
     */
    public void handleRestartServer(ActionEvent event) {
        adb.RestartServer();
    }

    /**
     * Handler na volani funkce KillServer()
     * @param event
     */
    public void handleAdbKillServer(ActionEvent event) {
        adb.KillServer();
    }

    /**
     * Handler na vyvolani dialogoveho okna a vypsani verze ADB
     * Informace o verzi ADB ziskava funkce ze tridy ADB funkcí Version()
     * @param event
     */
    public void handleAdbVersion(ActionEvent event) {
        dialogy.Info("Adb version", adb.Version());
    }
    /* konec handleru pro ADB */


    /* handlery pro samotnou aplikaci */

    /**
     * Funkce na prejiti do nastaveni
     * @param event
     */
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
