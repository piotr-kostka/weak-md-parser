package com.github.arena.challenges.weakmdparser;

public class MarkdownParser {

    public String parse(String markdown) {
        String[] lines = markdown.split("\n");
        StringBuilder result = new StringBuilder();
        boolean activeList = false;

        ContentParser contentParser = new ContentParser();

        for (String line : lines) {

            String theLine = contentParser.parseHeader(line);

            if (theLine == null) {
                theLine = contentParser.parseListItem(line);
            }

            if (theLine == null) {
                theLine = contentParser.parseParagraph(line);
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
}
