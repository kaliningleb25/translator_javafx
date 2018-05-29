package sample.util;

import sample.common.ITextPreProcessing;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class TextPreProcessing implements ITextPreProcessing {
    private String words;
    private String[] wordsArray;
    private Set<String> setOfWords;

    private void readFromFile(String path) {
        StringBuilder sb = new StringBuilder();

        try (Scanner in = new Scanner(new File(path))) {
            while (in.hasNext()) {
                sb.append(in.nextLine());
            }

            words = String.valueOf(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removePunctuationMarks() {
        words = words.replaceAll("\\pP", " ");
    }

    private void removeRedundantSpaces() {
        words = words.replaceAll("\\s{2,}", " ");
    }

    private void toLowerCase() {
        words = words.toLowerCase();
    }

    private void toArray() {
        wordsArray = words.split(" ");
    }

    private void toSet() {
        setOfWords = new LinkedHashSet<>();

        setOfWords.addAll(Arrays.asList(wordsArray));
    }

    @Override
    public String build(String path) {
        readFromFile(path);
        removePunctuationMarks();
        removeRedundantSpaces();
        toLowerCase();
        toArray();
        toSet();

        StringBuilder sb = new StringBuilder();

        for (String word : setOfWords) {
            sb.append(word);
            sb.append(",");
        }

        sb.replace(sb.length() - 1, sb.length(), "");

        return String.valueOf(sb);
    }
}
