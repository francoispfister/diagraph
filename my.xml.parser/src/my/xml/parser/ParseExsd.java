package my.xml.parser;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseExsd {
  public void query() throws ParserConfigurationException, SAXException,
      IOException, XPathExpressionException {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(false);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(".\\model\\org.isoe.fwk.automation.exsd");
    XPathFactory xFactory = XPathFactory.newInstance();
    XPath xpath = xFactory.newXPath();
    XPathExpression expr = xpath.compile("/schema/element[@name='extension']/complexType/sequence/element");

    Object result = expr.evaluate(doc, XPathConstants.NODESET);
	NodeList nodes = (NodeList) result;

	for (int a = 0; a < nodes.getLength(); a++) {
		Element el = (Element) nodes.item(a);
		String id = el.getAttribute("ref");
		System.out.println(id);
		//result.add(id);
	}
   }

  public static void main(String[] args) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
	  ParseExsd process = new ParseExsd();
      process.query();
  }
}