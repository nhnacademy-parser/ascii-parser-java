package com.nhnacademy.document.parser;

import com.nhnacademy.document.element.Element;

/**
 * @author : 이성준
 * @since : 1.0
 */


public interface Parser {
    Element parse(String string);

}
