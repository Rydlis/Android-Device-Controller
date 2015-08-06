package sample;

import javafx.scene.control.Alert;

import java.io.*;

/**
 * Created by david on 25.7.15.
 * TODO dodelat rozpoznavani OS a upravovani prikazu pro jednotliva OS
 */
public class Adb {

    //* konstruktor bez parametru
    public Adb(String OS){
        this.OS = OS;
    }

    private String OS;
    private ProcessBuilder pb;
    private Process pc;
    private String adbLogcatOutput = "";
    private String line = "";

    private Boolean isRunning = false;

    /**
     * Funkce na instalaci aplikace
     * @param file - soubor predavan z FileChooseru
     */
    public void Install (File file){
        pb = new ProcessBuilder("adb", "install", file.getPath());
        ProcessRunner();
    }

    /**
     * Funkce na start ADB serveru
     */
    public void StartServer(){
        pb = new ProcessBuilder("adb", "start-server");
        ProcessRunner();
    }

    /**
     * Funkce na restart ADB serveru, samotne ADB tuto funkci nepodporuje, proto se nejdrive spusti proces na vypnuti
     * serveru, a jakmile je server vypnuty spusti se proces na jeho zapnuti
     */
    public void RestartServer(){
        KillServer();
        StartServer();
    }

    /**
     * Funkce na vypnutí ADB serveru
     */
    public void KillServer(){
        pb = new ProcessBuilder("adb", "kill-server");
        ProcessRunner();
    }

    public void logCat(){
        pb = new ProcessBuilder("adb","logcat");
        try {
            pc = pb.start();
            System.out.printf("zapnut proces\n");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pc.getInputStream()));
            System.out.println("zaveden bufferedreader\n");
            while ((line = bufferedReader.readLine()) != null){
                adbLogcatOutput += line + "\n" + bufferedReader.readLine() + "\n";
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Funkce na zjisteni verze ADB nainstalovaneho v pocitaci
     * a pote String s verzi posle zpatky do Controlleru, kde se zobrazi pres funky Info ze tridy Dialogy
     * @return String output
     */
    public String Version(){
        String output= "";
        pb = new ProcessBuilder("adb", "version");
        try {
            pc = pb.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pc.getInputStream()));
            output = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(output);
        return output;
    }

    public void Device(){

    }

    /**
     * pomocna funkce ProcessRunner, slouzi k aktualnimu behu prikazu pro OS, uprava a zadani prikazu pro OS je delano rovnou ve funkcich
     * ktere volaji ProcessRunner
     */
    private void ProcessRunner(){
        new Thread(() -> {
            Process pc;
            try {
                pc = pb.start();
                System.out.println("proces zacal");
                String line;
                String output = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pc.getInputStream()));
                while ((line = bufferedReader.readLine()) != null){
                    System.out.println("zapocato cteni");
                    output = line + "\n" + bufferedReader.readLine() + "\n";
                }
                System.out.println(output);
                /**
                 * Pokud je VM zapnuta s parametrem -enableassertions, assert je chova jako
                 *  if (output == null) {
                 *      throw new AssertionError();
                 *   }
                 * pokud je ovsem Vm zapnuta bez tohoto parametru, assert se vubec necte
                 */
                assert output != null;
                if ((output.contains("Failure")) || (output.contains("error"))){
                    new Dialogy().Message(Alert.AlertType.ERROR, "Error", "Neco se pojebalo", "Kurva pica", output);
                }
                pc.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            } catch (NullPointerException e){
                System.out.println("no pica kurva!!!!!!!");
            }
            System.out.println("Done");
        }).start();
    }

    /**
     * Funkce na ziskani adbLogcatOutput
     * @return adbLogcatOuput
     */
    public String getAdbLogcatOutput() {
        return adbLogcatOutput;
    }

    /**
     * Funkce na zjisteni jestli prave bezi nejaky proces nebo ne
     * @return Boolean isRunnig
     */
    public Boolean getIsRunning() {
        return isRunning;
    }

    /**
     * Funkce na ziskani aktualniho radku z InputStreamu privazaneho k procesu
     * @return String line
     */
    public String getLine() {
        return line;
    }
}
