package source.style;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

public class Style
{
	// f�g Variablen hinzu, wenn dir welche einfallen, hab bestimmt net alle, hau die dann auch unten in den constructor
	public String identifier, style;
	public short format; //format: 0 = rechts, 1 = mitte, 2 = links, 3 = block
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
	}

	public Style(String identifier, String style, short format, boolean cursive, boolean underlined, boolean bold, float lineDistance, float size, Color color)
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
	}
	
	@Override
	public String toString()
	{
		return '"' + identifier + '"' + ";style=" + style + ";format=" + String.valueOf(format) + ";cursive=" + String.valueOf(cursive) + ";underlined=" + String.valueOf(underlined) + ";bold=" 
	           + String.valueOf(bold) + ";lineDistance=" + String.valueOf(lineDistance) +  ";size=" + String.valueOf(size) + ";color=" + String.valueOf(color).replaceAll("java.awt.Color", "");
	}
	
	public String getColorAsHex()
	{
		return "#".concat(Integer.toHexString(color.getRed()) + Integer.toHexString(color.getGreen()) + Integer.toHexString(color.getBlue()));
	}
}