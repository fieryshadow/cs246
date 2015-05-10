import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLParser {
   public static void main(String args[]) {
      new XMLParser().run(args[0]);
   }

   public void run(String filename) {
      try {
         File fXmlFile = new File(filename);
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = factory.newDocumentBuilder();
         Document doc = dBuilder.parse(fXmlFile);

         //optional, but recommended
         doc.getDocumentElement().normalize();
         System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

         NodeList nList = doc.getElementsByTagName("session");

         System.out.println("----------------------------");


         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               NodeList nList2 = nNode.getChildNodes();
               for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
                  Node nNode2 = nList2.item(temp2);
                  //System.out.println("\nCurrent Element :" + nNode2.getNodeName());
                  if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
                     System.out.println("Speaker Name: " + nNode2.getAttributes().getNamedItem("name").getNodeValue());
                  }
               }
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
