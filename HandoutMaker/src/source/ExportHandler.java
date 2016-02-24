package source;

import java.util.ArrayList;
import java.util.HashMap;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.list.List;

import source.style.Style;

//Zeilen mit # werden auskommentiert
//Zeilen mit $ werden als Interpreterbefehle aufgefasst
/*
 * list.start
 * list.end
 * table.start<x>x<y>
 * table.end
 * style:<style>
 */

public class ExportHandler
{
	public void export(String filename, HashMap<String, Style> styles, ArrayList<String> text) throws Exception
	{
		TextDocument doc = TextDocument.newTextDocument();
		//Kommentare herausfiltern
		ArrayList<Integer> linesToDelete = new ArrayList<>();
		for (int i = 0; i < text.size(); i++)
		{
			if (text.get(i).trim().startsWith("#"))
			{
				linesToDelete.add(i);
			}
		}

		for (int i = linesToDelete.size() - 1; i >= 0; i--)
		{
			linesToDelete.remove(i);
		}

		//Zeile für Zeile Parser
		for (int i = 0; i < text.size(); i++)
		{
			String line = text.get(i);
			if (line.trim().startsWith("$"))
			{
				execCmd(line);
			}
			else
			{
				doc.addParagraph(line);
			}
		}

	}

	private void execCmd(String line)
	{

	}
}
