import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import javax.swing.*;
import java.awt.Font;

public class FlipCardMode extends JFrame {

	private JPanel contentPane;
	private JTextArea frontArea = new JTextArea();
	private JTextArea backArea = new JTextArea();
	private AbstractDocument abDoc;
	private BufferedWriter bw;
	private BufferedReader br;
	private String fileName;
	private String[] frontList;
	private String[] backList;
	private int cards;
	private int currentCard;
	private File file;

	/**
	 * Create a new flipcard set
	 */
	public void newCard() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlipCardMode frame = new FlipCardMode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		cards = 1;
		currentCard = 1;
	}

	/**
	 * Create the frame, menu, buttons and text field
	 * Implement listeners to call for button and menu item methods
	 */
	public FlipCardMode() {
		setResizable(false);
		setTitle("Dynamic Notes");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 * Text Panes
		 */
		frontArea.setFont(new Font("Arial", Font.PLAIN, 16));
		frontArea.setBackground(Color.WHITE);
		frontArea.setBounds(20, 20, 555, 260);
		frontArea.setMargin(new Insets(10, 10, 10, 10));
		((AbstractDocument) frontArea.getDocument()).setDocumentFilter(new SizeFilter(500));
		contentPane.add(frontArea);
		
		backArea.setFont(new Font("Arial", Font.PLAIN, 16));
		backArea.setBackground(Color.WHITE);
		backArea.setBounds(20, 20, 555, 260);
		backArea.setMargin(new Insets(10, 10, 10, 10));
		((AbstractDocument) backArea.getDocument()).setDocumentFilter(new SizeFilter(500));
		contentPane.add(backArea);
		backArea.setVisible(false);
		
		/*
		 * Buttons
		 */
		JButton btnPrevious = new JButton("Prev");
		btnPrevious.setBackground(Color.WHITE);
		btnPrevious.setFont(new Font("Arial", Font.BOLD, 14));
		btnPrevious.setBounds(20, 310, 80, 30);
		contentPane.add(btnPrevious);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBackground(Color.WHITE);
		btnNext.setFont(new Font("Arial", Font.BOLD, 14));
		btnNext.setBounds(495, 310, 80, 30);
		contentPane.add(btnNext);
		
		JButton btnFlip = new JButton("Flip");
		btnFlip.setBackground(Color.WHITE);
		btnFlip.setFont(new Font("Arial", Font.BOLD, 14));
		btnFlip.setBounds(254, 310, 80, 30);
		contentPane.add(btnFlip);
		
		/*
		 * Menu and menu item interfaces
		 * File tab holds options to Save, Save As, Open, and Exit
		 * New Card: creates a new card and increments the necessary values
		 * Delete Card: removes a card and changes the needed values
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
		
		JMenuItem menuNewCard = new JMenuItem("New Card");
		menuNewCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateNewCard();
			}
		});
		mnNewMenu.add(menuNewCard);
		
		JMenuItem menuDeleteCard = new JMenuItem("Delete Card");
		menuDeleteCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteCard();
			}
		});
		mnNewMenu.add(menuDeleteCard);
		
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
		mnNewMenu.add(menuOpen);
		
		JMenuItem menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(menuExit);
	}
	
	/*
	 * Method to create a new card
	 */
	public void CreateNewCard() {
		frontList[currentCard] = frontArea.getText();
		backList[currentCard] = backArea.getText();
		frontArea.setText("");
		backArea.setText("");
		cards ++;
		currentCard ++;
	}
	
	/*
	 * Method to delete the current card
	 * Checks the current position and will move to the previous card unless the users is on the
	 * first card, in which case it moves forward
	 */
	public void DeleteCard() {
		//Removes the currently selected card. Doesn't work if there is only 1 card
		if(cards == 1)
			return;
		
		//If deleting the first card
		if(currentCard == 1) {
			frontArea.setText(frontList[1]);
			backArea.setText(backList[1]);
			for(int i = 0; i < frontList.length; i++) {
				frontList[i] = frontList[i+1];
				backList[i] = backList[i+1];
			}
			frontList[frontList.length] = null;
			backList[backList.length] = null;
		}
		//If deleting the last card
		else if(currentCard == cards) {
			frontArea.setText(frontList[cards-1]);
			backArea.setText(backList[cards-1]);
			frontList[cards] = null;
			backList[cards] = null;
		}
		//Else middle deletion
		else {
			frontArea.setText(frontList[currentCard-1]);
			backArea.setText(backList[currentCard-1]);
			for(int i = currentCard-1; i < frontList.length; i++) {
				frontList[i] = frontList[i+1];
				backList[i] = backList[i+1];
			}
			frontList[frontList.length] = null;
			backList[backList.length] = null;
		}
		cards --;
		currentCard --;
	}
	
	/*
	 * Method to save the flip cards as a file
	 * If there is no existing file, redirect to the SaveAs function
	 */
	public void Save() {
		if( fileName == null)
			SaveAs();
		else {
			file = new File(fileName);
		}
		
		try {
	    	bw = new BufferedWriter(new FileWriter(file));
	    	frontArea.write(bw);
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	    finally {
	         if (bw != null) {
	            try {
	               bw.close();
	            } 
	            catch (IOException e) {
	            }}}}
	
	/*
	 * Save As method to save the current file at a specific location
	 * Allows the user to specify a name and location for the file when saving
	 */
	public void SaveAs() {
		final JFileChooser SaveAs = new JFileChooser();
		SaveAs.setApproveButtonText("Save");
	    int actionDialog = SaveAs.showOpenDialog(this);
	    if (actionDialog != JFileChooser.APPROVE_OPTION) {
	       return;
	    }
	    
	    //Check if file is being saved over
	    if(fileName == null) {
	    	file = new File(SaveAs.getSelectedFile() + ".txt");
	    }
	    else if(fileName.substring(fileName.length() - 4).equals(".txt")) {
	    	file = new File(SaveAs.getSelectedFile() + "");
	    }
	    else {
	    	file = new File(SaveAs.getSelectedFile() + ".txt");
	    }
	    
	    fileName = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("\\")+1);
	    bw = null;
	    
	    try {
	    	bw = new BufferedWriter(new FileWriter(file));
	    	frontArea.write(bw);
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	    finally {
	         if (bw != null) {
	            try {
	               bw.close();
	            } 
	            catch (IOException e) {
	            }}}}
}