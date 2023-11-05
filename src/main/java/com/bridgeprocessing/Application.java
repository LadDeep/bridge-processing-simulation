package com.bridgeprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Author: Deep Pravinbhai Lad - B00938094
 * The Application class simulates frame generation and processing by a bridge.
 */
public class Application {

    public static void main(String[] args) {
        // Generate random frames

        FrameGenerator generator = new FrameGenerator(100, "RandomFrames.txt", 4);
        generator.generateRandomFrames();

        // simulating that the bridge is receiving frames and it is processing on the
        // go.
        String filePath = "src/main/java/com/bridgeprocessing/RandomFrames.txt";
        BridgeProcessor bridgeProcessor = new BridgeProcessor();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String sourceMAC, destMAC, arrivalPort;
            while ((sourceMAC = br.readLine()) != null) {
                destMAC = br.readLine();
                arrivalPort = br.readLine();
                bridgeProcessor.processFrame(sourceMAC, destMAC, arrivalPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}