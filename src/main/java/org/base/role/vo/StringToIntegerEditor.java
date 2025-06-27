package org.base.role.vo;

import org.base.utils.StringUtils;

import java.beans.PropertyEditorSupport;

public class StringToIntegerEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || StringUtils.isEmpty(text.trim()) || text.trim().equals("\"\"")) {
            setValue(null);
        } else {
            try {
                setValue(Integer.parseInt(text));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid integer format", e);
            }
        }
    }

}
