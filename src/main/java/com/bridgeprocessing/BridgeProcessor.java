package com.bridgeprocessing;

/**
 * Author: Deep Pravinbhai Lad - B00938094
 * The BridgeProcessor class manages frame processing, forwarding, discarding,
 * and broadcasting.
 */
public class BridgeProcessor {
    private final BridgeDB db;
    private final String OUTPUT_FILE_PATH = "src/main/java/com/bridgeprocessing/BridgeOutput.txt";
    private final FileManager bridgeOutputFM;

    /**
     * Constructor for BridgeProcessor class.
     */
    BridgeProcessor() {
        this.db = new BridgeDB();
        this.bridgeOutputFM = new FileManager(OUTPUT_FILE_PATH);
    }

    /**
     * Check the database for the MAC address entry.
     *
     * @param MACAddress The MAC address to check in the database.
     * @return An array with MAC address details if found; otherwise null.
     */
    public String[] checkDBEntry(String MACAddress) {
        return db.getMACAddress(MACAddress);
    }

    /**
     * Forward a frame to a new port.
     *
     * @param sourceMAC   Source MAC address of the frame.
     * @param destMAC     Destination MAC address of the frame.
     * @param arrivalPort Port where the frame arrived.
     * @param newPort     New port to forward the frame.
     */
    private void forwardFrame(String sourceMAC, String destMAC, String arrivalPort, String newPort) {
        String entry = sourceMAC + "\t" + destMAC + "\t" + arrivalPort + "\t" + "Forwarded on port " + newPort + "\n";
        bridgeOutputFM.writeLine(entry, true);
    }

    /**
     * Forward a frame to a new port.
     *
     * @param sourceMAC   Source MAC address of the frame.
     * @param destMAC     Destination MAC address of the frame.
     * @param arrivalPort Port where the frame arrived.
     * @param newPort     New port to forward the frame.
     */
    private void discardFrame(String sourceMAC, String destMAC, String arrivalPort) {
        String entry = sourceMAC + "\t" + destMAC + "\t" + arrivalPort + "\t" + "Discarded\n";
        bridgeOutputFM.writeLine(entry, true);
    }

    /**
     * Forward a frame to a new port.
     *
     * @param sourceMAC   Source MAC address of the frame.
     * @param destMAC     Destination MAC address of the frame.
     * @param arrivalPort Port where the frame arrived.
     * @param newPort     New port to forward the frame.
     */
    private void broadcastFrame(String sourceMAC, String destMAC, String arrivalPort) {
        String entry = sourceMAC + "\t" + destMAC + "\t" + arrivalPort + "\t" + "Broadcast\n";
        bridgeOutputFM.writeLine(entry, true);
    }

    /**
     * Process a frame - forward, discard, or broadcast based on MAC addresses and
     * ports.
     *
     * @param sourceMAC   Source MAC address of the frame.
     * @param destMAC     Destination MAC address of the frame.
     * @param arrivalPort Port where the frame arrived.
     */
    public void processFrame(String sourceMAC, String destMAC, String arrivalPort) {
        String[] sourceMACEntry = checkDBEntry(sourceMAC);
        if (sourceMACEntry != null) {
            String originalSrcPort = sourceMACEntry[1];

            if (!originalSrcPort.equals(arrivalPort)) {
                // update port
                db.updateEntry(sourceMAC, arrivalPort);
            }
        } else {
            // add entry
            db.putEntry(sourceMAC, arrivalPort);
        }

        String[] destMACEntry = checkDBEntry(destMAC);

        if (destMACEntry != null) {
            String originalDestPort = destMACEntry[1];

            if (!originalDestPort.equals(arrivalPort)) {
                // forwarded on destination port
                forwardFrame(sourceMAC, destMAC, arrivalPort, originalDestPort);
            } else {
                // Frame discarded
                discardFrame(sourceMAC, destMAC, arrivalPort);
            }
        } else {
            // Broadcast frame except arrival port
            broadcastFrame(sourceMAC, destMAC, arrivalPort);
        }
    }
}
