/**
 * Vytvořeno David Rejdlem za ucelem zprehledneni kodu
 * Tato trida byla použita u více projektu a rad bych ji i zverejnil jako API
 * TODO predelat na podporu javy 7, predelat na dialogy.jar a pak jenom importovat do projektu
 * navic, komu by se to chtelo porad psat
 * Tato trida bylo optimalizovana pomoci IntelliJ Idea Analyzator
 * trida je package-local, proto chybi "public" pred "class"
 */
package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

class Dialogy {

    // zavedeni tridy Alert
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    // funkce na tisk alertu, nechtelo se mi psat dalsi 3 radky kodu ke kazdemu try/catch, navic tohle zprehlednuje kod
    public void Error(String nadpis, String popis){
        alert.setTitle(nadpis);                // nastaveni nadpisu
        alert.setHeaderText(popis);                // nastaveni popisu chyby
        alert.setContentText(null);                   // nastavi prazdnou hodnotu pro ContentText
        alert.showAndWait();                        // zobrazeni dialogu s nasim textem
    }

    // funkce na tisk alertu, nechtelo se mi psat dalsi 3 radky kodu ke kazdemu try/catch, navic tohle zprehlednuje kod
    public void Info(String nadpis, String popis) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle(nadpis);                     // nastaveni nadpisu
        alert.setHeaderText(popis);                 // nastaveni popisu chyby
        alert.setContentText(null);                 // nastavi prazdnou hodnotu pro ContentText
        alert.showAndWait();                        // zobrazeni dialogu s nasim textem
    }

    public void Dialog (String popis, String obsah){
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setHeaderText(popis);
        alert.setContentText(obsah);
        alert.showAndWait();
    }

    // funkce na potvrzovaci dialog
    public Optional<ButtonType> Confirm(String nadpis, String dotaz){
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle(nadpis);
        alert.setHeaderText(dotaz);
        alert.setContentText(null);
        return alert.showAndWait();
    }

    public void ErrorMessage(String nadpis, String popis, String vyjimka, Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(nadpis);
        alert.setHeaderText(popis);
        alert.setContentText(vyjimka);

        // Create expandable Exception.
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        String exceptionText = stringWriter.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        /*alert.getDialogPane().setOnMousePressed(event -> {
            alert.getGraphic().getScene().getWindow().setHeight(400);
            alert.getGraphic().getScene().getWindow().setWidth(600);
        });*/

        alert.showAndWait();
    }

    public void Message(Alert.AlertType alertType,String nadpis, String popis, String vyjimka, String text){
        Alert alert = new Alert(alertType);
        alert.setTitle(nadpis);
        alert.setHeaderText(popis);
        alert.setContentText(vyjimka);

        /*
        // Create expandable message box
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        String messageText = stringWriter.toString();
        */

        TextArea textArea = new TextArea(text);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        /*alert.getDialogPane().setOnMousePressed(event -> {
            alert.getGraphic().getScene().getWindow().setHeight(400);
            alert.getGraphic().getScene().getWindow().setWidth(600);
        });*/

        alert.showAndWait();
    }

}
