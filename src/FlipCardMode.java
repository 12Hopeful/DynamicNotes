import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import javax.swing.*;

public class FlipCardMode extends JFrame {
	private JPanel contentPane; //Pane for the displayed graphics
	private JTextArea frontArea = new JTextArea(); //Area for the front side of the card
	private JTextArea backArea = new JTextArea(); //Area for the back side of the card
	private BufferedWriter bw; //Writer to write to a file
	private String fileName; //The currently accessed file. Stored for use with Save function
	private ArrayList<String> frontList = new ArrayList<String>(); //The ArrayList of all front of card text
	private ArrayList<String> backList = new ArrayList<String>(); //The ArrayList of all back of card text
	private int currentCard; //The card the user is currently viewing. Used for navigation
	private File file; //File that is being read from or written to

	/**
	 * Create a new flipcard set
	 */
	public void newCards() {
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
	}
	
	/**
	 * Create the frame, menu, buttons and text field
	 * Implement listeners to call for button and menu item methods
	 */
	public FlipCardMode() {
		setResizable(false);
		setTitle("Dynamic Notes");
		
		//Initialize the array lists
		if(frontList.size() == 0) {
			frontList.add("");
			backList.add("");
		}
		
		//Initialize the Content Frame
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
		frontArea.setLineWrap(true);
		frontArea.setWrapStyleWord(true);
		frontArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "none");
		contentPane.add(frontArea);
		
		backArea.setFont(new Font("Arial", Font.PLAIN, 16));
		backArea.setBackground(Color.WHITE);
		backArea.setBounds(20, 20, 555, 260);
		backArea.setMargin(new Insets(10, 10, 10, 10));
		((AbstractDocument) backArea.getDocument()).setDocumentFilter(new SizeFilter(500));
		backArea.setLineWrap(true);
		backArea.setWrapStyleWord(true);
		backArea.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "none");
		contentPane.add(backArea);
		backArea.setVisible(false);
		
		/*
		 * Buttons
		 * Used to navigate between cards and flip the current card
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
		
		//Go to the previous card
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frontArea.setVisible(true);
				backArea.setVisible(false);
				if(currentCard == 0)
					return;
				//Sets the current list elements
				frontList.set(currentCard, frontArea.getText());
				backList.set(currentCard, backArea.getText());
				currentCard --;
				frontArea.setText(frontList.get(currentCard));
				backArea.setText(backList.get(currentCard));
			}
		});
		
		//Go to the next card
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frontArea.setVisible(true);
				backArea.setVisible(false);
				if(currentCard+1 == frontList.size())
					return;
				frontList.set(currentCard, frontArea.getText());
				backList.set(currentCard, backArea.getText());
				currentCard ++;
				frontArea.setText(frontList.get(currentCard));
				backArea.setText(backList.get(currentCard));
			}
		});
		
		//Flip the card
		btnFlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(frontArea.isVisible()) {
					frontArea.setVisible(false);
					backArea.setVisible(true);
				}
				else if(backArea.isVisible()) {
					frontArea.setVisible(true);
					backArea.setVisible(false);
				}
			}
		});
		
		/*
		 * Menu and menu item interfaces
		 * File tab holds options to Save, Save As, Open, and Exit
		 * New Card: creates a new card and increments the necessary values
		 * Delete Card: removes a card and changes the needed values
		 * Save: saves the card set to a file 
		 * 		If a .txt file is not open, the user is prompted to create the file. Redirects to SaveAs
		 * SaveAs: allows the user to save the current text to a desired location with a desired name as a .txt
		 * Open: opens a .txt file and loads the stored text to the interface
		 * 		Parses a saved array list and then separates it into the front and back areas
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
	}
	//End of Flip Card Body
	
	/*
	 * Method to create a new card
	 * Makes sure that the front side is visible initially
	 * Increments the current card +1
	 * Edits the Textarea
	 * Updates the front and back lists
	 */
	public void CreateNewCard() {
		frontArea.setVisible(true);
		backArea.setVisible(false);
		frontList.add("");
		backList.add("");
		frontList.set(currentCard, frontArea.getText());
		backList.set(currentCard, backArea.getText());
		frontArea.setText("");
		backArea.setText("");
		currentCard ++;
	}
	
	/*
	 * Method to delete the current card
	 * Checks the current position and will move to the previous card unless the users is on the
	 * first card, in which case it moves forward
	 */
	public void DeleteCard() {
		frontArea.setVisible(true);
		backArea.setVisible(false);
		
		if(frontList.size() == 1) {
			frontArea.setText("");
			backArea.setText("");
		}
		else if(currentCard == 0 && frontList.size() > 1) {
			frontList.remove(currentCard);
			backList.remove(currentCard);
			frontArea.setText(frontList.get(currentCard));
			backArea.setText(backList.get(currentCard));
		}
		else{
			frontList.remove(currentCard);
			backList.remove(currentCard);
			currentCard --;
			frontArea.setText(frontList.get(currentCard));
			backArea.setText(backList.get(currentCard));
		}
	}
	
	/*
	 * Method to save the flip cards as a file
	 * If there is no existing file, redirect to the SaveAs function
	 */
	public void Save() {
		if( fileName == null) {
			SaveAs();
			return;
		}
		else {
			file = new File(fileName);
		}
		
		frontList.set(currentCard, frontArea.getText());
		backList.set(currentCard, backArea.getText());
		
		//Try saving
	    try {
	    	bw = new BufferedWriter(new FileWriter(file, false));
	        for(int i = 0; i < frontList.size(); i++) {
	        	bw.write(frontList.get(i));
	        	bw.newLine();
	        	bw.write(backList.get(i));
	        	if(i+1 == frontList.size()) {}
	        	else
	        		bw.newLine();
	        }
	        bw.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}
	
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
	    
	    frontList.set(currentCard, frontArea.getText());
		backList.set(currentCard, backArea.getText());
	    
	    /*
	     * Check if file is being saved over
	     * Creates the appropriate file name to be saved to
	     */
	    file = new File(SaveAs.getSelectedFile() + ".txt");
	    	
	    fileName = file.getAbsolutePath();//Store the filename for Save
	    
	    //Save to a file
	    try {
	        bw = new BufferedWriter(new FileWriter(file, false));
	        for(int i = 0; i < frontList.size(); i++) {
	        	bw.write(frontList.get(i));
	        	bw.newLine();
	        	bw.write(backList.get(i));
	        	if(i+1 == frontList.size()) {}
	        	else
	        		bw.newLine();
	        }
	        bw.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}
	
	//Method for opening from file
	public void Open(){
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
	    	frontList.clear();
	    	backList.clear();
	    	while(s.hasNextLine()) {
	    		frontList.add(s.nextLine());
	    		backList.add(s.nextLine());
	    		System.out.println("Hi");
	    	}
	    	currentCard = 0;
	    	frontArea.setVisible(true);
			backArea.setVisible(false);
	    	frontArea.setText(frontList.get(currentCard));
	    	backArea.setText(backList.get(currentCard));
	    	s.close();
	    }
	    catch (IOException e){
	    	e.printStackTrace();
	    }
	}
}