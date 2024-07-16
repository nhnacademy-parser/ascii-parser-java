package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.abs.DocsElement;
import com.nhnacademy.document.visitor.Visitor;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class ImageElement extends DocsElement {
    private final String altString;

    public ImageElement(String source, String altString) {
        super(source);
        this.altString = altString;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
