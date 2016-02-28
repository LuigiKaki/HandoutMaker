package source.exceptions;

public class StyleNotFoundException extends Exception
{
	String name;
	public StyleNotFoundException(String styleName)
	{
		super();
		name = styleName;
	}
}
