package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.visitor.Visitor;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class CommentElement extends DocsElement {
    public CommentElement(String s) {
        super(s);
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
