package source.style;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import source.Main;

public class IOHandler
{
	public static void saveStyles(File f) throws IOException
	{
		if(!Main.styles.isEmpty())
		{
			FileWriter writer = new FileWriter(f);
			Iterator<Style> it = Main.styles.values().iterator();
			while(it.hasNext())
			{
				Style style = it.next();
				writer.write(style.toString() + System.getProperty("line.separator"));
				System.out.println("Wrote style " + style.identifier + " to file " + f.getName());
			}
			writer.close();
			
			JOptionPane pane = new JOptionPane();
			pane.showMessageDialog(null, "Successfully saved the style file " + f.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
			pane.setVisible(true);
		}
		else
		{
			System.out.println("No styles are loaded.");
			JOptionPane pane = new JOptionPane();
			pane.showMessageDialog(null, "No styles are loaded!", "Error", JOptionPane.ERROR_MESSAGE);
			pane.setVisible(true);
		}
	}
	
	public static void loadStyles(File f) throws IOException, FileNotFoundException
	{
		Scanner scanner = new Scanner(f);
		
		while(scanner.hasNextLine())
		{		
			Style style = new Style();
			String[] content = scanner.nextLine().trim().split(";");
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
					case "underlined":
						style.underlined = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "cursive":
						style.cursive = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "lineDistance":
						style.lineDistance = Float.parseFloat(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					case "color": // soll wohl erst mal gehen
						String[] values = content[i].substring(content[i].indexOf("=") + 1, content[i].length()).replaceAll("[", "")
										  .replaceAll("]", "").replaceAll("r=", "").replaceAll("g=", "").replaceAll("b=", "").split(",");
						style.color = new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
						break;
					case "bold":
						style.bold = Boolean.parseBoolean(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
						break;
					default:
						System.out.println("Error loading style element number " + i);
						break;
				}
			}
			Main.styles.put(style.identifier, style);
			System.out.println("Style " + style.identifier + " added!");
		}
		scanner.close();
	}
}
