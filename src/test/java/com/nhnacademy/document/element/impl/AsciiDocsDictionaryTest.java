package com.nhnacademy.document.element.impl;

import com.nhnacademy.document.convertor.HtmlConverter;
import com.nhnacademy.document.element.DocsElement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/**
 * @author : 이성준
 * @since : 1.0
 */


class AsciiDocsDictionaryTest {

    @Test
    void resolve() throws IOException {
        File source = new File("asciidocs/template.adoc");
        File output = new File("asciidocs/test.html");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));


        List<DocsElement> docsElements = new ArrayList<>();

        String s;
        while ((s = bufferedReader.readLine()) != null) {
            DocsElement docsElement = new DocsElement(s);

            if (s.startsWith("=")) {    //heading or Example Block
                Pattern exampleBlockPattern = Pattern.compile("====$");
                Matcher matcher;

                if (exampleBlockPattern.matcher(s).find()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((s = bufferedReader.readLine()) != null) {
                        if (exampleBlockPattern.matcher(s).find()) {
                            break;
                        }
                        stringBuilder.append(s).append("\n");
                    }
                    docsElement = new ExampleElement(stringBuilder.toString());
                } else {
                    matcher = Pattern.compile("=").matcher(s);
                    int level = 0;
                    for (; matcher.find(); level++) ;
                    docsElement = new HeadingElement(matcher.replaceAll(""), level);
                }
            } else if (s.startsWith(".")) {// title or orderList

                if (Pattern.compile("^\\.[^ .]").matcher(s).find()) { // title
                    docsElement = new TitleElement(s.replace(".", ""));
                } else {
                    Matcher matcher = Pattern.compile("\\.").matcher(s);

                    int level = 0;
                    for (; matcher.find(); level++) ;
                    docsElement = new OrderedListElement(matcher.replaceAll(""), level);
                }
            } else if (s.startsWith("*")) { // orderList or sideBar

                Pattern sideBarPattern = Pattern.compile("\\*\\*\\*\\*$");
                Matcher matcher;

                if (sideBarPattern.matcher(s).find()) { // sideBar
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((s = bufferedReader.readLine()) != null) {
                        if (sideBarPattern.matcher(s).find()) {
                            break;
                        }
                        stringBuilder.append(s).append("\n");
                    }
                    docsElement = new SideBarElement(stringBuilder.toString());
                } else { // unordered List
                    matcher = Pattern.compile("\\*").matcher(s);
                    int level = 0;
                    for (; matcher.find(); level++) ;
                    docsElement = new UnOrderedListElement(matcher.replaceAll(""), level);
                }

            } else if (s.startsWith("-")) {// unorderedList(st. Markdown) , listing Block

                Pattern listingBlockPattern = Pattern.compile("----$");
                Matcher matcher;

                if (listingBlockPattern.matcher(s).find()) { // listing Block
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((s = bufferedReader.readLine()) != null) {
                        if (listingBlockPattern.matcher(s).find()) {
                            break;
                        }
                        stringBuilder.append(s).append("\n");
                    }
                    docsElement = new ListingBlockElement(stringBuilder.toString());
                } else {  // unorderedList(st. Markdown)
                    matcher = Pattern.compile("-").matcher(s);
                    int level = 0;
                    for (; matcher.find(); level++) ;
                    docsElement = new UnOrderedListElement(matcher.replaceAll(""), level);
                }

            } else if (s.startsWith("image::")) { // image

                Pattern ImageLinkPattern = Pattern.compile("^image::");
                Matcher matcher = ImageLinkPattern.matcher(s);

                if (matcher.find()) {
                    String temp = matcher.replaceAll("");

                    matcher = Pattern.compile("\\[.+]").matcher(temp);

                    if (matcher.find()) {
                        String alt = matcher.group();
                        temp = matcher.replaceAll("");
                        docsElement = new ImageElement(temp, alt.substring(1, alt.length() - 1));
                    }

                }
            } else if (s.startsWith("|===")) {

                Pattern tablePattern = Pattern.compile("\\|===$");
                Matcher matcher = tablePattern.matcher(s);

                if (matcher.find()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((s = bufferedReader.readLine()) != null) {
                        if (tablePattern.matcher(s).find()) {
                            break;
                        }
                        stringBuilder.append(s).append("\n");
                    }
                    docsElement = new TableElement(stringBuilder.toString());
                }

            } else if (s.startsWith("[")) { // Quotation Attribute
                Matcher matcher = Pattern.compile("\\[.+]$").matcher(s);

                if (matcher.find()) {
                    String attributes = matcher.group();
                    String[] temp = new String[3];
                    String[] copy = attributes.substring(1, attributes.length() - 1).split(",");

                    System.arraycopy(copy, 0, temp, 0, Math.min(copy.length, 3));

                    docsElement = new AttributeElement(temp[0], temp[1], temp[2]);
                }

            } else if (s.startsWith("_")) { // Quotation

                Pattern quotePattern = Pattern.compile("____");
                Matcher matcher = quotePattern.matcher(s);

                if (matcher.find()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((s = bufferedReader.readLine()) != null) {
                        if (quotePattern.matcher(s).find()) {
                            break;
                        }
                        stringBuilder.append(s).append("\n");
                    }
                    QuotationElement element = new QuotationElement(stringBuilder.toString());

                    DocsElement lastElement = docsElements.removeLast();
                    if (lastElement instanceof AttributeElement attributeElement) {
                        element.addAttributes(attributeElement);
                    }
                    docsElement = element;
                }

            } else if (s.startsWith("//")) { // Comment
                Pattern commentPattern = Pattern.compile("//");
                Matcher matcher = commentPattern.matcher(s);

                if (matcher.find()) {
                    docsElement = new CommentElement(matcher.replaceAll(""));
                }


            } else if (s.startsWith(":")) {

            } else if (s.contains("<<")) { //
                Pattern crossRefPattern = Pattern.compile("<<+.+>>");
                Matcher matcher = crossRefPattern.matcher(s);

                if (matcher.find()) {
                    String temp = matcher.group();
                    docsElements.add(new DocsElement(matcher.replaceAll("")));

                    Pattern angleBracket = Pattern.compile("[<>]");
                    Matcher matcher1 = angleBracket.matcher(temp);
                    temp = matcher1.replaceAll("");
                    String[] strings = temp.split(",");

                    docsElement = new CrossReferenceElement(strings[0], strings[1]);
                }
            } else if (s.contains("://")) { // protocol ref
                Pattern protocolIncludeAltTextPattern = Pattern.compile("(\\S+://)(.+)(\\[(.*)])");
                Pattern protocolExcludeAltTextPattern = Pattern.compile("(\\S+://[^ .]+)");

                Matcher matcher = protocolIncludeAltTextPattern.matcher(s);

                if (matcher.find()) {
                    String temp = matcher.group();

                    String preString = s.substring(0, matcher.start());
                    String postString = s.substring(matcher.end());

                    docsElements.add(new DocsElement(preString));

                    matcher = Pattern.compile("\\[(.*)]").matcher(temp);

                    if (matcher.find()) {
                        String altText = matcher.group().substring(1, matcher.group().length() - 1);
                        String href = matcher.replaceAll("");
                        docsElement = new AnchorElement(href, altText);
                    }
                    docsElements.add(docsElement);


                    docsElements.add(new DocsElement(postString));

                    continue;
                } else if ((matcher = protocolExcludeAltTextPattern.matcher(s)).find()) {
                    String temp = matcher.group();

                    String preString = s.substring(0, matcher.start());
                    String postString = s.substring(matcher.end());

                    docsElements.add(new DocsElement(preString));

                    docsElement = new AnchorElement(temp, "");
                    docsElements.add(docsElement);

                    docsElements.add(new DocsElement(postString));

                    continue;
                }

            }

            docsElements.add(docsElement);
        }

        StringBuilder builder = new StringBuilder();

        HtmlConverter htmlConverter = new HtmlConverter();
        for (DocsElement docsElement : docsElements) {
            String string = docsElement.accept(htmlConverter).toString();
            builder.append(string).append("\n");
        }

        bufferedWriter.write(builder.toString());
        bufferedWriter.flush();


    }


}