package source;

import java.io.File;
import java.util.Iterator;

import org.odftoolkit.odfdom.dom.style.props.OdfParagraphProperties;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions;
import org.odftoolkit.simple.style.StyleTypeDefinitions.TextLinePosition;
import org.odftoolkit.simple.text.Paragraph;

import source.style.Style;

public class TempHelperClass
{
	//TODO wenn das Programm fertig is mit exporthandler mergen
	public static void applyStyleToOdt(File file) throws Exception
	{
		TextDocument finalDoc = TextDocument.newTextDocument();
		TextDocument document = TextDocument.loadDocument(file);

		Iterator<Paragraph> it = document.getParagraphIterator();
		while (it.hasNext())
		{
			Paragraph pOld = it.next();
			Paragraph p = finalDoc.addParagraph(pOld.getTextContent());

			Iterator<String> it1 = Main.styles.keySet().iterator();
			boolean changed = false;
			while (it1.hasNext())
			{
				String s = it1.next();
				if (p.getTextContent().startsWith(s))
				{
					Style style = Main.styles.get(s);

					//schriftart, schriftgröße, schriftfarbe, unterstrichen, fett, kursiv, ausrichtung
					p.setFont(new Font(style.style, style.getFontStyle(), (double) style.size, new Color(style.color), style.underlined ? TextLinePosition.UNDER : TextLinePosition.REGULAR));
					p.setTextContent(p.getTextContent().replace(s, ""));
					p.setHorizontalAlignment(style.getHorizontalAlignmentType());
					
					//TODO zeilenabstand	
					p.getOdfElement().setProperty(OdfParagraphProperties.LineSpacing, "1.5in");
					
					changed = true;
					break;
				}
			}
			if(!changed)
			{
				p.setFont(pOld.getFont());
				p.setHorizontalAlignment(pOld.getHorizontalAlignment());
			}
		}
		finalDoc.removeParagraph(finalDoc.getParagraphByIndex(0, false));
		finalDoc.save(new File(file.getAbsolutePath().replace(".odt", "1.odt")));
	}
}
