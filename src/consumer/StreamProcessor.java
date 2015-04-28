package consumer;

import java.io.File;
import java.io.IOException;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class StreamProcessor {

	String name="";
	Streamsrc[] streams;
	int window_size=100;   //need to take from xml
	String window_type;
	
	NodeList nList;
	
	StreamProcessor(String s)
	{
		this.name=s;
		System.out.println("StreamProcessor Name="+name);
		File fXmlFile = new File("C:\\Users\\rishabh-pc\\Documents\\Java workspace\\StreamEmitters\\src\\StreamProcessor.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc=null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nList=doc.getElementsByTagName("stream");
		streams=new Streamsrc[nList.getLength()];
		populateStreamSources();
	}
	
	private void populateStreamSources() {
		// TODO Auto-generated method stub
		for(int temp = 0;temp<nList.getLength();temp++)
		{
			Node nNode=nList.item(temp);
			Element elementNode=(Element) nNode;
			streams[temp]=new Streamsrc();
			streams[temp].name=elementNode.getAttribute("name");
			streams[temp].port=Integer.parseInt(elementNode.getAttribute("port"));
			streams[temp].attributes=new StreamAttributes[3];
			
			NodeList InnerList;
			Node InnerNode;
			Element InnerElement;
		//	System.out.println("Name="+streams[temp].name+" "+"Port="+streams[temp].port);
			for(int j=0;j<3;j++)
			{
				String TagName="attr";
				InnerList=elementNode.getElementsByTagName(TagName);
				InnerNode=InnerList.item(0);
				InnerElement=(Element)InnerNode;
				streams[temp].attributes[j]=new StreamAttributes();
				streams[temp].attributes[j].name=InnerElement.getAttribute("name");
				streams[temp].attributes[j].type=InnerElement.getAttribute("type");
				streams[temp].attributes[j].createValueAttribute();
			//	System.out.print("Attr"+(j+1)+" name:"+streams[temp].attributes[j].name+" type:"+streams[temp].attributes[j].type);
			//	System.out.println(" Value="+streams[temp].attributes[j].value);
			}	
		}
		
		
	}

}
