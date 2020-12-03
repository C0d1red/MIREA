package com.c0d1red;

import com.c0d1red.caesar.Caesar;
import com.c0d1red.caesar.FrequencyAnalysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class TestScript {
    private static final int CAESAR_KEY = 100;
    private static final String OUTPUT_DIRECTORY = "output";
    private static final String FILENAME_ENCRYPTED_BY_KEY = "encrypted_by_key.txt";
    private static final String FILENAME_DECRYPTED_BY_KEY = "decrypted_by_key.txt";
    private static final String FILENAME_DECRYPTED_BY_SOLO_FREQUENCY = "decrypted_by_solo_frequency.txt";
    private static final String FILENAME_DECRYPTED_BY_BIGRAM_FREQUENCY = "decrypted_by_bigram_frequency.txt";
    private final Caesar caesar;
    private final FrequencyAnalysis frequencyAnalysis;

    public TestScript() throws IOException {
         caesar = new Caesar(CAESAR_KEY);
         frequencyAnalysis = new FrequencyAnalysis();
         if (!Files.exists(Path.of(OUTPUT_DIRECTORY))){
             Files.createDirectory(Path.of(OUTPUT_DIRECTORY));
         }
    }

    public String runEncryptScript(String term) throws IOException {
        String encryptedTerm = caesar.encrypt(term);
        Files.writeString(Paths.get(OUTPUT_DIRECTORY, FILENAME_ENCRYPTED_BY_KEY), encryptedTerm);
        return encryptedTerm;
    }

    public String runDecryptScript(String encryptedTerm) throws IOException {
        String decryptedTerm = caesar.decrypt(encryptedTerm);
        Files.writeString(Paths.get(OUTPUT_DIRECTORY, FILENAME_DECRYPTED_BY_KEY), decryptedTerm);
        return decryptedTerm;
    }

    public Map<String, Float> runSoloFrequencyScript(String term, String cryptType) {
        Map<String, Float> soloFrequencyMap = frequencyAnalysis.makeSoloAnalysis(term);
        System.out.println();
        System.out.println(cryptType + " solo frequency map:\n" + soloFrequencyMap);
        return soloFrequencyMap;
    }

    public Map<String, Float> runBigramFrequencyScript(String term, String cryptType) {
        Map<String, Float> bigramFrequencyMap= frequencyAnalysis.makeBigramAnalysis(term);
        System.out.println();
        System.out.println(cryptType + " bigram frequency map:\n" + bigramFrequencyMap);
        return bigramFrequencyMap;
    }

    public void runSoloFrequencyDecryptScript(String encryptedTerm,
                                              Map<String, Float> decryptedSoloFrequencyMap,
                                              Map<String, Float> encryptedSoloFrequencyMap) throws IOException {
        String soloFrequencyDecryptedTerm = caesar.decryptBySoloFrequency(encryptedTerm, decryptedSoloFrequencyMap, encryptedSoloFrequencyMap);
        Files.writeString(Paths.get(OUTPUT_DIRECTORY, FILENAME_DECRYPTED_BY_SOLO_FREQUENCY), soloFrequencyDecryptedTerm);
    }

    public void runBigramFrequencyDecryptScript(String encryptedTerm,
                                                Map<String, Float> decryptedBigramFrequencyMap,
                                                Map<String, Float> encryptedBigramFrequencyMap) throws IOException {
        String soloFrequencyDecryptedTerm = caesar.decryptByBigramFrequency(encryptedTerm, decryptedBigramFrequencyMap, encryptedBigramFrequencyMap);
        Files.writeString(Paths.get(OUTPUT_DIRECTORY, FILENAME_DECRYPTED_BY_BIGRAM_FREQUENCY), soloFrequencyDecryptedTerm);
    }

}
