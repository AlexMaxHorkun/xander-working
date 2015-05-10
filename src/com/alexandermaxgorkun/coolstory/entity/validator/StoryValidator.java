package com.alexandermaxgorkun.coolstory.entity.validator;

import com.alexandermaxgorkun.coolstory.entity.Story;

/**
 * Validates a Story.
 */
public class StoryValidator implements Validator<Story> {
    public boolean isValid(Story story) {
        if (story.getTitle() == null || story.getTitle().isEmpty()) {
            return false;
        }

        if (story.getCreated() == null) {
            return false;
        }

        if (story.getParagraphs().size() == 0 || story.getParagraphs().get(0).getText() == null || story.getParagraphs().get(0).getText().isEmpty()) {
            return false;
        }

        return true;
    }
}
