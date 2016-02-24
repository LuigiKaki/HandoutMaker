package source.style;

import java.awt.Color;
import java.awt.Font;

public class Style
{
	// f�g Variablen hinzu, wenn dir welche einfallen, hab bestimmt net alle, hau die dann auch unten in den constructor
	public short size, format; //format: 0 = rechts, 1 = mitte, 2 = links, 3 = block
	public String style, identifier;
	public boolean cursive, underlined, bold;
	public float lineDistance;
	public Color color; // Kein Plan wie dat mit Farben abl�uft, f�rs erste mal die Java-Version benutzt

	// F�G DIE SCHEISSE AUCH IN DEN CONSTRUCOTR EIN DU UNTERMENSCH ES STEHT DOCH EXTRA DA!!!!!!!!!!!!!!!!!!!!!!!!!

	public Style()
	{
		size = 12; // ab hier so standardeinstellungen
		style = "Times New Roman";
		cursive = false;
		underlined = false;
		format = 0;
		lineDistance = 1.0F;
		color = Color.BLACK;
		bold = false;
	}

	public Style(String identifier, short size, short format, String style, boolean cursive, boolean underlined, float lineDistance, Color color, boolean bold)
	{
		this.identifier = identifier;
		this.size = size;
		this.style = style;
		this.cursive = cursive;
		this.underlined = underlined;
		this.format = format;
		this.lineDistance = lineDistance;
		this.color = color;
		this.bold = bold;
	}
	
	public Font getFont()
	{
		return Font.decode(style + "-" + (bold ? cursive ? "bolditalic" : "bold" : cursive ? "italic" : "plain") + "-" + Integer.toString(size));
	}
}