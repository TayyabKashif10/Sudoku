import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class FileParser {
    Scanner fileStream;

    FileParser(String filePath)
    {
        try {
            fileStream = new Scanner(new File(filePath));
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "File " + filePath + " Not found.", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public String getRandomLine() {

        String result = null;
        Random rand = new Random();
        int n = 0;

        while (fileStream.hasNext())
        {
            ++n;
            String line = fileStream.nextLine();

            if(rand.nextInt(n) == 0)
                result = line;
        }

        return result;
    }


}
