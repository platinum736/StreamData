package consumer;

import java.util.Calendar;

public class StreamAttributes {
	
	String name;
	String type;
	Object value;

	public void createValueAttribute() {
		// TODO Auto-generated method stub
		//Create switch statements based on type
		switch(type){
		case "string":
			  value="";
			  break;
		case "int":
			  value=0;
			  break;
		case "timestamp":
			  // value="";
			  value=new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			  break;
		 
		}
		
		
	}

}
