package DesktopAppPack;

import java.awt.AWTException;
import java.awt.Robot;

public class mouse {
	mouse(String coordString)
	{
	try {
		Robot mouseRobot=new Robot();
		String[] numbers = coordString.replaceAll("[^0-9.]+", " ").trim().split(" ");
		System.out.println(Integer.valueOf(numbers[0])+" "+Integer.valueOf(numbers[1]));
		mouseRobot.mouseMove(Integer.valueOf(numbers[0]),Integer.valueOf(numbers[1]));
	} catch (AWTException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
