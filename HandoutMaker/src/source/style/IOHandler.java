package source.style;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JComboBox;

import source.Main;
import source.PopoutMessager;

public class IOHandler
{
	public static void saveStyles(File f) throws IOException
	{
		if(f == null)
		{
			PopoutMessager.showNoStyleFileDialogue();

		}
		else
		{
			if(!Main.styles.isEmpty())
			{
				FileWriter writer = new FileWriter(f);
				Iterator<Style> it = Main.styles.values().iterator();
				while(it.hasNext())
				{
					Style style = it.next();
					writer.write(style.toString() + System.getProperty("line.separator"));
					PopoutMessager.messageCmdOnly("Style " + style.identifier + " in Style-File " + f.getName() + " geschrieben", false);
				}
				writer.close();
				PopoutMessager.showSavedStyleFileDialogue(f.getName());
			}
			else
			{
				PopoutMessager.showNoStylesDialogue();
			}
		}
	}
	
	public static void loadStyles(File f, JComboBox removeList, JComboBox editList) throws IOException, FileNotFoundException
	{
		Scanner scanner = new Scanner(f);
		
		while(scanner.hasNextLine())
		{		
			Style style = new Style();
			String[] content = scanner.nextLine().trim().split(";");

			for (int i = 0; i < content.length; i++)
			{
				content[i] = content[i].trim();

				switch (content[i].substring(0, content[i].indexOf("=")))
				{
					case "identifier":
						style.identifier = content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim();
						break;
					case "style":
						style.style = content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim();
						break;
					case "size":
						style.size = Float.parseFloat(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "format":
						style.format = Short.parseShort(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "underlined":
						style.underlined = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "cursive":
						style.cursive = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "lineDistance":
						style.lineDistance = Float.parseFloat(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "color": 
						String[] values = content[i].substring(content[i].indexOf("=") + 1, content[i].length()).replace("[", "").replace("]", "").replace("r=", "").replace("g=", "").replace("b=", "").split(",");
						style.color = new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
						break;
					case "bold":
						style.bold = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "listStyle":
						style.listStyle = Short.parseShort(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					default:
						PopoutMessager.messageCmdOnly("Error: Fehler beim Laden des Styleelements an der Stelle " + i, true);					
						break;
				}
			}
			Main.styles.put(style.identifier, style);
			if(Main.guiMode)
			{
				removeList.addItem(style.identifier);
				editList.addItem(style.identifier);
			}	
			PopoutMessager.messageCmdOnly("Style " + style.identifier + " hinzugefügt", false);	
		}
		scanner.close();
		PopoutMessager.showStyleFileLoadedDialogue(f.getName());
	}
}
