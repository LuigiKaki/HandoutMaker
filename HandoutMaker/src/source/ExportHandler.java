package source;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.OdfStylesDom;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.dom.style.props.OdfTextProperties;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeAutomaticStyles;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeStyles;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStyle;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;

import source.exceptions.StyleNotFoundException;
import source.style.Style;

//Zeilen mit # werden auskommentiert
//Zeilen mit $ werden als Interpreterbefehle aufgefasst
/*
 * list.start
 * list.end
 * table.start<x>x<y>
 * table.end
 * style:<style>
 * defaultStyle:<style>
 */

public class ExportHandler
{
	TextDocument doc;
	OdfContentDom contentDom;
	OdfStylesDom stylesDom;
	OdfOfficeAutomaticStyles contentAutoStyles;
	OdfOfficeStyles stylesOfficestyles;
	ArrayList<String> loadedStyles;

	public ExportHandler() throws Exception
	{
		doc = TextDocument.newTextDocument();
		contentDom = doc.getContentDom();
		stylesDom = doc.getStylesDom();
		contentAutoStyles = contentDom.getOrCreateAutomaticStyles();
		stylesOfficestyles = doc.getOrCreateDocumentStyles();
		loadedStyles = new ArrayList<>();
	}

	public void export(String filename, HashMap<String, Style> styles, ArrayList<String> text) throws Exception
	{
		//ODF Toolkit Init undso 
		TextDocument doc = TextDocument.newTextDocument();
		OdfContentDom contentDom = doc.getContentDom();
		OdfStylesDom stylesDom = doc.getStylesDom();
		OdfOfficeAutomaticStyles contentAutoStyles = contentDom.getOrCreateAutomaticStyles();
		OdfOfficeStyles stylesOfficestyles = doc.getOrCreateDocumentStyles();
		ArrayList<String> loadedStyles = new ArrayList<>();
		Style defaultStyle = null;
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
				if (line.trim().startsWith("$style:"))
				{
					String name = line.trim().split(":")[1];
					try
					{
						loadStyle(name);
						//kommt noch
					}
					catch (StyleNotFoundException e)
					{
						JOptionPane.showConfirmDialog(null, "Style " + name + " not found", "Export error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			else
			{
				//kommt noch
			}
		}

	}

	private void loadStyle(String s) throws StyleNotFoundException
	{
		Style style = Main.styles.get(s);
		if (style == null)
		{
			throw new StyleNotFoundException(s);
		}	
		OdfStyle odfstyle = stylesOfficestyles.newStyle(style.identifier.substring(1), OdfStyleFamily.Paragraph);
		odfstyle.setProperty(OdfTextProperties.FontSize, String.valueOf(style.size + "pt"));
		if (style.bold)
		{
			odfstyle.setProperty(OdfTextProperties.FontWeight, "bold");
		}		
	}
}
