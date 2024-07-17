package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.visitor.Visitor;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class CrossReferenceElement extends DocsElement {
    private final String target;
    private final String altText;

    public CrossReferenceElement(String target, String altText) {
        super(altText);
        this.target = target;
        this.altText = altText;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
