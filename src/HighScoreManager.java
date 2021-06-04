import java.io.*;
import java.util.Scanner;

public class HighScoreManager
{
    private static int HighScore;

    public static int LoadScoreFromFile()
    {
        try {
            File file = new File(System.getProperty("user.dir") + "/SaveGame/HighScore.sfsf");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextInt()) {
                HighScore = scanner.nextInt();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return HighScore;
    }

    public static void newScore(int Score)
    {
        if (Score > HighScore)
        {
            HighScore = Score;
            try {
                FileOutputStream outputStream = new FileOutputStream(System.getProperty("user.dir") + "/SaveGame/HighScore.sfsf");
                byte[] strToBytes = String.valueOf(HighScore).getBytes();
                outputStream.write(strToBytes);
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
