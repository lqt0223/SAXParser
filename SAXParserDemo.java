import com.lqt0223.SAXParser;
import com.lqt0223.SAXParserHandler;
import sun.tools.java.SyntaxError;

import java.io.IOException;
import java.util.HashMap;

public class SAXParserDemo{
    public static void main(String[] args) throws IOException, SyntaxError {
        SAXParser parser = SAXParser.getInstance();
        parser.setHandler(new SAXParserHandler() {
            String resultName = "";
            String className = "";
            HashMap<String, String> resultJspMap = new HashMap<>();

            boolean foundClass = false;

            @Override
            public void startDocument() {
                System.out.println("Start parsing");
            }

            @Override
            public void startElement(String tagName, HashMap<String, String> attributes) {
                if(tagName.equals("action")){
                    if(attributes.get("name").equals("login")){
                        className = attributes.get("class");
                        foundClass = true;
                    }else{
                        foundClass = false;
                    }
                }else if(tagName.equals("result") && foundClass){
                    resultName = attributes.get("name");
                }
            }

            @Override
            public void endElement(String tagName) {

            }

            @Override
            public void innerText(String innerText) {
                if(foundClass){
                    String jsp = innerText;
                    resultJspMap.put(resultName,jsp);

                }
            }

            @Override
            public void endDocument() {
                System.out.println(className);
                System.out.println(resultJspMap);
                System.out.println("End parsing");
            }
        });
        parser.parse("src/test.xml");
    }
}