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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import source.style.IOHandler;
import source.style.Style;

public class Main
{
	private File styleFile, targetFile;
	public static HashMap<String, Style> styles = new HashMap<String, Style>();
	private JFrame frmHandoutMaker;
	private JTextField identifierField;
	private JTextField colorField;

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
				if(!styles.isEmpty())
				{
					styles.clear();
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
						IOHandler.loadStyles(styleFile);
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
				if (styles.isEmpty())
				{

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
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, addStylePanel);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addStylePanel.add(label);

		JLabel label_1 = new JLabel("Schriftgr\u00F6\u00DFe");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, label);
		addStylePanel.add(label_1);

		final JComboBox typeBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, typeBox, 5, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, typeBox, -307, SpringLayout.EAST, addStylePanel);
		typeBox.setModel(new DefaultComboBoxModel(new String[] { "Times New Roman" }));
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, typeBox, 6, SpringLayout.SOUTH, label);
		typeBox.setToolTipText("");
		typeBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addStylePanel.add(typeBox);

		final JComboBox sizeBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, sizeBox, 6, SpringLayout.EAST, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label_1, 0, SpringLayout.WEST, sizeBox);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, sizeBox, 0, SpringLayout.NORTH, typeBox);
		sizeBox.setModel(new DefaultComboBoxModel(new String[] { "11", "12", "13", "14", "15", "16", "17", "18" }));
		sizeBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sizeBox.setEditable(true);
		addStylePanel.add(sizeBox);

		final JCheckBox underlinedBox = new JCheckBox("Unterstrichen");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, underlinedBox, 30, SpringLayout.SOUTH, typeBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, underlinedBox, 0, SpringLayout.WEST, label);
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
		sl_addStylePanel.putConstraint(SpringLayout.WEST, formatBox, 198, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, sizeBox, -30, SpringLayout.WEST, formatBox);
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
		sl_addStylePanel.putConstraint(SpringLayout.EAST, linedistanceBox, -46, SpringLayout.EAST, addStylePanel);
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
				if(styleFile == null)
				{
					PopoutMessenger.showNoStyleFileDialogue();
				}
				else if(styles.containsKey(identifierField.getText()))
				{
					PopoutMessenger.showStyleIdentifierOccupiedDialogue(identifierField.getText());
				}
				else
				{
					String[] colorValues = colorField.getText().split(",");				
					styles.put(identifierField.getText(), new Style(identifierField.getText(), String.valueOf(typeBox.getSelectedItem()), Short.parseShort(String.valueOf(formatBox.getSelectedItem()).replace("Linksb\u00FCndig", "0").replace("Zentriert", "1").replace("Rechtsb\u00FCndig", "2").replace("Blocksatz", "3")),	cursiveBox.isSelected(), underlinedBox.isSelected(), boldBox.isSelected(), Float.parseFloat(String.valueOf(linedistanceBox.getSelectedItem()).replace(',', '.')), Float.parseFloat(String.valueOf(sizeBox.getSelectedItem()).replace(',', '.')), new Color(Integer.parseInt(colorValues[0]), Integer.parseInt(colorValues[1]), Integer.parseInt(colorValues[2]))));
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
		sl_addStylePanel.putConstraint(SpringLayout.WEST, identifierField, 0, SpringLayout.WEST, label);
		identifierField.setText("$");
		addStylePanel.add(identifierField);
		identifierField.setColumns(10);

		JLabel lblId = new JLabel("Identifier");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblId, 7, SpringLayout.SOUTH, underlinedBox);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, identifierField, 6, SpringLayout.SOUTH, lblId);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblId, 0, SpringLayout.WEST, label);
		addStylePanel.add(lblId);

		JLabel lblNewLabel = new JLabel("Farbe");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, lblId);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, lblNewLabel, 0, SpringLayout.EAST, sizeBox);
		addStylePanel.add(lblNewLabel);

		colorField = new JTextField();
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, colorField, 0, SpringLayout.NORTH, identifierField);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, colorField, 0, SpringLayout.WEST, lblNewLabel);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, colorField, -86, SpringLayout.WEST, btnHinzufgen);
		colorField.setText("255,255,255");
		addStylePanel.add(colorField);
		colorField.setColumns(10);

		JButton btnFarbauswahl = new JButton("Farbauswahl");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, btnFarbauswahl, 5, SpringLayout.SOUTH, colorField);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, btnFarbauswahl, 11, SpringLayout.WEST, label_1);
		addStylePanel.add(btnFarbauswahl);

		JPanel editStylePanel = new JPanel();
		tabbedPane_1.addTab("Styles bearbeiten", null, editStylePanel, null);

		JPanel removeStylePanel = new JPanel();
		tabbedPane_1.addTab("Style entfernen", null, removeStylePanel, null);

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
	}
}
