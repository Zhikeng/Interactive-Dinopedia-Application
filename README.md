# Interactive-Dinopedia-Application
DinoPedia is a lightweight JavaFX desktop application designed to provide users with an interactive, searchable encyclopedia of prehistoric creatures. Built with a focus on quick data access and clean UI layout, the application uses a custom CSV file parser to lightweight-manage structured dataset records without external database overhead. 

import os

# Create README.md file content
readme_content = """# 🦕 DinoPedia — Interactive JavaFX Dinosaur Encyclopedia

**DinoPedia** is a modern, interactive desktop application built with **JavaFX** and **SQLite**. It allows users to browse, search, and manage a rich collection of prehistoric creatures with full CRUD (Create, Read, Update, Delete) capability.

---

## 🌟 Key Features

* **Interactive Encyclopedia View:** Browse through dinosaurs with real-time detail views including name, geological period, diet, length, description, and high-resolution images.
* **Instant Dynamic Search:** Real-time search filtering by dinosaur name.
* **Full CRUD Operations:**
  * **Create:** Add new dinosaurs to the encyclopedia through an intuitive dialog form.
  * **Read:** Automatically loads stored dinosaurs from an embedded SQLite database.
  * **Update:** Edit existing dinosaur records directly from the application UI.
  * **Delete:** Remove outdated or incorrect entries with safety confirmation dialogs.
* **Embedded SQLite Database:** Self-contained, zero-configuration local database using standard JDBC.

---

## 🏗️ Technical Architecture & Choice of Database

### Why SQLite over CSV, XML, or NoSQL?

| Storage Option | Rating | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **SQLite / H2 (Chosen)** | ⭐⭐⭐⭐⭐ | Embedded, zero-config file database, supports relational SQL queries, indexing, and scalable updates. | Requires JDBC dependency. |
| **CSV File** | ⭐⭐☆☆☆ | Simple human-readable text format. | High error risk when parsing structured descriptions containing commas or quotes. |
| **XML / JSON File** | ⭐⭐⭐☆☆ | Structured and human-readable. | Requires loading the whole file into memory; poor scalability for search/indexing. |
| **NoSQL Database** | ⭐☆☆☆☆ | Flexible schema. | Overkill for desktop application footprint; introduces unnecessary server/daemon overhead. |

---

## 📂 Project Structure

```text
DinoPedia/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── dinopedia/
│       │           ├── Dinosaur.java             # Data Model
│       │           ├── DatabaseHandler.java      # SQLite JDBC Connection & CRUD Queries
│       │           ├── DinoPediaController.java  # JavaFX UI Controller & Logic
│       │           └── Main.java                 # App Entry Point
│       └── resources/
│           └── com/
│               └── dinopedia/
│                   ├── DinoPedia.fxml            # FXML Layout Definition
│                   └── images/                   # Dinosaur Image Assets (.png)
├── dinopedia.db                                  # Local SQLite Database (auto-generated)
├── pom.xml                                       # Maven Configuration
└── README.md                                     # Project Documentation
