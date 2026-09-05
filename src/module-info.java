module Dinosaur_Encyclopedia {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.opencsv;
	requires javafx.media;
	
	opens com.dinopedia to javafx.graphics, javafx.fxml;
}
