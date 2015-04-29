package consumer;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import view.QueryBuilder1;

public class Executer implements Runnable {

	QueryBuilder1 parent;
	StreamProcessor sp;
	int operation;
	private String spname;
	private int stream_pos;
	private int attribute_position;
	private int where_stream_pos;
	private int where_att_pos;
	private String value;
	private int function;
	private int no_of_streams;
	private int window_size;
	private int window_speed;
	private String window_type;
	private String tempData[];
	private LinkedList temp = new LinkedList();
	
	int sum=0,avg=0,min=100,max=0,filtercount=0;

	LinkedList<Streamsrc>[] linkedlist;
	StreamConsumer[] consumers;
	Thread[] t;
	boolean[] lock;

	public Executer(QueryBuilder1 parent, String spname, int window_size,
			String window_type, int window_speed, int no_of_streams,
			int stream_pos, int attribute_position, int function,
			int where_stream_pos, int where_att_pos, int operation, String value) {
		this.parent = parent;
		this.spname = spname;
		this.window_size = window_size;
		this.window_type = window_type;
		this.window_speed = window_speed;
		this.stream_pos = stream_pos;
		this.attribute_position = attribute_position;
		this.where_stream_pos = where_stream_pos;
		this.where_att_pos = where_att_pos;
		this.value = value;
		this.operation = operation;
		this.function = function;
		this.no_of_streams = no_of_streams;
		sp = new StreamProcessor(spname);
		System.out.println("n=" + no_of_streams);
		linkedlist = new LinkedList[no_of_streams];
		consumers = new StreamConsumer[no_of_streams];
		t = new Thread[no_of_streams];
		tempData = new String[6];
		lock = new boolean[no_of_streams];
	}

	public void run() {
		for (int i = 0; i < no_of_streams; i++) {
			// passsing all the parameters for 1st stream to consumer

			linkedlist[i] = new LinkedList<Streamsrc>();
			consumers[i] = new StreamConsumer(this, i, sp.streams[i].port,
					sp.streams[i], linkedlist[i], window_size, window_type,
					window_speed);
			t[i] = new Thread(consumers[i]);
			t[i].start();
		}
		try {
			// if(stream_pos==0 && attribute_position==0)
			printAll();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notify(int i) {
		lock[i] = true;
		// System.out.println("unlocked by "+i);
	}

	private void printAll() throws ParseException {
		// TODO Auto-generated method stub
		int count = 0;
		boolean flag = false;
		int length;

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss.SSS");
		Date parsedDate = null;
		while (true) {
			// while(linkedlist[0].size()<=count);
			if (linkedlist[0].size() > count && linkedlist[1].size() > count) {
				// System.out.println("Size "+linkedlist[0].size()+"Count "+count);
				if (((sp.streams[0] = (Streamsrc) linkedlist[0].get(count)) != null)
						&& ((sp.streams[1] = (Streamsrc) linkedlist[1]
								.get(count)) != null)) {
					// System.out.print((dateFormat.parse((String)
					// sp.streams[0].attributes[2].value)).getTime());
					if (((dateFormat
							.parse((String) sp.streams[0].attributes[2].value))
							.getTime()
							- (dateFormat
									.parse((String) sp.streams[0].attributes[2].value))
									.getTime() < 5000)
							|| (((dateFormat
									.parse((String) sp.streams[0].attributes[2].value))
									.getTime()
									- (dateFormat
											.parse((String) sp.streams[0].attributes[2].value))
											.getTime() < 5000))) {
						for (int j = 0; j < 3; j++) // Print each attribute
						{
							// System.out.print(sp.streams[0].attributes[j].name+"="+sp.streams[0].attributes[j].value+"\n"+
							// sp.streams[1].attributes[j].name+"="+sp.streams[1].attributes[j].value+"\n");
							tempData[0 * 3 + j] = ((String) sp.streams[0].attributes[j].value)
									.trim();
							tempData[1 * 3 + j] = ((String) sp.streams[1].attributes[j].value)
									.trim();

							if (stream_pos == -1 && attribute_position == -1) {
								if (where_att_pos == -1) {
									parent.rowAdd[0 * 3 + j] = ((String) sp.streams[0].attributes[j].value)
											.trim();
									parent.rowAdd[1 * 3 + j] = ((String) sp.streams[1].attributes[j].value)
											.trim();
									flag = true;
								} else
									flag = filterResult(count, j);
							} else {
								if ((where_att_pos == -1) && (j==2)) {
									flag=aggregate(j,count);
								} else
									flag = agregateandfilterResult(count, j);
								
							}
						}//j loop finish
						System.out.println(linkedlist[0].size() + " " + count
								+ " " + linkedlist[1].size());
						if (flag == true) {
							parent.onnotify();
						}
						temp.add(Arrays.toString(tempData));
						System.out.println(temp.getFirst());
					}
				}
				count++;
				// }
				// else
				// {
				if (window_type.equalsIgnoreCase("Folding_Window")
						&& count == window_size) {
					count = 0;
					temp.clear();
				} else if (window_type.equalsIgnoreCase("sliding_Window")
						&& count == window_size) {
					// length=temp.size();
					// if(temp.size())
					temp.removeFirst();
					count = window_size - 1;
					System.out.println("c" + temp.size() + " " + count + " "
							+ linkedlist[0].size());

					lock[0] = false;
					lock[1] = false;
					while (lock[0] == false || lock[1] == false) {
						System.out.println("struck!!");
					} // Busy Wait until new element produced
				}

			}
		} // While loop finish
	} // Function Finish

	private boolean agregateandfilterResult(int count, int j) {
		// TODO Auto-generated method stub
         int vanishingValue;
		 switch(function){
			 case 1:{
				 //sum
				 if(filterResultAgg())
				     sum+=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value).trim());				 
				 if(count==window_size-1 && window_type.equalsIgnoreCase("sliding_window")){
					 parent.rowAdd[0] =sum+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];   //2 is for index of timestamp
					 vanishingValue=Integer.parseInt(temp.getFirst().toString().replaceAll("\\[","").replaceAll("\\]", "").split(",")[attribute_position]);
					 if(filterResultAgg(vanishingValue))
					    sum-=vanishingValue;
					 return true;
				 }
				 else if(count==window_size-1 && window_type.equalsIgnoreCase("folding_window")){
					 parent.rowAdd[0] =sum+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 sum=0;
					 return true;
				 }
				 else
					 return false;
			 }
			 case 2:{
				 //count
				 if(filterResultAgg())
				     filtercount++;
				 if(count==window_size-1 && window_type.equalsIgnoreCase("sliding_window")){
					 parent.rowAdd[0] =filtercount+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 vanishingValue=Integer.parseInt(temp.getFirst().toString().replaceAll("\\[","").replaceAll("\\]", "").split(",")[attribute_position]);
					 if(filterResultAgg(vanishingValue))
					    filtercount--;
					 return true;
				 }
				 else if(count==window_size-1 && window_type.equalsIgnoreCase("folding_window")){
					 parent.rowAdd[0] =filtercount+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 filtercount=0;
					 return true;
				 }
				 else
					 return false;
			 }
			 case 3:{
				 //average
				 if(filterResultAgg())
				 {
				     sum+=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value));
				     filtercount++;
				 }
				 if(count==window_size-1 && window_type.equalsIgnoreCase("sliding_window")){
					 avg=sum/filtercount;
					 parent.rowAdd[0] =avg+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 vanishingValue=Integer.parseInt(temp.getFirst().toString().replaceAll("\\[","").replaceAll("\\]", "").split(",")[attribute_position]);
					 if(filterResultAgg(vanishingValue))
					 {
					    sum-=vanishingValue;
					    filtercount--;
					 }
					 return true;
				 }
				 else if(count==window_size-1 && window_type.equalsIgnoreCase("folding_window"))
				 {
					 avg=sum/filtercount;
					 parent.rowAdd[0] =avg+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 avg=0;
					 return true;
				 }
				 else
					 return false;
			 }
			 case 4:{
				 //max
				 if(filterResultAgg()){
				 if(Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value))>max)
				 {
					 max=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value));
				 }	
				 }
				 if(count==window_size-1){
					 parent.rowAdd[0] =max+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 return true;
				 }
				 else
					 return false;
			 }
			 case 5:{
				 //min
				 if(filterResultAgg()){
				 if(Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value))<min)
				 {
					 min=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value));
				 }
				 }
				 if(count==window_size-1){
					 parent.rowAdd[0] =max+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 return true;
				 }
				 else
					 return false;
			  }
			default:
					 return false;
		 }
	}

	private boolean filterResult(int count, int j) {
		// TODO Auto-generated method stub
		System.out.println(operation);
		switch (operation) {
		case 0: {
			if (Integer.parseInt(tempData[where_att_pos]) < Integer
					.parseInt(value)) {
				parent.rowAdd[0 * 3 + j] = ((String) sp.streams[0].attributes[j].value)
						.trim();
				parent.rowAdd[1 * 3 + j] = ((String) sp.streams[1].attributes[j].value)
						.trim();
				return true;
			} else
				return false;
		}
		case 1: {
			if (Integer.parseInt(tempData[where_att_pos]) <= Integer
					.parseInt(value)) {
				parent.rowAdd[0 * 3 + j] = ((String) sp.streams[0].attributes[j].value)
						.trim();
				parent.rowAdd[1 * 3 + j] = ((String) sp.streams[1].attributes[j].value)
						.trim();
				return true;
			} else
				return false;
			// break;
		}
		case 2: {
			if (Integer.parseInt(tempData[where_att_pos]) == Integer
					.parseInt(value)) {
				parent.rowAdd[0 * 3 + j] = ((String) sp.streams[0].attributes[j].value)
						.trim();
				parent.rowAdd[1 * 3 + j] = ((String) sp.streams[1].attributes[j].value)
						.trim();
				return true;
			} else
				return false;
			// break;
		}
		case 3: {
			if (Integer.parseInt(tempData[where_att_pos]) >= Integer
					.parseInt(value)) {
				parent.rowAdd[0 * 3 + j] = ((String) sp.streams[0].attributes[j].value)
						.trim();
				parent.rowAdd[1 * 3 + j] = ((String) sp.streams[1].attributes[j].value)
						.trim();
				return true;
			} else
				return false;
			// break;
		}
		case 4: {
			if (Integer.parseInt(tempData[where_att_pos]) > Integer
					.parseInt(value)) {
				parent.rowAdd[0 * 3 + j] = ((String) sp.streams[0].attributes[j].value)
						.trim();
				parent.rowAdd[1 * 3 + j] = ((String) sp.streams[1].attributes[j].value)
						.trim();
				return true;
			} else
				return false;
			// break;
		}
		default:
			return false;
		}
	}

	private boolean filterResultAgg(int vanishingValue) {
		// TODO Auto-generated method stub
		System.out.println(operation);
		switch (operation) {
		case 0: {
			if (vanishingValue < Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
		}
		case 1: {
			if (vanishingValue <= Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		case 2: {
			if (vanishingValue == Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		case 3: {
			if (vanishingValue >= Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		case 4: {
			if (vanishingValue > Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		default:
			return false;
		}
	}

	
	private boolean filterResultAgg() {
		// TODO Auto-generated method stub
		System.out.println(operation);
		switch (operation) {
		case 0: {
			if (Integer.parseInt(tempData[where_att_pos]) < Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
		}
		case 1: {
			if (Integer.parseInt(tempData[where_att_pos]) <= Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		case 2: {
			if (Integer.parseInt(tempData[where_att_pos]) == Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		case 3: {
			if (Integer.parseInt(tempData[where_att_pos]) >= Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		case 4: {
			if (Integer.parseInt(tempData[where_att_pos]) > Integer
					.parseInt(value)) {
				return true;
			} else
				return false;
			// break;
		}
		default:
			return false;
		}
	}

	private boolean aggregate(int j,int count) {
		
		 switch(function){
			 case 1:{
				 //sum
				 sum+=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value).trim());				 
				 if(count==window_size-1 && window_type.equalsIgnoreCase("sliding_window")){
					 parent.rowAdd[0] =sum+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 sum-=Integer.parseInt(temp.getFirst().toString().replaceAll("\\[","").replaceAll("\\]", "").split(",")[attribute_position]);
					 return true;
				 }
				 else if(count==window_size-1 && window_type.equalsIgnoreCase("folding_window")){
					 parent.rowAdd[0] =sum+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 sum=0;
					 return true;
				 }
				 else
					 return false;
			 }
			 case 2:{
				 //count
				 filtercount++;
				 if(count==window_size-1 && window_type.equalsIgnoreCase("sliding_window")){
					 parent.rowAdd[0] =filtercount+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 filtercount--;
					 return true;
				 }
				 else if(count==window_size-1 && window_type.equalsIgnoreCase("folding_window")){
					 parent.rowAdd[0] =filtercount+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 filtercount=0;
					 return true;
				 }
				 else
					 return false;
			 }
			 case 3:{
				 //average
				 sum+=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value));
				 filtercount++;
				 if(count==window_size-1 && window_type.equalsIgnoreCase("sliding_window")){
					 avg=sum/filtercount;
					 parent.rowAdd[0] =avg+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 sum-=Integer.parseInt(temp.getFirst().toString().replaceAll("\\[","").replaceAll("\\]", "").split(",")[attribute_position]);
					 filtercount--;
					 return true;
				 }
				 else if(count==window_size-1 && window_type.equalsIgnoreCase("folding_window"))
				 {
					 avg=sum/filtercount;
					 parent.rowAdd[0] =avg+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 avg=0;
					 return true;
				 }
				 else
					 return false;
			 }
			 case 4:{
				 //max
				 if(Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value))>max)
				 {
					 max=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value));
				 }	
				 if(count==window_size-1){
					 parent.rowAdd[0] =max+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 return true;
				 }
				 else
					 return false;
			 }
			 case 5:{
				 //min
				 if(Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value))<min)
				 {
					 min=Integer.parseInt(((String) sp.streams[stream_pos].attributes[attribute_position].value));
				 }
				 if(count==window_size-1){
					 parent.rowAdd[0] =max+"";
					 parent.rowAdd[1]=temp.get(window_size/2).toString().replaceAll("\\[", "").replaceAll("\\]", "").split(",")[2];
					 return true;
				 }
				 else
					 return false;
			  }
			default:
					 return false;
		 }
	}

}
