package com.example.localchat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    public static boolean saveHistory(String nickName, String text) {

        File history = new File("history_" + nickName + ".txt");

        if (!history.exists()) {
            try {
                if (!history.createNewFile()) return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            String fileName = "history_" + nickName + ".txt";
            File userHistory = new File(fileName);

            PrintWriter fileWriter1 = new PrintWriter(new FileWriter(userHistory, true));

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter1);
            bufferedWriter.write(text);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static String loadHistory(String nickName) {
        StringBuilder sb = new StringBuilder();
        int maxLines = 100;
        int lineCounter=0;


        List<String> result = new ArrayList<>();

        try  (BufferedReader reader = new BufferedReader(new FileReader(new File("history_" + nickName + ".txt"))))
        {

            String line;
            while ((line = reader.readLine()) != null ) {
                result.add(line);
                lineCounter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lineCounter>maxLines){
        for (int i = 0; i < lineCounter-maxLines; i++) {
            result.remove(0);
        }
        }
        for (String str :
                result) {
            sb.insert(0, maxLines-- + ") " + str + "\n");
        }
        return sb.toString();
    }

}
