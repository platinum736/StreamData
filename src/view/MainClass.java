package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import consumer.Executer;

public class MainClass extends javax.swing.JFrame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		int querycount = 0;
		String query = "";
		String select = "0";

		JFrame i = new JFrame();
		i.show();
		
		

		select = JOptionPane.showInputDialog(i, "Enter Query name to save",
				null);

		try {

			String sCurrentLine;

			br = new BufferedReader(
					new FileReader(
							"C:\\Users\\rishabh-pc\\Documents\\Java workspace\\StreamEmitters\\src\\savedQueries.txt"));

			if (select.equalsIgnoreCase("0")) {
				QueryBuilder1.main();
			} else {
				while ((sCurrentLine = br.readLine()) != null) {
					query = sCurrentLine;
					if (query.split(",")[0].equalsIgnoreCase(select))
						break;
				}
				
				QueryBuilder1 q1 = new QueryBuilder1();
				String spname=query.split(",")[1];
				int window_size=Integer.parseInt(query.split(",")[2]);
				String window_type=query.split(",")[3]; 
				int window_speed=Integer.parseInt(query.split(",")[4]);
				int no_of_streams=Integer.parseInt(query.split(",")[5]);
				int stream_pos=Integer.parseInt(query.split(",")[6]);
				int attribute_position=Integer.parseInt(query.split(",")[7]);
				int function=Integer.parseInt(query.split(",")[8]);
				int where_stream_pos=Integer.parseInt(query.split(",")[9]);
				int where_att_pos=Integer.parseInt(query.split(",")[10]);
				int operation=Integer.parseInt(query.split(",")[11]);
				String value=query.split(",")[12];
				q1.main();
				Executer ex = new Executer(q1, spname, window_size,window_type, window_speed, no_of_streams, stream_pos,attribute_position,
						function, where_stream_pos,where_att_pos, operation, value);
				if (stream_pos == 0 && attribute_position == 0) {
					q1.updatedResults = new String[0][6];
					q1.headerFields = new String[6];
				} else {
					q1.updatedResults = new String[0][2];
					q1.headerFields = new String[2];
				}
				q1.model = new DefaultTableModel(q1.updatedResults, q1.headerFields);
				q1.resultsTable.setModel(q1.model);
				Thread t = new Thread(ex);
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}
