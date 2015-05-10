package com.alexandermaxgorkun.coolstory.entity;

/**
 * Paragraphs form a story.
 */
public class Paragraph {
    private String text;

    public Paragraph(String text) {
        setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
