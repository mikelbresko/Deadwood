//package com.mkyong.seo;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ParseCards{


  public static void main(String[] args) {

    try {

    	File fXmlFile = new File("cards.xml");
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    	Document doc = dBuilder.parse(fXmlFile);

    	//optional, but recommended
    	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    	doc.getDocumentElement().normalize();

    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

    	NodeList nList = doc.getElementsByTagName("card");

    	System.out.println("----------------------------");

    	for (int temp = 0; temp < nList.getLength(); temp++) {

    		Node card = nList.item(temp);

    		System.out.println("\nCurrent Element :" + card.getNodeName());

    		if (card.getNodeType() == Node.ELEMENT_NODE) {

    			Element eElement = (Element) card;

    			System.out.println("Name: " + eElement.getAttribute("name"));
          System.out.println("Budget: " + eElement.getAttribute("budget"));
    			System.out.println("Scene Number : " + eElement.getAttribute("number"));
          NodeList roles = eElement.getElementsByTagName("part");
          for(int j = 0; j < roles.getLength();j++){
            Node partNode = roles.item(j);
            System.out.println("Current part: "+ partNode.getNodeName());
            Element part = (Element) partNode;
            if(part.getNodeType() == Node.ELEMENT_NODE){
              System.out.println("Role : " + part.getAttribute("name"));
              System.out.println("Role Lvl: " + part.getAttribute("level"));
              System.out.println("Line: "+ part.getElementsByTagName("line").item(0).getTextContent());
            }
          }
    		}
    	}
    }
    catch (Exception e) {
	     e.printStackTrace();
    }
  }
}
