package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controller.ErrorController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);

        Parent root = FXMLLoader.load(getClass().getResource("stage/main.fxml"));
        primaryStage.setTitle("Translator");
        primaryStage.setScene(new Scene(root, 363, 184));
        primaryStage.setOnHiding(p -> System.exit(0));
        primaryStage.show();
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            try {
                showErrorDialog(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            System.err.println("An unexpected error occurred in " + t);

        }
    }

    private static void showErrorDialog(Throwable e) throws IOException {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("stage/error.fxml"));
        try {
            Parent root = loader.load();
            ((ErrorController) loader.getController()).setErrorText("Sorry, this language is not supported now.");
            dialog.setScene(new Scene(root, 363, 184));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
