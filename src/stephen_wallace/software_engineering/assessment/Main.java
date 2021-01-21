package stephen_wallace.software_engineering.assessment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainUI.fxml"));
        Parent root = loader.load();
        ((Controller) loader.getController()).setPrimaryStage(primaryStage);

        primaryStage.setTitle("Napier Bank Message Filterer");
        primaryStage.setScene(new Scene(root, 586, 461));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
