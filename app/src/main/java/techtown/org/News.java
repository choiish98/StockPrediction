package techtown.org;

public class News {
    String title;
    String summary;
    String date;
    String press;
    String img;
    String link;

    News() {
        this.title = null;
        this.summary = null;
        this.date = null;
        this.press = null;
        this.img = null;
        this.link = null;
    }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() {
        return this.title;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getSummary() {
        return this.summary;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }
    public void setPress(String press) { this.press = press; }
    public String getPress() { return this.press; }
    public void setImg(String img) { this.img = img; }
    public String getImg() { return this.img; }
    public void setLink(String link) { this.link = link; }
    public String getLink() { return this.link; }
    public int size() {
        return 5;
    }
}