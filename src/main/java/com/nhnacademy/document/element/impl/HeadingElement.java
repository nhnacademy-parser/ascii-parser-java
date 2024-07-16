package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.abs.DocsElement;
import com.nhnacademy.document.visitor.Visitor;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class HeadingElement extends DocsElement {

    int level;


    public HeadingElement(String s) {
        this(s, 1);
    }

    public HeadingElement(String value, int level) {
        super(value);
        this.level = level;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }

}
