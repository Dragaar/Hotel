package com.rosivanyshyn.controller.tag;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.LocalDate;

public class CurrentYearTag extends SimpleTagSupport {

    /**
     *  Writes to JspWriter formatted current year.
     */
    @Override
    public void doTag() throws IOException {
        final JspWriter writer = getJspContext().getOut();
        writer.print(LocalDate.now().getYear());
    }
}

