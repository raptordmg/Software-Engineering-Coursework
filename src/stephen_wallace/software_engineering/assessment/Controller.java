package stephen_wallace.software_engineering.assessment;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Stage primaryStage;
    private final ProcessMessages messageProcessor = new ProcessMessages();
    private final JsonHandler jsonHandler = new JsonHandler();

    @FXML
    TextField inputHeader;
    @FXML
    Label messageType;
    @FXML
    TextArea messageBody;
    @FXML
    TextArea processedMessage;

    /*
    *   Sets the stage the UI is using
    *
    *   @param  stage  a UI stage
     */
    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    /*
     *   Displays the type of message header that has been input
     */
    private void displayMessageType() {
        messageType.setText(messageProcessor.detectMessageType(inputHeader.getText()).toString());
    }

    /*
     *   Processes the message body and displays the output
     *   Then adds them to the message.json file
     */
    public void processMessages() throws IOException {
        displayMessageType();
        if (messageType.getText() == "SMS") {
            processedMessage.setText(messageProcessor.processSMSMessage(messageBody.getText()));
            if (!processedMessage.getText().equals("Error: Invalid SMS Message")){
                addMessageToJson(inputHeader.getText(),processedMessage.getText());
            }
        } else if (messageType.getText() == "EMAIL"){
            processedMessage.setText(messageProcessor.processEmail(messageBody.getText()));
            if (!processedMessage.getText().equals("Error: Invalid Email")){
                addMessageToJson(inputHeader.getText(),processedMessage.getText());
            }
        } else if (messageType.getText() == "TWITTER"){
            processedMessage.setText(messageProcessor.processTwitterMessage(messageBody.getText()));
            if (!processedMessage.getText().equals("Error: Invalid Twitter Message")){
                addMessageToJson(inputHeader.getText(),processedMessage.getText());
            }
        }
    }


    /*
     *   Ends the input session and display the end of session screen
     */
    public void endSession() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EndSessionUI.fxml"));
        Parent root = loader.load();
        ((EndSessionController) loader.getController()).setPrimaryStage(primaryStage, messageProcessor);

        primaryStage.setScene(new Scene(root));
    }

    /*
     *   Adds a processed message to the json file
     *
     *   @param  header  string containing the header data
     *   @param  body  string containing the processed body of the message
     */
    private void addMessageToJson(String header, String body) throws IOException {
        jsonHandler.addToJson(header,body);
    }

    /*
     *   Reads in message data from a csv file
     */
    public void readFileData() throws IOException {
        new ReadMessageFile().readInFiles(primaryStage);
    }
}