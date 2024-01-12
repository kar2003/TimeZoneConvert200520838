package com.example.timezoneconvert200520838.controller;

import com.example.timezoneconvert200520838.Main;
import com.example.timezoneconvert200520838.model.TimezoneInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.asynchttpclient.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TimeZoneController {
    @FXML
    private TextField latitudeField;

    @FXML
    private TextField longitudeField;

    @FXML
    private Label resultLabel;

    private String countryName;
    private String timezoneId;

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;

    public void findTimezone() throws IOException {
        double latitude = Double.parseDouble(latitudeField.getText());
        double longitude = Double.parseDouble(longitudeField.getText());

        // Use latitude and longitude in the API call
        String apiUrl = "https://timezone-by-location.p.rapidapi.com/timezone?lat=" + latitude + "&lon=" + longitude + "&c=1&s=0";
        String apiKey = "695bc10799mshb7ac0656b21b95ep1507f4jsna4b16e634c0b";

        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", apiUrl)
                .setHeader("X-RapidAPI-Key", apiKey)
                .setHeader("X-RapidAPI-Host", "timezone-by-location.p.rapidapi.com")
                .execute()
                .toCompletableFuture()
                .thenAccept(response -> {
                    // Parse the JSON response
                    JSONParser parser = new JSONParser();
                    try {
                        JSONObject jsonResult = (JSONObject) parser.parse(response.getResponseBody());
                        // Getting Zones array
                        JSONArray zonesArray = (JSONArray) jsonResult.get("Zones");
                        // Getting the first element in the array
//                        JSONObject firstZone = (JSONObject) zonesArray.getFirst();
                        JSONObject firstZone = (JSONObject) zonesArray.get(0);



                        TimezoneInfo timezoneInfo = new TimezoneInfo();
                        timezoneInfo.setCountryName((String) firstZone.get("CountryName"));
                        timezoneInfo.setTimezoneId((String) firstZone.get("TimezoneId"));

                        countryName = timezoneInfo.getCountryName();
                        timezoneId = timezoneInfo.getTimezoneId();

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .join();

        client.close();
    }

    public void setResultLabelText(String text) {
        resultLabel.setText(text);
    }

    @FXML
    protected void convertToTimeZone(ActionEvent clickEvent) throws IOException {
        findTimezone();
//        System.out.println("Country: " + countryName + "\nTimezone: " + timezoneId);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("result.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)clickEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,320, 240);
        TimeZoneController controller = loader.getController();
        controller.setResultLabelText(String.format("Country: %s \nTimezone: %s ", countryName, timezoneId));
        stage.setTitle("TimeZoneFinder");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void homePage(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("time-zone.fxml"));
        stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root, 320, 240);
        stage.setTitle("TimeZoneFinder");
        stage.setScene(scene);
        stage.show();
    }
}
