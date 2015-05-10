package com.alexandermaxgorkun.coolstory.domain;

import com.alexandermaxgorkun.coolstory.entity.Story;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

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

    /**
     * Posts a story on remote server n shit.
     */
    public void create(Story story) {
        if (story.getRemoteId() != null) {
            throw new RuntimeException("Story already has remote ID " + story.getRemoteId().toString());
        }

        Random rnd = new Random();
        BigInteger max = new BigInteger("8180385048");
        do {
            BigInteger i = new BigInteger(max.bitLength(), rnd);
            if (i.compareTo(max) <= 0) {
                story.setRemoteId(i);
                break;
            }
        } while (true);
    }
}
