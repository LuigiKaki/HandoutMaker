package source;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import source.style.Style;
import source.style.StyleParser;

public class Main 
{
	public static HashMap<String, Style> styles = new HashMap<String, Style>();
	
	public static void main(String[] args)
	{	
		try 
		{
			File styleFile = new File("./resources/styleNormal.txt");
			File targetFile = new File("./resources/text.txt");
			//File styleFile = new File(args[0]);
			//File targetFile = new File(args[1]);
			
			if(!styleFile.exists())
			{
				System.out.println("Style sheet could not be found. Make sure the path and spelling are correct!");
				System.exit(0); 
			}
			if(!targetFile.exists())
			{
				System.out.println("Target could not be found. Make sure the path and spelling are correct!");
				System.exit(0); 
			}
			styleFile = StyleParser.formatFile(styleFile);
			StyleParser.loadStyles(styleFile);
		} 
		catch (Exception e)
		{	
			e.printStackTrace();
		} 
		System.exit(0);
	}

	private static void applyStyle(File target, Style style) //WIP und so (WIP = work in progress f�r die Ungebildeten)
	{
		if(style.identifier.equals("(!�$%&/()=?)"))
		{
			System.out.println("Error loading style identifier; Check your style sheet!");
			return;
		}
		
		if(style.identifier.equals("indent")) //oder was auch immer du als option daf�r verwenden willst
		{
			//style anwenden (indent)
		}
		else
		{
			//style anwenden (non-indent)
		}
	}
}
