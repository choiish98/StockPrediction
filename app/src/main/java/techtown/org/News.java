package techtown.org;

public class News {
    String title;
    String summary;
    String date;
    String news;
    String img;

    News(String title, String summary, String date, String news, String img) {
        this.title = title;
        this.summary = summary;
        this.date = date;
        this.news = news;
        this.img = img;
    }

    News() {
        this.title = null;
        this.summary = null;
        this.date = null;
        this.news = null;
        this.img = null;
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
    public void setNews(String date) { this.date = date; }
    public String getNews() { return this.date; }
    public void setImg(String img) { this.img = img; }
    public String getImg() { return this.img; }

    public int size() {
        return 10;
    }
}