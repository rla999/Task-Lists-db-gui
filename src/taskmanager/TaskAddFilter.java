package taskmanager;

import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class TaskAddFilter extends DocumentFilter {
    private Pattern regEx;
//    private Pattern regEx = Pattern.compile("[y][n][Y][N]");
//    private int maxCharLength = 1;

    public TaskAddFilter(Pattern regEx) {
        this.regEx = regEx;
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        
        Matcher matcher = regEx.matcher("placeholder"); 
        if (text != null || text != "")
            matcher= regEx.matcher(text);
        
        if (matcher.matches() == false) {
            super.replace(fb, offset, length, text, attrs);
        }
        else{
            Toolkit.getDefaultToolkit().beep();
        }
    }
}