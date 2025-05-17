package com.ecommerce.ecommerce_backend.dtos;

import com.ecommerce.ecommerce_backend.enums.SentimentLabel;
import lombok.Data;

@Data
public class SentimentAnalysisResult {
    private SentimentLabel sentiment;
    private double score;

    public SentimentAnalysisResult(SentimentLabel sentiment, double score) {
        this.sentiment = sentiment;
        this.score = score;
    }

    public SentimentLabel getSentiment() {
        return sentiment;
    }

    public void setSentiment(SentimentLabel sentiment) {
        this.sentiment = sentiment;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}