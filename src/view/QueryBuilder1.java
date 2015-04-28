/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import consumer.Executer;

/**
 *
 * @author rajeevsingh
 */
public class QueryBuilder1 extends javax.swing.JFrame {

	/**
	 * Creates new form QueryBuilder
	 * 
	 * 
	 */
	public QueryBuilder1() {
		super("Stream Data Query Builder");
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        use = new javax.swing.JLabel();
        from = new javax.swing.JLabel();
        select = new javax.swing.JLabel();
        where = new javax.swing.JLabel();
        streamProcessorcb = new javax.swing.JComboBox();
        streamNamescb = new javax.swing.JComboBox();
        SelectAttrributes = new javax.swing.JList();
        
        jPanel2 = new javax.swing.JPanel();
        whereAttributescb = new javax.swing.JComboBox();
        selectAttributescb = new javax.swing.JComboBox();
        operationscb = new javax.swing.JComboBox();
        operatorscb = new javax.swing.JComboBox();
        whereValue = new javax.swing.JTextField();
        joinon = new javax.swing.JLabel();
        submitBtn = new javax.swing.JButton();
        joinonValue = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        resultsLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        titleLabel = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        SelectAttrributes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(SelectAttrributes);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        use.setText("Use StreamProcessorName");

        from.setText("From");

        select.setText("Select");

        where.setText("Where");

       
        
        
        
        
        
        //Initialize the stream processor names and create a select event for the combo box
        streamProcessorcb.setModel(new javax.swing.DefaultComboBoxModel(getstreamProcessors()));
        streamProcessorcb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	//System.out.print(streamProcessorcb.getSelectedItem().toString());
            	if(streamProcessorcb.getSelectedItem()!="")
            	     streamNamescb.setModel(new javax.swing.DefaultComboBoxModel(getStreamNames(streamProcessorcb.getSelectedIndex())));
            }
        });

        //Stream processor related things ends here
        
        
        
        
        whereAttributescb.setModel(new javax.swing.DefaultComboBoxModel(streamAttributes));//getAttributeListwhere()));
        
        whereAttributescb.addActionListener(new java.awt.event.ActionListener(){
        	public void actionPerformed(java.awt.event.ActionEvent evt){
        		if(whereAttributescb.getSelectedIndex()==streamAttributes.length)
        		{
        			where.setVisible(false);
        		}
        	}
        });
        
      //Initialize the stream names and create a select event for the combo box
        
        streamNamescb.setModel(new javax.swing.DefaultComboBoxModel(streamNames));//getStreamNames(streamProcessorcb.getSelectedIndex())));
        
        streamNamescb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	selectAttributescb.setModel(new DefaultComboBoxModel(getAttributeList()));
            	whereAttributescb.setModel(new javax.swing.DefaultComboBoxModel(getAttributeListwhere()));
            }
        });
        
        
       
        

        selectAttributescb.setModel(new javax.swing.DefaultComboBoxModel(streamAttributes));//getAttributeList()));

        operationscb.setModel(new javax.swing.DefaultComboBoxModel(operations));

        operatorscb.setModel(new javax.swing.DefaultComboBoxModel(operators));

        whereValue.setText("Enter the value");
        whereValue.setToolTipText("");
        whereValue.addMouseListener(new MouseAdapter() {
        	  @Override
        	  public void mouseClicked(MouseEvent e) {
        	    whereValue.setText("        ");
        	  }
        	});

        
        joinon.setText("Join on");

        submitBtn.setText("Submit");
        
        Object This=this;
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
        	
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	    streamPos=1;
            	    AttributePos=1;
                	Executer ex=new Executer((QueryBuilder1)This,streamProcessors[streamProcessorcb.getSelectedIndex()],window_size,window_type,window_speed,
                			2,streamPos,AttributePos,
                			operationscb.getSelectedIndex(),1,2,operatorscb.getSelectedIndex(),whereValue.getText());
                	if(streamPos==0 && AttributePos==0){
                	      updatedResults=new String[0][6];
                	      headerFields=new String[6];
                	}
                	else{
                		  updatedResults=new String[0][3];
                	      headerFields=new String[3];
                	}
					model=new DefaultTableModel(updatedResults,headerFields);
			        resultsTable.setModel(model);
			        
                	Thread t=new Thread(ex);
					t.start();					
					/*Timer timer=new Timer(1000,new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
						    //if(Arrays.toString(rowAdd)!=null)
                            
					        
							   model.addRow(rowAdd);
							   System.out.println(model.getRowCount());
							   System.out.println(Arrays.toString(rowAdd));
			
						}
			        });
					timer.start();
			        timer.setDelay(5000);*/
            }
        });
        
        
        joinonValue.setText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(joinon)
                        .addGap(179, 179, 179)
                        .addComponent(joinonValue, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(select)
                            .addGap(18, 18, 18)
                            .addComponent(selectAttributescb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(operationscb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(from)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(streamNamescb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(use)
                            .addGap(48, 48, 48)
                            .addComponent(streamProcessorcb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(where)
                        .addGap(18, 18, 18)
                        .addComponent(whereAttributescb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(submitBtn)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(operatorscb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(whereValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(streamProcessorcb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(use, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(from)
                    .addComponent(streamNamescb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(select)
                    .addComponent(selectAttributescb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(operationscb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(where)
                    .addComponent(whereAttributescb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(operatorscb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(whereValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(joinon)
                    .addComponent(joinonValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 217, Short.MAX_VALUE)
                .addComponent(submitBtn)
                .addGap(28, 28, 28))
        );

        resultsLabel.setText("Results");
        
       // System.arraycopy(streamAttributes, 1, headerFields, 0, 6);
        
       // model=new DefaultTableModel(updatedResults,headerFields);
        //resultsTable.setModel(model);
        
        
        
        
        
        
        
        
        
        jScrollPane2.setViewportView(resultsTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(resultsLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(resultsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        titleLabel.setText("Query Builder");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(618, 618, 618)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(titleLabel)))
                .addGap(23, 23, 23)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(147, 147, 147))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	// public static void updateResult(Object[] result)
	// {
	// System.out.print(count);
	// updatedResults[count]=result;

	// count++;
	// }

	private String[] getAttributeList() {
	//private ComboBoxModel getAttributes(){

		String[] a = getAttributeListwhere();
		chkboxoptions=new JCheckBox[a.length];
		for (int i = a.length - 1; i > 0; i--) {
			a[i] = a[i - 1];
			chkboxoptions[i]=new JCheckBox(a[i]);
		}
		a[0] = "*";
		
		//return (new ComboBoxModel(chkboxoptions));
		//return chkboxoptions;
		return a;

	}

	private String[] getAttributeListwhere() {
		// TODO Auto-generated method stub

		File xmlFile = new File("src/StreamProcessor.xml");

		DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentbuilder;

		streamAttributes = new String[7];
		try {
			documentbuilder = documentbuilderfactory.newDocumentBuilder();
			Document document = documentbuilder.parse(xmlFile);
			for (int i = 0; i < streamlist.getLength(); i++) {
				attributelist = ((Element) streamlist.item(i))
						.getElementsByTagName("attr");
				for (int j = 0; j < attributelist.getLength(); j++) {
					Node node = attributelist.item(j);
					Element element = (Element) node;
					streamAttributes[i * (streamlist.getLength() + 1) + j] = ((Element) streamlist
							.item(i)).getAttribute("name")
							+ "."
							+ element.getAttribute("name");
				}
			}
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
		return streamAttributes;
	}

	private String[] getStreamNames(int position) {
		// TODO Auto-generated method stub

		File xmlFile = new File("src/StreamProcessor.xml");
		streamlist = null;

		DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentbuilder;
		try {
			documentbuilder = documentbuilderfactory.newDocumentBuilder();
			Document document = documentbuilder.parse(xmlFile);
			streamlist = ((Element) splist.item(position-1))
					.getElementsByTagName("stream");
			join_on=((Element)splist.item(position-1)).getAttribute("join_on");
			window_size=Integer.parseInt(((Element)splist.item(position-1)).getAttribute("window_size"));
			window_type=((Element)splist.item(position-1)).getAttribute("window_type");
			window_speed=Integer.parseInt(((Element)splist.item(position-1)).getAttribute("window_speed"));
			joinonValue.setText(join_on);
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
        String temp=streamNames[0];
		streamNames = new String[streamlist.getLength()+1];
        streamNames[0]=temp;
        
		for (int i = 0; i < streamlist.getLength(); i++) {
			Node node = streamlist.item(i);
			Element element = (Element) node;
			streamNames[i+1] = element.getAttribute("name");
			// }

		}
		return streamNames;
	}

	private String[] getstreamProcessors() {
		// TODO Auto-generated method stub
		File xmlFile = new File("src/StreamProcessor.xml");
		splist = null;
		DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentbuilder;
		try {
			documentbuilder = documentbuilderfactory.newDocumentBuilder();
			Document document = documentbuilder.parse(xmlFile);
			splist = document.getElementsByTagName("stream-processor");
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

		streamProcessors = new String[splist.getLength()+1];

		for (int i = 0; i < splist.getLength(); i++) {
			Node node = splist.item(i);
			Element element = (Element) node;
			streamProcessors[i+1] = element.getAttribute("name");
			// }

		}
		return streamProcessors;
	}

	public void onnotify(){
    	model.addRow(rowAdd);
    }
	
	private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_submitBtnActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_submitBtnActionPerformed

	private void streamProcessorcbActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_streamProcessorcbActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_streamProcessorcbActionPerformed

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(QueryBuilder.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(QueryBuilder.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(QueryBuilder.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(QueryBuilder.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new QueryBuilder1().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton submitBtn;
	private javax.swing.JComboBox streamProcessorcb;
	private javax.swing.JComboBox streamNamescb;
	private javax.swing.JComboBox operationscb;
	private javax.swing.JComboBox operatorscb;
	private javax.swing.JComboBox whereAttributescb;
	private javax.swing.JComboBox selectAttributescb;
	private javax.swing.JLabel use;
	private javax.swing.JLabel from;
	private javax.swing.JLabel select;
	private javax.swing.JLabel where;
	private javax.swing.JLabel joinon;
	private javax.swing.JLabel joinonValue;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel resultsLabel;
	private javax.swing.JLabel titleLabel;
	private javax.swing.JList SelectAttrributes;
	//private javax.swing.JList SelectAttrributes;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTable resultsTable;
	private javax.swing.JTextField whereValue;
	private javax.swing.JCheckBox options;

	private static String[] operations = {"", "Sum", "Count", "Average", "Max", "Min" };
	private static String[] operators = { "<", "<=", "==", ">=", ">", "like" };
	private static NodeList splist;
	private static NodeList streamlist;
	private static NodeList attributelist;

	private static String[] streamNames={"Available streams"};
	private static String[] streamProcessors={"Select Stream processor name"};
	private static JCheckBox[] chkboxoptions={};
	private static String[] streamAttributes={};
	private static String[] headerFields;// = new String[6]; // need to code length

	public String[][] updatedResults;// = new String[0][6];
	public String[] rowAdd=new String[6];
	public String join_on="";
	public int window_size;
	public String window_type;
	public int window_speed;
	static int count = 0;
	int streamPos;
	int AttributePos;
	public DefaultTableModel model;
	// private static Node spnode;
	// End of variables declaration//GEN-END:variables
}
