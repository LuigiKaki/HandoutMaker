package source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.text.Paragraph;

import source.style.IOHandler;
import source.style.Style;

public class Main
{
	public static HashMap<String, Style> styles = new HashMap<String, Style>();
	public static boolean guiMode = true;
	public static boolean allowMessages = true;
	
	private File styleFile, targetFile;
	private JFrame frmHandoutMaker;
	private JTextField identifierField;
	private JTextField colorField;
	private JComboBox removeTargetBox;
	private JTextField colorField_1;
	private JComboBox editTargetBox;
	private JTextArea textEditing;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		if(args.length > 0)
		{
			for(int i = 0; i < args.length; i+=2)
			{
				switch(args[i])
				{
					case "/nogui":
						guiMode = false;
						break;
					case "/loud":
						allowMessages = true;
						break;
					case "/applyStyle":
						
						//TODO WIP
						File f = new File(args[i+1]);
						try
						{
							TempHelperClass.applyStyleToOdt(f);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						
						i++;
						break;
					default:
						System.err.println("Error: Unbekannte Variable: " + args[i]);
						System.exit(1);
						break;
				}
			}
		}
		
		if(guiMode)
		{
			EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					try
					{
						Main window = new Main();
						window.frmHandoutMaker.setVisible(true);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public Main()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmHandoutMaker = new JFrame();
		frmHandoutMaker.setResizable(false);
		frmHandoutMaker.setTitle("Handout Maker");
		frmHandoutMaker.setBounds(100, 100, 450, 300);
		frmHandoutMaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHandoutMaker.getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frmHandoutMaker.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel();
		tabbedPane.addTab("Main", null, mainPanel, null);

		JButton btnApplyStyleTo = new JButton("Textdatei laden");
		btnApplyStyleTo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Text", "txt", "odt"));
				chooser.setDialogTitle("Wähle die Textdatei");
				chooser.setFont(new Font("Tahoma", Font.PLAIN, 11));

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					targetFile = chooser.getSelectedFile();
					
					if(targetFile.getName().endsWith(".odt"))
					{
						try
						{
							TempHelperClass.applyStyleToOdt(targetFile);
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}
					}
				
					//TODO .txt handlind
					if(targetFile.getName().endsWith(".txt"))
					{
						//deine funktionen einbauen
					}
					
					
					
					
					//Text Einlesen für Edit im Programm (Textbearbeitung ala Word)
					//TODO vllt entfernen
					try
					{
						String content = "";
						Scanner s = new Scanner(targetFile);
						while (s.hasNext())
						{
							content += content.equals("") ? s.nextLine() : System.getProperty("line.separator") + s.nextLine();
						}
						textEditing.setText(content);
						s.close();
						PopoutMessager.showTextLoadedDialogue(targetFile.getName());
					}
					catch (FileNotFoundException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});

		JButton btnImportStyleFile = new JButton("Style-File laden");
		btnImportStyleFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!styles.isEmpty())
				{
					styles.clear();
					removeTargetBox.removeAllItems();
					editTargetBox.removeAllItems();
					PopoutMessager.messageCmdOnly("Styles zurückgesetzt", false);				
				}
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
				chooser.setDialogTitle("Wähle das Style-File");
				chooser.setFont(new Font("Tahoma", Font.PLAIN, 11));

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					styleFile = chooser.getSelectedFile();

					try
					{
						IOHandler.loadStyles(styleFile, removeTargetBox, editTargetBox);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		SpringLayout sl_mainPanel = new SpringLayout();
		sl_mainPanel.putConstraint(SpringLayout.NORTH, btnApplyStyleTo, 0, SpringLayout.NORTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, btnApplyStyleTo, 0, SpringLayout.SOUTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.EAST, btnApplyStyleTo, 0, SpringLayout.EAST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, btnImportStyleFile, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, btnImportStyleFile, 0, SpringLayout.WEST, mainPanel);
		mainPanel.setLayout(sl_mainPanel);
		mainPanel.add(btnImportStyleFile);
		mainPanel.add(btnApplyStyleTo);
		
		JSeparator separator = new JSeparator();
		separator.setVisible(false);
		sl_mainPanel.putConstraint(SpringLayout.WEST, btnApplyStyleTo, 0, SpringLayout.EAST, separator);
		sl_mainPanel.putConstraint(SpringLayout.EAST, btnImportStyleFile, 0, SpringLayout.WEST, separator);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, separator, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, separator, 0, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.HORIZONTAL_CENTER, separator, 0, SpringLayout.HORIZONTAL_CENTER, mainPanel);
		mainPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setVisible(false);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, btnImportStyleFile, -60, SpringLayout.NORTH, separator_1);
		sl_mainPanel.putConstraint(SpringLayout.WEST, separator_1, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, separator_1, 0, SpringLayout.EAST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.VERTICAL_CENTER, separator_1, 0, SpringLayout.VERTICAL_CENTER, mainPanel);
		mainPanel.add(separator_1);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Style-File", null, tabbedPane_1, null);

		JButton btnNewButton = new JButton("Speichern");
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					IOHandler.saveStyles(styleFile);
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		JPanel addStylePanel = new JPanel();
		tabbedPane_1.addTab("Style hinzuf\u00FCgen", null, addStylePanel, null);
		SpringLayout sl_addStylePanel = new SpringLayout();
		addStylePanel.setLayout(sl_addStylePanel);

		JLabel label = new JLabel("Schriftart");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addStylePanel.add(label);

		JLabel label_1 = new JLabel("Schriftgr\u00F6\u00DFe");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, label_1, 0, SpringLayout.SOUTH, label);
		addStylePanel.add(label_1);

		final JComboBox typeBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.EAST, typeBox, 120, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label, -15, SpringLayout.NORTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.NORTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, typeBox, 26, SpringLayout.NORTH, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, typeBox, 46, SpringLayout.NORTH, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, typeBox, 5, SpringLayout.WEST, addStylePanel);
		typeBox.setModel(new DefaultComboBoxModel(new String[] { "Times New Roman", "Arial", "Calibri" }));
		typeBox.setToolTipText("");
		typeBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addStylePanel.add(typeBox);

		final JComboBox sizeBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.EAST, sizeBox, 55, SpringLayout.EAST, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, label_1, 75, SpringLayout.WEST, sizeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, sizeBox, 10, SpringLayout.EAST, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label_1, 0, SpringLayout.WEST, sizeBox);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, sizeBox, 0, SpringLayout.NORTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, sizeBox, 0, SpringLayout.SOUTH, typeBox);
		sizeBox.setModel(new DefaultComboBoxModel(new String[] { "11", "12", "13", "14", "15", "16", "17", "18" }));
		sizeBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sizeBox.setEditable(true);
		addStylePanel.add(sizeBox);

		final JCheckBox underlinedBox = new JCheckBox("Unterstrichen");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, underlinedBox, 25, SpringLayout.SOUTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, underlinedBox, 0, SpringLayout.WEST, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, underlinedBox, 40, SpringLayout.SOUTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, underlinedBox, 110, SpringLayout.WEST, typeBox);
		addStylePanel.add(underlinedBox);

		final JCheckBox boldBox = new JCheckBox("Fett");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, boldBox, 6, SpringLayout.SOUTH, underlinedBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, boldBox, 0, SpringLayout.WEST, label);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, boldBox, 21, SpringLayout.SOUTH, underlinedBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, boldBox, -60, SpringLayout.EAST, underlinedBox);
		addStylePanel.add(boldBox);

		final JCheckBox cursiveBox = new JCheckBox("Kursiv");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, cursiveBox, 6, SpringLayout.SOUTH, boldBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, cursiveBox, 0, SpringLayout.WEST, label);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, cursiveBox, 42, SpringLayout.SOUTH, underlinedBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, cursiveBox, -359, SpringLayout.EAST, addStylePanel);
		addStylePanel.add(cursiveBox);

		final JComboBox formatBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, formatBox, 35, SpringLayout.EAST, sizeBox);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, formatBox, 0, SpringLayout.NORTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, formatBox, 0, SpringLayout.SOUTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, formatBox, 130, SpringLayout.EAST, sizeBox);
		formatBox.setModel(new DefaultComboBoxModel(new String[] { "Linksb\u00FCndig", "Zentriert", "Rechtsb\u00FCndig", "Blocksatz" }));
		formatBox.setMaximumRowCount(4);
		addStylePanel.add(formatBox);

		JLabel label_2 = new JLabel("Ausrichtung");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label_2, 0, SpringLayout.NORTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label_2, 0, SpringLayout.WEST, formatBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, label_2, 0, SpringLayout.SOUTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, label_2, 0, SpringLayout.EAST, formatBox);
		addStylePanel.add(label_2);

		JLabel lblHervorhebung = new JLabel("Hervorhebungen");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblHervorhebung, 5, SpringLayout.SOUTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblHervorhebung, 0, SpringLayout.WEST, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, lblHervorhebung, 25, SpringLayout.SOUTH, typeBox);
		addStylePanel.add(lblHervorhebung);
		
		JLabel lblListStyle = new JLabel("Listen-Style");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblListStyle, 5, SpringLayout.SOUTH, cursiveBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblListStyle, 0, SpringLayout.WEST, label);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, lblListStyle, 25, SpringLayout.SOUTH, cursiveBox);
		addStylePanel.add(lblListStyle);
		
		final JComboBox liststyleBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.EAST, liststyleBox, 110, SpringLayout.WEST, label);
		liststyleBox.setModel(new DefaultComboBoxModel(new String[] {"Numerisch: X)", "Numerisch: X.", "Stichpunkte"}));
		liststyleBox.setSelectedIndex(2);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, liststyleBox, 0, SpringLayout.SOUTH, lblListStyle);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, liststyleBox, 0, SpringLayout.WEST, label);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, liststyleBox, 20, SpringLayout.SOUTH, lblListStyle);
		addStylePanel.add(liststyleBox);
		
		final JComboBox linedistanceBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, linedistanceBox, 0, SpringLayout.NORTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, linedistanceBox, 10, SpringLayout.EAST, formatBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, linedistanceBox, 0, SpringLayout.SOUTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, linedistanceBox, 60, SpringLayout.EAST, formatBox);
		linedistanceBox.setEditable(true);
		linedistanceBox.setMaximumRowCount(4);
		linedistanceBox.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "1.5", "2" }));
		linedistanceBox.setSelectedIndex(1);
		addStylePanel.add(linedistanceBox);

		JButton btnHinzufgen = new JButton("Hinzuf\u00FCgen");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, btnHinzufgen, 0, SpringLayout.NORTH, liststyleBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, btnHinzufgen, 0, SpringLayout.WEST, linedistanceBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, btnHinzufgen, 0, SpringLayout.SOUTH, liststyleBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, btnHinzufgen, 100, SpringLayout.WEST, linedistanceBox);
		btnHinzufgen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (styleFile == null)
				{
					PopoutMessager.showNoStyleFileDialogue();
				}
				else if (styles.containsKey(identifierField.getText()))
				{
					PopoutMessager.showStyleIdentifierOccupiedDialogue(identifierField.getText());
				}
				else
				{
					try
					{
						String[] colorValues = colorField.getText().split(",");
						styles.put(identifierField.getText(),
								new Style(identifierField.getText(), String.valueOf(typeBox.getSelectedItem()), Short.parseShort(String.valueOf(formatBox.getSelectedItem()).replace("Linksb\u00FCndig", "0").replace("Zentriert", "1").replace("Rechtsb\u00FCndig", "2").replace("Blocksatz", "3")),
										cursiveBox.isSelected(), underlinedBox.isSelected(), boldBox.isSelected(), Float.parseFloat(String.valueOf(linedistanceBox.getSelectedItem()).replace(',', '.')), Float.parseFloat(String.valueOf(sizeBox.getSelectedItem()).replace(',', '.')),
										new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2])), 
										Short.parseShort(String.valueOf(liststyleBox.getSelectedItem()).replace("Numerisch: X)", "0").replace("Numerisch: X.", "1").replace("Stichpunkte", "2"))));
						removeTargetBox.addItem(identifierField.getText());
						editTargetBox.addItem(identifierField.getText());
						PopoutMessager.showStyleAddedDialogue(identifierField.getText());
					}
					catch (NumberFormatException e1)
					{
						PopoutMessager.showNumberFormatDialogue();
					}
				}
			}
		});
		addStylePanel.add(btnHinzufgen);
		
		JLabel lblId = new JLabel("Identifier");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblId, 0, SpringLayout.NORTH, lblListStyle);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblId, 0, SpringLayout.WEST, sizeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, lblId, 0, SpringLayout.SOUTH, lblListStyle);
		addStylePanel.add(lblId);
		
		identifierField = new JTextField();
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, identifierField, 0, SpringLayout.NORTH, liststyleBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, identifierField, 0, SpringLayout.WEST, sizeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, identifierField, 0, SpringLayout.SOUTH, liststyleBox);
		identifierField.setText("$");
		identifierField.setColumns(10);
		addStylePanel.add(identifierField);	
		
		JLabel lblZeilenabstand = new JLabel("Zeilenabstand");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblZeilenabstand, 0, SpringLayout.NORTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblZeilenabstand, 0, SpringLayout.WEST, linedistanceBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, lblZeilenabstand, 0, SpringLayout.SOUTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, lblZeilenabstand, 80, SpringLayout.WEST, linedistanceBox);
		addStylePanel.add(lblZeilenabstand);

		JLabel lblColor = new JLabel("Schriftfarbe");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblColor, 0, SpringLayout.NORTH, lblHervorhebung);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblColor, 0, SpringLayout.WEST, sizeBox);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, lblColor, 0, SpringLayout.SOUTH, lblHervorhebung);
		addStylePanel.add(lblColor);

		colorField = new JTextField();
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, colorField, 0, SpringLayout.SOUTH, lblColor);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, colorField, 0, SpringLayout.WEST, lblColor);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, colorField, 20, SpringLayout.SOUTH, lblColor);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, colorField, -75, SpringLayout.WEST, btnHinzufgen);
		colorField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_KP_LEFT || e.getKeyCode() == KeyEvent.VK_KP_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_KP_UP || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_KP_DOWN || e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)
				{
					return;
				}

				String s = "";
				for (int i = 0; i < colorField.getText().length(); i++)
				{
					char c = colorField.getText().charAt(i);
					if (Character.isDigit(c) || c == ',')
					{
						s += c;
					}
				}
				colorField.setText(s);
			}
		});
		colorField.setText("0,0,0");
		addStylePanel.add(colorField);
		colorField.setColumns(10);

		JButton btnFarbauswahl = new JButton("Farbauswahl");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, btnFarbauswahl, 0, SpringLayout.SOUTH, colorField);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, btnFarbauswahl, 0, SpringLayout.WEST, colorField);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, btnFarbauswahl, 20, SpringLayout.SOUTH, colorField);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, btnFarbauswahl, 0, SpringLayout.EAST, colorField);
		btnFarbauswahl.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JColorChooser colorChooser = new JColorChooser();
				Color color = colorChooser.showDialog(null, "Wähle eine Schriftfarbe", Color.BLACK);
				if (color != null)
				{
					colorField.setText(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
				}
			}
		});
		addStylePanel.add(btnFarbauswahl);
		


		JPanel editStylePanel = new JPanel();
		tabbedPane_1.addTab("Style bearbeiten", null, editStylePanel, null);
		editStylePanel.setLayout(null);

		JLabel label_3 = new JLabel("Schriftart");
		label_3.setBounds(10, 66, 45, 14);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		editStylePanel.add(label_3);

		JLabel label_4 = new JLabel("Schriftgr\u00F6\u00DFe");
		label_4.setBounds(134, 66, 80, 14);
		editStylePanel.add(label_4);

		final JComboBox typeBox_1 = new JComboBox();
		typeBox_1.setModel(new DefaultComboBoxModel(new String[] { "Times New Roman", "Arial", "Calibri" }));
		typeBox_1.setBounds(10, 91, 112, 20);
		typeBox_1.setToolTipText("");
		typeBox_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		editStylePanel.add(typeBox_1);

		final JComboBox sizeBox_1 = new JComboBox();
		sizeBox_1.setModel(new DefaultComboBoxModel(new String[] { "11", "12", "13", "14", "15", "16", "17", "18" }));
		sizeBox_1.setBounds(134, 91, 59, 20);
		sizeBox_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sizeBox_1.setEditable(true);
		editStylePanel.add(sizeBox_1);

		final JCheckBox underlinedBox_1 = new JCheckBox("Unterstrichen");
		underlinedBox_1.setBounds(134, 136, 116, 23);
		editStylePanel.add(underlinedBox_1);

		final JCheckBox boldBox_1 = new JCheckBox("Fett");
		boldBox_1.setBounds(134, 162, 59, 23);
		editStylePanel.add(boldBox_1);

		final JCheckBox cursiveBox_1 = new JCheckBox("Kursiv");
		cursiveBox_1.setBounds(195, 162, 69, 23);
		editStylePanel.add(cursiveBox_1);

		final JComboBox formatBox_1 = new JComboBox();
		formatBox_1.setModel(new DefaultComboBoxModel(new String[] { "Linksb\u00FCndig", "Zentriert", "Rechtsb\u00FCndig", "Blocksatz" }));
		formatBox_1.setBounds(208, 91, 106, 20);
		formatBox_1.setMaximumRowCount(4);
		editStylePanel.add(formatBox_1);

		JLabel label_5 = new JLabel("Ausrichtung");
		label_5.setBounds(208, 66, 69, 14);
		editStylePanel.add(label_5);

		JLabel label_6 = new JLabel("Hervorhebungen");
		label_6.setBounds(134, 122, 116, 14);
		editStylePanel.add(label_6);

		JLabel lblNewLabel = new JLabel("Listen-Style");
		lblNewLabel.setBounds(296, 122, 80, 14);
		editStylePanel.add(lblNewLabel);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Numerisch: X)", "Numerisch: X.", "Stichpunkte"}));
		comboBox_1.setSelectedIndex(2);
		comboBox_1.setBounds(296, 137, 110, 20);
		editStylePanel.add(comboBox_1);
		
		final JComboBox linedistanceBox_1 = new JComboBox();
		linedistanceBox_1.setBounds(328, 91, 60, 20);
		linedistanceBox_1.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "1.5", "2" }));
		linedistanceBox_1.setSelectedIndex(1);
		linedistanceBox_1.setMaximumRowCount(4);
		linedistanceBox_1.setEditable(true);
		editStylePanel.add(linedistanceBox_1);

		JButton btnnderungenbernehmen = new JButton("\u00C4ndern");
		btnnderungenbernehmen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (styleFile == null)
				{
					PopoutMessager.showNoStyleFileDialogue();
				}
				else if (editTargetBox.getSelectedItem() != null && styles.containsKey(String.valueOf(editTargetBox.getSelectedItem())))
				{
					try
					{
						String[] colorValues = colorField_1.getText().split(",");
						styles.put(String.valueOf(editTargetBox.getSelectedItem()),
								new Style(String.valueOf(editTargetBox.getSelectedItem()), String.valueOf(typeBox_1.getSelectedItem()),
										Short.parseShort(String.valueOf(formatBox_1.getSelectedItem()).replace("Linksb\u00FCndig", "0").replace("Zentriert", "1").replace("Rechtsb\u00FCndig", "2").replace("Blocksatz", "3")), cursiveBox_1.isSelected(), underlinedBox_1.isSelected(),
										boldBox_1.isSelected(), Float.parseFloat(String.valueOf(linedistanceBox_1.getSelectedItem()).replace(',', '.')), Float.parseFloat(String.valueOf(sizeBox_1.getSelectedItem()).replace(',', '.')),
										new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2])), 
										Short.parseShort(String.valueOf(comboBox_1.getSelectedItem()).replace("Numerisch: X)", "0").replace("Numerisch: X.", "1").replace("Stichpunkte", "2"))));
						PopoutMessager.showStyleEditedDialogue(String.valueOf(editTargetBox.getSelectedItem()));
					}
					catch (NumberFormatException e1)
					{
						PopoutMessager.showNumberFormatDialogue();
						e1.printStackTrace();
					}
				}
			}
		});
		btnnderungenbernehmen.setBounds(296, 162, 112, 23);
		editStylePanel.add(btnnderungenbernehmen);

		JLabel label_7 = new JLabel("Zeilenabstand");
		label_7.setBounds(328, 66, 80, 14);
		editStylePanel.add(label_7);

		JLabel label_9 = new JLabel("Schriftfarbe");
		label_9.setBounds(10, 122, 93, 14);
		editStylePanel.add(label_9);

		colorField_1 = new JTextField();
		colorField_1.setBounds(10, 139, 112, 20);
		colorField_1.setText("0,0,0");
		colorField_1.setColumns(10);
		editStylePanel.add(colorField_1);

		JButton button_1 = new JButton("Farbauswahl");
		button_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JColorChooser colorChooser = new JColorChooser();
				Color color = colorChooser.showDialog(null, "Wähle eine Schriftfarbe", Color.BLACK);
				colorField_1.setText(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
			}
		});
		button_1.setBounds(10, 164, 112, 23);
		editStylePanel.add(button_1);

		editTargetBox = new JComboBox();
		editTargetBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if (editTargetBox.getSelectedItem() != null && styles.containsKey(String.valueOf(editTargetBox.getSelectedItem())))
				{
					Style s = styles.get(String.valueOf(editTargetBox.getSelectedItem()));
					typeBox_1.setSelectedItem(s.style);
					sizeBox_1.setSelectedItem(s.size);
					formatBox_1.setSelectedIndex(s.format);
					linedistanceBox_1.setSelectedItem(s.lineDistance);
					colorField_1.setText(s.color.getRed() + "," + s.color.getGreen() + "," + s.color.getBlue());
					underlinedBox_1.setSelected(s.underlined);
					boldBox_1.setSelected(s.bold);
					cursiveBox_1.setSelected(s.cursive);
					comboBox_1.setSelectedIndex(s.listStyle);
				}
			}
		});
		editTargetBox.setBounds(177, 27, 91, 20);
		editStylePanel.add(editTargetBox);

		JLabel lblStyleauswahl = new JLabel("Styleauswahl");
		lblStyleauswahl.setBounds(177, 11, 81, 14);
		editStylePanel.add(lblStyleauswahl);
		


		JPanel removeStylePanel = new JPanel();
		tabbedPane_1.addTab("Style entfernen", null, removeStylePanel, null);
		removeStylePanel.setLayout(null);

		JLabel lblWhleDenZu = new JLabel("W\u00E4hle den zu entfernenden Style");
		lblWhleDenZu.setBounds(105, 0, 191, 25);
		removeStylePanel.add(lblWhleDenZu);

		removeTargetBox = new JComboBox();
		removeTargetBox.setBounds(105, 26, 229, 25);
		removeStylePanel.add(removeTargetBox);

		JButton removeStyleButton = new JButton("Ausgew\u00E4hlten Style entfernen");
		removeStyleButton.setBounds(105, 57, 229, 35);
		removeStyleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (styleFile == null)
				{
					PopoutMessager.showNoStyleFileDialogue();
				}
				else if (styles.isEmpty())
				{
					PopoutMessager.showNoStylesDialogue();
				}
				else
				{
					styles.remove(String.valueOf(removeTargetBox.getSelectedItem()));
					PopoutMessager.showStyleRemovedDialogue(String.valueOf(removeTargetBox.getSelectedItem()));
					removeTargetBox.removeAllItems();
					Iterator<String> it = styles.keySet().iterator();
					while (it.hasNext())
					{
						removeTargetBox.addItem(it.next());
					}
				}
			}
		});
		removeStylePanel.add(removeStyleButton);
		tabbedPane_1.addTab("Speichern", null, btnNewButton, null);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Textbearbeitung", null, panel, null);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);

		textEditing = new JTextArea();
		sl_panel.putConstraint(SpringLayout.SOUTH, textEditing, -10, SpringLayout.SOUTH, panel);
		textEditing.setText("Lade eine Textdatei");
		panel.add(textEditing);

		JToolBar toolBar = new JToolBar();
		sl_panel.putConstraint(SpringLayout.EAST, toolBar, -10, SpringLayout.EAST, panel);
		sl_panel.putConstraint(SpringLayout.WEST, textEditing, 0, SpringLayout.WEST, toolBar);
		sl_panel.putConstraint(SpringLayout.EAST, textEditing, 0, SpringLayout.EAST, toolBar);
		sl_panel.putConstraint(SpringLayout.NORTH, toolBar, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, toolBar, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, textEditing, 0, SpringLayout.SOUTH, toolBar);
		panel.add(toolBar);

		JButton btnNewButton_1 = new JButton("Style w\u00E4hlen");
		toolBar.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Liste erstellen");
		toolBar.add(btnNewButton_2);
	}
}
