package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import sample.translate_api.TranslateAPI;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Controller {
    public Button btnChooseFile;
    public Button translate_btn;

    public ComboBox cbSourceLang;
    public ComboBox cbTargetLang;

    public Text fileName;

    private File textFile;
    private String sourceLang;
    private String targetLang;


    public File getFile() {
        return textFile;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    @FXML
    public void initialize() throws IOException {
        ObservableList<String> sourceLangsList = FXCollections.observableArrayList();
        ObservableList<String> targetLangsList = FXCollections.observableArrayList();

        sourceLangsList.add("Auto");
        sourceLangsList.addAll(languagesList());

        targetLangsList.addAll(languagesList());

        cbSourceLang.setItems(sourceLangsList);
        cbSourceLang.getSelectionModel().selectFirst();

        cbTargetLang.setItems(targetLangsList);
        cbTargetLang.getSelectionModel().selectFirst();
    }

    private ObservableList<String> languagesList() throws IOException {
        Map<String, String> langsMap = TranslateAPI.getLanguages();
        List<String> langsList = new ArrayList<>();

        langsList.addAll(langsMap.values());

        Collections.sort(langsList);

        return FXCollections.observableArrayList(langsList);
    }

    public void btn_pressed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        Stage fileChooserStage = new Stage();

        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        textFile = fileChooser.showOpenDialog(fileChooserStage);

        if (textFile != null) {
            cbSourceLang.setDisable(false);
            cbTargetLang.setDisable(false);
            translate_btn.setDisable(false);

            System.out.println(textFile.getName());
            fileName.setText(textFile.getName());
        }
    }


    public void translate_btn_pressed(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        sourceLang = (String) cbSourceLang.getValue();
        targetLang = (String) cbTargetLang.getValue();

        showTranslate(this);
    }

    private Stage showTranslate(Controller controller) throws IOException, ExecutionException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/stage/translated_text.fxml"));

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load(), 600, 400));

        DictionaryController dictionaryController = loader.getController();
        dictionaryController.initData(controller);

        stage.show();

        return stage;
    }
}