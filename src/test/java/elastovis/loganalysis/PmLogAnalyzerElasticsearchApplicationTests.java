package elastovis.loganalysis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class PmLogAnalyzerElasticsearchApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void hashSortFileList(){
        String filePath = "D:\\Users\\altokgoz\\Documents\\xmlparsefilelist1\\output.log.1";

        HashMap<Integer,String> mapFileList=new HashMap<Integer,String>();

        System.out.println(filePath.replaceAll(".*\\\\", ""));
        //mapFileList.put(Integer.valueOf( filePath.replaceAll(".*\\\\", "")), filePath);
        System.out.println(mapFileList);

    }



}
