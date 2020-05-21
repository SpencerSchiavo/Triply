package project.triply.triply_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

public class OutputActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String[] resultsArr;
    private String[] formattedLocations;
    private ArrayList<String> locations;
    private Button mapsButton;
    private boolean addOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Intent intent = getIntent();
        resultsArr = intent.getStringArrayExtra("resultsArr");
        locations = intent.getStringArrayListExtra("locations");
        addOrigin = intent.getBooleanExtra("addOrigin", false);
        formattedLocations = buildAddressList(locations, getIndices(resultsArr));
        mapsButton = (Button) findViewById(R.id.button3);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this, formattedLocations);
        recyclerView.setAdapter(recyclerViewAdapter);
        mapsButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String originFormatted = formattedLocations[0]
                                .replaceAll(" ", "+")
                                .replaceAll(",", "%2C");
                        Uri mapsIntentUri;
                        if (addOrigin) {
                            mapsIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1" +
                                    "&origin=" + originFormatted + "&" +
                                    "destination=" + originFormatted +
                                    "&waypoints=" +
                                    formatDestinationsForMaps(formattedLocations)
                                    + "&travelmode=driving");
                        } else {
                            String destinationFormatted = formattedLocations[
                                                          formattedLocations.length - 1]
                                                          .replaceAll(" ", "+")
                                                          .replaceAll(",", "%2C");
                            mapsIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1" +
                                    "&origin=" + originFormatted + "&" +
                                    "destination=" + destinationFormatted +
                                    "&waypoints=" +
                                    formatDestinationsForMaps(formattedLocations)
                                    + "&travelmode=driving");
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, mapsIntentUri);
                        System.out.println(mapsIntentUri);
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                    }
                });
    }

    private int[] getIndices(String[] resultsArr) {
        String indicesString = resultsArr[0];
        int[] indices = new int[indicesString.length()];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = Integer.parseInt(String.valueOf(indicesString.charAt(i)));
        }
        return indices;
    }

    private String[] buildAddressList(ArrayList<String> locations, int[] indices) {
        String[] convertedLocs = new String[indices.length];
        for (int i = 0; i < indices.length; i++) {
            convertedLocs[i] = locations.get(indices[i]);
        }
        return convertedLocs;
    }

    private String formatDestinationsForMaps(String[] formattedLocations) {
        String returnString;
        String[] newLocs = new String[formattedLocations.length - 2]; // exclude the origin;
        for (int i = 1; i < formattedLocations.length - 1; i++) {
            String temp = formattedLocations[i];
            String noSpaces = temp.replaceAll(" ", "+");
            String finalString = noSpaces.replaceAll(",", "%2C");
            newLocs[i-1] = finalString;
        }
        returnString = String.join("%7C", newLocs);
        return returnString;
    }
}

