package consumer;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private  int no_of_streams;
	private int window_size;
	private int window_speed;
	private String window_type;
	
	
    LinkedList<Streamsrc>[] linkedlist;
    StreamConsumer[] consumers;
    Thread[] t;
    
    public Executer(QueryBuilder1 parent,String spname,int window_size,String window_type,int window_speed,int no_of_streams,
    		int stream_pos,int attribute_position,int function,int where_stream_pos,int where_att_pos,int operation,String value) {
    	this.parent=parent;
    	this.spname=spname;
    	this.window_size=window_size;
    	this.window_type=window_type;
    	this.window_speed=window_speed;
    	this.stream_pos=stream_pos;
    	this.attribute_position=attribute_position;
    	this.where_stream_pos=where_stream_pos;
    	this.where_att_pos=where_att_pos;
    	this.value=value;
    	this.operation=operation;
    	this.function=function;
    	this.no_of_streams=no_of_streams;
    	sp=new StreamProcessor(spname);
    	System.out.println("n="+no_of_streams);
    	linkedlist=new LinkedList[no_of_streams];
    	consumers=new StreamConsumer[no_of_streams];
    	t=new Thread[no_of_streams];
    	
	}



	public void run() {
       for(int i=0;i<no_of_streams;i++)
       {
    	   //passsing all the parameters for 1st stream to consumer
    	
    	   linkedlist[i]=new LinkedList<Streamsrc>();
    	   consumers[i]=new StreamConsumer(sp.streams[i].port,sp.streams[i],linkedlist[i],window_size,window_type,window_speed); 
    	   t[i]=new Thread(consumers[i]);
    	   t[i].start();
       }
       try {
    //	   if(stream_pos==0 && attribute_position==0)
		           printAll();
    	   
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	private  void printAll() throws ParseException {
		// TODO Auto-generated method stub	
		int count=0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date parsedDate=null;
		while(true){
			if(linkedlist[0].size()>count && linkedlist[1].size()>count){
				//System.out.println("Size "+linkedlist[0].size()+"Count "+count);
		    	 if(((sp.streams[0]=(Streamsrc) linkedlist[0].get(count))!=null) && ((sp.streams[1]=(Streamsrc)linkedlist[1].get(count))!=null)){
		    		 //System.out.print((dateFormat.parse((String) sp.streams[0].attributes[2].value)).getTime());
		    		 if(((dateFormat.parse((String) sp.streams[0].attributes[2].value)).getTime()-(dateFormat.parse((String) sp.streams[0].attributes[2].value)).getTime()<5000)||
		    			(((dateFormat.parse((String) sp.streams[0].attributes[2].value)).getTime()-(dateFormat.parse((String) sp.streams[0].attributes[2].value)).getTime()<5000)))
		    	      for(int j=0;j<3;j++)   //Print each attribute
		    	    	  {
		    	        //  System.out.print(sp.streams[0].attributes[j].name+"="+sp.streams[0].attributes[j].value+"\n"+
		    	    	  //                        sp.streams[1].attributes[j].name+"="+sp.streams[1].attributes[j].value+"\n");
		    	          parent.rowAdd[0*3+j]=((String) sp.streams[0].attributes[j].value).trim();
		    	          parent.rowAdd[1*3+j]=((String) sp.streams[1].attributes[j].value).trim();
		    	    	  }
		    		     //QueryBuilder1.updatedResults[count]=QueryBuilder1.rowAdd;
		    		      parent.onnotify();
		    	 //} 
		    		 
		    	 }
			count++;  
		}
		    	 
		    	 if( window_type.equalsIgnoreCase("Folding_Window") && count==window_size )
		    		 count=0;
		    	 else if( window_type.equalsIgnoreCase("Sliding_Window") && count==window_size)
		    		 count--;
		    	 
		}		
	}
	
    private void sum(int position)
    {
    	int count=0;
    	
    }

}
