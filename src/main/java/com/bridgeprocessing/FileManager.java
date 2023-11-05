package com.bridgeprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Deep Pravinbhai Lad - B00938094
 * The FileManager class provides methods for file manipulation such as reading,
 * writing, scanning, and updating files.
 */
public class FileManager {
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private String path;

    /**
     * Get the current file path.
     *
     * @return The path of the file being handled.
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the file path.
     *
     * @param path The path to the file that will be managed.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Constructor to set the file path for the FileManager object.
     *
     * @param path The path to the file that will be managed.
     */
    FileManager(String path) {
        this.path = path;
    }

    /**
     * Scan the file for a specific data and return the data along with its
     * corresponding port.
     *
     * @param data The data to search for in the file.
     * @return An array containing the data and its corresponding port if found;
     *         otherwise null.
     * @throws RuntimeException if an error occurs during file handling.
     */
    protected String[] scanFile(String data) {
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            String port;
            // System.out.println("here");
            while ((line = bufferedReader.readLine()) != null) {
                // System.out.println("inside reader");
                if (line.equals(data)) {
                    port = bufferedReader.readLine();
                    bufferedReader.close();
                    return new String[] { line, port };
                }
            }
            bufferedReader.close();
            return null;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read alternate lines from the file.
     *
     * @return An ArrayList containing the lines of the file at odd line numbers.
     * @throws RuntimeException if an error occurs during file handling.
     */
    protected ArrayList<String> readAlternateLines() {
        ArrayList<String> macList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            String line;
            // System.out.println("here");
            int lineNo = 1;
            while ((line = bufferedReader.readLine()) != null) {
                if (lineNo % 2 != 0) {
                    macList.add(line);
                }
                lineNo++;
            }
            bufferedReader.close();
            return macList;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write a line to the file, either appending it or overwriting the content.
     *
     * @param entry  The entry to write to the file.
     * @param append If true, the entry is appended to the file; if false, the file
     *               content is overwritten with the entry.
     * @throws RuntimeException if an error occurs during file handling.
     */
    protected void writeLine(String entry, boolean append) {
        try {
            fileWriter = new FileWriter(path, append);
            fileWriter.write(entry);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create the file if it does not exist.
     *
     * @param filePath The file path to create if absent.
     * @throws RuntimeException if an error occurs during file handling.
     */
    private void createFileIfAbsent(String filePath) {
        String[] pathSplit = filePath.split("/");
        String currentDir = "";
        for (String path : pathSplit) {
            if (path.contains(".txt")) {
                File f = new File(filePath);
                try {
                    if (f.createNewFile()) {
                        // log here
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                File f = currentDir.isEmpty() ? new File(path) : new File(currentDir, path);
                if (f.mkdir()) {
                    // log here
                }
                currentDir = currentDir + path + "/";
            }
        }
    }

    /**
     * Write content to the file, creating the file if it doesn't exist.
     *
     * @param data   The data to write to the file.
     * @param append If true, the data is appended to the file; if false, the file
     *               content is overwritten with the data.
     * @throws RuntimeException if an error occurs during file handling.
     */
    protected void writeFile(String data, boolean append) {
        createFileIfAbsent(path);
        try {
            FileWriter fileWriter = new FileWriter(path, append);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the next line after a specific entry in the file with the provided
     * content.
     *
     * @param entryToUpdateNextLine The entry in the file to search for.
     * @param updatedEntry          The content to replace the line following the
     *                              found entry.
     * @throws RuntimeException if an error occurs during file handling.
     */
    protected void updateNextLine(String entryToUpdateNextLine, String updatedEntry) {
        try {
            File file = new File(path);
            File tempFile = new File("temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;

            int i = 1;

            while ((line = reader.readLine()) != null) {
                if (line.equals(entryToUpdateNextLine)) {
                    writer.write(entryToUpdateNextLine + "\n" + updatedEntry + "\n");
                    line = reader.readLine();
                } else {
                    writer.write(line + "\n");
                }
                i++;
            }

            reader.close();
            writer.close();

            if (file.delete()) {
                if (!tempFile.renameTo(file)) {
                    throw new IOException("Could not rename temp file to original file name");
                }
            } else {
                throw new IOException("Could not delete the original file");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
