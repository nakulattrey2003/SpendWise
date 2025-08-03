package com.backend.spendwise.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvLoader {
    static {
        try {
            // This will NOT throw an error if .env is missing
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

            // Load values from .env only if available
            dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
            );

        } catch (Exception ignored) {
            // On Render, we expect no .env file
        }
    }
}

// package com.backend.spendwise.config;

// import io.github.cdimascio.dotenv.Dotenv;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class EnvLoader {
//     static {
//         Dotenv dotenv = Dotenv.load();
//         dotenv.entries().forEach(e ->
//             System.setProperty(e.getKey(), e.getValue())
//         );
//     }
// }
