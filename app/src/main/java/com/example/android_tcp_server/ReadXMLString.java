package com.example.android_tcp_server;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.HashMap;
import org.xml.sax.InputSource;

public class ReadXMLString {



    public static HashMap<String, String>  readXMLString(String xmlString) {
        String testString = new String();
        HashMap<String, String> values = new HashMap<String, String>();
       // String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><user><kyc>123</kyc><address>test</address><resiFI>asds</resiFI></user>";
        Document xml = convertStringToDocument(xmlString);
        if(xml == null)
            return null;
        Node user = xml.getFirstChild();
        NodeList childs = user.getChildNodes();
        Node child;
        for (int i = 0; i < childs.getLength(); i++) {
            child = childs.item(i);
            testString+="Node name: "+child.getNodeName();
            testString+=" Content: "+child.getTextContent()+"\n";
            values.put(child.getNodeName(), child.getTextContent());
        }
        return values;
    }

    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(
                    xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}