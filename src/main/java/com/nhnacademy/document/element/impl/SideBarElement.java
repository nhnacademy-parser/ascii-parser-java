package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.abs.DocsElement;
import com.nhnacademy.document.visitor.Visitor;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class SideBarElement extends DocsElement {
    public SideBarElement(String string) {
        super(string);
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
