package com.github.arena.challenges.weakmdparser;

public class MarkdownParser {

    public String parse(String markdown) {
        String[] lines = markdown.split("\n");
        String result = "";
        boolean activeList = false;

        for (int i = 0; i < lines.length; i++) {

            String theLine = parseHeader(lines[i]);

            if (theLine == null) {
                theLine = parseListItem(lines[i]);
            }

            if (theLine == null) {
                theLine = parseParagraph(lines[i]);
            }

            if (theLine.matches("(<li>).*") && !theLine.matches("(<h).*") && !theLine.matches("(<p>).*") && !activeList) {
                activeList = true;
                result = result + "<ul>";
                result = result + theLine;
            } else if (!theLine.matches("(<li>).*") && activeList) {
                activeList = false;
                result = result + "</ul>";
                result = result + theLine;
            } else {
                result = result + theLine;
            }
        }

        if (activeList) {
            result = result + "</ul>";
        }

        return result;
    }

    private String parseHeader(String markdown) {
        int count = 0;

        for (int i = 0; i < markdown.length() && markdown.charAt(i) == '#'; i++) {
            count++;
        }

        if (count == 0) {
            return null;
        }

        return "<h" + count + ">" + markdown.substring(count + 1) + "</h" + count + ">";
    }

    private String parseListItem(String markdown) {
        if (markdown.startsWith("*")) {
            String skipAsterisk = markdown.substring(2);
            String listItemString = parseContent(skipAsterisk);
            return "<li>" + listItemString + "</li>";
        }

        return null;
    }

    private String parseParagraph(String markdown) {
        return "<p>" + parseContent(markdown) + "</p>";
    }

    private String parseContent(String markdown) {
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
}
