package com.nhnacademy.document.element.abs;

import com.nhnacademy.document.element.Element;
import com.nhnacademy.document.visitor.Visitor;
import java.util.List;
import lombok.Getter;

/**
 * @author : 이성준
 * @since : 1.0
 */

@Getter
public class DocsElement implements Element {

    String value;

    public DocsElement(String value) {
        this.value = value;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
