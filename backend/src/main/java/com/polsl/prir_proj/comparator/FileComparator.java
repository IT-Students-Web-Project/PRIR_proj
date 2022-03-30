package com.polsl.prir_proj.comparator;

public class FileComparator {

    public ComparisonResult compareForSentences(FileContent original,FileContent compared) {
        int identical = 0;

        for(String sentence : compared.getSentences()) {
            for(String orgSentence : original.getSentences()) {
                if(sentence.equalsIgnoreCase(orgSentence))
                    identical++;
            }
        }

        return new ComparisonResult(
                compared.getSentences().size(),
                original.getSentences().size(),
                identical
        );
    }
}
