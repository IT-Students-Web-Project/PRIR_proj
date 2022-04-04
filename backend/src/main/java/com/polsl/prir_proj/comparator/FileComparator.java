package com.polsl.prir_proj.comparator;

import java.util.ArrayList;

public class FileComparator {

    public ComparisonResult compareForSentences(FileContent original,FileContent compared) {
        int identical = 0;
        ArrayList<String> identicalSentences = new ArrayList<>();
        for(String sentence : compared.getSentences()) {
            for(String orgSentence : original.getSentences()) {
                if(sentence.equalsIgnoreCase(orgSentence)) {
                    identical++;
                    identicalSentences.add(sentence);
                }
            }
        }

        return new ComparisonResult(
                compared.getSentences().size(),
                original.getSentences().size(),
                identical,
                identicalSentences,
                original.getFileId()
        );
    }
}
