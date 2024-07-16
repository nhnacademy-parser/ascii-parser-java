package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class TableElement extends DocsElement {

    String[] columnHeadings;
    List<String[]> rows;


    public TableElement(String string) {
        super();
        rows = new ArrayList<>();

        String[] lines = string.split("\n");

        String[] startLine = lines[0].split("\\|");
        this.columnHeadings = new String[startLine.length - 1];
        System.arraycopy(startLine, 1, this.columnHeadings, 0, 2);

        StringBuilder builder = new StringBuilder();

        for (int i = 1; i < lines.length; i++) {
            builder.append(lines[i]);
        }

        String[] element = builder.toString().split("\\|");

        for (int i = 1; i < element.length; i += columnHeadings.length) {
            String[] row = new String[columnHeadings.length];
            System.arraycopy(element, i, row, 0, columnHeadings.length);
            rows.add(row);
        }
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getValue() {
        StringBuilder builder = new StringBuilder();

        for (String column : columnHeadings) {
            builder.append("|").append(column);
        }
        builder.append("\n");

        rows.forEach(
                column -> {
                    for (String s : column) {
                        builder.append("|").append(s);
                    }
                    builder.append("\n");
                }
        );

        return builder.toString();
    }

    public String[] getColumnHeadings() {
        return this.columnHeadings;
    }

    public List<String[]> getRows() {
        return rows;
    }
}
