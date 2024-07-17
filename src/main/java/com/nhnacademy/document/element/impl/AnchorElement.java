package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.visitor.Visitor;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class AnchorElement extends DocsElement {
    private final String href;
    private final String altText;

    public AnchorElement(String href, String altText) {
        super(href);
        this.href = href;
        this.altText = altText;
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
