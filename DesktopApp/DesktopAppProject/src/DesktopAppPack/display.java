package DesktopAppPack;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

public class display{
	display()
	{
		try {
			Robot dispCapture=new Robot();
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenBufferImage = dispCapture.createScreenCapture(screenRect);
			ByteArrayOutputStream bStream=new ByteArrayOutputStream();
			ImageIO.write(screenBufferImage,"PNG", bStream);
			byte[] tempArray=bStream.toByteArray();
			Byte[] finalArray=new Byte[tempArray.length];
			int i=0;
			for(byte ivar:tempArray)
				finalArray[i++]=ivar;
			sendTest(finalArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	static void sendTest(Object o)
	{
		main.send(o);	
	}
}
                   
    
    