import java.io.*;
import java.net.*;

class StartEmitter
{
   public static void start(int Port) throws Exception
      {
            byte[] sendData = new byte[1024];
            String sentence="";
            ConfigureEmitter c=new ConfigureEmitter();
            InetAddress recvAddress = InetAddress.getByName("localhost");
            DatagramSocket serverSocket = new DatagramSocket();
            while(true)
               {
            	  sentence=c.toString();
            	  sendData=sentence.getBytes();
                  DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length,recvAddress,Port);
              //    System.out.println(sentence);
                  serverSocket.send(sendPacket);
                  Thread.sleep(5000);
               }
      }
}