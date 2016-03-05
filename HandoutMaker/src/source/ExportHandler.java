package source;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.OdfStylesDom;
import org.odftoolkit.odfdom.dom.attribute.style.StyleFontStyleNameAttribute;
import org.odftoolkit.odfdom.dom.attribute.style.StyleLineSpacingAttribute;
import org.odftoolkit.odfdom.dom.attribute.style.StyleTextUnderlineColorAttribute;
import org.odftoolkit.odfdom.dom.attribute.style.StyleTextUnderlineStyleAttribute;
import org.odftoolkit.odfdom.dom.attribute.style.StyleTextUnderlineWidthAttribute;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.dom.style.props.OdfParagraphProperties;
import org.odftoolkit.odfdom.dom.style.props.OdfTextProperties;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeAutomaticStyles;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeStyles;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStyle;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
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

	@SuppressWarnings("unused")
	public void export(String filename, HashMap<String, Style> styles, ArrayList<String> text) throws Exception
	{
		//ODF Toolkit Init undso 
		TextDocument doc = TextDocument.newTextDocument();
		OdfContentDom contentDom = doc.getContentDom();
		OdfStylesDom stylesDom = doc.getStylesDom();
		OdfOfficeAutomaticStyles contentAutoStyles = contentDom.getOrCreateAutomaticStyles();
		OdfOfficeStyles stylesOfficestyles = doc.getOrCreateDocumentStyles();
		ArrayList<String> loadedStyles = new ArrayList<>();
		String currentStyle = null;
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
			//Kommando? 
			if (line.trim().startsWith("$"))
			{
				//Style Kommando
				if (line.trim().startsWith("$style:"))
				{
					String name = line.trim().split(":")[1];
					try
					{
						loadStyle(name);
						currentStyle = name;
					} catch (StyleNotFoundException e)
					{
						//TODO Popout Klasse Verwenden
						JOptionPane.showConfirmDialog(null, "Style " + name + " not found", "Export error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} else
			{
				Paragraph p = doc.addParagraph(text.get(i));
				if(currentStyle != null) p.setStyleName(currentStyle);
			}
		}

	}

	/*
	 *  lädt Style aus der HashMap in die style.xml des ODF-Archivs
	 */
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
		/*
		 *TODO Es gibt hundert verschiedene möglichkeiten, wie etwas unterstrichen werden soll... müsstest du vielleicht im style generator mal einfügen
		 * http://www.books.evc-cit.info/odbook/ch03.html <- mit strg + f  im Firefox mal nach "underline" suchen. dann siehst du die einzelnen Parameter
		 */
		if (style.underlined)
		{
			odfstyle.setProperty(OdfTextProperties.TextUnderlineColor,
					StyleTextUnderlineColorAttribute.Value.FONT_COLOR.toString());
			odfstyle.setProperty(OdfTextProperties.TextUnderlineStyle,
					StyleTextUnderlineStyleAttribute.Value.SOLID.toString());
			odfstyle.setProperty(OdfTextProperties.TextUnderlineWidth,
					StyleTextUnderlineWidthAttribute.Value.AUTO.toString());
		}
		if (style.cursive)
		{
			odfstyle.setProperty(OdfTextProperties.FontStyle, FontStyle.ITALIC.toString());
		}
		//TODO Muss dynamisch gestaltet werden (Verwendung der Color-Klasse vom odftk in der Style.java Datei)
		odfstyle.setProperty(OdfTextProperties.Color, Color.BLACK.toString());
		odfstyle.setProperty(OdfParagraphProperties.LineSpacing, String.valueOf(style.lineDistance));
	}
}
