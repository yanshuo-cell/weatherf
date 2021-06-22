package com.taimin.weatherforecast.bean;

import java.io.Serializable;

public class IndexBean implements Serializable {
    String title;
    String level;
    String desc;

    public IndexBean(String title, String level, String desc) {
        this.title = title;
        this.level = level;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
