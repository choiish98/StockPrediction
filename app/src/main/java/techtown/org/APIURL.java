package techtown.org;

public class APIURL {
    private static String apiURL;

    APIURL() {
        apiURL = "http://stockpredict.kr";
    }

    public String getApiURL() {
        return this.apiURL;
    }
}
