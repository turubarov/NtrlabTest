package ru.turubarov.ntrlabtest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            URLConnection yandexUrl = new URL("https://news.yandex.ru/computers.html").openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yandexUrl.getInputStream()));
            String str;
            String htmlCode = "";
            while (in.readLine() != null) {
                str = in.readLine().toString();
                htmlCode += str;
            }
            in.close();
            Document doc = Jsoup.parse(htmlCode);
            String textOfWebPage = doc.body().text();
            String[] wordsFrowText = textOfWebPage.split(" ");
            for (int i = 0; i < wordsFrowText.length; i++) {
                wordsFrowText[i] = wordsFrowText[i].toLowerCase();
            }
            Map<String, Integer> countWords = new LinkedHashMap<>();
            for (int i = 0; i < args.length; i++) {
                countWords.put(args[i].toLowerCase(), 0);
            }
            for (int i = 0; i < wordsFrowText.length; i++) {
                for (String searchWord : countWords.keySet()) {
                    if (searchWord.equals(wordsFrowText[i])) {
                        Integer count = countWords.get(searchWord);
                        countWords.put(searchWord, ++count);
                        continue;
                    }
                }
            }
            List<Map.Entry<String, Integer>> list = new ArrayList<>(countWords.entrySet());
            list.sort(Map.Entry.comparingByValue(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2 - o1;
                }
            }));
            for (Map.Entry entry : list) {
                System.out.println(entry.getKey()+ " " + entry.getValue());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
