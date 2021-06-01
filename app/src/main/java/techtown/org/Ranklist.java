package techtown.org;

public class Ranklist {
    String rankingName;
    String rankingPoint;
    String rankingDailyPoint;
    String rankingProfit;
    int size;

    Ranklist(){
        this.rankingName = null;
        this.rankingPoint =null;
        this.rankingDailyPoint = null;
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
    public void getRankingDailyPoint(String rankingDailyPoint){this.rankingDailyPoint = rankingDailyPoint;}
    public String setRankingDailyPoint(){return this.rankingDailyPoint;}
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
