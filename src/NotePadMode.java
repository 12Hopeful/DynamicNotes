import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class NotePadMode extends JFrame {

	private JPanel contentPane;
	JTextPane noteZone = new JTextPane();
	private BufferedWriter bw; //Writer to write to a file
	private String fileName; //The currently accessed file. Stored for use with Save function
	private File file; //File that is being read from or written to

	/**
	 * Create a new note
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
	 * Create the frame, menu, buttons and text field
	 * Implement listeners to call for button and menu item methods
	 */
	public NotePadMode() {
		setResizable(false);
		setTitle("Dynamic Notes");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		noteZone.setBounds(0, 0, 594, 533);
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
		
		JMenuItem menuSave = new JMenuItem("Save");
		menuSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Save();
			}
		});
		mnNewMenu.add(menuSave);
		
		JMenuItem menuSaveAs = new JMenuItem("Save As");
		menuSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveAs();
			}
		});
		mnNewMenu.add(menuSaveAs);
		
		JMenuItem menuOpen = new JMenuItem("Open");
		menuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Open();
			}
		});
		mnNewMenu.add(menuOpen);
		
		JMenuItem menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(menuExit);
		
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
		
		
		JButton btnI = new JButton(new StyledEditorKit.ItalicAction());
		btnI.setText("I");
		btnI.setFont(new Font("Serif", Font.ITALIC, 12));
		menuBar.add(btnI);
	}
	
	//Method to save to the currently accessed file
	public void Save() {
		if( fileName == null) {
			SaveAs();
			return;
		}
		else {
			file = new File(fileName);
		}
		
		//Try saving
	    try {
	    	bw = new BufferedWriter(new FileWriter(file, false));
	    	bw.write(noteZone.getText());
	        bw.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}
	
	//Method to save to a specific directory. Checks that the file is a .txt file
	public void SaveAs() {
		final JFileChooser SaveAs = new JFileChooser();
		SaveAs.setApproveButtonText("Save");
	    int actionDialog = SaveAs.showOpenDialog(this);
	    if (actionDialog != JFileChooser.APPROVE_OPTION) {
	       return;
	    }
	    
	    file = new File(SaveAs.getSelectedFile() + ".txt");
	    
	    fileName = file.getAbsolutePath();//Store the filename for Save
	    
	    //Save to a file
	    try {
	        bw = new BufferedWriter(new FileWriter(file, false));
	        bw.write(noteZone.getText());
	        bw.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}
	
	
	//Method to open a text file
	public void Open() {
		final JFileChooser Open = new JFileChooser();
		Open.setApproveButtonText("Open");
	    int actionDialog = Open.showOpenDialog(this);
	    if (actionDialog != JFileChooser.APPROVE_OPTION) {
	       return;
	    }
	    
	    file = new File(Open.getSelectedFile() + "");
	    
	    fileName = file.getAbsolutePath();//Store the filename for Save
	    
	    //Scan by lines and put them into the correct spots
	    try {
	    	Scanner s = new Scanner(Open.getSelectedFile());
	    	String str = "";
	    	
	    	// keep reading till readLine returns null
	    	while (s.hasNextLine()) {
	    	    str = str + s.nextLine() + "\n";
	    	}
	    	noteZone.setText(str);
	    	s.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}
}
