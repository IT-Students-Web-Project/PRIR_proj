package com.polsl.prir_proj.comparator;

import lombok.Getter;

@Getter
public class ComparisonResult {

    private int comparedSentences;
    private int originalSentences;
    private int identicalSentences;
    private double similarityDegree;

    public ComparisonResult(int comparedSentences, int originalSentences, int identicalSentences) {
        this.comparedSentences = comparedSentences;
        this.originalSentences = originalSentences;
        this.identicalSentences = identicalSentences;
        this.similarityDegree = (double) identicalSentences / comparedSentences * 100;
    }
}
