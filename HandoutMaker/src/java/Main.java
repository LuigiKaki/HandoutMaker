package java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.style.Style;
import java.util.HashMap;

public class Main 
{
	private static short lineNumber = 0;
	public static HashMap<String, Style> styles = new HashMap<String, Style>();
	
	public static void main(String[] args)
	{
		File styleFile = new File(args[0]);
		File targetFile = new File(args[1]);
		
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
		
		Style style;
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(styleFile));
			reader.mark(100);
			
			do
			{
				style = loadNextStyle(reader, true);
				styles.put(style.identifier, style);
			}
			while(!style.equals(loadNextStyle(reader, false)));
		} 
		catch (FileNotFoundException e)
		{	
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.exit(0);
	}

	private static void applyStyle(File target, Style style) //WIP und so (WIP = work in progress f�r die Ungebildeten)
	{
		if(style.identifier.equals("(!�$%&/()=?)"))
		{
			System.out.println("Error loading style identifier; Check your style sheet in line: " + lineNumber);
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

	private static Style loadNextStyle(BufferedReader reader, boolean continueToNext) throws IOException //same here, dachte mir man geht so reihe f�r reihe das style sheet durch und applied das nacheinander, das boolean sagt aus ob man zur n�chsten Zeile gehen soll wegen der while-do-schleife (s.o.)
	{	
		Style style = new Style();
		String content = reader.readLine();
		
		//Identifier auslesen
		content.trim();	
		style.identifier = content.substring(0, findCharacter(content, '"'));
		content = content.replaceFirst(style.identifier + ";", "");
		style.identifier.replaceFirst(String.valueOf(")+ String.valueOf("), "indent").replaceAll(String.valueOf('"'), "");
		
		while(!content.equals(""))
		{
			//Anderes auslesen
			String temp = content.substring(0, findCharacter(content, ';'));
			content = content.replaceFirst(temp, "");
			
			switch (temp.substring(0, findCharacter(temp, '=')))
			{
				case "style":
				//TODO	
					break;
				case "size":
				//TODO
					break;
				case "type":
				//TODO
					break;
				case "underlinded":
				//TODO
					break;
				case "cursive":
				//TODO
					break;
				case "lineDistance":
				//TODO
					break;
				case "color":
				//TODO
					break;
				default: System.out.println("Error loading style property in line: " + lineNumber);				
			}	
		}
		
		if(continueToNext)
		{
			lineNumber++;
		}
		else
		{
			reader.reset();
		}
		
		reader.mark(100);
		return style;
	}
	
	private static int findCharacter(String s, Character c)
	{
		for(int i = 1; i < s.length(); i++)
		{
			if(s.charAt(i) == Character.valueOf(c))
			{
				return i;
			}
		}
		return -1;
	}
}
