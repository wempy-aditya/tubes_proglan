package com.example.tubes_proglan;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
public class MovieSearchController {
    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private GridPane resultGrid;

    @FXML
    private void searchButtonClicked() {
        String searchQuery = searchField.getText().trim();
        if (!searchQuery.isEmpty()) {
            // Panggil metode backend untuk mendapatkan hasil pencarian
            List<Movie> searchResults = MovieSearchBackend.fetchMoviesFromApi(searchQuery);

            // Menyimpan hasil pencarian ke dalam file
            for (Movie movie : searchResults) {
                FileHandler.saveToFile(movie.getTitle(), movie.getImageUrl(), movie.getDescription(), "search_results.txt");
            }

            // Hapus semua node dari resultGrid sebelum menambahkan kartu-kartu baru
            resultGrid.getChildren().clear();

            // Menambahkan setiap hasil pencarian sebagai kartu
            int column = 0;
            int row = 0;
            for (Movie movie : searchResults) {
                // Ganti dengan logika pembuatan kartu yang sesuai
                Label card = createCard(movie);

                // Menambahkan event handler untuk menangani klik kartu
                card.setOnMouseClicked(event -> showMovieDetailDialog(movie));

                resultGrid.add(card, column, row);

                // Ubah posisi baris dan kolom untuk setiap kartu
                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    @FXML
    private void showHistory() {
        List<String[]> historyEntries = FileHandler.readAllFromFile("search_results.txt");

        // Bersihkan hasil pencarian sebelum menambahkan kartu-kartu baru
        resultGrid.getChildren().clear();

        // Menambahkan setiap entri histori sebagai kartu
        int column = 0;
        int row = 0;
        for (String[] entry : historyEntries) {
            // Ganti dengan logika pembuatan kartu yang sesuai
            Label card = createCardHist(entry[1], entry[2]);

            resultGrid.add(card, column, row);

            // Ubah posisi baris dan kolom untuk setiap kartu
            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }


    private Label createCard(Movie movie) {
        // Ganti dengan logika pembuatan kartu yang sesuai
        Label card = new Label(movie.getTitle());
        card.getStyleClass().add("result-card");

        // Tambahkan ImageView untuk menampilkan gambar
        ImageView imageView = new ImageView(new Image(movie.getImageUrl()));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        // Tambahkan ImageView ke dalam kartu
        VBox cardContent = new VBox(imageView, card);
        cardContent.setAlignment(Pos.CENTER);
        card.setGraphic(cardContent);

        return card;
    }

    private Label createCardHist(String title, String imageUrl) {
        // Buat label untuk judul film
        Label card = new Label(title);
        card.getStyleClass().add("result-card");

        // Buat ImageView untuk menampilkan gambar film
        ImageView imageView = new ImageView(new Image(imageUrl));
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        // Menambahkan ImageView ke dalam kartu
        VBox cardContent = new VBox(imageView, card);
        cardContent.setAlignment(Pos.CENTER);
        card.setGraphic(cardContent);

        return card;
    }


    @FXML
    private void clearResults() {
        // Bersihkan hasil pencarian
        resultGrid.getChildren().clear();
    }

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showMovieDetailDialog(Movie movie) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Detail Film");
        alert.setHeaderText(null);
        alert.setContentText(getMovieDescription(movie)); // Gantilah ini dengan logika yang sesuai

        // Tambahkan gambar ke dalam dialog (opsional)
        Image image = new Image(movie.getImageUrl());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200); // Sesuaikan ukuran sesuai kebutuhan
        imageView.setPreserveRatio(true);
        alert.getDialogPane().setGraphic(imageView);

        // Tambahkan tombol OK
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);

        alert.showAndWait();
    }
    private String getMovieDescription(Movie movie) {
        // Gantilah ini dengan logika mendapatkan deskripsi film, misalnya dari API lain atau database lokal
        return "Judul film " + movie.getTitle() + "\n" + "Deskripsi film: " + movie.getDescription() + "\n";
    }
}