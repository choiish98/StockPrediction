package techtown.org;

public class Stock {
    String stock_name;
    String amount;
    String gae;
    int size;

    Stock() {
        this.stock_name = null;
        this.amount = null;
        this.gae = "개";
        this.size = 0;
    }

    public void setStock_name(String stock_name) { this.stock_name = stock_name; }
    public String getStock_name() {
        return this.stock_name;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getAmount() { return this.amount; }
    public void setGae(String amount) { this.amount = amount; }
    public String getGae() { return this.gae; }
    public void setSize(int size) { this.size = size;}
    public int size() {
        return size;
    }
}