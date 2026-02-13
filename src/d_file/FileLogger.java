package d_file;

import d_model.EventStage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileLogger {
    private final ArrayList<String> stringLogArray = new ArrayList<>();

    public void CreateLogFile(String shipName) {

        Path file = Paths.get("Logs/" + shipName + ".txt");

        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            if (Files.notExists(file)) {
                Files.createFile(file);
            } else {
                System.out.println("Fil findes allerede");
            }
        } catch (IOException e) {
            System.out.println("Directory kunne ikke oprettes");
        }
    }


    public void WriteLogFile(String shipName, String captainName, Enum<EventStage> eventNumber, boolean isDead) {
        Path file = Paths.get("Logs/" + shipName + ".txt");

        try (FileWriter writer = new FileWriter(file.toFile(),true)) {

            String str = "==================================================\n";
            writer.write(str);
            String shipNameText = "Ship name:   " + shipName + "\n";
            String captainNameText = "Captain:  " + captainName + "\n";
            String space = "\n";
            String eventNumberText = "Event number: " + eventNumber + "\n";
            String aliveStatus = "You are still alive and well captain!" + "\n";
            writer.write(shipNameText);
            writer.write(captainNameText);
            writer.write(space);
            writer.write(eventNumberText);
            writer.write(space);
            writer.write(space);

            addStringLogToFile(writer);
            writer.write(space);
            if (isDead) {
                writer.write("You are done, dead, no longer existing... Please do not try again" + "\n");
            } else {
                writer.write(aliveStatus);
            }

            String str2 = "==================================================\n";
            writer.write(space);
            writer.write(str2);

        } catch (IOException e) {
            System.out.println("Kunne ikke skrive i filen");
        }
    }

    public void updateStringLog(String logEntry) {
        stringLogArray.add(logEntry);
    }

    public void addStringLogToFile(FileWriter fileWriter) throws IOException {

        for (String stringLog : stringLogArray) {
            fileWriter.write(stringLog + "\n");
        }
    }

    public void printLogToConsole (String shipName) {

        Path file = Paths.get("Logs/" + shipName + ".txt");

        for (int i = 0; i < 5; i++){
            System.out.println();
        }

        System.out.println("------------ LOG FILE ------------");

        System.out.println();

        BufferedReader readFile = null;
        try  {
            readFile = new BufferedReader(new FileReader(file.toFile()));
            String line = readFile.readLine();

            while (line != null){
                System.out.println(line);
                line = readFile.readLine();
            }
        } catch (IOException e) {
            System.out.println("Kunne ikke finde filen: " + shipName);
        } finally {
            try {
                if (readFile != null ) readFile.close();
                } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
    }
}
