package com.nhnacademy.document.convertor;

import com.nhnacademy.document.element.abs.DocsElement;
import com.nhnacademy.document.element.impl.ExampleElement;
import com.nhnacademy.document.element.impl.HeadingElement;
import com.nhnacademy.document.element.impl.ImageElement;
import com.nhnacademy.document.element.impl.OrderedListElement;
import com.nhnacademy.document.element.impl.SideBarElement;
import com.nhnacademy.document.element.impl.TitleElement;
import com.nhnacademy.document.element.impl.UnOrderedListElement;
import com.nhnacademy.document.visitor.Visitor;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class HtmlConverter implements Visitor {
    @Override
    public String visit(HeadingElement element) {
        String tag = "h" + element.getLevel();
        return "<" + tag + ">" + element.getValue() + "</" + tag + ">";
    }

    @Override
    public String visit(ExampleElement element) {
        return "<p>" + element.getValue() + "</p>";
    }

    @Override
    public String visit(TitleElement element) {
        return "<div class=\"title\">" + element.getValue() + "</div>";
    }

    @Override
    public String visit(OrderedListElement element) {
        return "<ol>" + element.getValue() + "</ol>";
    }

    @Override
    public String visit(UnOrderedListElement element) {
        return "<ul>" + element.getValue() + "</ul>";
    }

    @Override
    public String visit(SideBarElement element) {
        return "<div class=\"side-bar\">" + element.getValue() + "</div>";
    }

    @Override
    public String visit(ImageElement element) {
        return "<img class=image src=\"" + element.getValue() + "\" " + "alt=\"" + element.getAltString() + "\"/>";
    }

    @Override
    public String visit(DocsElement element) {
        return element.getValue();
    }
}
