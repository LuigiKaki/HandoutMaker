package source.style;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import source.Main;

public class StyleParser
{
	// ---Testing---
	public static ArrayList<String> formatFile(File f) throws IOException, FileNotFoundException
	{
		BufferedReader reader = new BufferedReader(new FileReader(f));
		ArrayList<String> parsedLines = new ArrayList<String>();
		boolean inString = false;
		// Zeilen einlesen
		for (String str = reader.readLine(); str != null; str = reader.readLine())
		{
			String result = "";
			// Zeichen durchgehen
			for (int index = 0; index < str.length(); index++)
			{
				char chr = str.charAt(index);
				// Wenn hier ein String anfängt/endet wird (nicht) weiter
				// geparsed bis der String zu Ende ist, damit Leerzeichen etc.
				// im String erhalten bleiben.
				if (chr == '"')
					inString = inString ? false : true;
				// Wenn man sich nicht in einem String befindet wird geparsed
				if (!inString && chr != '\n' && chr != ' ')
					result = result + chr;
			}
			parsedLines.add(result);
		}
		reader.close();
		String[] parsedLinesAsArray = new String[parsedLines.size()];
		for (int counter = 0; counter < parsedLinesAsArray.length; counter++)
			parsedLinesAsArray[counter] = parsedLines.get(counter);
		return parsedLines;
	}

	public static void loadStyles(File f) throws IOException, FileNotFoundException
	{
		BufferedReader reader = new BufferedReader(new FileReader(f));

		for (String data = reader.readLine().trim(); !data.equals(""); data = reader.readLine().trim())
		{
			String[] content = data.split(";");

			if (!(content[0].startsWith(String.valueOf('"')) && content[0].endsWith(String.valueOf('"')))
					|| content[0].replaceAll(String.valueOf('"'), "").trim().equals(""))
			{
				System.out.println("Error loading style identifier!");
				continue;
			}

			Style style = new Style();
			style.identifier = content[0].replaceAll(String.valueOf('"'), "").trim();

			for (int i = 1; i < content.length; i++)
			{
				content[i] = content[i].trim();

				switch (content[i].substring(0, content[i].indexOf("=")))
				{
				case "style":
					style.style = content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim();
					break;
				case "size":
					style.size = Short
							.parseShort(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
					break;
				case "format": // erst mal okay
					style.format = Short
							.parseShort(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
					break;
				case "underlinded":
					style.underlined = Boolean.parseBoolean(
							content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
					break;
				case "cursive":
					style.cursive = Boolean.parseBoolean(
							content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
					break;
				case "lineDistance":
					style.lineDistance = Float
							.parseFloat(content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
					break;
				case "color": // soll wohl erst mal gehen
					style.color = Color.getColor(content[i].substring(content[i].indexOf("=") + 1, content[i].length()),
							Color.BLACK);
					break;
				case "bold":
					style.bold = Boolean.parseBoolean(
							content[i].substring(content[i].indexOf("=") + 1, content[i].length()).trim());
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
