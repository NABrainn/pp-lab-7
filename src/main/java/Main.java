import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
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
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                browseDirectory();
            }
        };
        browseButton.setOnAction(event);
        Button searchButton = new Button("Search");
        EventHandler<ActionEvent> search = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                searchFiles();
            }
        };
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
        }
        File directory = new File(directoryPathField.getText());
        if(!directory.isDirectory()) {
            resultArea.setText("The provided path is not a directory.");
        }
        StringBuilder results = new StringBuilder();
        listFilesInDirectory(directory, results);
        resultArea.setText(results.toString());
    }


    private void browseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if(selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void listFilesInDirectory(File directory, StringBuilder results) {
        File[] files = directory.listFiles();
        if (files != null) {
            for(File file : files) {
                results.append(file.getAbsolutePath()).append("\n");
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}