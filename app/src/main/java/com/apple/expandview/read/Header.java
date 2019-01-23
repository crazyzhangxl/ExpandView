package com.apple.expandview.read;

/**
 * @author crazyZhangxl on 2019/1/20.
 * Describe:
 */
public class Header {
    private String title;

    public Header(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Header){
            Header header = (Header)obj;
            return header.title.equals(title);
        }
        return false;
    }

    public Header copy(){
        return new Header(title);
    }
}
