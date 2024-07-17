package com.nhnacademy.document.visitor;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.element.impl.AnchorElement;
import com.nhnacademy.document.element.impl.AttributeElement;
import com.nhnacademy.document.element.impl.CommentElement;
import com.nhnacademy.document.element.impl.CrossReferenceElement;
import com.nhnacademy.document.element.impl.ExampleElement;
import com.nhnacademy.document.element.impl.HeadingElement;
import com.nhnacademy.document.element.impl.ImageElement;
import com.nhnacademy.document.element.impl.OrderedListElement;
import com.nhnacademy.document.element.impl.QuotationElement;
import com.nhnacademy.document.element.impl.SideBarElement;
import com.nhnacademy.document.element.impl.TableElement;
import com.nhnacademy.document.element.impl.TitleElement;
import com.nhnacademy.document.element.impl.UnOrderedListElement;

/**
 * @author : 이성준
 * @since : 1.0
 */


public interface Visitor {
    String visit(HeadingElement element);

    String visit(ExampleElement element);

    String visit(TitleElement element);

    String visit(OrderedListElement element);

    String visit(UnOrderedListElement element);

    String visit(SideBarElement element);

    String visit(ImageElement element);

    String visit(TableElement element);

    String visit(QuotationElement element);

    String visit(AttributeElement element);
    String visit(CommentElement element);

    String visit(CrossReferenceElement element);

    String visit(AnchorElement element);

    String visit(DocsElement element);
}
