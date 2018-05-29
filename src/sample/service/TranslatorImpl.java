package sample.service;

import sample.common.ITextPreProcessing;
import sample.common.ITranslator;
import sample.config.SystemConfig;
import sample.entity.Dictionary;
import sample.util.TextPreProcessing;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class TranslatorImpl implements ITranslator {
    private static final int AVAILABLE_THREADS_COUNT = SystemConfig.THREADS_COUNT;
    private static final int ONE_THREAD = 1;

    private ITextPreProcessing iTextPreProcessing;

    private String words;
    private String[] wordsArray;

    private int countOfWords;
    private int countOfWordsForOneThread;

    private int limitOfThreads;

    public TranslatorImpl() throws IOException {
        iTextPreProcessing = new TextPreProcessing();
    }

    @Override
    public Map<String, String> getWordsTranslations(String path, String sourceLang, String targetLang) throws IOException, ExecutionException, InterruptedException {
        words = iTextPreProcessing.build(path);

        return buildDictionary(sourceLang, targetLang);
    }

    private Map<String, String> buildDictionary(String sourceLang, String targetLang) throws IOException, InterruptedException, ExecutionException {
        wordsInit();

        List<Callable<Map<String, String>>> callableTask = new ArrayList<>();
        List<Future<Map<String, String>>> futures;
        Map<String, String> dict = new LinkedHashMap<>();

        ExecutorService executor = Executors.newCachedThreadPool();

        limitOfThreads = wordsArray.length < AVAILABLE_THREADS_COUNT ?
                ONE_THREAD :
                AVAILABLE_THREADS_COUNT;

        for (int indOfThreads = 0; indOfThreads < limitOfThreads; indOfThreads++) {
            callableTask.add(new Dictionary(getPartOfText(indOfThreads), sourceLang, targetLang));

            System.out.println("processing...");
        }

        futures = executor.invokeAll(callableTask);

        for (Future<Map<String, String>> future : futures) {
            dict.putAll(future.get());
        }

        executor.shutdownNow();

        return dict;
    }

    private void wordsInit() {
        wordsArray = words.split(",");
        countOfWords = wordsArray.length;
        countOfWordsForOneThread = countOfWords / AVAILABLE_THREADS_COUNT;
    }

    private String getPartOfText(int indOfThreads) {
        StringBuilder sb = new StringBuilder();

        int bgnInd = countOfWordsForOneThread * indOfThreads;
        int endInd = indOfThreads != limitOfThreads - 1 ?
                countOfWordsForOneThread * (indOfThreads + 1) :
                countOfWords;

        for (int indOfWords = bgnInd; indOfWords < endInd; indOfWords++) {
            sb.append(wordsArray[indOfWords]);
            sb.append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), "");

        return String.valueOf(sb);
    }
}
