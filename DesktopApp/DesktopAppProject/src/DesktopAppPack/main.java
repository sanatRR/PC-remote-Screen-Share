package DesktopAppPack;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class main {
	
	private volatile static int readStatus=1,sendStatus=1; //1=safe   0=blocked
	private volatile static int receivePortnum=-1,remotePortnum;
	private volatile static Inet4Address myIp;
	private volatile static String myIpAdd,remoteIpAdd;
	static volatile ObjectInputStream oRec;
	static volatile ObjectOutputStream oSend;
	
	public static void main(String[] args) {
	Scanner scan1=new Scanner(System.in);
	try {
			//gets the LAN address of pc
			myIp=(Inet4Address)Inet4Address.getLocalHost();
			myIpAdd=myIp.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	
	
	Thread readThread=new Thread(new Runnable()
	{

		@Override
		public void run() {
			try
			{
				ServerSocket sRec=new ServerSocket(0); //Make a server socket with the generated Portnum
				receivePortnum=sRec.getLocalPort();
				Socket soRec=sRec.accept();	
				oRec=new ObjectInputStream(soRec.getInputStream()) ;
			//	while(true){}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	});
	
	
	
	readThread.start();
		
		while(receivePortnum==-1) {};
		System.out.println("\nDesktop details:\nip:"+myIpAdd+"\nport:"+receivePortnum);
		System.out.println("\nEnter Remote details:\nip:");
		remoteIpAdd=scan1.next();
		System.out.println("port:");
		remotePortnum=scan1.nextInt();
		
		
		Thread sendThread=new Thread(new Runnable() {

			@Override
			public void run() {
				try {	
					Socket soSend=new Socket(remoteIpAdd,remotePortnum);
					oSend=new ObjectOutputStream(soSend.getOutputStream());
				//	while(true){}
				} catch (Exception e) {
					e.printStackTrace();
					run();
				} 
			}
			
		});
		
		sendThread.start();
		
		
		
		while(true)  
		{
			if(readStatus==1)
				{
					readStatus=0;
					receive();
				}
		}  
		
	}
	
	
	
	
	public static void receive() 
	{
		new Thread(new Runnable() {

			@Override
			public void run() {
				Object receivedObject;
				try {
					while(oRec==null) {}
					System.out.println("debug 1 oRec not null");
					receivedObject=oRec.readObject();
					switch(receivedObject.getClass().getName())
					{
					case "java.lang.Integer":
						new keyboard((Integer) receivedObject);
						break;
					case "java.lang.String":
						new mouse((String) receivedObject);
						break;
					case "java.lang.Double":
						if((Double)receivedObject==50.0)
							new display();
						break;
					default:
						break;
					}
				}
				catch(EOFException e){
					System.out.println("Remote has Shut down");
					System.exit(0);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					readStatus=1;
				}
			}
		}).start();
	}
	
	
	public static void send(Object o) ///Added to thread
	{
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(oSend==null) {};
				try {
					oSend.writeObject(o);
					oSend.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

