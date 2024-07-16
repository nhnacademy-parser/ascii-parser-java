package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.visitor.Visitor;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class ListingBlockElement extends DocsElement {
    public ListingBlockElement(String string) {
        super(string);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
