package com.bridgeprocessing;

import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Deep Pravinbhai Lad - B00938094
 * The FrameGenerator class generates random frames using MAC addresses and
 * ports.
 */
public class FrameGenerator {
    private int noOfFrames;
    private int MIN_NO_PORTS = 1;
    private int MAX_NO_PORTS;
    private String outputFileName;
    private final FileManager fileManager;

    /**
     * Constructor for FrameGenerator class.
     *
     * @param noOfFrames     The number of frames to generate.
     * @param outputFileName The name of the output file for storing generated
     *                       frames.
     * @param MAX_NO_PORTS   The maximum number of ports for the frames.
     */
    FrameGenerator(int noOfFrames, String outputFileName, int maxPorts) {
        this.noOfFrames = noOfFrames;
        this.outputFileName = outputFileName;
        this.MAX_NO_PORTS = maxPorts;
        this.fileManager = new FileManager("src/main/java/com/bridgeprocessing/BridgeFDB.txt");
    }

    /**
     * Retrieves a list of MAC addresses.
     *
     * @return An ArrayList of MAC addresses.
     */
    private ArrayList<String> getAllMACAddress() {
        return fileManager.readAlternateLines();
    }

    /**
     * Generates random frames using available MAC addresses and ports.
     */
    public void generateRandomFrames() {
        ArrayList<String> MACAddressList = getAllMACAddress();
        int macAddressCount = MACAddressList.size();

        if (macAddressCount > 0) {
            StringBuilder frameEntries = new StringBuilder();

            Random random = new Random();
            int sourceMACIndex, destMACIndex;
            int randomPort;
            for (int i = 0; i < noOfFrames; i++) {
                sourceMACIndex = random.nextInt(macAddressCount - 1);
                destMACIndex = random.nextInt(macAddressCount - 1);
                while (sourceMACIndex == destMACIndex) {
                    destMACIndex = random.nextInt(macAddressCount - 1);
                }
                randomPort = random.nextInt(MAX_NO_PORTS) + MIN_NO_PORTS;
                frameEntries.append(MACAddressList.get(sourceMACIndex) + "\n");
                frameEntries.append(MACAddressList.get(destMACIndex) + "\n");
                frameEntries.append(randomPort + "\n");

            }
            fileManager.setPath("src/main/java/com/bridgeprocessing/" + outputFileName);
            fileManager.writeFile(frameEntries.toString(), true);
        }
    }

    /**
     * Retrieves the number of frames to be generated.
     *
     * @return The number of frames to generate.
     */
    public int getNoOfFrames() {
        return noOfFrames;
    }

    /**
     * Sets the output file name for storing generated frames.
     *
     * @param outputFileName The name of the output file.
     */
    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
}
