/** A scripture reference which knows how to format itself, pretty like. */
public class Scripture implements Annotation {
   private String book, chapter, startVerse, endVerse, displayText;

   public Scripture(String book, String chapter, String startVerse, String endVerse) {
      this.book = book;
      this.chapter = chapter;
      this.startVerse = startVerse;
      this.endVerse = endVerse;

      // pre make the display string since we know it can't change
      if (chapter.equals("")) {
         displayText = book;
      } else if (startVerse.equals("")) {
         displayText = book + " " + chapter;
      } else if (endVerse.equals("")) {
         displayText = book + " " + chapter + ":" + startVerse;
      } else {
         displayText = book + " " + chapter + ":" + startVerse + "-" + endVerse;
      }
   }

   @Override
   public String getDisplayText() {
      return displayText;
   }
}
