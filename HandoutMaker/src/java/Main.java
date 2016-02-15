package java;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main 
{
	private static short lineNumber = 0;
	
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
				applyStyle(targetFile, style);
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

	private static void applyStyle(File target, Style style) //WIP und so (WIP = work in progress für die Ungebildeten)
	{
		if(style.identifier.equals("(!§$%&/()=?)"))
		{
			System.out.println("Error loading style identifier; Check your style sheet in line: " + lineNumber);
			return;
		}
		
		if(style.identifier.equals("indent")) //oder was auch immer du als option dafür verwenden willst
		{
			//style anwenden (indent)
		}
		else
		{
			//style anwenden (non-indent)
		}
	}

	private static Style loadNextStyle(BufferedReader reader, boolean continueToNext) throws IOException //same here, dachte mir man geht so reihe für reihe das style sheet durch und applied das nacheinander, das boolean sagt aus ob man zur nächsten Zeile gehen soll wegen der while-do-schleife (s.o.)
	{	
		Style style = new Style();
		String content = reader.readLine();
		
		//Identifier auslesen
		content.trim();	
		style.identifier = content.substring(0, findIdentifierEnd(content));
		content = content.replaceFirst(style.identifier + ";", "");
		style.identifier.replaceFirst(String.valueOf(")+ String.valueOf("), "indent").replaceAll(String.valueOf('"'), "");
		
		while(!content.equals(""))
		{
			//Anderes auslesen
			String temp = content.substring(0, findEndOfEntry(content));
			content = content.replaceFirst(temp, "");
			
			switch (temp.substring(0, findEquals(temp)))
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
	
	private static int findIdentifierEnd(String s)
	{
		for(int i = 1; i < s.length(); i++)
		{
			if(s.charAt(i) == Character.valueOf('"'))
			{
				return i;
			}
		}
		return -1;
	}
	
	private static int findEndOfEntry(String s)
	{
		for(int i = 1; i < s.length(); i++)
		{
			if(s.charAt(i) == Character.valueOf(';'))
			{
				return i;
			}
		}
		return -1;
	}
	
	private static int findEquals(String s)
	{
		for(int i = 1; i < s.length(); i++)
		{
			if(s.charAt(i) == Character.valueOf('='))
			{
				return i;
			}
		}
		return -1;
	}
	
	private static class Style
	{
		//füg Variablen hinzu, wenn dir welche einfallen, hab bestimmt net alle, hau die dann auch unten in den constructor
		private short size, format; //format = Blocksatz, zentriert etc., mir fiel kein besserer Name ein, datentyp muss vllt gewechselt werden, fürs erste: 0=rechts, 1 = mitte, 2 = links, 3 = block
		private String style, identifier;
		private boolean cursive, underlined;
		private float lineDistance;
		private Color color; //Kein Plan wie dat mit Farben abläuft, fürs erste mal die Java-Version benutzt 
		
		private Style() //kb 5000 constructors zu erstellen wo man alles einzeln setzen kann, eher einfach setzen und einen MAIN constructor
		{
			identifier = "(!§$%&/()=?)"; //ersma random zeichen, damit dat nich gesucht wird, wenn die id fehlt
			size = 12;	//ab hier so standardeinstellungen
			style = "Times New Roman";
			cursive = false;	
			underlined = false;
			format = 0;
			lineDistance = 1.0F;
			color = Color.BLACK;
		}
		
		private Style(String identifier, short size, short format, String style, boolean cursive, boolean underlined, float lineDistance, Color color)
		{
			this.identifier = identifier;
			this.size = size;
			this.style = style;
			this.cursive = cursive;
			this.underlined = underlined;
			this.format = format;
			this.lineDistance = lineDistance;
			this.color = color;
		}
	}
}
