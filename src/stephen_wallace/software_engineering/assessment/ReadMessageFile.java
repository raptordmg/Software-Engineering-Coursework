package stephen_wallace.software_engineering.assessment;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadMessageFile {
    final List<String> header = new ArrayList<>();
    final List<String> body = new ArrayList<>();

    /*
     *   Reads in messages from a csv file with ";" as the separator and then adds them to the json
     *
     *   @param  stage  a UI stage
     */
    public void readInFiles(Stage primaryStage) throws IOException {
        List<String> rawData = new ArrayList<>();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        try{
            if (selectedFile != null){
                Scanner scanner = new Scanner(selectedFile);
                while (scanner.hasNextLine()){
                    rawData.add(scanner.nextLine());
                }
                scanner.close();
            }
        } catch (IOException e){

        }

        if (!rawData.isEmpty()){
            for (String rawDatum : rawData) {
                String[] array = rawDatum.split(";");
                for (int j = 0; j < array.length; j++) {
                    if (j % 2 == 0) {
                        header.add(array[j]);
                    } else {
                        body.add(array[j]);
                    }
                }
            }
            JsonHandler jsonHandler = new JsonHandler();
            ProcessMessages messageProcessor = new ProcessMessages();
            Validation validator = new Validation();
            for (int i = 0; i < header.size(); i++) {
                String messageType = messageProcessor.detectMessageType(header.get(i)).toString();
                switch (messageType) {
                    case "SMS":
                        String[] smsWords = body.get(i).trim().split(" ");
                        if (validator.validateHeader(header.get(i)) && validator.validatePhoneNum(smsWords[0])){
                            jsonHandler.addToJson(header.get(i), body.get(i));
                        }
                        break;
                    case "EMAIL":
                        String[] emailWords = body.get(i).trim().split(" ");
                        if (validator.validateHeader(header.get(i)) && validator.validateEmail(emailWords[0])){
                            jsonHandler.addToJson(header.get(i), body.get(i));
                        }
                        break;
                    case "TWITTER":
                        String[] twitterWords = body.get(i).trim().split(" ");
                        if (validator.validateHeader(header.get(i)) && validator.validateTwitterName(twitterWords[0])){
                            jsonHandler.addToJson(header.get(i), body.get(i));
                        }
                        break;
                }
            }
        }

    }
}
