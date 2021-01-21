package stephen_wallace.software_engineering.assessment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadAbbreviations {
    final List<String> abbreviations = new ArrayList<>();
    final List<String> readings = new ArrayList<>();

    /*
     *   Opens the file containing abbreviations and adds them to a pair of lists
     */
    private void openFile() {
        List<String> rawData = new ArrayList<>();
        try{
            File file = new File("textwords.csv");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()){
                rawData.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String rawDatum : rawData) {
            String[] array = rawDatum.split(",");
            for (int j = 0; j < array.length; j++) {
                if (j % 2 == 0) {
                    abbreviations.add(array[j]);
                } else {
                    readings.add(array[j]);
                }
            }
        }
    }

    /*
     *   Returns the abbreviations list
     */
    public List<String> getAbbreviations() {
        if (abbreviations.isEmpty()){
            openFile();
        }
        return abbreviations;
    }

    /*
     *   Returns the readings list
     */
    public List<String> getReadings() {
        if (readings.isEmpty()){
            openFile();
        }
        return readings;
    }
}
