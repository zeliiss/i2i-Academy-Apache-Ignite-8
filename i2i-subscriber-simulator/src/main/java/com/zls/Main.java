package com.zls;

import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.sql.ResultSet;
import org.apache.ignite.sql.SqlRow;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("i2i Simulation starting...");

        // Veritabanina Baglanti
        try (IgniteClient client = IgniteClient.builder()
                .addresses("127.0.0.1:10800")
                .build()) {

            System.out.println("Connected to Apache Ignite 3 node successfully.");

            // Tablo Olusturma
            client.sql().execute(null,
                    "CREATE TABLE IF NOT EXISTS Subscriber (" +
                    "customerId VARCHAR PRIMARY KEY, " +
                    "dataUsage DOUBLE, " +
                    "smsUsage INT, " +
                    "callUsage INT)"
            );

            // Adim 5: Tabloyu Temizleme
            client.sql().execute(null, "DELETE FROM Subscriber");
            System.out.println("Table cleared and ready for new simulation.");

            // Adim 6: 5 Dummy Subscriber Ekleme
            System.out.println("Inserting 5 dummy subscribers with initial values 0...");
            for (int i = 1; i <= 5; i++) {
                String custId = "CUST-" + i;
                client.sql().execute(null,
                        "INSERT INTO Subscriber (customerId, dataUsage, smsUsage, callUsage) VALUES (?, ?, ?, ?)",
                        custId, 0.0, 0, 0);
            }
            System.out.println("Subscribers inserted successfully.");

            // Adim 7: Simulasyon (Veri Cekme ve Guncelleme)
            System.out.println("Running usage simulation...");
            Random random = new Random();
            
            try (ResultSet<SqlRow> rs = client.sql().execute(null, "SELECT customerId, dataUsage, smsUsage, callUsage FROM Subscriber")) {
                while (rs.hasNext()) {
                    SqlRow row = rs.next();
                    String id = row.stringValue("customerId");
                    
                    double newData = Math.round((row.doubleValue("dataUsage") + random.nextDouble() * 5.0) * 100.0) / 100.0; 
                    int newSms = row.intValue("smsUsage") + random.nextInt(20); 
                    int newCall = row.intValue("callUsage") + random.nextInt(50); 
                    
                    client.sql().execute(null,
                            "UPDATE Subscriber SET dataUsage = ?, smsUsage = ?, callUsage = ? WHERE customerId = ?",
                            newData, newSms, newCall, id);
                }
            }
            System.out.println("Simulation completed, database updated.");

            // Adim 8: Son Durumu Konsola Yazdirma
            System.out.println("\nFINAL SUBSCRIBER STATES:");
            try (ResultSet<SqlRow> finalRs = client.sql().execute(null, "SELECT customerId, dataUsage, smsUsage, callUsage FROM Subscriber")) {
                while (finalRs.hasNext()) {
                    SqlRow row = finalRs.next();
                    
                    Subscriber sub = new Subscriber(
                            row.stringValue("customerId"),
                            row.doubleValue("dataUsage"),
                            row.intValue("smsUsage"),
                            row.intValue("callUsage")
                    );
                    
                    System.out.println(sub.toString());
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}