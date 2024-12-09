import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class FileScan {

    public static void main(String[] args) {
        File selectedFile;
        String rec = "";
        int line = 0;
        int wordCnt = 0;
        int charCnt = 0;
        ArrayList<String> lines = new ArrayList<>();

        try {
            Path file;

            if (args.length > 0) {
                // Use command-line argument as the file path
                file = Path.of(args[0]);
                selectedFile = file.toFile();
                if (!selectedFile.exists()) {
                    System.out.println("File not found: " + args[0]);
                    System.exit(1);
                }
            } else {
                // Launch JFileChooser to select a file
                JFileChooser chooser = new JFileChooser();
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                    file = selectedFile.toPath();
                } else {
                    System.out.println("No file chosen. Exiting.");
                    System.exit(0);
                    return;
                }
            }

            // Read and process the file
            InputStream in = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while (reader.ready()) {
                rec = reader.readLine();
                lines.add(rec);
                line++;
                charCnt += rec.length();
                String[] words = rec.split("\\s+");
                wordCnt += words.length;
            }
            reader.close();

            // Output results
            System.out.println("\nFile Stats:");
            System.out.println("File: " + selectedFile.getName());
            System.out.println("Total lines: " + line);
            System.out.println("Total words: " + wordCnt);
            System.out.println("Total characters: " + charCnt);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
