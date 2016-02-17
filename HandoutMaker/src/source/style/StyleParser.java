package source.style;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import source.Main;

public class StyleParser
{
	public static File formatFile(File f) 
	{
		//TODO das gegebene file formattieren, sodass mein parser es verarbeiten kann; wenn es schon passt einfach so lassen; änderungen am input vornehmen und dann wieder returnen;
		return f;
	}
	
	public static void loadStyles(File f) throws IOException, FileNotFoundException
	{	
		BufferedReader reader = new BufferedReader(new FileReader(f));	

		for(String data = reader.readLine().trim(); !data.equals(""); data = reader.readLine().trim())
		{
			String[] content = data.split(";");
			
			if(!(content[0].startsWith(String.valueOf('"')) && content[0].endsWith(String.valueOf('"'))) || content[0].replaceAll(String.valueOf('"'), "").trim().equals(""))
			{
				System.out.println("Error loading style identifier!");
				continue;
			}
			
			Style style = new Style();
			style.identifier = content[0].replaceAll(String.valueOf('"'), "").trim();
			
			for(int i = 1; i < content.length; i++)
			{			
				content[i] = content[i].trim();
				
				switch (content[i].substring(0, content[i].indexOf("=")))
				{
					case "style":
						style.style = content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim();
						break;
					case "size":
						style.size = Short.parseShort(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());							
						break;
					case "format": //erst mal okay
						style.format = Short.parseShort(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "underlinded":
						style.underlined = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "cursive":
						style.cursive = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "lineDistance":
						style.lineDistance = Float.parseFloat(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());	
						break;
					case "color": //soll wohl erst mal gehen
						style.color = Color.getColor(content[i].substring(content[i].indexOf("=") + 1, content[i].length()), Color.BLACK);						
						break;
					case "bold":
						style.bold = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					default: 
						System.out.println("Error loading style property in line: " + i);
						break;
				}	
			}
			Main.styles.put(style.identifier, style);
		}
		reader.close();
	}
}

