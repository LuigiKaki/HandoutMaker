package source.style;

import java.awt.Color;
import java.lang.reflect.Field;

import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;

public class Style
{
	// f�g Variablen hinzu, wenn dir welche einfallen, hab bestimmt net alle, hau die dann auch unten in den constructor
	public String identifier, style;
	public short format, listStyle; //format: 0 = rechts, 1 = mitte, 2 = links, 3 = block
	public boolean cursive, underlined, bold;
	public float lineDistance, size;
	public Color color; // Kein Plan wie dat mit Farben abl�uft, f�rs erste mal die Java-Version benutzt

	// F�G DIE SCHEISSE AUCH IN DEN CONSTRUCOTR EIN DU UNTERMENSCH ES STEHT DOCH EXTRA DA!!!!!!!!!!!!!!!!!!!!!!!!!

	public Style()
	{
		identifier = "$text";	
		style = "Times New Roman";
		format = 0;
		cursive = false;
		underlined = false;
		bold = false;
		lineDistance = 1.0F;
		size = 12F; 
		color = Color.BLACK;
		listStyle = 0;
	}

	public Style(String identifier, String style, short format, boolean cursive, boolean underlined, boolean bold, float lineDistance, float size, Color color, short listStyle)
	{
		this.identifier = identifier;
		this.style = style;
		this.format = format;
		this.cursive = cursive;
		this.underlined = underlined;
		this.bold = bold;
		this.lineDistance = lineDistance;
		this.size = size;
		this.color = color;
		this.listStyle = listStyle;
	}
	
	public FontStyle getFontStyle()
	{
		return bold ? (cursive ? FontStyle.BOLDITALIC : FontStyle.BOLD) : (cursive ? FontStyle.ITALIC : FontStyle.REGULAR);
	}
	
	public HorizontalAlignmentType getHorizontalAlignmentType()
	{
		switch(format)
		{
			case 0:
				return HorizontalAlignmentType.LEFT;
			case 1:
				return HorizontalAlignmentType.CENTER;
			case 2:
				return HorizontalAlignmentType.RIGHT;
			case 3:
				return HorizontalAlignmentType.FILLED;
			default:
				return HorizontalAlignmentType.DEFAULT;
		}
	}
	/*
	@Override
	public String toString()
	{
		return '"' + identifier + '"' + ";style=" + style + ";format=" + String.valueOf(format) + ";cursive=" + String.valueOf(cursive) + ";underlined=" + String.valueOf(underlined) + ";bold="  + String.valueOf(bold) + ";lineDistance=" + String.valueOf(lineDistance) +  ";size=" + String.valueOf(size) + ";color=" + String.valueOf(color).replaceAll("java.awt.Color", "") + ";liststyle=" + String.valueOf(listStyle); 
	} */
	
	public String toString()
	{
		String s = "";	
		for(Field f : this.getClass().getDeclaredFields())
		{	
			try
			{
				s = s.concat(f.getName() + "=" + String.valueOf(f.get(this)) + ";");
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}	
		s = s.replace("java.awt.Color", "");
		return s;
	}
}