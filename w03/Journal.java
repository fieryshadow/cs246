import java.util.List;
import java.util.ArrayList;

/**
 * A collection for written thoughts in the course of one's life.
 *
 * Contains an arbitrary number of entries. */
public class Journal {
   private List<Entry> entries;

   public Journal() {
      entries = new ArrayList<Entry>();
   }

   /** Adds a written thought to this special collection. */
   public void addEntry(Entry entry) {
      entries.add(entry);
   }

   /** Prints all the pages of journal entries to system out. */
   public void display() {
      for (Entry entry : entries) {
         System.out.println("\n----------------------------------------------");
         entry.display();
      }
   }
}
