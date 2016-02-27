package source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import source.style.IOHandler;
import source.style.Style;
import javax.swing.JFormattedTextField;
import java.awt.GridLayout;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main
{
	private File styleFile, targetFile;
	public static HashMap<String, Style> styles = new HashMap<String, Style>();
	private JFrame frmHandoutMaker;
	private JTextField identifierField;
	private JTextField colorField;
	protected JComboBox removeTargetBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
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

	/**
	 * Create the application.
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
		frmHandoutMaker.setTitle("Handout Maker");
		frmHandoutMaker.setBounds(100, 100, 450, 300);
		frmHandoutMaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHandoutMaker.getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frmHandoutMaker.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel mainPanel = new JPanel();
		tabbedPane.addTab("Main", null, mainPanel, null);
		SpringLayout sl_mainPanel = new SpringLayout();
		mainPanel.setLayout(sl_mainPanel);

		JButton btnImportStyleFile = new JButton("Style-File laden");
		btnImportStyleFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!styles.isEmpty())
				{
					styles.clear();
					removeTargetBox.removeAllItems();
					System.out.println("Styles zurückgesetzt");
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
						IOHandler.loadStyles(styleFile, removeTargetBox);
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		sl_mainPanel.putConstraint(SpringLayout.NORTH, btnImportStyleFile, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, btnImportStyleFile, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, btnImportStyleFile, 53, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, btnImportStyleFile, 209, SpringLayout.WEST, mainPanel);
		mainPanel.add(btnImportStyleFile);

		JButton btnApplyStyleTo = new JButton("Styles anwenden auf...");
		btnApplyStyleTo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(styleFile == null)
				{
					PopoutMessenger.showNoStyleFileDialogue();
				}
				else if (styles.isEmpty())
				{
					PopoutMessenger.showNoStylesDialogue();
				}
				else
				{
					JFileChooser chooser = new JFileChooser();
					chooser.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
					chooser.setDialogTitle("Wähle die Textdatei");
					chooser.setFont(new Font("Tahoma", Font.PLAIN, 11));

					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					{
						targetFile = chooser.getSelectedFile();
						Iterator<Style> it = styles.values().iterator();

						while (it.hasNext())
						{
							it.next().applyTo(targetFile);
						}
					}
				}
			}
		});
		sl_mainPanel.putConstraint(SpringLayout.NORTH, btnApplyStyleTo, 0, SpringLayout.NORTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.WEST, btnApplyStyleTo, 6, SpringLayout.EAST, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, btnApplyStyleTo, 0, SpringLayout.SOUTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.EAST, btnApplyStyleTo, 0, SpringLayout.EAST, mainPanel);
		mainPanel.add(btnApplyStyleTo);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Style-File", null, tabbedPane_1, null);

		JPanel addStylePanel = new JPanel();
		tabbedPane_1.addTab("Style hinzuf\u00FCgen", null, addStylePanel, null);
		SpringLayout sl_addStylePanel = new SpringLayout();
		addStylePanel.setLayout(sl_addStylePanel);

		JLabel label = new JLabel("Schriftart");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label, 9, SpringLayout.NORTH, addStylePanel);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addStylePanel.add(label);

		JLabel label_1 = new JLabel("Schriftgr\u00F6\u00DFe");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label_1, 83, SpringLayout.EAST, label);
		addStylePanel.add(label_1);

		final JComboBox typeBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, typeBox, 6, SpringLayout.SOUTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, typeBox, 5, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, typeBox, -307, SpringLayout.EAST, addStylePanel);
		typeBox.setModel(new DefaultComboBoxModel(new String[] { "Times New Roman", "Arial", "Calibri" }));
		typeBox.setToolTipText("");
		typeBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addStylePanel.add(typeBox);

		final JComboBox sizeBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, sizeBox, 6, SpringLayout.SOUTH, label_1);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, sizeBox, 0, SpringLayout.WEST, label_1);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, sizeBox, -10, SpringLayout.EAST, label_1);
		sizeBox.setModel(new DefaultComboBoxModel(new String[] { "11", "12", "13", "14", "15", "16", "17", "18" }));
		sizeBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sizeBox.setEditable(true);
		addStylePanel.add(sizeBox);

		final JCheckBox underlinedBox = new JCheckBox("Unterstrichen");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, underlinedBox, 30, SpringLayout.SOUTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, underlinedBox, 5, SpringLayout.WEST, addStylePanel);
		addStylePanel.add(underlinedBox);

		final JCheckBox boldBox = new JCheckBox("Fett");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, boldBox, 0, SpringLayout.NORTH, underlinedBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, boldBox, 89, SpringLayout.EAST, underlinedBox);
		addStylePanel.add(boldBox);

		final JCheckBox cursiveBox = new JCheckBox("Kursiv");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, cursiveBox, 0, SpringLayout.NORTH, underlinedBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, cursiveBox, 91, SpringLayout.EAST, boldBox);
		addStylePanel.add(cursiveBox);

		final JComboBox formatBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, formatBox, 215, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, formatBox, 0, SpringLayout.NORTH, typeBox);
		formatBox.setModel(new DefaultComboBoxModel(new String[] { "Linksb\u00FCndig", "Zentriert", "Rechtsb\u00FCndig", "Blocksatz" }));
		formatBox.setMaximumRowCount(4);
		addStylePanel.add(formatBox);

		JLabel label_2 = new JLabel("Ausrichtung");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label_2, 0, SpringLayout.NORTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label_2, 0, SpringLayout.WEST, formatBox);
		addStylePanel.add(label_2);

		JLabel lblHervorhebung = new JLabel("Hervorhebungen");
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblHervorhebung, 162, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, lblHervorhebung, -6, SpringLayout.NORTH, boldBox);
		addStylePanel.add(lblHervorhebung);

		final JComboBox linedistanceBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, linedistanceBox, 0, SpringLayout.NORTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, linedistanceBox, 0, SpringLayout.WEST, cursiveBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, linedistanceBox, -44, SpringLayout.EAST, addStylePanel);
		linedistanceBox.setEditable(true);
		linedistanceBox.setMaximumRowCount(4);
		linedistanceBox.setModel(new DefaultComboBoxModel(new String[] { "0", "1", "1.5", "2" }));
		linedistanceBox.setSelectedIndex(1);
		addStylePanel.add(linedistanceBox);

		JButton btnHinzufgen = new JButton("Hinzuf\u00FCgen");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, btnHinzufgen, 25, SpringLayout.SOUTH, cursiveBox);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, btnHinzufgen, -30, SpringLayout.EAST, addStylePanel);
		btnHinzufgen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (styleFile == null)
				{
					PopoutMessenger.showNoStyleFileDialogue();
				}
				else if (styles.containsKey(identifierField.getText()))
				{
					PopoutMessenger.showStyleIdentifierOccupiedDialogue(identifierField.getText());
				}
				else
				{
					String[] colorValues = colorField.getText().split(",");
					styles.put(identifierField.getText(), new Style(identifierField.getText(), String.valueOf(typeBox.getSelectedItem()), Short.parseShort(String.valueOf(formatBox.getSelectedItem()).replace("Linksb\u00FCndig", "0").replace("Zentriert", "1").replace("Rechtsb\u00FCndig", "2").replace("Blocksatz", "3")),	cursiveBox.isSelected(), underlinedBox.isSelected(), boldBox.isSelected(), Float.parseFloat(String.valueOf(linedistanceBox.getSelectedItem()).replace(',', '.')), Float.parseFloat(String.valueOf(sizeBox.getSelectedItem()).replace(',', '.')), new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2]))));
					removeTargetBox.addItem(identifierField.getText());
					PopoutMessenger.showStyleAddedDialogue(identifierField.getText());		
				}
			}
		});
		addStylePanel.add(btnHinzufgen);

		JLabel lblZeilenabstand = new JLabel("Zeilenabstand");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblZeilenabstand, 0, SpringLayout.NORTH, label);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblZeilenabstand, 0, SpringLayout.WEST, cursiveBox);
		addStylePanel.add(lblZeilenabstand);

		identifierField = new JTextField();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, identifierField, 5, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, identifierField, 72, SpringLayout.WEST, addStylePanel);
		identifierField.setText("$");
		addStylePanel.add(identifierField);
		identifierField.setColumns(10);

		JLabel lblId = new JLabel("Identifier");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblId, 7, SpringLayout.SOUTH, underlinedBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblId, 5, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, identifierField, 6, SpringLayout.SOUTH, lblId);
		addStylePanel.add(lblId);

		JLabel lblNewLabel = new JLabel("Schriftfarbe");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblNewLabel, 6, SpringLayout.SOUTH, boldBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblNewLabel, 91, SpringLayout.EAST, lblId);
		addStylePanel.add(lblNewLabel);

		colorField = new JTextField();
		colorField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
					String s = "";
					for(int i = 0; i < colorField.getText().length(); i++)
					{
						char c = colorField.getText().charAt(i);
						if(Character.isDigit(c) || c == ',' || c == '.')
						{
							s += c;
						}				
					}
					colorField.setText(s);
			}
		});
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, colorField, 1, SpringLayout.NORTH, btnHinzufgen);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, colorField, 0, SpringLayout.WEST, lblNewLabel);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, colorField, -22, SpringLayout.EAST, lblHervorhebung);
		colorField.setText("0,0,0");
		addStylePanel.add(colorField);
		colorField.setColumns(10);

		JButton btnFarbauswahl = new JButton("Farbauswahl");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, btnFarbauswahl, 6, SpringLayout.SOUTH, colorField);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, btnFarbauswahl, 0, SpringLayout.WEST, lblNewLabel);
		btnFarbauswahl.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JColorChooser colorChooser = new JColorChooser();
				Color color = colorChooser.showDialog(null, "Wähle eine Schriftfarbe", Color.BLACK);
				colorField.setText(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
			}
		});
		addStylePanel.add(btnFarbauswahl);

		JPanel editStylePanel = new JPanel();
		tabbedPane_1.addTab("Styles bearbeiten", null, editStylePanel, null);

		JPanel removeStylePanel = new JPanel();
		tabbedPane_1.addTab("Style entfernen", null, removeStylePanel, null);
		SpringLayout sl_removeStylePanel = new SpringLayout();
		removeStylePanel.setLayout(sl_removeStylePanel);
		
		JLabel lblWhleDenZu = new JLabel("W\u00E4hle den zu entfernenden Style");
		sl_removeStylePanel.putConstraint(SpringLayout.NORTH, lblWhleDenZu, 0, SpringLayout.NORTH, removeStylePanel);
		sl_removeStylePanel.putConstraint(SpringLayout.WEST, lblWhleDenZu, 105, SpringLayout.WEST, removeStylePanel);
		sl_removeStylePanel.putConstraint(SpringLayout.SOUTH, lblWhleDenZu, -180, SpringLayout.SOUTH, removeStylePanel);
		sl_removeStylePanel.putConstraint(SpringLayout.EAST, lblWhleDenZu, -128, SpringLayout.EAST, removeStylePanel);
		removeStylePanel.add(lblWhleDenZu);
		
		removeTargetBox = new JComboBox();
		sl_removeStylePanel.putConstraint(SpringLayout.NORTH, removeTargetBox, 1, SpringLayout.SOUTH, lblWhleDenZu);
		sl_removeStylePanel.putConstraint(SpringLayout.WEST, removeTargetBox, 105, SpringLayout.WEST, removeStylePanel);
		sl_removeStylePanel.putConstraint(SpringLayout.SOUTH, removeTargetBox, -154, SpringLayout.SOUTH, removeStylePanel);
		sl_removeStylePanel.putConstraint(SpringLayout.EAST, removeTargetBox, -90, SpringLayout.EAST, removeStylePanel);
		removeStylePanel.add(removeTargetBox);
		
		JButton removeStyleButton = new JButton("Ausgew\u00E4hlten Style entfernen");
		removeStyleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(styleFile == null)
				{
					PopoutMessenger.showNoStyleFileDialogue();
				}
				else if(styles.isEmpty())
				{
					PopoutMessenger.showNoStylesDialogue();
				}
				else
				{
					styles.remove(String.valueOf(removeTargetBox.getSelectedItem()));
					PopoutMessenger.showStyleRemovedDialogue(String.valueOf(removeTargetBox.getSelectedItem()));
					removeTargetBox.removeAllItems();
					Iterator<String> it = styles.keySet().iterator();
					while(it.hasNext())
					{
						removeTargetBox.addItem(it.next());
					}
				}
			}
		});
		sl_removeStylePanel.putConstraint(SpringLayout.NORTH, removeStyleButton, 6, SpringLayout.SOUTH, removeTargetBox);
		sl_removeStylePanel.putConstraint(SpringLayout.WEST, removeStyleButton, 0, SpringLayout.WEST, lblWhleDenZu);
		sl_removeStylePanel.putConstraint(SpringLayout.SOUTH, removeStyleButton, -113, SpringLayout.SOUTH, removeStylePanel);
		sl_removeStylePanel.putConstraint(SpringLayout.EAST, removeStyleButton, -90, SpringLayout.EAST, removeStylePanel);
		removeStylePanel.add(removeStyleButton);

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
		tabbedPane_1.addTab("Speichern", null, btnNewButton, null);
		frmHandoutMaker.setResizable(false);
	}
}
