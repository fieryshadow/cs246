import java.util.List;
import java.util.ArrayList;

/** A container for a parsel of thought, including relevant topics and scriptures. */
public class Entry {
   private String name;
   private String date;
   private String content;
   private List<Annotation> annotations;

   public Entry() {
      // defaults
      name = "Entry";
      date = "00-00-0000";
      content = "";
      annotations = new ArrayList<Annotation>();
   }

   /** Steals the annotation given and adds it to its secret vault of things. */
   public void addAnnotation(Annotation annotation) {
      annotations.add(annotation);
   }

   /** Prints a pretty terminal version of these human scribles. */
   public void display() {
      System.out.println(name);
      System.out.println("Date: " + date);
      System.out.println("Content:\n" + content);
      System.out.println("Annotations:");
      for (Annotation annotation : annotations) {
         System.out.println("-" + annotation.getDisplayText());
      }
   }

   // Getters/Setters

   public void setName(String name) {
      this.name = name;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public void setContent(String content) {
      this.content = content;
   }
}
