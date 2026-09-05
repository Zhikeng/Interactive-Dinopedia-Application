package com.dinopedia;

public class Dinosaur {
    private String name;
    private String period; // e.g., Cretaceous
    private String diet;   // e.g., Carnivore
    private String length; // e.g., 12 meters
    private String description;
    private String imagePath;

    public Dinosaur(String name, String period, String diet, String length, String description, String imagePath) {
        this.name = name;
        this.period = period;
        this.diet = diet;
        this.length = length;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public String getPeriod() { return period; }
    public String getDiet() { return diet; }
    public String getLength() { return length; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }

    @Override
    public String toString() {
        return name; // Displays name nicely inside the JavaFX ListView
    }
}
