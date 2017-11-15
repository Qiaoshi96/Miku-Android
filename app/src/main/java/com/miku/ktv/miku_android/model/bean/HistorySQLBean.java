package com.miku.ktv.miku_android.model.bean;

/**
 * Created by 焦帆 on 2017/10/24.
 */

public class HistorySQLBean {

    /**
     * _id : 65743
     * songname : 说散就散
     * author : JC
     * link : http://cdn.fibar.cn/shuosanjiusanbanzou.mp3
     * lrc : http://cdn.fibar.cn/shuosanjiusan.kas
     * mode : 1
     *
     */

    private int _id;//主键
    private int songid;
    private String songname;
    private String author;
    private String link;
    private String lrc;
    private int mode;

    public HistorySQLBean() {
    }

    public HistorySQLBean(int _id, int songid, String songname, String author, String link, String lrc, int mode) {
        this._id = _id;
        this.songid = songid;
        this.songname = songname;
        this.author = author;
        this.link = link;
        this.lrc = lrc;
        this.mode = mode;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "HistorySQLBean{" +
                "_id=" + _id +
                ", songid=" + songid +
                ", songname='" + songname + '\'' +
                ", author='" + author + '\'' +
                ", link='" + link + '\'' +
                ", lrc='" + lrc + '\'' +
                ", mode=" + mode +
                '}';
    }
}
