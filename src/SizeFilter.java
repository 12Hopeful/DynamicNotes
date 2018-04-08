import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

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