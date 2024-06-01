import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {

    private TextField directoryPathField;
    private TextField searchField;
    private TextArea resultArea;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("File Browser and Search");
        directoryPathField = new TextField();
        directoryPathField.setPromptText("Enter directory path");
        searchField = new TextField();
        searchField.setPromptText("Enter search phrase");
        Button browseButton = new Button("Browse");
        browseButton.setOnAction(e -> browseDirectory());
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchFiles());
        this.resultArea = new TextArea();
        resultArea.setPrefHeight(400);
        HBox hBox = new HBox(10, directoryPathField, browseButton);
        VBox vBox = new VBox(10, hBox, searchField, searchButton, resultArea);
        Scene scene = new Scene(vBox, 600, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void searchFiles() {
        if(directoryPathField.getText().isEmpty()) {
            resultArea.setText("Please provide a directory path.");
            return;
        }
        File directory = new File(directoryPathField.getText());
        if(!directory.isDirectory()) {
            resultArea.setText("The provided path is not a directory.");
            return;
        }
        StringBuilder results = new StringBuilder();
        searchInDirectory(directory, results, searchField.getText());
        resultArea.setText(results.toString());
    }


    private void browseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void searchInDirectory(File directory, StringBuilder results, String searchPhrase) {
        File[] files = directory.listFiles();
        if (files != null) {
            for(File file : files) {
                if (containsPhrase(file, searchPhrase)) {
                    results.append(file.getAbsolutePath()).append("\n");
                }
            }
        }
    }

    private boolean containsPhrase(File file, String searchPhrase) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchPhrase)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public static void main(String[] args) {
        launch(args);
    }
}