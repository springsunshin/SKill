package com.skill.dto;

public class SkillUrl {

    //enable为true，代表id有对应的商品
    private boolean enable;
    private String md5;
    private Integer id;
    private long now;
    private long start;
    private long end;

    public SkillUrl() {}

    public SkillUrl(boolean enable, Integer id) {
        this.enable = enable;
        this.id = id;
    }

    public SkillUrl(boolean enable, Integer id, long now, long start, long end) {
        this.enable = enable;
        this.id = id;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public SkillUrl(boolean enable, String md5, Integer id, long now, long start, long end) {
        this.enable = enable;
        this.md5 = md5;
        this.id = id;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public SkillUrl(String md5, Integer id, long now, long start, long end) {
        this.md5 = md5;
        this.id = id;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "SkillUrl{" +
                "enable=" + enable +
                ", md5='" + md5 + '\'' +
                ", id=" + id +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
