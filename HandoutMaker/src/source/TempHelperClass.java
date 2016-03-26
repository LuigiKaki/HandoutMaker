package source;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.odftoolkit.odfdom.dom.style.props.OdfParagraphProperties;
import org.odftoolkit.odfdom.dom.style.props.OdfStyleProperty;
import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.TextLinePosition;
import org.odftoolkit.simple.text.Paragraph;
import org.odftoolkit.simple.text.list.List;
import org.odftoolkit.simple.text.list.ListItem;

import source.style.Style;

public class TempHelperClass
{
	
 /*	public static void toDo(File file) throws Exception
	{
		TextDocument document = TextDocument.loadDocument(file);
		TextDocument tempDoc = TextDocument.newTextDocument();
		HashMap<Integer, Object> content = new HashMap<Integer, Object>();
		
		boolean changed;
		int contentNumber = -1;
		
		//Paragraph Handling
		Iterator<Paragraph> it = document.getParagraphIterator();
		while (it.hasNext())
		{
			Paragraph pOld = it.next();
			Paragraph p = tempDoc.addParagraph(pOld.getTextContent());
			contentNumber++;
			
			//Prüfen ob Style gegeben und anwenden
			Iterator<String> it1 = Main.styles.keySet().iterator();	
			changed = false;
			while (it1.hasNext())
			{
				String s = it1.next();
				if (p.getTextContent().startsWith(s))
				{
					Style style = Main.styles.get(s);
	
					p.setFont(new Font(style.style, style.getFontStyle(), (double) style.size, new Color(style.color), style.underlined ? TextLinePosition.UNDER : TextLinePosition.REGULAR));
					p.setTextContent(p.getTextContent().replace(s, ""));
					p.setHorizontalAlignment(style.getHorizontalAlignmentType());			
					p.getOdfElement().setProperty(OdfParagraphProperties.LineSpacing, (style.lineDistance - 1F) * 14F + "pt");			
			
					changed = true;
					break;
				}
			}
			if(!changed) //wenn kein style gefunden wird so wie im Ursprung übernehmen
			{
				p.setFont(pOld.getFont());
				p.setHorizontalAlignment(pOld.getHorizontalAlignment());
				p.getOdfElement().setProperty(OdfParagraphProperties.LineSpacing, pOld.getOdfElement().getProperty(OdfParagraphProperties.LineSpacing));	
			}
			
			if(!p.getTextContent().equals(""))
			{
				content.put(contentNumber, p);
			}
		}
		
		//List Handling
		contentNumber = -1;
		Iterator<List> it1 = document.getListIterator();
		while (it1.hasNext())
		{
			List l = tempDoc.addList();
			List lOld = it1.next();
			contentNumber++;
			
			for(ListItem i : lOld.getItems())
			{
				l.addItem(i.getTextContent());
			}
			
			if(l != null && !l.getItems().isEmpty())
			{
				content.put(contentNumber, l);
			}
		}
		
		TextDocument finalDoc = TextDocument.newTextDocument();
		finalDoc.removeParagraph(finalDoc.getParagraphByIndex(0, false));
		int nextCont = 0;
		
		while(!content.isEmpty())
		{
			Iterator<Entry<Integer, Object>> cont = content.entrySet().iterator();
			while(cont.hasNext())
			{
				Entry<Integer, Object> ent = cont.next();			
				if(ent.getKey().equals(nextCont))
				{
					if(ent.getValue() instanceof Paragraph)
					{
						Paragraph pOld = (Paragraph) ent.getValue();
						Paragraph p = finalDoc.addParagraph(pOld.getTextContent());
						p.setFont(pOld.getFont());
						p.setHorizontalAlignment(pOld.getHorizontalAlignment());
						
						for(Field f : OdfParagraphProperties.class.getDeclaredFields())
						{
							OdfStyleProperty property = (OdfStyleProperty) f.get(new OdfParagraphProperties(){});
						} 				
					}
					else if(ent.getValue() instanceof List)
					{
						List l = finalDoc.addList();
						for(ListItem i : ((List)ent.getValue()).getItems())
						{
							l.addItem(i.getTextContent());
						}
					}
					content.remove(ent.getKey());
				}
			}
		}
		finalDoc.save(new File(file.getAbsolutePath().replace(".odt", "1.odt")));
	} */
	
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

					//schriftart, schriftgröße, schriftfarbe, unterstrichen, fett, kursiv, ausrichtung, zeilenabstand	
					p.setFont(new Font(style.style, style.getFontStyle(), (double) style.size, new Color(style.color), style.underlined ? TextLinePosition.UNDER : TextLinePosition.REGULAR));
					p.setTextContent(p.getTextContent().replace(s, ""));
					p.setHorizontalAlignment(style.getHorizontalAlignmentType());			
					for(Field f : OdfParagraphProperties.class.getDeclaredFields())
					{
						OdfStyleProperty property = (OdfStyleProperty) f.get(new OdfParagraphProperties(){});
					} 			
					p.getOdfElement().setProperty(OdfParagraphProperties.LineSpacing, (style.lineDistance - 1F) * 14F + "pt");			
			
					changed = true;
					break;
				}
			}
			if(!changed)
			{
				p.setFont(pOld.getFont());
				p.setHorizontalAlignment(pOld.getHorizontalAlignment());
				for(Field f : OdfParagraphProperties.class.getDeclaredFields())
				{
					OdfStyleProperty property = (OdfStyleProperty) f.get(new OdfParagraphProperties(){});
				} 
			}
		}
		
		Iterator<List> it1 = document.getListIterator();
		while (it1.hasNext())
		{
			List l = finalDoc.addList();
			List lOld = it1.next();
			
			for(ListItem i : lOld.getItems())
			{
				l.addItem(i.getTextContent());
			}
		} 		

		finalDoc.removeParagraph(finalDoc.getParagraphByIndex(0, false));
		finalDoc.save(new File(file.getAbsolutePath().replace(".odt", "1.odt")));
	}
}
