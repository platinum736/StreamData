package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QueryBuilder {

	//static File fXmlFile = new File(
	//		"C:\\Users\\rishabh-pc\\Documents\\Java workspace\\StreamEmitters\\src\\StreamProcessor.xml");
	static DocumentBuilderFactory dbFactory = DocumentBuilderFactory
			.newInstance();
	static DocumentBuilder dBuilder = null;
	static Document doc = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Scheduling a job for event-dispatching thread
		// Creating and showing the main screen

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createMainScreen();
			}
		});

	}

	public static void createMainScreen() {
	/*	try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		String[] attributes = { "Temperature", "Timestamp", "RandomString" };
		String[] streamProcessors = { "" };

		// Creating the Opening screen
		JFrame mainframe = new JFrame("Query Builder");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Adding Use Label
		JLabel useLabel = new JLabel("Use Stream Processor");
		mainframe.getContentPane().add(useLabel);

		// Add Use stream Processors combo Box
		streamProcessors=readDatabases(streamProcessors);
		JComboBox streamProcessor = new JComboBox(streamProcessors);
		mainframe.getContentPane().add(streamProcessor);

		// Adding select Label
		JLabel selectLabel = new JLabel("Select");
		mainframe.getContentPane().add(selectLabel);

		// Create DropDown box
		JComboBox attrList = new JComboBox(attributes);
		mainframe.getContentPane().add(attrList);

		// Display the window
		mainframe.pack();
		mainframe.setVisible(true);

	}

	private static String[] readDatabases(String[] a) {
		// TODO Auto-generated method stub
		File xmlFile = new File("src/StreamProcessor.xml");
		NodeList list = null;

		DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentbuilder;
		try {
			documentbuilder = documentbuilderfactory.newDocumentBuilder();
			Document document = documentbuilder.parse(xmlFile);
			list = document.getElementsByTagName("stream-processor");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			Element element = (Element) node;
			a[i] = element.getAttribute("name");
			// }

		}
		return a;
	}
}
