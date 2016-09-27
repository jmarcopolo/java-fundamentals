package data.xml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * See https://docs.oracle.com/javase/tutorial/jaxp/sax/
 */
public class SaxExample {

  private static final Logger log = LoggerFactory.getLogger(SaxExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.xml");
    read(dest);
  }

  private static void read(Path src) throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);
    SAXParser parser = factory.newSAXParser();
    XMLReader reader = parser.getXMLReader();
    reader.setContentHandler(new MovieHandler());
//    try (InputStream in = Files.newInputStream(src)) {
//      reader.parse(new InputSource(in));
//    }
    reader.parse(new InputSource(src.toUri().toString()));
  }

  private static class MovieHandler extends DefaultHandler {
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (localName.equals("movie")) {
        String title = attributes.getValue("title");
        String year = attributes.getValue("year");
        log.info("Found movie {} ({})", title, year);
      }
    }
  }

}
