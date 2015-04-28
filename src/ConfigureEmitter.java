import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;


public class ConfigureEmitter {

	int attribute1;
	String attribute2;
	Timestamp currentTimestamp;
	
	
	private void setAttributes() {
		this.attribute1 = 0+(int) (Math.random()*(50));
		SecureRandom random=new SecureRandom();
		attribute2=new BigInteger(130,random).toString(32);
		currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	}
	
	public String toString(){
		setAttributes();
		return("Attribute1="+this.attribute1+","+"Attribute2="+this.attribute2+","+"TimeStamp="+this.currentTimestamp);
	}
}
