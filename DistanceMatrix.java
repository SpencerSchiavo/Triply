package project.triply.triply_app;


import android.os.Build;
import androidx.annotation.RequiresApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class DistanceMatrix {

    private int[][] distMat;

    public DistanceMatrix() {
        distMat = new int[1][1];
    }

    public DistanceMatrix(int n) {
        distMat = new int[n][n];
    }

    public DistanceMatrix(int[][] distMat) {
        this.distMat = distMat;
    }

    public void setDistMat(int[][] distMat) {
        this.distMat = distMat;
    }

    public int[][] getDistMat() {
        return distMat;
    }

    public int getLength() { return distMat.length; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateDistances(String citiesForUrl) throws JSONException, IOException {
        JSONObject obj = null;
        String urlString = "https://maps.googleapis.com/maps/api/distancematrix/json?" +
                "units=imperial&origins=" + citiesForUrl +
                "&destinations=" + citiesForUrl +
                "&key=" + "AIzaSyD7E6K0slUQAiB6NX_pyTPe8VcTuJ9iaew";
        obj = readJsonFromUrl(urlString);
        JSONArray arr = obj.getJSONArray("rows");
        for (int i = 0; i < arr.length(); i++) {
            JSONArray tempArr = arr.getJSONObject(i).getJSONArray("elements");
            for (int j = 0; j < tempArr.length(); j++) {
                distMat[i][j] = tempArr.getJSONObject(j).getJSONObject("distance")
                                                               .getInt("value");
            }
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,
                    Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public void printDistMat() {
        System.out.println("rows: " + distMat.length + ", columns: " + distMat[0].length);
        for (int i = 0; i < distMat.length; i++) {
            for (int j = 0; j < distMat[0].length; j++) {
                System.out.println(distMat[i][j]);
            }
        }
    }
}
