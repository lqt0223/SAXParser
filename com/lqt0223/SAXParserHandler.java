package com.lqt0223;

import java.util.HashMap;

public interface SAXParserHandler {
    void startDocument();
    void startElement(String tagName, HashMap<String, String> attributes);
    void endElement(String tagName);
    void endDocument();
    void innerText(String innerText);
}
