package source.style;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import source.Main;

public class StyleParser
{
	public static void loadStyles(File f) throws IOException, FileNotFoundException
	{
		//TODO krass verbuggte scheiße / parser komplett neu schreiben für automatische erstellung durch programm
		Scanner scanner = new Scanner(f);
		
		while(scanner.hasNextLine())
		{
			String data = scanner.nextLine();
			
			System.out.println(data);
			
			Style style = new Style();
			String[] content = data.trim().split(";");
			style.identifier = content[0].replaceAll(String.valueOf('"'), "").trim();

			if (style.identifier.equals(""))
			{
				System.out.println("Error loading style identifier!");
				continue;
			}
			
			System.out.println(style.identifier);

			for (int i = 1; i < content.length; i++)
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
					case "format": // erst mal okay
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
					case "color": // soll wohl erst mal gehen
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
			System.out.println("Style added!");
		}
		scanner.close();
	}
}
