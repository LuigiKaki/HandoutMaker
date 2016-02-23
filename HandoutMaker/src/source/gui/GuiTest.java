package source.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import source.style.Style;
import source.style.StyleParser;

public class GuiTest
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
					GuiTest window = new GuiTest();
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
	public GuiTest()
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

		JButton btnImportStyleFile = new JButton("Import style file");
		btnImportStyleFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Text", ".txt"));
				chooser.setDialogTitle("Please select a style file");
				chooser.setFont(new Font("Tahoma", Font.PLAIN, 11));
				
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					styleFile = chooser.getSelectedFile();
					
				}			
			}
		});
		sl_mainPanel.putConstraint(SpringLayout.NORTH, btnImportStyleFile, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, btnImportStyleFile, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, btnImportStyleFile, 53, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, btnImportStyleFile, 209, SpringLayout.WEST, mainPanel);
		mainPanel.add(btnImportStyleFile);

		JButton btnApplyStyleTo = new JButton("Apply selected style to...");
		btnApplyStyleTo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("Text", ".txt"));
				chooser.setDialogTitle("Please select the source file");
				chooser.setFont(new Font("Tahoma", Font.PLAIN, 11));
				
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					styleFile = chooser.getSelectedFile();
					try
					{
						StyleParser.loadStyles(StyleParser.formatFile(styleFile));
					}
					catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}			
			}
		});
		sl_mainPanel.putConstraint(SpringLayout.NORTH, btnApplyStyleTo, 0, SpringLayout.NORTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.WEST, btnApplyStyleTo, 6, SpringLayout.EAST, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, btnApplyStyleTo, 0, SpringLayout.SOUTH, btnImportStyleFile);
		sl_mainPanel.putConstraint(SpringLayout.EAST, btnApplyStyleTo, 0, SpringLayout.EAST, mainPanel);
		mainPanel.add(btnApplyStyleTo);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Style File", null, panel, null);
		panel.setLayout(new GridLayout(2, 2, 5, 5));

		JButton btnAddStyle = new JButton("Add style");
		panel.add(btnAddStyle);

		JButton btnRemoveStyle = new JButton("Remove style");
		panel.add(btnRemoveStyle);

		JButton btnEditStyle = new JButton("Edit style");
		panel.add(btnEditStyle);

		JButton btnSaveChanges = new JButton("Save changes");
		panel.add(btnSaveChanges);
	}
}
