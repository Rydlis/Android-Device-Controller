package sample;

import java.io.*;

/**
 * Created by david on 25.7.15.
 * TODO dodelat rozpoznavani OS a upravovani prikazu pro jednotliva OS
 */
public class Adb {

    //* konstruktor bez parametru
    public Adb(){

    }

    private String OS;
    private ProcessBuilder pb;
    private Process pc;

    public void Install (File file){
        pb = new ProcessBuilder("adb", "install", file.getPath());
        ProcessRunner();
    }

    public void Start(){
        pb = new ProcessBuilder("adb", "start-server");
        ProcessRunner();
    }

    public void RestartServer(){
        System.out.println(OS);
        pb = new ProcessBuilder("adb", "kill-server");
        ProcessRunner();
        pb = new ProcessBuilder("adb", "start-server");
        ProcessRunner();
    }

    public void KillServer(){
        pb = new ProcessBuilder("adb", "kill-server");
        ProcessRunner();
    }

    public String logCat(){
        String output = "";
        pb = new ProcessBuilder("adb","logcat");
        try {
            pc = pb.start();
            System.out.printf("zapnut proces\n");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pc.getInputStream()));
            System.out.println("zaveden bufferedreader\n");
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                System.out.printf("ctu \n");
                output += line + bufferedReader.readLine() + "\n";
                System.out.printf(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

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
        Process pc;
        try {
            pc = pb.start();
            System.out.println("proces zacal");
            String line;
            String output = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pc.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){
                System.out.println("zapocato cteni");
                output += line + bufferedReader.readLine() + "\n";
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
                System.out.println("KURVA PICA NEJEDE TO!!!");
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
    }
}
