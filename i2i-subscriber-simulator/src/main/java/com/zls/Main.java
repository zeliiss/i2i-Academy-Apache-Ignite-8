package com.zls;

import org.apache.ignite.client.IgniteClient;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Apache Ignite 3 düğümüne bağlantı başlatılıyor...");

        try (IgniteClient client = IgniteClient.builder()
                .addresses("127.0.0.1:10800")
                .build()) {
            
            System.out.println("✅ Apache Ignite 3 düğümüne başarıyla bağlanıldı!");
            
            
        } catch (Exception e) {
            System.err.println("❌ Ignite bağlantısı sırasında bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
