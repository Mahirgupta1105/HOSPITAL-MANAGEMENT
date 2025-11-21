🏥 Hospital Management System (Java + Swing + MySQL)
A GUI-based Hospital Management System developed in Java (Swing) with MySQL database connectivity using JDBC. This project allows users to manage doctors, patients, and appointments from a user-friendly interface.

✨ Features
🔐 Secure Login System (admin panel)
👨‍⚕️ Add Doctor with specialization and contact
🧑‍🤝‍🧑 Register Patients with age, gender, and contact
📅 Book Appointments between patients and doctors
📋 View all records using tabbed tables (Doctors, Patients, Appointments)
🛠 Technologies Used
Java (JDK 8 or higher)
Swing for GUI
JDBC for MySQL connectivity
MySQL (Workbench or CLI)
🗂️ Project Structure

⚙️ How to Run
1. ✅ Requirements
Java (JDK 8 or higher)
MySQL Server
MySQL Workbench (or CLI)
VS Code / IntelliJ / Eclipse
2. 🧬 Setup Database
Open MySQL Workbench
Create a new schema: hospital_db
Import hospital.sql file provided in this repo
CREATE TABLE users ( id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50), password VARCHAR(50) );

CREATE TABLE doctors ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), specialization VARCHAR(100), contact VARCHAR(100) );

CREATE TABLE patients ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), age INT, gender VARCHAR(10), contact VARCHAR(100) );

CREATE TABLE appointments ( id INT AUTO_INCREMENT PRIMARY KEY, patient_id INT, doctor_id INT, date DATE );

-- Sample admin user INSERT INTO users(username, password) VALUES ('admin', 'admin');


These are some screenshots of the Project
![IMG-20250525-WA0010](https://github.com/user-attachments/assets/8353e70a-11da-4153-8909-997df73e30f3)
![IMG-20250525-WA0009](https://github.com/user-attachments/assets/93031df2-b66e-4318-bc2d-3e044d21de56)
![IMG-20250525-WA0008](https://github.com/user-attachments/assets/2d369566-5648-474b-8f39-fa0de1702d79)
![IMG-20250525-WA0007](https://github.com/user-attachments/assets/c28c6bea-ecfb-4b2a-b422-0e0115b9576a)
![IMG-20250525-WA0012](https://github.com/user-attachments/assets/80e9abe6-b6d1-4d75-a9f6-603d93355ff3)
![IMG-20250525-WA0011](https://github.com/user-attachments/assets/9fc58b66-8260-4cad-96f1-0cd0f56aee83)
![IMG-20250525-WA0013](https://github.com/user-attachments/assets/d44ff884-de4e-4dfa-8da4-fbe7f25d2624)
