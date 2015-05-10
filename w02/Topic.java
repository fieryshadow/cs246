import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Comparable;
import java.util.List;

/**
 * Stores a topic name and relevant words, allowing it to say if it
 * appears in a given block of text.
 */
public class Topic implements Comparable<Topic> {
   private String name;
   private String[] terms;
   private Pattern pattern;

   /**
    * Returns a string of each list element separated by a given delimiter.
    *
    * This makes the constructor more succinct.
    */
   private static String joinList(String delimiter, String[] list) {
      if (list.length == 0) {
         return "";
      }
      StringBuilder sb = new StringBuilder(list[0]);
      for (int i = 1; i < list.length; i++) {
         sb.append(delimiter);
         sb.append(list[i]);
      }
      return sb.toString();
   }

   /** Makes a Topic with a name and relevant words. */
   public Topic(String name, String[] terms) {
      this.name = name;
      this.terms = terms;
      this.pattern = Pattern.compile(Topic.joinList("|", terms).toLowerCase());
   }

   /** Searches the given text for matching words. */
   public boolean isMentionedIn(String text) {
      Matcher m = pattern.matcher(text.toLowerCase());
      return m.find(); // is there a match?
   }

   @Override
   public int compareTo(Topic t) {
      return name.compareTo(t.name);
   }

   @Override
   public String toString() {
      return name;
   }
}
