
import java.awt.EventQueue;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	
	//Load the menu when called
	public void LoadMenu() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Create and manage the menu
	 * Frame contains label and buttons
	 * Listeners on buttons carry out a function
	 */
	public MainMenu() {
		
		//Frame
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		//Title Label
		JLabel lblDynamicNotes = new JLabel("Dynamic Notes");
		lblDynamicNotes.setFont(new Font("Arial", Font.BOLD, 20));
		GridBagConstraints gbc_lblDynamicNotes = new GridBagConstraints();
		gbc_lblDynamicNotes.gridx = 4;
		gbc_lblDynamicNotes.gridy = 1;
		contentPane.add(lblDynamicNotes, gbc_lblDynamicNotes);
		
		//Button to create a new note
		JButton btnNewNote = new JButton("New Note");
		GridBagConstraints gbc_btnNewNote = new GridBagConstraints();
		gbc_btnNewNote.gridx = 3;
		gbc_btnNewNote.gridy = 4;
		contentPane.add(btnNewNote, gbc_btnNewNote);
		
		//Button to create a new flipcard
		JButton btnNewFlip = new JButton("New Flipcard");
		GridBagConstraints gbc_btnNewFlip = new GridBagConstraints();
		gbc_btnNewFlip.gridx = 5;
		gbc_btnNewFlip.gridy = 4;
		contentPane.add(btnNewFlip, gbc_btnNewFlip);
		
		//Button to Open a note file
		JButton btnOpenNote = new JButton("Open Note");
		GridBagConstraints gbc_btnOpenNote = new GridBagConstraints();
		gbc_btnOpenNote.gridx = 3;
		gbc_btnOpenNote.gridy = 6;
		contentPane.add(btnOpenNote, gbc_btnOpenNote);
		
		//Button to open a flipcard file
		JButton btnOpenFlip = new JButton("Open Flipcard");
		GridBagConstraints gbc_btnOpenFlip = new GridBagConstraints();
		gbc_btnOpenFlip.gridx = 5;
		gbc_btnOpenFlip.gridy = 6;
		contentPane.add(btnOpenFlip, gbc_btnOpenFlip);
		
		
		//Button Listeners
		//New will create a new window for the user to work in
		//Open will open file explorer so that the use can search for the file they want to open
		
		btnNewNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnNewFlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnOpenNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		btnOpenFlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}
}
