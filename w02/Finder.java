// Colaborators: Shane Jensen, Brandyn Burbank
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;
import java.util.Properties;

/**
 * Takes a list of topics from a file and finds which occur in every text file
 * of a directory.
 *
 * Topic file and directory are specified in property file.
 * A system property specifies where the property file can be found.
 */
public class Finder {
   public static void main(String[] args) {
      if (args.length != 0) {
         System.out.println("Usage: java -DpropertyDir=loc -jar finder.jar");
         System.exit(0);
      }
      new Finder().run();
   }

   /** Find and display topics within a directory of files. */
   public void run() {
      String[] places = getProperties();
      List<Topic> topics = parseTopicFile(places[0]);
      List<File> files = getFilesFromDirectory(places[1]);

      // sort files by file name
      Collections.sort(files, new Comparator<File>() {
         @Override
         public int compare(File f1, File f2) {
            return f1.getName().compareTo(f2.getName());
         }
      });

      // display topics for each file, separated by file names
      for (File file : files) {
         System.out.println("\n" + file.getName() + ":");
         List<Topic> mentionedTopics = getTopicsWithinFile(file, topics);
         Collections.sort(mentionedTopics);
         for (Topic t : mentionedTopics) {
            System.out.println("  " + t.toString());
         }
      }
   }

   /** Find and read the properties file. */
   public String[] getProperties() {
      String dir = System.getProperty("propertyDir");
      if (dir == null) {
         System.out.println("Usage: java -DpropertyDir=loc -jar finder.jar");
         System.exit(0);
      }
      File file = new File(dir + "/finder.properties");
      if (!file.exists()) {
         System.out.println("Can't find a properties file within: " + dir);
         System.exit(0);
      }

      // read in the config settings
      Properties props = new Properties();
      try {
         props.load(new FileReader(file));
      } catch (IOException e) {
         System.out.println("'" + file.getName() + "' is corrupted.");
         System.exit(0);
      }

      // make sure the correct settings are given
      String topicsFilename = props.getProperty("topicsFile");
      String parseDirectory = props.getProperty("parsingDirectory");

      if (topicsFilename == null || parseDirectory == null) {
         System.out.println("Looks like '" + file.getName() + "' isn't valid.");
         System.exit(0);
      }

      return new String[] { topicsFilename, parseDirectory };
   }

   /** Extract topics from a file into a usable form. */
   public List<Topic> parseTopicFile(String topicsFilename) {
      BufferedReader topicsFile;
      try {
         topicsFile = new BufferedReader(new FileReader(topicsFilename));
      } catch (IOException e) {
         System.out.println("'" + topicsFilename + "' doesn't seem to exist.");
         System.exit(0);
         return null; // make compiler happy
      }

      // create list of topics from file
      String line;
      List<Topic> topics = new ArrayList<Topic>();
      try {
         while ((line = topicsFile.readLine()) != null) {
            String[] temp = line.split(":");
            String name = temp[0];
            String[] words = temp[1].split(",");
            topics.add(new Topic(name, words));
         }
      } catch (IOException e) {
         System.out.println("Error reading '" + topicsFilename + "'");
         System.exit(0);
      }
      return topics;
   }

   /** Find relevant files to read within a directory. */
   public List<File> getFilesFromDirectory(String dirName) {
      File dir = new File(dirName);
      if (!dir.exists()) {
         System.out.println("'" + dir + "' doesn't seem to exist.");
         System.exit(0);
      }
      if (!dir.isDirectory()) {
         System.out.println("Can't parse '" + dirName + "' as a direcotry.");
         System.exit(0);
      }

      // grab [only the *.txt] files contained in the directory
      // commented LOC are just to keep this to spec, but I think add to this
      List<File> files = new ArrayList<File>();
      for (File file : dir.listFiles()) {
         //String name = file.getName();
         //if (name.contains(".txt")) {
            files.add(file);
         //}
      }
      return files;
   }

   /** Search for all mentioned topics in a file. */
   public List<Topic> getTopicsWithinFile(File file, List<Topic> topics) {
      String verse;
      BufferedReader parseFile;
      Set<Topic> mentionedTopics = new HashSet<Topic>();
      try {
         parseFile = new BufferedReader(new FileReader(file));
      } catch (IOException e) {
         System.out.println("'" + file.getName() + "' doesn't seem to exist.");
         return new ArrayList<Topic>(); // continue parsing other files
      }

      // search file for mentioned topics
      try {
         while ((verse = parseFile.readLine()) != null) {
            for (Topic t : topics) {
               if (t.isMentionedIn(verse)) {
                  mentionedTopics.add(t);
               }
            }
         }
      } catch (IOException e) {
         System.out.println("'" + file.getName() + "' seems unreadable.");
         return new ArrayList<Topic>(); // continue parsing other files
      }
      return new ArrayList<Topic>(mentionedTopics);
   }
}
