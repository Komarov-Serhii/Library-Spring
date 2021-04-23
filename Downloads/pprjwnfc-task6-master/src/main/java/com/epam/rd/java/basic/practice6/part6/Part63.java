package com.epam.rd.java.basic.practice6.part6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Part63 {

    private String fileName;
    private String[] input;

    Part63() {
    }

    public static void main(String[] args) {

        new Part63().console("--input", "part6.txt", "--task", "duplicates");
    }

    private boolean console(String input, String fileName, String task, String operation) {

        if (!("--input".equals(input) || "-i".equals(input))) {
            System.err.println("Wrong operation");
            return false;
        }
        if (!("--task".equals(task) || "-t".equals(task))) {
            System.err.println("Wrong task");
            return false;
        }

        this.fileName = fileName;
        initialize();

        if(operation.equals("duplicates")) getResultDublicates();
            else  return false;

        return true;
    }

    private String getInput() {
        StringBuilder sb = new StringBuilder();
        try (Scanner file = new Scanner(new File(fileName), "CP1251")) {
            while (file.hasNext()) {
                sb.append(file.next()).append(" ");
            }
        } catch (FileNotFoundException e) {
            System.err.println(String.format("File: %s not found", fileName));
        }
        return sb.toString();
    }

    public void initialize() {
        StringBuilder sb = new StringBuilder();
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(getInput());
        while (m.find()) {
            sb.append(m.group() + " ");
        }
        input = sb.toString().split(" ");
    }

    private void getResultDublicates() {
        final Map<String, Integer> wordCounts = new LinkedHashMap<>();
        for (int place = 0; place < input.length; place++) {
            String w = input[place];
            Integer countWithPlace = wordCounts.get(w);
            if (countWithPlace == null) {
                wordCounts.put(w, 1);
            } else {
                wordCounts.put(w, countWithPlace += 1);
            }
        }
        int i = 0;
        for (Map.Entry<String, Integer> wordCount : wordCounts.entrySet()) {
            if (i == 3) {
                break;
            }
            if (wordCount.getValue() > 1) {
                i++;
                System.out.println(new StringBuilder(wordCount.getKey()).reverse().toString().toUpperCase());
            }
        }
    }
}