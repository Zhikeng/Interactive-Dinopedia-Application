package com.dinopedia;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class DinoPediaController {

    @FXML private TextField searchField;
    @FXML private ListView<Dinosaur> dinoListView;
    @FXML private Label nameLabel;
    @FXML private Label periodLabel;
    @FXML private Label dietLabel;
    @FXML private Label lengthLabel;
    @FXML private TextArea descriptionArea;
    @FXML private ImageView dinoImageView;
    @FXML private Button roar_button;
    @FXML private Button growl_button;
    @FXML private Button call_button;
    @FXML private MediaPlayer mediaPlayer_roar;
    @FXML private MediaPlayer mediaPlayer_growl;
    @FXML private MediaPlayer mediaPlayer_call;

    private final ObservableList<Dinosaur> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws CsvValidationException {
    	// 1. load the sample database to the portal
        loadSampleDatabase();

        FilteredList<Dinosaur> filteredData = new FilteredList<>(masterData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(dino -> {
                if (newValue == null || newValue.isEmpty()) return true;
                return dino.getName().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        dinoListView.setItems(filteredData);
        dinoListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> displayDetails(newSel));
        
        // call button won't appear unless pterodactyl's info pops up
        call_button.setVisible(false);
        roar_button.setVisible(true);
        growl_button.setVisible(true);
        
        // 2. Locate the MP3 file in your resources folder
        URL resource_roar = getClass().getResource("/audios/dinosaur-roar.mp3");
        URL resource_growl = getClass().getResource("/audios/dinosaur-growl.mp3");
        URL resource_call = getClass().getResource("/audios/pterodactyl-call.mp3");
        
        if (resource_roar == null || resource_growl == null) {
            System.err.println("Error: MP3 file not found in resources!");
            return;
        }
        
        // 3. Create the Media and MediaPlayer objects
        Media media_roar = new Media(resource_roar.toExternalForm());
        Media media_growl = new Media(resource_growl.toExternalForm());
        Media media_call = new Media(resource_call.toExternalForm());
        mediaPlayer_roar = new MediaPlayer(media_roar);
        mediaPlayer_growl = new MediaPlayer(media_growl);
        mediaPlayer_call = new MediaPlayer(media_call);
        
    }

    private void displayDetails(Dinosaur dino) {
        if (dino == null) return;
        nameLabel.setText(dino.getName());
        periodLabel.setText("Period: " + dino.getPeriod());
        dietLabel.setText("Diet: " + dino.getDiet());
        lengthLabel.setText("Length: " + dino.getLength());
        descriptionArea.setText(dino.getDescription());

        try {
            dinoImageView.setImage(new Image(getClass().getResourceAsStream(dino.getImagePath())));
        } catch (Exception e) {
            dinoImageView.setImage(null);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please provide the image path information.");
			alert.setContentText("The image path is not working: " + dino.getImagePath());
			alert.showAndWait();
        }
        
        // when Pteranodon information pops up, the call button becomes visible and the other buttons disappear
        if (dino.getName().equalsIgnoreCase("Pteranodon")) {
        	call_button.setVisible(true);
        	roar_button.setVisible(false);
        	growl_button.setVisible(false);
        } else {
        	call_button.setVisible(false);
        	roar_button.setVisible(true);
        	growl_button.setVisible(true);
        }
    }

    private void loadSampleDatabase() throws CsvValidationException {
        // SQL query equivalent: SELECT * FROM dinosaurs
//        masterData.add(new Dinosaur(
//            "Tyrannosaurus Rex", "Late Cretaceous", "Carnivore", "12 meters",
//            "One of the largest land predators known, equipped with massive jaws and sharp serrated teeth.",
//            "/images/trex.png"
//        ));
//        masterData.add(new Dinosaur(
//            "Triceratops", "Late Cretaceous", "Herbivore", "9 meters",
//            "Recognizable by its large bony frill and three sharp facial horns used for defense.",
//            "/images/triceratop.png"
//        ));
//        masterData.add(new Dinosaur(
//            "Velociraptor", "Late Cretaceous", "Carnivore", "2 meters",
//            "A agile, feathered bipedal predator featuring a enlarged sickle-shaped claw on each hind foot.",
//            "/images/velociraptor.png"
//        ));
    	String filePath = "DinoDatabase.csv";
 
    	try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
				.withCSVParser(new CSVParserBuilder().withSeparator(',').build()).build()) {
			String[] nextLine;

			try {
				while ((nextLine = reader.readNext()) != null) {
					String name = nextLine[0];
					String period = nextLine[1];
					String diet = nextLine[2];
					String length = nextLine[3];
					String description = nextLine[4];
					String imagePath = nextLine[5];
					masterData.add(new Dinosaur(name,period,diet,length,description,imagePath));
				}
			} catch (CsvValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	} catch (IOException e) {
    		System.err.println("Error reading the CSV file: " + e.getMessage());
    	}
        
    }
    
    @FXML
    private void handleRoarAction() {
        if (mediaPlayer_roar != null) {
            // Stop the audio if it's already playing, then play from the start
            mediaPlayer_roar.stop(); 
            mediaPlayer_roar.play();
        }
    }
    
    @FXML
    private void handleGrowlAction() {
        if (mediaPlayer_growl != null) {
            // Stop the audio if it's already playing, then play from the start
            mediaPlayer_growl.stop(); 
            mediaPlayer_growl.play();
        }
    }
    
    @FXML
    private void handleCallAction() {
    	if (mediaPlayer_call != null) {
            // Stop the audio if it's already playing, then play from the start
            mediaPlayer_call.stop(); 
            mediaPlayer_call.play();
    	}
    }
    
    
}
