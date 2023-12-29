package com.example.tubes_proglan;
import com.example.tubes_proglan.Movie;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieSearchBackend {
    private static final String API_KEY = "048769c304ad53e89d72b3dbfc09371a"; // Ganti dengan kunci API TMDb Anda

    public static List<Movie> fetchMoviesFromApi(String title) {
        List<Movie> results = new ArrayList<>();

        try {
            // Ganti URL dengan endpoint pencarian film TMDb
            String apiUrl = "https://api.themoviedb.org/3/search/movie";
            String apiKeyParam = "?api_key=" + API_KEY;
            String queryParam = "&query=" + title;

            // Membuat URL untuk pencarian film
            URL url = new URL(apiUrl + apiKeyParam + queryParam);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set metode HTTP yang diinginkan (GET)
            connection.setRequestMethod("GET");

            // Baca respons dari API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Proses respons JSON menggunakan pustaka Gson
            JsonParser parser = new JsonParser();
            JsonObject jsonResponse = parser.parse(response.toString()).getAsJsonObject();

            // Dapatkan array hasil pencarian film dari respons JSON
            JsonArray resultsArray = jsonResponse.getAsJsonArray("results");

            // Ambil informasi judul dan URL gambar dari setiap objek hasil pencarian
            for (int i = 0; i < resultsArray.size(); i++) {
                JsonObject movieObject = resultsArray.get(i).getAsJsonObject();
                String movieTitle = movieObject.get("title").getAsString();
                String imageUrl = "https://image.tmdb.org/t/p/w500" + movieObject.get("poster_path").getAsString();
                String movieDesc = movieObject.get("overview").getAsString();

                results.add(new Movie(movieTitle, imageUrl, movieDesc));
            }

            // Tutup koneksi
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle kesalahan koneksi atau pemrosesan respons di sini
            MovieSearchController.showAlert("Error", "Gagal dalam mengambil data, cek koneksi anda, dan coba lagi!");
        }

        return results;
    }
}
