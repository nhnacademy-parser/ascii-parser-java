package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.visitor.Visitor;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */


@Getter
public class OrderedListElement extends DocsElement {
    private final int level;

    public OrderedListElement(String s, int level) {
        super(s);
        this.level = level;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
