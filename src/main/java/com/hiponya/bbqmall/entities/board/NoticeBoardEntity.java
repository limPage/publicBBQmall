package com.hiponya.bbqmall.entities.board;

public class NoticeBoardEntity {

    private int id;
    private String text;


    public int getId() {
        return id;
    }

    public NoticeBoardEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public NoticeBoardEntity setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
