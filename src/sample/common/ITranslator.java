package sample.common;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * На вход программе подается текстовый файл, содержащий текст на иностранном языке (например, английском).
 * Нужно разделить весь текст на отдельные слова и перевести каждое из них на другой язык (русский, например)
 * используя API любого из понравившегося переводчиков. Для ускорения процесса предлагается использовать Multithreading.
 * Кроме того, если встречаются одинаковые слова, не нужно переводить их дважды.
 *
 * Предлагается использовать Google Api или Yandex Api, так как они наиболее хорошо документированны.
 * Можно использовать несколько переводчиков одновременно, для получения лучших результатов.
 */

public interface ITranslator {

    /**
     * @param path path to the file with the text to be translated
     * @param sourceLang the source language of text. For auto-detection just write 'auto'.
     * @param targetLang the target language for translation
     * @return the dictionary (source word -> translated word)
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    Map<String, String> getWordsTranslations(String path, String sourceLang, String targetLang) throws IOException, ExecutionException, InterruptedException;
}
