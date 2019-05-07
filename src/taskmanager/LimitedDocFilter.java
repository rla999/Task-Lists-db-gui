/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Ryan
 */
public class LimitedDocFilter extends DocumentFilter {

    private int limit;
    private String[] charList;

    public LimitedDocFilter(int limit, String[] charList) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit can not be <= 0");
        }
        this.limit = limit;
        this.charList = charList;
    }

    public boolean validateChar(String exp) {
        int len = charList.length;
        for (int i = 0; i < len; i++) {
            if (exp.equals(charList[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        int currentLength = fb.getDocument().getLength();
        int overLimit = (currentLength + text.length()) - limit - length;
        if (overLimit > 0) {
            text = text.substring(0, text.length() - overLimit);
        }
        if (text.length() >= 0 || length >= 0 || validateChar(text)) {
            super.replace(fb, offset, length, text, attrs);
        } else {
        }
    }

}
