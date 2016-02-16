package source.style;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class StyleParser
{
	//hab dir ma deine HashMap abgerippt, is jz in Main, schreib da rein
	File src;
	public StyleParser(File f)throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String parsingchar = "  ";
		for(String s = reader.readLine(); s != null; reader.readLine()){
			if(s.indexOf(parsingchar) == -1){
				
				
				reader.close();
			}
		}
	}
}
