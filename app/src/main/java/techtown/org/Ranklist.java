package techtown.org;

import android.graphics.drawable.Drawable;

public class Ranklist {
    Drawable rankImg;
    String rank;
    String rankingName;
    String rankingPoint;
    String rankingDailyPoint;
    String rankingTotalPoint;
    String rankingProfit;
    int size;

    Ranklist(){
        this.rank = null;
        this.rankImg = null;
        this.rankingName = null;
        this.rankingPoint =null;
        this.rankingDailyPoint = null;
        this.rankingTotalPoint = null;
        this.rankingProfit = null;
        this.size = 0;
    }

    public void setRank(String rank){this.rank = rank;}
    public String getRank(){return this.rank;}

    public void setRankingName(String rankingName){
        this.rankingName = rankingName;
    }
    public String getRankingName(){
        return this.rankingName;
    }

    public Drawable getRankImg(){return this.rankImg;}
    public void setRankImg(Drawable rankImg){this.rankImg = rankImg;}

    public void setRankingPoint(String rankingPoint){
        this.rankingPoint= rankingPoint;
    }

    public String getRankingPoint(){
        return this.rankingPoint;
    }

    public void setRankingDailyPoint(String rankingDailyPoint){this.rankingDailyPoint = rankingDailyPoint;}
    public String getRankingDailyPoint(){return this.rankingDailyPoint;}

    public void setRankingTotalPoint(String rankingTotalPoint){this.rankingTotalPoint = rankingTotalPoint;}
    public String getRankingTotalPoint(){return this.rankingTotalPoint;}

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