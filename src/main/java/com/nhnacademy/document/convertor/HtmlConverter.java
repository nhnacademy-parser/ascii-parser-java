package com.nhnacademy.document.convertor;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.element.impl.AnchorElement;
import com.nhnacademy.document.element.impl.AttributeElement;
import com.nhnacademy.document.element.impl.BoldTextElement;
import com.nhnacademy.document.element.impl.CommentElement;
import com.nhnacademy.document.element.impl.CrossReferenceElement;
import com.nhnacademy.document.element.impl.ExampleElement;
import com.nhnacademy.document.element.impl.FootNoteElement;
import com.nhnacademy.document.element.impl.HeadingElement;
import com.nhnacademy.document.element.impl.ImageElement;
import com.nhnacademy.document.element.impl.ItalicTextElement;
import com.nhnacademy.document.element.impl.OrderedListElement;
import com.nhnacademy.document.element.impl.QuotationElement;
import com.nhnacademy.document.element.impl.SideBarElement;
import com.nhnacademy.document.element.impl.TableElement;
import com.nhnacademy.document.element.impl.TitleElement;
import com.nhnacademy.document.element.impl.UnOrderedListElement;
import com.nhnacademy.document.visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 이성준
 * @since : 1.0
 */


public class HtmlConverter implements Visitor {

    public static final String LINE_BREAK = "\n";
    public static final String DOUBLE_QUOTES = "\"";

    @Override
    public String visit(HeadingElement element) {
        StringBuilder builder = new StringBuilder();
        String tag = "h" + element.getLevel();
        String id = element.getValue().toString().replace(" ", "_").toLowerCase();
        builder.append("<").append(tag)
                .append(" id=").append(DOUBLE_QUOTES).append(id).append(DOUBLE_QUOTES).append(">")
                .append(element.getValue())
                .append("</").append(tag).append(">");

        return builder.toString();
    }

    @Override
    public String visit(ExampleElement element) {
        return "<p>" + element.getValue() + "</p>";
    }

    @Override
    public String visit(TitleElement element) {
        return "<div class=" + DOUBLE_QUOTES + "title\">" + element.getValue() + "</div>";
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
        return "<div class=" + DOUBLE_QUOTES + "side-bar\">" + element.getValue() + "</div>";
    }

    @Override
    public String visit(ImageElement element) {
        return "<img class=image src=" + DOUBLE_QUOTES + element.getValue() + DOUBLE_QUOTES + " alt=" +
                DOUBLE_QUOTES + element.getAltString() + DOUBLE_QUOTES + "/>";
    }

    @Override
    public String visit(TableElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<table>").append(LINE_BREAK);
        {
            builder.append("<thead>").append(LINE_BREAK);
            builder.append("<tr>").append(LINE_BREAK);
            for (String columnHeading : element.getColumnHeadings()) {
                builder.append("<th>").append(columnHeading).append("</th>").append(LINE_BREAK);
            }
            builder.append("</tr>").append(LINE_BREAK);
            builder.append("</thead>").append(LINE_BREAK);
        }
        {
            builder.append("<tbody>").append(LINE_BREAK);
            for (String[] row : element.getRows()) {
                builder.append("<tr>").append(LINE_BREAK);
                for (String column : row) {
                    builder.append("<td>").append(column).append("</td>").append(LINE_BREAK);
                }
                builder.append("</tr>").append(LINE_BREAK);
            }
            builder.append("</tbody>").append(LINE_BREAK);
        }

        builder.append("</table>").append(LINE_BREAK);

        return builder.toString();
    }

    @Override
    public String visit(QuotationElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<blockquote>").append(LINE_BREAK);
        builder.append("<div class=" + DOUBLE_QUOTES + "paragraph\">").append(LINE_BREAK);
        builder.append("<p>").append(element.getValue()).append("</p>").append(LINE_BREAK);
        builder.append("</div>").append(LINE_BREAK);
        builder.append("</blockquote>").append(LINE_BREAK);
        builder.append(this.visit(element.getAttributeElement()));
        return builder.toString();
    }

    @Override
    public String visit(AttributeElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<div class=" + DOUBLE_QUOTES + "attribution\">").append(LINE_BREAK);
        builder.append("&#8212; ").append(element.getBy()).append("<br>").append(LINE_BREAK);
        builder.append("<cite>").append(element.getFrom()).append("</cite>").append(LINE_BREAK);
        builder.append("</div>").append(LINE_BREAK);
        return builder.toString();
    }

    @Override
    public String visit(CommentElement element) {
        return "<!--" + element.getValue() + "-->";
    }

    @Override
    public String visit(CrossReferenceElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<a href=").append(DOUBLE_QUOTES).append("#").append(element.getTarget()).append(DOUBLE_QUOTES)
                .append(">");
        builder.append(element.getAltText());
        builder.append("</a>");

        return builder.toString();
    }

    @Override
    public String visit(AnchorElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<a href=").append(DOUBLE_QUOTES).append(element.getHref()).append(DOUBLE_QUOTES)
                .append(">");
        builder.append(element.getAltText());
        builder.append("</a>");

        return builder.toString();
    }

    @Override
    public String visit(BoldTextElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<strong>").append(element.getValue()).append("</strong>");
        return builder.toString();
    }

    @Override
    public String visit(ItalicTextElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<em>").append(element.getValue()).append("</em>");
        return builder.toString();
    }

    List<FootNoteElement> footNoteElements = new ArrayList<>();

    @Override
    public String visit(FootNoteElement element) {
        footNoteElements.add(element);
        StringBuilder builder = new StringBuilder();

        /**
         *             <p>This is another paragraph.<sup class="footnote">[<a id="_footnoteref_1" class="footnote"
         *                                                                    href="#_footnotedef_1" title="View footnote.">1</a>]</sup>
         *             </p>
         */

        String footnoteId = "_footnoteref_" + footNoteElements.size();

        builder.append("<sup class=").append(DOUBLE_QUOTES).append("footnote").append(DOUBLE_QUOTES).append(">")
                .append("[")
                .append("<a ")
                .append("id=").append(DOUBLE_QUOTES).append(footnoteId).append(DOUBLE_QUOTES)
                .append("class=").append(DOUBLE_QUOTES).append("footnote").append(DOUBLE_QUOTES)
                .append("href=").append(DOUBLE_QUOTES).append("#").append(footnoteId).append(DOUBLE_QUOTES)
                .append(">")
                .append(footNoteElements.size())
                .append("</a>")
                .append("]")
                .append("</sup>");

        return builder.toString();
    }


    @Override
    public String visit(DocsElement element) {
        if (element.getValue().toString().isEmpty()) {
            return "";
        }
        return "<p>" + element.getValue() + "</p>";
    }
}
