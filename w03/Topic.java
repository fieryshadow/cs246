/** A topic or subject name that can format itself for pretty printing. */
public class Topic implements Annotation {
   private String name;

   public Topic(String name) {
      this.name = name;
   }

   @Override
   public String getDisplayText() {
      return name;
   }
}
