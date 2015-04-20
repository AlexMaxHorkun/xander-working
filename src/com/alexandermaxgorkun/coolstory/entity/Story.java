package com.alexandermaxgorkun.coolstory.entity;

import java.util.Date;

/**
 * @author Alexander Gorkun mindkilleralexs@gmail.com
 */
public class Story {
    private String title;

    private Date created;

    public Story(String title) {
        setTitle(title);
        setCreated(new Date());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String toString() {
        return getTitle();
    }
}
