import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

public class FileParser {
    RandomAccessFile file;

    FileParser(String filePath)
    {
        try {
            file = new RandomAccessFile(filePath, "r");
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "File " + filePath + " Not found.", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public String getRandomLine(int requiredLength) {

        String result = null;

        Random rand = new Random();

        try {


            file.seek(rand.nextLong
                    (file.length()));


            // Seek left to the beginning of a line
            while (file.getFilePointer() > 0 && file.readByte() != '\n') {
                file.seek(file.getFilePointer() - 2);
            }
            if (file.getFilePointer() > 0) {
                file.readLine(); // Read the line terminator and move to the next line
            }

            // store the left position of the line.
            long startPosition = file.getFilePointer();

            // Seek right to the end of the line
            while (file.getFilePointer() < file.length() && file.readByte() != '\n') {
                file.seek(file.getFilePointer() + 1);
            }

            // store the right position.
            long endPosition = file.getFilePointer();

            // Read the line
            byte[] lineBytes = new byte[(int) (endPosition - startPosition)];

            file.seek(startPosition);
            file.readFully(lineBytes);

            String line = new String(lineBytes);

            if (line.length() >= 81) {
                return line.trim();
            } else {
                return getRandomLine(requiredLength); // Retry if line is too short
            }

        }
        catch (Exception ignored)
        {

        }



        // null is only returned if there is an IOException in reading from the file
        return result;
    }


}
