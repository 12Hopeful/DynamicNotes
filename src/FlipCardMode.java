import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import javax.swing.*;
import java.awt.Font;

public class FlipCardMode extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane = new JTextPane();
	private AbstractDocument abDoc;

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
		 * Text Pane
		 */
		textPane.setFont(new Font("Arial", Font.PLAIN, 16));
		textPane.setBackground(Color.WHITE);
		textPane.setBounds(20, 20, 555, 260);
		textPane.setMargin(new Insets(10, 10, 10, 10));
		Document doc = textPane.getStyledDocument();
	    if (doc instanceof AbstractDocument) {
	      abDoc = (AbstractDocument) doc;
	      abDoc.setDocumentFilter(new SizeFilter(500));
	    }
		contentPane.add(textPane);
		
		
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
	}
}


/*
 * Class to set the field limit for the flip card
 * Prevents the use of more than 500 characters
 */
class SizeFilter extends DocumentFilter {
	  int len;
	  public SizeFilter(int max_Chars) {
	    len = max_Chars;
	  }
	  
	  public void insertString(FilterBypass fb, int offset, String str, AttributeSet a) throws BadLocationException {
	    if ((fb.getDocument().getLength() + str.length()) <= len){
	      super.insertString(fb, offset, str, a);
	    }
	  }
	  
	  public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet a) throws BadLocationException {
	    if ((fb.getDocument().getLength() + str.length() - length) <= len){
	      super.replace(fb, offset, length, str, a);
	    }
	  }
	}
