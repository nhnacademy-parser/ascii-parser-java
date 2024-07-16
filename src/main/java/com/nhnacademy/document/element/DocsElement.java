package com.nhnacademy.document.element;

import com.nhnacademy.document.visitor.Visitor;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class DocsElement implements Element {

    Object value;

    public DocsElement(Object value) {
        this.value = value;
    }

    protected DocsElement() {
        value = "";
    }

    @Override
    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
