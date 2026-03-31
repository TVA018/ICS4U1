package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO implements AutoCloseable {
    public final File file;
    private final BufferedReader reader;
    private String fileText;

    public FileIO(File file) throws IOException, FileNotFoundException {
        this.file = file;

        if(!file.exists()) file.createNewFile();

        reader = new BufferedReader(new FileReader(file));

        fileText = reader.readAllAsString();
    }

    public FileIO(String filePath) throws IOException {
        this(new File(filePath));
    }

    /** @return The file contents represented as text */
    public String read() {
        return fileText;
    }

    /** @return The file contents represented as an array of lines */
    public String[] readLines() {
        String[] lines = fileText.split("\n");

        for(int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replace("\r", ""); // Remove carriage returns
        }

        return lines;
    }

    /** Appends the {@code text} to the end of the file content */
    public void append(String text) {
        fileText += text;
    }
    
    /** Appends the {@code line} to the end of the file content as a line (ends with new-line) */
    public void appendLine(String line) {
        append(line + "\n");
    }

    /** Overwrites the text content of the file */
    public void overwrite(String newText) {
        fileText = newText;
    }
    
    public void close(boolean shouldSave) throws IOException {
        if(shouldSave) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileText);
            writer.close();
        }

        reader.close();
    }

    @Override
    public void close() throws IOException {
        close(true);
    }
}
