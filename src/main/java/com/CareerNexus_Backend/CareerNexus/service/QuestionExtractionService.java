package com.CareerNexus_Backend.CareerNexus.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource; // Import the generic Resource
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionExtractionService {

    private final ChatClient chatClient;

    @Autowired
    public QuestionExtractionService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    public String extractQuestionsFromFile(Resource fileResource) {

        String promptText = """
               
        You are an automated API endpoint. Your ONLY function is to parse the text of a document and return a structured JSON object.

                Analyze the following document text and perform these actions:
        1.  Identify all multiple-choice questions and their corresponding correct answers.
        2.  Construct a single JSON object as the output.
        3.  This object MUST contain exactly two top-level keys: "questions" and "answers".
        4.  The value for "questions" MUST be a JSON array of objects. Each object must contain the keys: "question_no", "question_text", and "options".
        5.  The value for "answers" MUST be a JSON array of objects. Each object must contain the keys: "question_no" and "correct_answer".
        6.  The value of "correct_answer" MUST exactly match one of the strings in the corresponding "options" array.
        7.  Ignore all non-essential text like page numbers, instructions, or explanations.

                CRITICAL FINAL INSTRUCTION: Your entire response must be ONLY the raw JSON object itself. Do NOT include the word "json", markdown backticks (
        """;

        Media pdfMedia = new Media(MediaType.APPLICATION_PDF, fileResource);


        UserMessage userMessage = new UserMessage(promptText, List.of(pdfMedia));
        Prompt prompt = new Prompt(userMessage);

        // Call the AI model and return the content.
        return chatClient.prompt(prompt).call().content();
    }
}

