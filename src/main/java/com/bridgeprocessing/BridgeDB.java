package com.bridgeprocessing;

/**
 * Author: Deep Pravinbhai Lad - B00938094
 * The BridgeDB class manages operations related to MAC address entries in a
 * file-based database.
 */
public class BridgeDB {

    FileManager fileManager = new FileManager("src/main/java/com/bridgeprocessing/BridgeFDB.txt");

    /**
     * Constructor for BridgeDB class.
     */
    BridgeDB() {
    }

    /**
     * Retrieves MAC address details from the database file.
     *
     * @param MAC The MAC address to retrieve details for.
     * @return An array containing MAC address details if found; otherwise null.
     */
    protected String[] getMACAddress(String MAC) {

        return fileManager.scanFile(MAC);
    }

    /**
     * Update the entry for a specific MAC address with a new arrival port in the
     * database file.
     *
     * @param sourceMAC   The MAC address to update.
     * @param arrivalPort The new arrival port to set for the MAC address.
     */
    protected void updateEntry(String sourceMAC, String arrivalPort) {
        fileManager.updateNextLine(sourceMAC, arrivalPort);
    }

    /**
     * Insert a new entry for a MAC address with its arrival port into the database
     * file.
     *
     * @param sourceMAC   The new MAC address to insert.
     * @param arrivalPort The arrival port associated with the new MAC address.
     */
    protected void putEntry(String sourceMAC, String arrivalPort) {
        String entry = sourceMAC + "\n" + arrivalPort + "\n";
        fileManager.writeLine(entry, true);
    }
}
