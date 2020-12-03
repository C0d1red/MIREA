package com.c0d1red;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Main {
    private static final String FILENAME_INPUT = "text.txt";

    public static void main(String[] args) throws IOException {
        String term = Files.readString(Path.of(FILENAME_INPUT), StandardCharsets.UTF_8);

        TestScript testScript = new TestScript();
        String encryptedTerm = testScript.runEncryptScript(term);
        String decryptedTerm = testScript.runDecryptScript(encryptedTerm);

        Map<String, Float> encryptedSoloFrequencyMap = testScript.runSoloFrequencyScript(encryptedTerm, "Encrypted");
        Map<String, Float> decryptedSoloFrequencyMap = testScript.runSoloFrequencyScript(decryptedTerm, "Decrypted");

        Map<String, Float> encryptedBigramFrequencyMap= testScript.runBigramFrequencyScript(encryptedTerm, "Encrypted");
        Map<String, Float> decryptedBigramFrequencyMap = testScript.runBigramFrequencyScript(decryptedTerm, "Decrypted");

        testScript.runSoloFrequencyDecryptScript(encryptedTerm, decryptedSoloFrequencyMap, encryptedSoloFrequencyMap);
        testScript.runBigramFrequencyDecryptScript(encryptedTerm, decryptedBigramFrequencyMap, encryptedBigramFrequencyMap);
    }

}
