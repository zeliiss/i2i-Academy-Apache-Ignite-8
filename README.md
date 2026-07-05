# Apache Ignite Subscriber Simulation

This project was developed as a task for the **i2i Systems Internship** program. It is a Java-based simulation that demonstrates database operations using Apache Ignite 3.

## Project Overview

The application connects to a local Apache Ignite node, sets up a database table, and simulates mobile subscriber usage. The workflow includes the following steps:

1. **Connection:** Connects to the Apache Ignite 3 Thin Client via port 10800.
2. **Initialization:** Creates a `Subscriber` table and clears previous data for a fresh execution.
3. **Insertion:** Inserts 5 dummy subscriber records with initial usage values (0 MB, 0 SMS, 0 Minutes).
4. **Simulation:** Generates random usage amounts for data, SMS, and calls, then updates the database records.
5. **Output:** Retrieves the updated records from the database and prints the final states to the console.

## Technologies Used

* Java
* Apache Ignite 3 (Thin Client API)
* Maven
* Docker

## How to Run

1. Start the official Apache Ignite 3 container via Docker (ensure ports 10800 and 10300 are exposed).
2. Clone this repository to your local machine.
3. Open the project in your preferred IDE (e.g., VS Code).
4. Run the `Main.java` file.
5. View the simulation results in the terminal console.
