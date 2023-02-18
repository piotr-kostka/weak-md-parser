package com.github.arena.challenges.weakmdparser;

public class MarkdownParser {

    public String parse(String markdown) {
        String[] lines = markdown.split("\n");
        String result = "";
        boolean activeList = false;

        for (int i = 0; i < lines.length; i++) {

            String theLine = ph(lines[i]);

            if (theLine == null) {
                theLine = li(lines[i]);
            }

            if (theLine == null) {
                theLine = p(lines[i]);
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

    private String ph(String markdown) {
        int count = 0;

        for (int i = 0; i < markdown.length() && markdown.charAt(i) == '#'; i++) {
            count++;
        }

        if (count == 0) {
            return null;
        }

        return "<h" + count + ">" + markdown.substring(count + 1) + "</h" + count + ">";
    }

    private String li(String markdown) {
        if (markdown.startsWith("*")) {
            String skipAsterisk = markdown.substring(2);
            String listItemString = parseSomeSymbols(skipAsterisk);
            return "<li>" + listItemString + "</li>";
        }

        return null;
    }

    private String p(String markdown) {
        return "<p>" + parseSomeSymbols(markdown) + "</p>";
    }

    private String parseSomeSymbols(String markdown) {

        String lookingFor = "__(.+)__";
        String update = "<strong>$1</strong>";
        String workingOn = markdown.replaceAll(lookingFor, update);

        lookingFor = "_(.+)_";
        update = "<em>$1</em>";
        return workingOn.replaceAll(lookingFor, update);
    }
}
