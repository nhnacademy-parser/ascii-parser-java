package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.abs.DocsElement;
import com.nhnacademy.document.visitor.Visitor;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class UnOrderedListElement extends DocsElement {

    private final int level;

    public UnOrderedListElement(String s, int level) {
        super(s);
        this.level = level;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
