// Collaborators: Shane Jensen, Ryan Duvall
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import java.io.File;

/**
 * Takes an xml journal file and parses it into a class hierarchy, afterward
 * displaying the journal to system out.
 */
public class XMLParser {
   public static void main(String[] args) {
      if (args.length != 1) {
         System.out.println("Usage: java XMLParser xmlFileName");
         System.exit(0);
      }
      new XMLParser().run(args[0]);
   }

   /** Runs the default parse and display of an xml journal file. */
   public void run(String filename) {
      Journal journal = parse(filename);
      journal.display();
   }

   /** Parses a given xml journal file and returns a Journal. */
   public Journal parse(String filename) {
      DocumentBuilderFactory factory;
      DocumentBuilder builder;
      Document document;
      try {
         factory = DocumentBuilderFactory.newInstance();
         builder = factory.newDocumentBuilder();
         document = builder.parse(new File(filename));
      } catch (Exception e) {
         System.out.println("The given xml file is unparsable.");
         System.exit(0);
         return null; // make compiler happy
      }

      Journal journal = new Journal();
      document.getDocumentElement().normalize(); // fix any weird xml formatting
      NodeList nodes = document.getDocumentElement().getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
         Node node = nodes.item(i);
         if (node instanceof Element) {
            journal.addEntry(extractEntry(node));
         }
      }
      return journal;
   }

   /** Takes a journal entry node and extracts all the info into an Entry. */
   public Entry extractEntry(Node node) {
      Entry entry = new Entry();
      if (node.getAttributes().getNamedItem("name") != null) {
         entry.setName(node.getAttributes().getNamedItem("name").getNodeValue());
      }
      entry.setDate(node.getAttributes().getNamedItem("date").getNodeValue());

      NodeList childNodes = node.getChildNodes();
      for (int i = 0; i < childNodes.getLength(); i++) {
         Node node2 = childNodes.item(i);
         if (node2 instanceof Element) {
            String name = node2.getNodeName();
            if (name.equals("scripture")) {
               entry.addAnnotation(extractScripture(node2));
            } else if (name.equals("topic")) {
               entry.addAnnotation(extractTopic(node2));
            } else if (name.equals("content")) {
               entry.setContent(extractContent(node2));
            } else {
               System.out.println("Journal entry has unknown child node: " + name);
            }
         }
      }
      return entry;
   }

   /** Takes a scripture node and pulls all the info into a Scripture. */
   public Scripture extractScripture(Node node) {
      NamedNodeMap attributes = node.getAttributes();

      Node node2 = attributes.getNamedItem("book");
      if (node2 == null) { // don't need to halt and catch fire here
         return new Scripture("(No Book Of Scripture Specified)", "", "", "");
      }
      String book = node2.getNodeValue();

      node2 = attributes.getNamedItem("chapter");
      String chapter = node2 != null ? node2.getNodeValue() : "";

      node2 = attributes.getNamedItem("startVerse");
      String startVerse = node2 != null ? node2.getNodeValue() : "";

      node2 = attributes.getNamedItem("endVerse");
      String endVerse = node2 != null ? node2.getNodeValue() : "";

      return new Scripture(book, chapter, startVerse, endVerse);
   }

   /** Takes a topic node and pulls all the info out into a Topic. */
   public Topic extractTopic(Node node) {
      String name = node.getAttributes().getNamedItem("name").getNodeValue();
      return new Topic(name);
   }

   /** Takes a content xml node and extracts its String value. */
   public String extractContent(Node node) {
      // node should only have one child node if it's a content node
      return node.getChildNodes().item(0).getNodeValue();
   }
}
