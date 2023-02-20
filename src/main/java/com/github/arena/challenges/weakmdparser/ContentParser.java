package com.github.arena.challenges.weakmdparser;

import java.util.List;

public class ContentParser {

    protected String parseHeader(String markdown) {
        int count = 0;

        for (int i = 0; i < markdown.length() && markdown.charAt(i) == '#'; i++) {
            count++;
        }

        return "<h" + count + ">" + markdown.substring(count + 1) + "</h" + count + ">";
    }

    protected String parseListItem(String markdown) {
        return "<li>" + markdown.substring(2) + "</li>";
    }

    protected String parseList(List<String> itemsList) {
        StringBuilder result = new StringBuilder();
        for (String item : itemsList) {
            result.append(parseListItem(item));
        }
        return "<ul>" + result + "</ul>";
    }

    protected String parseParagraph(String markdown) {
        return "<p>" + markdown + "</p>";
    }

    private String parseBold(String markdown) {
        String lookingFor = "__(.+)__";
        String update = "<strong>$1</strong>";
        return markdown.replaceAll(lookingFor, update);
    }

    private String parseItalic(String markdown) {
        String lookingFor = "_(.+)_";
        String update = "<em>$1</em>";
        return markdown.replaceAll(lookingFor, update);
    }

    protected String parseSymbols(String markdown) {
        String parsedBold = parseBold(markdown);
        return parseItalic(parsedBold);
    }
}
