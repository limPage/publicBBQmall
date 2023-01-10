package com.hiponya.bbqmall.entities.product;

public class ProductImageEntity {
    private int index;
    private int reviewIndex;
    private byte[] data;
    private String type;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getReviewIndex() {
        return reviewIndex;
    }

    public void setReviewIndex(int reviewIndex) {
        this.reviewIndex = reviewIndex;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
