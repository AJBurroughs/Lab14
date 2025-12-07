import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class Lab14_File_Scan {
    public static void main(String[] args) {
        File selectedFile = null;   // will hold the file chosen either via argument or chooser
        String rec = "";            // stores each line read from the file
        int totalWords = 0;         // running count of words
        int totalChars = 0;         // running count of characters
        String[] words;             // temporary array to hold words from each line

        try {
            // --- Step 1: Check if a command-line argument was provided ---
            if (args.length > 0) {
                // Use the first argument as the file name
                selectedFile = new File(args[0]);

                // Validate that the file exists and is a regular file
                if (!selectedFile.exists() || !selectedFile.isFile()) {
                    System.out.println("Invalid file: " + args[0]);
                    return; // exit program if argument is invalid
                }
            } else {
                // --- Step 2: No argument → launch JFileChooser ---
                JFileChooser chooser = new JFileChooser();

                // Set chooser to start in the current working directory
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                // Show the chooser dialog and check if user approved a file
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                } else {
                    // User canceled → exit program gracefully
                    System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
                    return;
                }
            }

            // --- Step 3: Prepare to read the selected file ---
            Path file = selectedFile.toPath();
            InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            int line = 0; // line counter

            // --- Step 4: Read file line by line ---
            while (reader.ready()) {
                rec = reader.readLine();          // read one line
                words = rec.split("\\s+");        // split line into words by whitespace
                totalWords += words.length;       // add to word count
                totalChars += rec.length();       // add to character count
                line++;                           // increment line counter

                // Print the line number and the line itself
                System.out.printf("\nLine %4d %-60s ", line, rec);
            }
            reader.close(); // close the reader when done

            // --- Step 5: Print summary statistics ---
            System.out.println("\n\nData file read!");
            System.out.println("Total words: " + totalWords);
            System.out.println("Total characters: " + totalChars);
            System.out.println("Total lines: " + line);

        } catch (FileNotFoundException e) {
            // Handles case where file cannot be found
            System.out.println("File not found!!!");
            e.printStackTrace();
        } catch (IOException e) {
            // Handles general input/output errors
            e.printStackTrace();
        }
    }
}