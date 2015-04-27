package com.alexandermaxgorkun.coolstory.domain;

import com.alexandermaxgorkun.coolstory.entity.Story;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alexander Gorkun mindkilleralexs@gmail.com
 */
public class StoryDomain {
    public enum ORDER {
        CREATED_DESC
    }

    /**
     * (Remotely) searching for stories.
     */
    public Collection<Story> find(ORDER order, int limit) {
        ArrayList<Story> stories = new ArrayList<Story>();
        Story story = new Story("Remote Cool story");
        stories.add(story);
        story = new Story("Remote Some bullshit");
        stories.add(story);

        if (limit >= 0 && stories.size() > limit) {
            stories = (ArrayList<Story>) stories.subList(0, limit - 1);
        }
        
        return stories;
    }

    /**
     * Find stories in local storage.
     */
    public Collection<Story> findLocal(ORDER order, int limit) {
        ArrayList<Story> stories = new ArrayList<Story>();
        Story story = new Story("Cool story");
        stories.add(story);
        story = new Story("Some bullshit");
        stories.add(story);

        if (limit >= 0 && stories.size() > limit) {
            stories = (ArrayList<Story>) stories.subList(0, limit - 1);
        }

        return stories;
    }
}
