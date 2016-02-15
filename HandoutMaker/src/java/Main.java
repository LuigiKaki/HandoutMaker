package java;

import java.awt.Color;
import java.io.File;

public class Main 
{
	private static short line = 0;
	
	public static void main(String[] args)
	{
		File styleFile = new File(args[0]);
		File targetFile = new File(args[1]);
		
		if(!styleFile.exists())
		{
			System.out.println("Style sheet could not be found. Make sure the path and spelling are correct!");
			System.exit(0); //kp ob man hier 0 oder was anderes raushauen soll, überlegs dir mal
		}
		if(!targetFile.exists())
		{
			System.out.println("Target could not be found. Make sure the path and spelling are correct!");
			System.exit(0); //kp ob man hier 0 oder was anderes raushauen soll, überlegs dir mal
		}
		
		Style style;
		do
		{
			style = loadNextStyle(styleFile, true);
			applyStyle(targetFile, style);
		}
		while(!style.equals(loadNextStyle(styleFile, false)));

		
		System.exit(0);
	}

	private static void applyStyle(File target, Style style) //WIP und so (WIP = work in progress für die Ungebildeten)
	{
		if(style.identifier.equals("(!§$%&/()=?)"))
		{
			System.out.println("Error loading style identifier; Check your style sheet in line: " + line);
		}
		
	}

	private static Style loadNextStyle(File styleFile, boolean continueToNext) //same here, dachte mir man geht so reihe für reihe das style sheet durch und applied das nacheinander, das boolean sagt aus ob man zur nächsten Zeile gehen soll wegen der while-do-schleife (s.o.)
	{
		if(continueToNext)
		{
			line++;
		}
		
		//style in der line laden und returnen
		
		return null;
	}
	
	private static class Style
	{
		//füg Variablen hinzu, wenn dir welche einfallen, hab bestimmt net alle, hau die dann auch unten in den constructor
		private short size, format; //format = Blocksatz, zentriert etc., mir fiel kein besserer Name ein, datentyp muss vllt gewechselt werden, fürs erste: 0=rechts, 1 = mitte, 2 = links, 3 = block
		private String type, identifier;
		private boolean cursive, underlined;
		private float lineDistance;
		private Color color; //Kein Plan wie dat mit Farben abläuft, fürs erste mal die Java-Version benutzt 
		
		private Style() //kb 5000 constructors zu erstellen wo man alles einzeln setzen kann, eher einfach setzen und einen MAIN constructor
		{
			identifier = "(!§$%&/()=?)"; //ersma random zeichen, damit dat nich gesucht wird, wenn die id fehlt
			size = 12;
			type = "Times New Roman";
			cursive = false;	
			underlined = false;
			format = 0;
			lineDistance = 1.0F;
			color = Color.BLACK;
		}
		
		private Style(String identifier, short size, short format, String type, boolean cursive, boolean underlined, float lineDistance, Color color)
		{
			this.identifier = identifier;
			this.size = size;
			this.type = type;
			this.cursive = cursive;
			this.underlined = underlined;
			this.format = format;
			this.lineDistance = lineDistance;
			this.color = color;
		}
	}
}
