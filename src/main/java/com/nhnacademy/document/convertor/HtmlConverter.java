package com.nhnacademy.document.convertor;

import com.nhnacademy.document.element.DocsElement;
import com.nhnacademy.document.element.impl.AttributeElement;
import com.nhnacademy.document.element.impl.CommentElement;
import com.nhnacademy.document.element.impl.ExampleElement;
import com.nhnacademy.document.element.impl.HeadingElement;
import com.nhnacademy.document.element.impl.ImageElement;
import com.nhnacademy.document.element.impl.OrderedListElement;
import com.nhnacademy.document.element.impl.QuotationElement;
import com.nhnacademy.document.element.impl.SideBarElement;
import com.nhnacademy.document.element.impl.TableElement;
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
    public String visit(TableElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<table>").append("\n");
        {
            builder.append("<thead>").append("\n");
            builder.append("<tr>").append("\n");
            for (String columnHeading : element.getColumnHeadings()) {
                builder.append("<th>").append(columnHeading).append("</th>").append("\n");
            }
            builder.append("</tr>").append("\n");
            builder.append("</thead>").append("\n");
        }
        {
            builder.append("<tbody>").append("\n");
            for (String[] row : element.getRows()) {
                builder.append("<tr>").append("\n");
                for (String column : row) {
                    builder.append("<td>").append(column).append("</td>").append("\n");
                }
                builder.append("</tr>").append("\n");
            }
            builder.append("</tbody>").append("\n");
        }

        builder.append("</table>").append("\n");

        return builder.toString();
    }

    @Override
    public String visit(QuotationElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<blockquote>").append("\n");
        builder.append("<div class=\"paragraph\">").append("\n");
        builder.append("<p>").append(element.getValue()).append("</p>").append("\n");
        builder.append("</div>").append("\n");
        builder.append("</blockquote>").append("\n");
        builder.append(this.visit(element.getAttributeElement()));
        return builder.toString();
    }

    @Override
    public String visit(AttributeElement element) {
        StringBuilder builder = new StringBuilder();

        builder.append("<div class=\"attribution\">").append("\n");
        builder.append("&#8212; ").append(element.getBy()).append("<br>").append("\n");
        builder.append("<cite>").append(element.getFrom()).append("</cite>").append("\n");
        builder.append("</div>").append("\n");
        return builder.toString();
    }

    @Override
    public String visit(CommentElement element) {
        return "<!--" + element.getValue() + "-->";
    }


    @Override
    public String visit(DocsElement element) {
        if (element.getValue().toString().isEmpty()) {
            return "";
        }
        return "<p>" + element.getValue() + "</p>";
    }
}
