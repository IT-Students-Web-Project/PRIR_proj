package com.polsl.prir_proj.comparator;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ComparisonResult {

    private String originalFileId;
    private String originalFileName;
    private int comparedSentences;
    private int originalSentences;
    private int identicalSentences;
    private double similarityDegree;
    private ArrayList<String> identicalSentencesList;

    public ComparisonResult(
            int comparedSentences, int originalSentences, int identicalSentences,
            ArrayList<String> identicalSentencesList, String originalFileId,
            String originalFileName) {
        this.comparedSentences = comparedSentences;
        this.originalSentences = originalSentences;
        this.identicalSentences = identicalSentences;
        this.originalFileName = originalFileName;
        this.similarityDegree = (double) identicalSentences / comparedSentences * 100;
        this.identicalSentencesList = identicalSentencesList;
        this.originalFileId = originalFileId;
    }
}
