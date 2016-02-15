package java.style;

import java.awt.Color;

public class Style

{
	//f�g Variablen hinzu, wenn dir welche einfallen, hab bestimmt net alle, hau die dann auch unten in den constructor
	public short size, format; //format = Blocksatz, zentriert etc., mir fiel kein besserer Name ein, datentyp muss vllt gewechselt werden, f�rs erste: 0=rechts, 1 = mitte, 2 = links, 3 = block
	public String style, identifier;
	public boolean cursive, underlined, bold;
	public float lineDistance;
	public Color color; //Kein Plan wie dat mit Farben abl�uft, f�rs erste mal die Java-Version benutzt 
	
	public Style() //kb 5000 constructors zu erstellen wo man alles einzeln setzen kann, eher einfach setzen und einen MAIN constructor
	{
		size = 12;	//ab hier so standardeinstellungen
		style = "Times New Roman";
		cursive = false;	
		underlined = false;
		format = 0;
		lineDistance = 1.0F;
		color = Color.BLACK;
	}
	
	public Style(String identifier, short size, short format, String style, boolean cursive, boolean underlined, float lineDistance, Color color)
	{
		this.identifier = identifier;
		this.size = size;
		this.style = style;
		this.cursive = cursive;
		this.underlined = underlined;
		this.format = format;
		this.lineDistance = lineDistance;
		this.color = color;
	}
}