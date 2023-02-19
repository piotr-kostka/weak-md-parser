package com.github.arena.challenges.weakmdparser;

public class ContentParser {

    private String parseSymbols(String markdown) {
        String parsedBold = parseBold(markdown);
        return parseItalic(parsedBold);
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

    protected String parseHeader(String markdown) {
        int count = 0;

        for (int i = 0; i < markdown.length() && markdown.charAt(i) == '#'; i++) {
            count++;
        }

        if (count == 0) {
            return null;
        }

        return "<h" + count + ">" + markdown.substring(count + 1) + "</h" + count + ">";
    }

    protected String parseListItem(String markdown) {
        if (markdown.startsWith("*")) {
            String skipAsterisk = markdown.substring(2);
            String listItemString = parseSymbols(skipAsterisk);
            return "<li>" + listItemString + "</li>";
        }

        return null;
    }

    protected String parseParagraph(String markdown) {
        return "<p>" + parseSymbols(markdown) + "</p>";
    }
}
