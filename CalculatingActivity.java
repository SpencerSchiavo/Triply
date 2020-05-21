package project.triply.triply_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CalculatingActivity extends AppCompatActivity {

    private ArrayList<String> locations;
    private ProgressBar progressBarTwo;
    private boolean addOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculating);
        Intent intent = getIntent();
        locations = intent.getStringArrayListExtra("locationArray");
        addOrigin = intent.getBooleanExtra("addOrigin", false);
        progressBarTwo = (ProgressBar) findViewById(R.id.progressBar2);
        progressBarTwo.setVisibility(View.VISIBLE);
        progressBarTwo.setProgress(0);
        progressBarTwo.setMax(10);
        FetchDistancesTask newTask = new FetchDistancesTask();
        newTask.execute(formatLocations(locations));
    }

    /**
     * Inner class to handle the asynchronous task of finding the shortest path.
     */
    private class FetchDistancesTask extends AsyncTask<String, Integer, DistanceMatrix> {

        @Override
        protected DistanceMatrix doInBackground(String... params) {
            DistanceMatrix distMat = new DistanceMatrix(locations.size());
            try {
                distMat.generateDistances(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return distMat;
        }

        @Override
        protected void onPostExecute(DistanceMatrix distMat) { //open output activity
            TSPSolver tspSolver = new TSPSolver(distMat, addOrigin);
            String[] resultsArr = tspSolver.findShortestPath(generateIndices(locations), distMat);
            openOutputActivity(resultsArr, locations);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBarTwo.setProgress(values[0]);
        }
    }

    private void openOutputActivity(String[] resultsArr, ArrayList<String> locations) {
        Intent intent = new Intent(this, OutputActivity.class);
        intent.putExtra("resultsArr", resultsArr);
        intent.putExtra("locations", locations);
        intent.putExtra("addOrigin", addOrigin);
        startActivity(intent);
    }

    private int[] generateIndices(ArrayList<String> locations) {
        int[] indices = new int[locations.size()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }
        return indices;
    }

    private String formatLocations(ArrayList<String> listLocs) {
        String returnString;
        String[] newLocs = new String[listLocs.size()];
        for (int i = 0; i < newLocs.length; i++) {
            String temp = listLocs.get(i);
            String noSpaces = temp.replaceAll(" ", "+");
            String finalString = noSpaces.replaceAll(",", "");
            newLocs[i] = finalString;
        }
        returnString = String.join("%7C", newLocs);
        return returnString;
    }
}
