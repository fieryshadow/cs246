import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class WordCounter {
   public static void main(String[] args) {
      if (args.length == 1) {
         WordCounter counter = new WordCounter();
         counter.countWords(args[0]);
      } else {
         System.out.println("Usage: java WordCounter fileToRead");
      }
   }

   private void countWords(String filename) {
      BufferedReader file;
      try {
         file = new BufferedReader(new FileReader(filename));
      } catch (IOException e) {
         System.out.println("File not found.");
         return;
      }

      String line;
      String[] words;
      try {
         line = file.readLine();
         while (line != null) {
            words = line.split("\\s+");
            System.out.println(words.length + ": " + line);
            line = file.readLine();
         }
      } catch (IOException e) {
         System.out.println("Error reading file.");
      }
   }
}
