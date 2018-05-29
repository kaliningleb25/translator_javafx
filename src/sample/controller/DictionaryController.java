package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import sample.service.TranslatorImpl;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DictionaryController {
    private static final int OFFSET = 1;

    public TextArea translatedTextArea;

    private String sourceLang;
    private String targetLang;
    private File file;

    void initData(Controller controller) throws InterruptedException, ExecutionException, IOException {
        this.sourceLang = controller.getSourceLang();
        this.targetLang = controller.getTargetLang();
        this.file = controller.getFile();

        System.out.println(sourceLang);
        System.out.println(targetLang);
        System.out.println(file.getAbsolutePath());

        String dict = buildOutput();

        translatedTextArea.setText(dict);
    }

    @FXML
    public void initialize() throws InterruptedException, ExecutionException, IOException {}

    private String buildOutput() throws ExecutionException, InterruptedException, IOException {
        TranslatorImpl translator = new TranslatorImpl();
        StringBuilder dictBuilder = new StringBuilder();
        Map<String, String> dict;
        int maxLength;

        dict = translator.getWordsTranslations(file.getAbsolutePath(), sourceLang, targetLang);

        maxLength = dict.keySet().stream().max(Comparator.comparingInt(String::length)).get().length();

        dictBuilder.append(buildHeader(maxLength));
        dictBuilder.append(buildDictionary(dict, maxLength));

        return String.valueOf(dictBuilder);
    }

    private StringBuilder buildHeader(int maxLength) {
        StringBuilder headerBuilder = new StringBuilder();

        int sourceLangLength = sourceLang.length();

        headerBuilder.append(sourceLang);
        for (int i = 0; i < (maxLength - sourceLangLength) + OFFSET; i++) {
            headerBuilder.append(" ");
        }
        headerBuilder.append(targetLang).append("\n");

        return headerBuilder;
    }

    private StringBuilder buildDictionary(Map<String, String> dictMap, int maxLength) {
        StringBuilder dictionaryBuilder = new StringBuilder();

        for (Map.Entry entry : dictMap.entrySet()) {
            String word = (String) entry.getKey();
            int length = word.length();

            String translatedWord = (String) entry.getValue();

            dictionaryBuilder.append(word);
            for (int i = 0; i < (maxLength - length) + OFFSET; i++) {
                dictionaryBuilder.append(" ");
            }
            dictionaryBuilder.append(translatedWord).
                    append("\n");
        }

        return dictionaryBuilder;
    }
}