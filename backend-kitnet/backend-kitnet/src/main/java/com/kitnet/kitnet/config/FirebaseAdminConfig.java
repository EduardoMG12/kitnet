package com.kitnet.config; // Pacote da sua aplicação

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseAdminConfig {

    // Define o caminho do arquivo JSON da chave de serviço no application.properties
    // Para desenvolvimento: src/main/resources/kitnet-firebase-adminsdk-xxxxx.json
    // Para produção: Caminho de um arquivo externo ou diretamente o conteúdo via variável de ambiente
    @Value("${firebase.service-account.path}")
    private String serviceAccountPath;

    @PostConstruct
    public void initializeFirebaseApp() {
        try {
            // Tenta carregar o arquivo do classpath (bom para desenvolvimento local)
            Resource resource = new ClassPathResource(serviceAccountPath);
            InputStream serviceAccount = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    // Se você tiver um banco de dados Firebase Realtime Database, configure a URL aqui
                    // .setDatabaseUrl("https://<YOUR_DATABASE_NAME>.firebaseio.com")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase Admin SDK inicializado com sucesso!");
            } else {
                System.out.println("Firebase Admin SDK já está inicializado.");
            }

        } catch (IOException e) {
            System.err.println("Erro ao inicializar Firebase Admin SDK: " + e.getMessage());
            throw new RuntimeException("Falha ao inicializar o Firebase Admin SDK", e);
        }
    }
}