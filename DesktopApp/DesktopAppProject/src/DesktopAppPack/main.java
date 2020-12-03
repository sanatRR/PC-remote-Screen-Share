package DesktopAppPack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class main {

	private volatile static int receivePortnum=-1,remotePortnum;
	private volatile static Inet4Address myIp;
	private volatile static String myIpAdd,remoteIpAdd;
	
	public static void main(String[] args) {
	Scanner scan1=new Scanner(System.in);
	try {
			//gets the LAN address of pc
			myIp=(Inet4Address)Inet4Address.getLocalHost();
			myIpAdd=myIp.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to reach host");
		}
	
		new Thread(new Runnable()
				{

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							receive();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start(); 
		
		while(receivePortnum==-1) {};
		System.out.println("\nDesktop details:\nip:"+myIpAdd+"\nport:"+receivePortnum);
		System.out.println("\nEnter Remote details:\nip:");
		remoteIpAdd=scan1.next();
		System.out.println("port:");
		remotePortnum=scan1.nextInt();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					send(remoteIpAdd,remotePortnum);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}).start();
			
	}
	
	public static void receive() throws IOException
	{
		String rmessage="null";
		ServerSocket sRec=new ServerSocket(0); //Make a server socket with the generated Portnum
		receivePortnum=sRec.getLocalPort();
		Socket soRec=sRec.accept();	
		ObjectInputStream oRec=(ObjectInputStream) soRec.getInputStream();
		try {
			rmessage=(String) oRec.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(rmessage);
		oRec.close();
		soRec.close();
		sRec.close();
	}
	
	
	public static void send(String remoteIpAdd,int remotePortnum) throws UnknownHostException, IOException
	{
		String helloPhone="hello from desktop";
		Socket soSend=new Socket(remoteIpAdd,remotePortnum);
		ObjectOutputStream oSend=(ObjectOutputStream) soSend.getOutputStream();
		oSend.writeObject(helloPhone);
		oSend.flush();
		oSend.close();
		soSend.close();
	}

}
