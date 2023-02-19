package com.github.arena.challenges.weakmdparser;

public class MarkdownParser {

    public String parse(String markdown) {
        String[] lines = markdown.split("\n");
        StringBuilder result = new StringBuilder();
        boolean activeList = false;

        for (String line : lines) {

            String theLine = parseHeader(line);

            if (theLine == null) {
                theLine = parseListItem(line);
            }

            if (theLine == null) {
                theLine = parseParagraph(line);
            }

            if (theLine.matches("(<li>).*") && !theLine.matches("(<h).*") && !theLine.matches("(<p>).*") && !activeList) {
                activeList = true;
                result.append("<ul>");
                result.append(theLine);
            } else if (!theLine.matches("(<li>).*") && activeList) {
                activeList = false;
                result.append("</ul>");
                result.append(theLine);
            } else {
                result.append(theLine);
            }
        }

        if (activeList) {
            result.append("</ul>");
        }

        return result.toString();
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
