package org.example.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Algorithms {

    public static int[][] parseFile(String fileName) {
        List<int[]> dataList = new ArrayList<>();

        try (InputStream inputStream = Algorithms.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    int[] row = new int[3];
                    row[0] = Integer.parseInt(parts[0]);
                    row[1] = Integer.parseInt(parts[1]);
                    row[2] = Integer.parseInt(parts[2]);
                    dataList.add(row);
                }
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return dataList.toArray(new int[0][0]);
    }
    }
