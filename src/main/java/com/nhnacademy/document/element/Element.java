package com.nhnacademy.document.element;

import com.nhnacademy.document.visitor.Visitor;

/**
 * @author : 이성준
 * @since : 1.0
 */


public interface Element {

    Object accept(Visitor visitor);
}
