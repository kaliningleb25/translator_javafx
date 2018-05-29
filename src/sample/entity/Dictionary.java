package sample.entity;

import sample.common.IDictionary;
import sample.translate_api.TranslateAPI;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class Dictionary implements IDictionary, Callable {
    private String words = null;
    private String sourceLang = null;
    private String targetLang = null;

    private Map<String, String> dict;
    private Map<String, String> langs;


    public Dictionary(String words, String sourceLang, String targetLang) throws IOException {
        this.words = words;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;

        dict = new LinkedHashMap<>();
        langs = TranslateAPI.getLanguages();
    }

    @Override
    public Map<String, String> makeDictionary(String words, String sourceLang, String targetLang) throws IOException {
        String source = null;
        try {
            source = isAutoDetect(sourceLang) ?
                    TranslateAPI.detectLanguage(words) :
                    TranslateAPI.getKey(langs, sourceLang);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String target = TranslateAPI.getKey(langs, targetLang);

        String[] wordsArray = words.split(",");

        for (String word : wordsArray) {
            dict.put(word, TranslateAPI.translate(word, source, target));
        }

        return dict;
    }

    private boolean isAutoDetect(String sourceLang) {
        return sourceLang.equalsIgnoreCase("auto");
    }

    @Override
    public Object call() throws Exception {
        return makeDictionary(words, sourceLang, targetLang);
    }
}
