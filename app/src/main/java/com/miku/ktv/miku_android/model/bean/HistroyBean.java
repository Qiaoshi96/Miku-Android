package com.miku.ktv.miku_android.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 焦帆 on 2017/10/16.
 */

public class HistroyBean implements Parcelable {

    /**
     * id : 65743
     * lrc : http://cdn.fibar.cn/shuosanjiusan.kas
     * name : 说散就散
     * author : JC
     * original : http://cdn.fibar.cn/shuosanjiusan.mp3
     * link : http://cdn.fibar.cn/shuosanjiusanbanzou.mp3
     *
     */

    private int id;
    private String lrc;
    private String name;
    private String author;
    private String original;
    private String link;

    public HistroyBean() {
    }

    public HistroyBean(int id, String lrc, String name, String author, String original, String link) {
        this.id = id;
        this.lrc = lrc;
        this.name = name;
        this.author = author;
        this.original = original;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "HistroyBean{" +
                "id=" + id +
                ", lrc='" + lrc + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", original='" + original + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<HistroyBean> CREATOR = new Creator<HistroyBean>() {

        @Override
        public HistroyBean createFromParcel(Parcel source) {
            // TODO Auto-generated method stub

            HistroyBean his = new HistroyBean();
            his.id = source.readInt();
            his.lrc = source.readString();
            his.name = source.readString();
            his.author = source.readString();
            his.original = source.readString();
            his.link = source.readString();
            return his;

        }

        @Override
        public HistroyBean[] newArray(int size) {
            // TODO Auto-generated method stub
            return new HistroyBean[size];
        }
    };

    /** 将实体类数据写入Parcel */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(lrc);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(original);
        dest.writeString(link);
    }
}
