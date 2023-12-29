package com.example.tubes_proglan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void saveToFile(String title, String imageUrl, String description, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());

            writer.write(timestamp + "," + title + "," + imageUrl + "," + description + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle kesalahan penyimpanan ke file di sini
            MovieSearchController.showAlert("Error", "Gagal dalam menyimpan file!");
        }
    }

    public static List<String[]> readAllFromFile(String fileName) {
        List<String[]> entries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                entries.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle kesalahan membaca dari file di sini
            MovieSearchController.showAlert("Error", "Gagal dalam membuka file!");
        }

        return entries;
    }
}
