package com.ecommerce.ecommerce_backend.services;

import com.ecommerce.ecommerce_backend.dtos.SentimentAnalysisResult;
import com.ecommerce.ecommerce_backend.enums.SentimentLabel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentAnalysisService {

    public SentimentAnalysisResult analyzeText(String text) {
        // Implémentation simulée - en production utiliseriez un service d'IA réel
        double score = calculateSentimentScore(text);
        SentimentLabel sentiment = classifySentiment(score);

        return new SentimentAnalysisResult(sentiment, score);
    }

    private double calculateSentimentScore(String text) {
        // Logique simplifiée de calcul de score
        String lowerText = text.toLowerCase();
        int positiveWords = countMatches(lowerText, List.of("good", "great", "excellent"));
        int negativeWords = countMatches(lowerText, List.of("bad", "poor", "terrible"));

        return (positiveWords - negativeWords) / 10.0; // Normalisé entre -1 et 1
    }

    private int countMatches(String text, List<String> words) {
        return (int) words.stream()
                .filter(text::contains)
                .count();
    }

    private SentimentLabel classifySentiment(double score) {
        if (score > 0.3) return SentimentLabel.POSITIVE;
        if (score < -0.3) return SentimentLabel.NEGATIVE;
        return SentimentLabel.NEUTRAL;
    }
}