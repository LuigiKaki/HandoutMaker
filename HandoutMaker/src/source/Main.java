package source;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import source.style.IOHandler;
import source.style.Style;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Main
{
	private File styleFile, targetFile;
	public static HashMap<String, Style> styles = new HashMap<String, Style>();
	private JFrame frmHandoutMaker;

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

		JButton btnImportStyleFile = new JButton("Style File laden");
		btnImportStyleFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
				chooser.setDialogTitle("Please select a style file");
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

					System.out.println("Loaded style file " + styleFile.getName());
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
				styles.clear();
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Text", ".txt"));
				chooser.setDialogTitle("Please select the source file");
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
		});
		sl_mainPanel.putConstraint(SpringLayout.NORTH, btnApplyStyleTo, 0, SpringLayout.NORTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.WEST, btnApplyStyleTo, 6, SpringLayout.EAST, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, btnApplyStyleTo, 0, SpringLayout.SOUTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.EAST, btnApplyStyleTo, 0, SpringLayout.EAST, mainPanel);
		mainPanel.add(btnApplyStyleTo);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Style File", null, tabbedPane_1, null);
		
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
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label_1, 89, SpringLayout.EAST, label);
		addStylePanel.add(label_1);
		
		JComboBox comboBox = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, comboBox, 5, SpringLayout.WEST, addStylePanel);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Times New Roman"}));
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, comboBox, 6, SpringLayout.SOUTH, label);
		comboBox.setToolTipText("");
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		addStylePanel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, comboBox_1, 139, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, comboBox, -22, SpringLayout.WEST, comboBox_1);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, comboBox_1, 6, SpringLayout.SOUTH, label_1);
		sl_addStylePanel.putConstraint(SpringLayout.EAST, comboBox_1, 0, SpringLayout.EAST, label_1);
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"11", "12", "13", "14", "15", "16", "17", "18"}));
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox_1.setEditable(true);
		addStylePanel.add(comboBox_1);
		
		JCheckBox checkBox = new JCheckBox("Unterstrichen");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, checkBox, 30, SpringLayout.SOUTH, comboBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, checkBox, 0, SpringLayout.WEST, label);
		addStylePanel.add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("Fett");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, checkBox_1, 0, SpringLayout.NORTH, checkBox);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, checkBox_1, 0, SpringLayout.WEST, label_1);
		addStylePanel.add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox("Kursiv");
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, checkBox_2, 0, SpringLayout.NORTH, checkBox);
		addStylePanel.add(checkBox_2);
		
		JComboBox comboBox_2 = new JComboBox();
		sl_addStylePanel.putConstraint(SpringLayout.WEST, checkBox_2, 0, SpringLayout.WEST, comboBox_2);
		sl_addStylePanel.putConstraint(SpringLayout.WEST, comboBox_2, 231, SpringLayout.WEST, addStylePanel);
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Linksb\u00FCndig", "Zentriert", "Rechtsb\u00FCndig", "Blocksatz"}));
		comboBox_2.setMaximumRowCount(4);
		addStylePanel.add(comboBox_2);
		
		JLabel label_2 = new JLabel("Ausrichtung");
		sl_addStylePanel.putConstraint(SpringLayout.WEST, label_2, 229, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, comboBox_2, 6, SpringLayout.SOUTH, label_2);
		sl_addStylePanel.putConstraint(SpringLayout.NORTH, label_2, 0, SpringLayout.NORTH, label);
		addStylePanel.add(label_2);
		
		JLabel lblHervorhebung = new JLabel("Hervorhebungen");
		sl_addStylePanel.putConstraint(SpringLayout.WEST, lblHervorhebung, 127, SpringLayout.WEST, addStylePanel);
		sl_addStylePanel.putConstraint(SpringLayout.SOUTH, lblHervorhebung, -6, SpringLayout.NORTH, checkBox_1);
		addStylePanel.add(lblHervorhebung);
		
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

	public static void showNoStyleFileLoadedDialogue()
	{
		System.err.println("No style file found");
		JOptionPane pane = new JOptionPane();
		pane.showMessageDialog(null, "No style file is loaded. Load one using the 'Import style file'-button in tab 'Main'!", "Error: No style file loaded", JOptionPane.ERROR_MESSAGE);
		pane.setVisible(true);
	}
}
