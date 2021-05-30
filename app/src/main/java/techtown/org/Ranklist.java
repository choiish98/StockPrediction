package techtown.org;

public class Ranklist {
    String rank;
    String rankingName;
    String rankingPoint;
    String rankingProfit;
    String rankImg;

    Ranklist(){
        this.rank = null;
        this.rankingName = null;
        this.rankingPoint =null;
        this.rankingProfit = null;
        this.rankImg = null;
    }

    //NumberFormat.getInstance(Locale.getDefault()).format(Int형 변수);
    public void setRank(String rank){
        this.rank = rank;
    }
    public String getRank(){
        return this.rank;
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
    public void setRankImg(String rankImg){
        this.rankImg = rankImg;
    }
    public String getRankImg(){
        return this.rankImg;
    }
    public int size() {
        return 10;
    }
}
