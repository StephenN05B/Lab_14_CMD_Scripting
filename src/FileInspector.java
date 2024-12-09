import java.io.BufferedInputStream;
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

public class FileInspector
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        int line = 0;
        int wordCnt = 0;
        int charCnt = 0;

        ArrayList<String> lines = new ArrayList<>();

        try
        {
            File workingDirectory = new File(System.getProperty("user.dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                InputStream in = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                while(reader.ready())
                {
                    rec = reader.readLine();
                    lines.add(rec);
                    line++;
                    charCnt += rec.length();
                    String[] words = rec.split(" ");
                    wordCnt += words.length;
                    System.out.printf("\nLine %4d %-60s ", line, rec);
                }
                for(String l : lines)
                {
                    System.out.println(l);
                }

                String fields[] = lines.get(5).split(", ");
                for(String f : fields)
                    System.out.println(f);

                reader.close();
                System.out.println("\n\nData file read!");
                System.out.println("File" + selectedFile.getName());
                System.out.println("File Stats:");
                System.out.println("Total lines " + line);
                System.out.println("Total words " + wordCnt);
                System.out.println("Total characters " + charCnt);
            }
            else
            {
                System.out.println("Failed to choose a file to process");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
