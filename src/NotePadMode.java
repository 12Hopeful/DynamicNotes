import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledEditorKit;

import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotePadMode extends JFrame {

	private JPanel contentPane;

	/**
	 * Load the interface when called
	 */
	public void newNote() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotePadMode frame = new NotePadMode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame, buttons and text field
	 */
	public NotePadMode() {
		setResizable(false);
		setTitle("Dynamic Notes");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane noteZone = new JTextPane();
		noteZone.setBounds(0, 0, 394, 344);
		noteZone.setMargin(new Insets(5, 5, 5, 5));
		contentPane.add(noteZone);
		
		/*
		 * Menu and menu item interfaces
		 * File tab holds options to Save, Save As, Open, and Exit
		 * Save: saves the currently open text to the open .txt file 
		 * 		If a .txt file is not open, the user is prompted to create the file
		 * Save: as allows the user to save the current text to a desired location with a desired name as a .txt
		 * Open: opens a .txt file and loads the stored text to the interface
		 * Exit: closes the program
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Save");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnNewMenu.add(mntmSaveAs);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnNewMenu.add(mntmOpen);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnNewMenu.add(mntmExit);
		
		
		/*
		 * Buttons that control font display parameters
		 * B: bolds the selected text, or enables bold for text that is typed
		 * I: italicizes the selected text, or enables italics for text that is typed
		 * Bullets: creates bullet points starting from the currently selected line(s)
		 */
		JButton btnB = new JButton(new StyledEditorKit.BoldAction());
		btnB.setText("B");
		btnB.setFont(new Font("Serif", Font.BOLD, 12));
		menuBar.add(btnB);
		
		JButton btnI = new JButton("I");
		btnI.setFont(new Font("Serif", Font.ITALIC, 12));
		menuBar.add(btnI);
		
		JButton btnBullets = new JButton("Bullets");
		btnBullets.setFont(new Font("Serif", Font.PLAIN, 12));
		menuBar.add(btnBullets);
		
		/*
		 * Creates a drop menu used to resize text
		 * Should call a text resize method
		 */
		SpinnerModel sm = new SpinnerNumberModel(10, 10, 20, 1);
		JSpinner fontSize = new JSpinner(sm);
		Dimension d = new Dimension(45, 50);
		fontSize.setMaximumSize(d);
		menuBar.add(fontSize);
	}
}
