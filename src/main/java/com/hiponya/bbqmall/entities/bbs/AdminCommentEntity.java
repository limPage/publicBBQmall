package com.hiponya.bbqmall.entities.bbs;

import java.util.Date;

public class AdminCommentEntity {


    private int index;
    private String boardId;
    private String content;
    private Date writtenOn;
    private Date modifiedOn;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(Date writtenOn) {
        this.writtenOn = writtenOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
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
