package source;

import javax.swing.JOptionPane;

public class PopoutMessenger
{
	//wenn du ne nachricht mit popout machen willst, erstell ne neue funktion, damit wir die alle zentral verwalten können
	
	private static void showDialogue(String cmdMessage, String popOutMessage, String popOutTitle)
	{
		System.out.println(cmdMessage);
		JOptionPane.showMessageDialog(null, popOutMessage, popOutTitle, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static void showErrorDialogue(String cmdMessage, String popOutMessage, String popOutTitle)
	{
		System.err.println(cmdMessage);
		JOptionPane.showMessageDialog(null, popOutMessage, popOutTitle, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showNoStyleFileDialogue()
	{
		showErrorDialogue("Kein Style-File geladen", "Es ist kein Style-File geladen. Lade eins mit dem Button 'Style-File laden' im Tab Main!", "Error: Kein Style-File geladen");
	}
	
	public static void showNoStylesDialogue()
	{
		showErrorDialogue("Keine Styles sind geladen", "Keine Styles geladen. Erstelle einen im Tab 'Style hinzufügen' im Tab 'Style-File'!", "Error: Keine Styles geladen");
	}
	
	public static void showSavedStyleFileDialogue(String filename)
	{
		showDialogue("Styles in " + filename + " gespeichert", "Das Style-File " + filename + " wurde erfolgreich gespeichert.", "Speichern erfolgreich");
	}
	
	public static void showStyleIdentifierOccupiedDialogue(String id)
	{
		showErrorDialogue("Der Style-Identifier " + id + " ist bereits belegt","Der Style mit dem Identifier " + id + " ist bereits belegt. Entferne oder editiere ihn im Tab 'Style-File'!", "Error: Style Identifer bereits belegt");
	}
	
	public static void showStyleFileLoadedDialogue(String filename)
	{
		showDialogue("Style-File " + filename + " wurde geladen", "Das Style-File " + filename + " wurde erfolgreich geladen!", "Laden erfolgreich");
	}
	
	public static void showStyleAddedDialogue(String id)
	{
		showDialogue("Style-Identifier " + id + " wurde ein Style zugewiesen", "Der Style mit dem Identifer " + id + " wurde erfolreich hinzugefügt.", "Hinzufügen erfolgreich");
	}
}
