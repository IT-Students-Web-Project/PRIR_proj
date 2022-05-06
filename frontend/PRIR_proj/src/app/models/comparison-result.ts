export interface ComparisonResult {
    originalFileId: string;
    originalFileName: string;
    comparedSentences: number;
    originalSentences: number;
    identicalSentences: number;
    similarityDegree: number;
    identicalSentencesList: string[];
}