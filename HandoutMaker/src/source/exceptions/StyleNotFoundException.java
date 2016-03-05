package source.exceptions;

public class StyleNotFoundException extends Exception
{
	private static final long serialVersionUID = 5001L;
	String name;
	public StyleNotFoundException(String styleName)
	{
		super();
		name = styleName;
	}
}
