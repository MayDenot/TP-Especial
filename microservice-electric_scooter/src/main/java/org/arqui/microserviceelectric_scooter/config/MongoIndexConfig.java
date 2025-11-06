package org.arqui.microserviceelectric_scooter.config;

import com.mongodb.client.MongoCollection;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoIndexConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void createIndexes() {
        try {
            System.out.println("========================================");
            System.out.println("🔧 INICIANDO CONFIGURACIÓN DE ÍNDICES");
            System.out.println("========================================");

            MongoCollection<Document> collection = mongoTemplate.getCollection("electric_scooters");

            // Listar índices existentes
            System.out.println("📋 Índices actuales:");
            boolean indexExists = false;
            for (Document index : collection.listIndexes()) {
                System.out.println("  - " + index.toJson());
                if (index.containsKey("key")) {
                    Document key = (Document) index.get("key");
                    if (key.containsKey("ubicacion")) {
                        indexExists = true;
                    }
                }
            }

            if (indexExists) {
                System.out.println("✅ El índice geoespacial YA EXISTE");
            } else {
                System.out.println("🔨 CREANDO índice geoespacial...");
                Document indexKeys = new Document("ubicacion", "2dsphere");
                collection.createIndex(indexKeys);
                System.out.println("✅ ÍNDICE GEOESPACIAL CREADO EXITOSAMENTE");
            }

            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("❌ ERROR al configurar índices:");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
