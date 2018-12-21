# Hotel Management Simulation with JDBC
A Hotel Reservation System in completion of a CS157A Semester Project. Utilized Java Swing with JDBC. Assists hotel staff and hotel guests with the entire logistical cycle. 

## Installation:

### Database set-up:

1. This project was developed on MySQl Distrib 5.7.23. It is advised to run the application on that version.
2. On your MySQL instance, create a database called hotelmanagement.
3. Run createDatabase.sql on hotelmanagement in order to create all the necessary tables. 
4. For required triggers, run triggers.sql on hotelmanagement DB.
5. For required stored procedures, run procedures.sql on the DB instance.
6. To populate the database with data, run populateDatabase.sql.

### Java Project set-up:

1. This project was developed on Java 8 SE and runs on the Java SE Runtime Environment v1.8.0_181.
2. Import "hotel-management" as a project on your Java IDE. 
3. If your IDE does not have the MySQL Java Connector Jar file installed, we have provided a copy of it in the directory.
4. Simply add the mysql-connector-java-5.1.37-bin.jar to your IDE's library build system. 
5. On Eclipse, right click "JRE System Library" and under "Build Path," click "Configure Build Path."
6. Click "Add external JAR" and upload the provided "mysql-connector-java-5.1.37-bin.jar" file. 
7. Edit "db.properties" so that "MYSQL_DB_USERNAME" contains the user that you created the DB with and that "MYSQL_DB_PASSWORD" contains the associated password.
8. In order to run the application, run "Runner.java"
