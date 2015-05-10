import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collection;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LineReader {
   public static void main(String[] args) {
      new LineReader().run();
   }

   public void run() {
      System.out.println("books.txt:\n");
      List<String> books = new ArrayList<String>();
      readFileToCollection("books.txt", books);
      displayList(books);

      System.out.println("\n\nch01_words.txt:\n");
      Set<String> words = new HashSet<String>();
      readFileToCollection("ch01_words.txt", words);
      displayList(words);
   }

   public void readFileToCollection(String filename, Collection<String> coll) {
      try {
         BufferedReader file = new BufferedReader(new FileReader(filename));
         String line;
         while ((line = file.readLine()) != null) {
            coll.add(line);
         }
      } catch (IOException e) {
         System.out.println("The given file doesn't seem to exist.");
      }
   }

   public void readFileToMap(String filename, Map<String, String> map) {
      try {
         BufferedReader file = new BufferedReader(new FileReader(filename));
         String line;
         while ((line = file.readLine()) != null) {
            set.add(line);
         }
      } catch (IOException e) {
         System.out.println("The given file doesn't seem to exist.");
      }
      return set;
   }

   public void displayList(Collection<String> list) {
      for (String line : list) {
         System.out.println(line);
      }
   }
}
