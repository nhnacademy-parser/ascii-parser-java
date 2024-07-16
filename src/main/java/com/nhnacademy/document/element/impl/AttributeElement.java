package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.element.DocsElement;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class AttributeElement extends DocsElement {
    private final String quotationType;
    private final String by;
    private final String from;

    public AttributeElement(String quotationType, String by, String from) {

        this.quotationType = quotationType;
        this.by = by;
        this.from = from;
    }
}
