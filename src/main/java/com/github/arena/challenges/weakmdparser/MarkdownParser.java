package com.github.arena.challenges.weakmdparser;

import java.util.ArrayList;
import java.util.List;

public class MarkdownParser {
    ContentParser contentParser = new ContentParser();
    StringBuilder result = new StringBuilder();
    List<String> itemsList = new ArrayList<>();

    public String parse(String markdown) {
        String[] lines = markdown.split("\n");
        int counter = 0;

        for (String line : lines) {
            counter++;

            if (line.startsWith("#")) {
                result.append(contentParser.parseHeader(line));
                continue;
            }

            if (line.startsWith("*")) {
                itemsList.add(line);
                if (counter == lines.length) {
                    result.append(contentParser.parseList(itemsList));
                }
                continue;
            }

            if (!itemsList.isEmpty()) {
                result.append(contentParser.parseList(itemsList));
            }

            result.append(contentParser.parseParagraph(line));
        }

        return contentParser.parseSymbols(result.toString());
    }
}
