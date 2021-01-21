package stephen_wallace.software_engineering.assessment;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class EndSessionController {
    @FXML
    TextArea trendingListBox;
    @FXML
    TextArea mentionsListBox;
    @FXML
    TextArea sirListBox;
    private Stage primaryStage;
    private ProcessMessages messageProcessor;

    /*
     *   Sets the stage the UI is using
     *
     *   @param  stage  a UI stage
     *   @param  messageProcessor  the message processor that was used to process the messages
     */
    public void setPrimaryStage(Stage stage, ProcessMessages messageProcessor){
        this.primaryStage = stage;
        this.messageProcessor = messageProcessor;
        displayLists();
    }

    /*
     *   Displays various lists of items from the message processor
     */
    private void displayLists() {
        trendingListBox.setText(messageProcessor.getTrending());
        mentionsListBox.setText(messageProcessor.getMentions());
        sirListBox.setText(messageProcessor.getSIRs());
    }

    /*
     *   Closes the application
     */
    public void close() {
        primaryStage.close();
    }
}
