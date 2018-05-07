package ru.turubarov.ntrlabtest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            URLConnection yandexUrl = new URL("https://news.yandex.ru/computers.html").openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yandexUrl.getInputStream()));
            String str;
            StringBuilder htmlCode = new StringBuilder();
            while (in.readLine() != null) {
                str = in.readLine();
                htmlCode.append(str);
            }
            in.close();
            Document doc = Jsoup.parse(htmlCode.toString());
            String textOfWebPage = doc.body().text();
            Map<String, Integer> countWords = new LinkedHashMap<>();
            for (String arg : args) {
                countWords.put(arg, 0);
            }
            for (String t : textOfWebPage.split(" ")) {
                countWords.computeIfPresent(t, (k, v) -> v + 1);
            }
            List<Map.Entry<String, Integer>> list = new ArrayList<>(countWords.entrySet());
            list.sort(Map.Entry.comparingByValue((o1, o2) -> o2 - o1));
            for (Map.Entry entry : list) {
                System.out.println(entry.getKey()+ " " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
