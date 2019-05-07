/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Ryan
 */
public class LimitedDocFilter extends DocumentFilter {

    private Pattern regEx;
//    private Pattern regEx = Pattern.compile("^[YNyn]$");
    private int maxCharLength;
//    private int maxCharLength = 1;

    public LimitedDocFilter(Pattern regEx, int maxCharLength) {
        this.regEx = regEx;
        this.maxCharLength = maxCharLength;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        Matcher matcher = regEx.matcher(text);
        if ((fb.getDocument().getLength() + text.length()) <= maxCharLength && matcher.matches()) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

}
