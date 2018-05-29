package sample.common;

import java.io.IOException;
import java.util.Map;

public interface IDictionary {
    Map<String, String> makeDictionary(String words, String sourceLang, String targetLang) throws IOException;
}
