package project.triply.triply_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import java.util.ArrayList;

public class ExtraActivity extends AppCompatActivity {

    private Button mButton;
    private PlacesAutocompleteTextView mEditDestFive;
    private PlacesAutocompleteTextView mEditDestSix;
    private PlacesAutocompleteTextView mEditDestSeven;
    private PlacesAutocompleteTextView mEditDestEight;
    private PlacesAutocompleteTextView mEditDestNine;
    private ArrayList<String> locOutputExtra;
    private boolean addOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        Intent intent = getIntent();
        addOrigin = intent.getBooleanExtra("addOrigin", false);
        mButton = (Button)findViewById(R.id.button2);
        mEditDestFive = (PlacesAutocompleteTextView)findViewById(R.id.Dest5);
        mEditDestSix = (PlacesAutocompleteTextView)findViewById(R.id.Dest6);
        mEditDestSeven = (PlacesAutocompleteTextView)findViewById(R.id.Dest7);
        mEditDestEight = (PlacesAutocompleteTextView)findViewById(R.id.Dest8);
        mEditDestNine = (PlacesAutocompleteTextView)findViewById(R.id.Dest9);
        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        locOutputExtra = new ArrayList<>();
                        Intent intent = getIntent();
                        ArrayList<String> originalLocations = intent.getStringArrayListExtra("locationArray");
                        locOutputExtra.addAll(originalLocations);
                        locOutputExtra.add("");
                        locOutputExtra.add("");
                        locOutputExtra.add("");
                        locOutputExtra.add("");
                        locOutputExtra.add("");
                        if (!mEditDestFive.getText().toString().equals("")) {
                            locOutputExtra.set(5, mEditDestFive.getText().toString());
                        }
                        if (!mEditDestSix.getText().toString().equals("")) {
                            locOutputExtra.set(6, mEditDestSix.getText().toString());
                        }
                        if (!mEditDestSeven.getText().toString().equals("")) {
                            locOutputExtra.set(7, mEditDestSeven.getText().toString());
                        }
                        if (!mEditDestEight.getText().toString().equals("")) {
                            locOutputExtra.set(8, mEditDestEight.getText().toString());
                        }
                        if (!mEditDestNine.getText().toString().equals("")) {
                            locOutputExtra.set(9, mEditDestNine.getText().toString());
                        }
                        openCalculatingActivity(deleteBlanks(locOutputExtra));
                    }
                });
    }

    private void openCalculatingActivity(ArrayList<String> locOutputExtra) {
        Intent intent = new Intent(this, CalculatingActivity.class);
        intent.putExtra("locationArray", locOutputExtra);
        intent.putExtra("addOrigin", addOrigin);
        startActivity(intent);
    }

    private ArrayList<String> deleteBlanks(ArrayList<String> locOutput) {
        int i = 0;
        while (i < locOutput.size()) {
            if (locOutput.get(i).equals("")) {
                locOutput.remove(i);
                i--;
            }
            i++;
        }
        return locOutput;
    }
}
