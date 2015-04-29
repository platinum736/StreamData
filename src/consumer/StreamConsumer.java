package consumer;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

class StreamConsumer implements Runnable
{
	String[] tokens;
	int id;
	Executer executer;
	static String delim=","; 
	int Sum=0,Count=0;
	Timestamp match;
	int count;
	final int port;
    Timestamp timestamp = null;
    Streamsrc stream;
    LinkedList<Streamsrc> linkedlist;
    int window_size;
    String window_type;
    int window_speed;
    
	StreamConsumer(Executer ex,int i,int port, Streamsrc stream, LinkedList ll,int window_size,String window_type,int window_speed){
		this.executer=ex;
		this.id=i;
		this.port=port;
		this.stream=stream;
		this.linkedlist=ll;
		this.window_size=window_size;
		this.window_type=window_type;
		this.window_speed=window_speed;
	}
	
	
	
    public void run() {
    	
    	// TODO Auto-generated method stub
      DatagramSocket clientSocket = null;
	try {
		clientSocket = new DatagramSocket(port);
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      byte[] receiveData = new byte[1024];

      DatagramPacket rcvPacket = new DatagramPacket(receiveData, receiveData.length);
      
      while(true){
          try {
			clientSocket.receive(rcvPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          String modifiedSentence = new String(rcvPacket.getData());
          StreamAnlysis(modifiedSentence);
      //  System.out.println("FROM SERVER:" + modifiedSentence);
      }
   }
   
   
   
private void StreamAnlysis(String modifiedSentence) {
	// TODO Auto-generated method stub
	String[] tokens=modifiedSentence.split(delim);
	int value;
	String Time;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    Date parsedDate = null;    
    for(int i=0;i<3;i++)
    {
    	stream.attributes[i].value=tokens[i].split("=")[1];
    }
    if(linkedlist.size()<window_size)
        linkedlist.add(stream);
    else
    	if(window_type.equalsIgnoreCase("Folding_window") && linkedlist.size()==window_size)
    	{
    	   linkedlist.clear();
    	   linkedlist.add(stream);
    	}
        if(window_type.equalsIgnoreCase("sliding_window") && linkedlist.size()==window_size)
        {
        	linkedlist.removeFirst();
        	linkedlist.add(stream);
        	executer.notify(id);
        	System.out.println("removed cosumer"+linkedlist.size());
        }
  }
}
