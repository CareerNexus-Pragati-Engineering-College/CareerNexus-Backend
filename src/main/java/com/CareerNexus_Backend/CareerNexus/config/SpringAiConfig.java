package com.CareerNexus_Backend.CareerNexus.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiConfig {

    /**
     * Creates a ChatClient bean using the auto-configured ChatClient.Builder.
     * The Spring AI starters will automatically create a ChatClient.Builder bean
     * based on your application.properties. This method simply finalizes the build.
     * This explicit definition resolves potential auto-configuration issues.
     *
     * @param builder the auto-configured ChatClient.Builder instance.
     * @return a configured ChatClient instance ready for injection.
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}

