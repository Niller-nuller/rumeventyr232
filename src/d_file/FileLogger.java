package d_file;

import d_model.EventStage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileLogger {
    private ArrayList<String> stringLogArray = new ArrayList<>();

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
            String aliveStatus = "Are you dead?: " + isDead + "\n";
            writer.write(shipNameText);
            writer.write(captainNameText);
            writer.write(space);
            writer.write(eventNumberText);
            writer.write(space);
            writer.write(space);

            addStringLogToFile(writer);
            writer.write(space);
            writer.write(aliveStatus);
            if (isDead) {
                writer.write("You are done, dead, no longer existing... Please do not try again");
            }

            String str2 = "==================================================\n";
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

}
