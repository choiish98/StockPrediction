package techtown.org;

public class Ranklist {
    String rankingName;
    String rankingPoint;
    String rankingProfit;
    int size;

    Ranklist(){
        this.rankingName = null;
        this.rankingPoint =null;
        this.rankingProfit = null;
        this.size = 0;
    }

    public void setRankingName(String rankingName){
        this.rankingName = rankingName;
    }
    public String getRankingName(){
        return this.rankingName;
    }
    public void setRankingPoint(String rankingPoint){
        this.rankingPoint = rankingPoint;
    }
    public String getRankingPoint(){
        return this.rankingPoint;
    }
    public void setRankingProfit(String rankingProfit){
        this.rankingProfit = rankingProfit;
    }
    public String getRankingProfit(){
        return this.rankingProfit;
    }
    public void setSize(int size) { this.size  = size;}
    public int size() {
        return size;
    }
}
