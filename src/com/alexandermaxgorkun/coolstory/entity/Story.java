package com.alexandermaxgorkun.coolstory.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Gorkun mindkilleralexs@gmail.com
 */
public class Story {
    private BigInteger remoteId;

    private String title;

    private Date created;

    private List<Paragraph> paragraphs = new ArrayList<Paragraph>();

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

    public BigInteger getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(BigInteger remoteId) {
        this.remoteId = remoteId;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }
}
