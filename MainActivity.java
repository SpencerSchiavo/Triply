package project.triply.triply_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private Button addAnotherButton;
    private PlacesAutocompleteTextView mEditOrigin;
    private PlacesAutocompleteTextView mEditDestOne;
    private PlacesAutocompleteTextView mEditDestTwo;
    private PlacesAutocompleteTextView mEditDestThree;
    private PlacesAutocompleteTextView mEditDestFour;
    private Switch originSwitch;
    private ArrayList<String> locOutput;
    private boolean addOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        addAnotherButton = (Button) findViewById(R.id.AddAnother);
        mEditOrigin = (PlacesAutocompleteTextView) findViewById(R.id.Origin);
        mEditDestOne = (PlacesAutocompleteTextView) findViewById(R.id.Dest1);
        mEditDestTwo = (PlacesAutocompleteTextView) findViewById(R.id.Dest2);
        mEditDestThree = (PlacesAutocompleteTextView) findViewById(R.id.Dest3);
        mEditDestFour = (PlacesAutocompleteTextView) findViewById(R.id.Dest4);
        originSwitch = (Switch) findViewById(R.id.switch1);
        addOrigin = false;
        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        locOutput = new ArrayList<>();
                        locOutput.add("");
                        locOutput.add("");
                        locOutput.add("");
                        locOutput.add("");
                        locOutput.add("");
                        if (!mEditOrigin.getText().toString().equals("")) {
                            locOutput.set(0, mEditOrigin.getText().toString());
                        }
                        if (!mEditDestOne.getText().toString().equals("")) {
                            locOutput.set(1, mEditDestOne.getText().toString());
                        }
                        if (!mEditDestTwo.getText().toString().equals("")) {
                            locOutput.set(2, mEditDestTwo.getText().toString());
                        }
                        if (!mEditDestThree.getText().toString().equals("")) {
                            locOutput.set(3, mEditDestThree.getText().toString());
                        }
                        if (!mEditDestFour.getText().toString().equals("")) {
                            locOutput.set(4, mEditDestFour.getText().toString());
                        }
                        if (locOutput.size() > 1 && !mEditOrigin.getText().toString().equals("")) {
                            openCalculatingActivity(deleteBlanks(locOutput));
                        }
                    }
                });
        addAnotherButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        locOutput = new ArrayList<>();
                        locOutput.add("");
                        locOutput.add("");
                        locOutput.add("");
                        locOutput.add("");
                        locOutput.add("");
                        if (!mEditOrigin.getText().toString().equals("")) {
                            locOutput.set(0, mEditOrigin.getText().toString());
                        }
                        if (!mEditDestOne.getText().toString().equals("")) {
                            locOutput.set(1, mEditDestOne.getText().toString());
                        }
                        if (!mEditDestTwo.getText().toString().equals("")) {
                            locOutput.set(2, mEditDestTwo.getText().toString());
                        }
                        if (!mEditDestThree.getText().toString().equals("")) {
                            locOutput.set(3, mEditDestThree.getText().toString());
                        }
                        if (!mEditDestFour.getText().toString().equals("")) {
                            locOutput.set(4, mEditDestFour.getText().toString());
                        }
                        if (findNumEntries(locOutput) == 5) {
                            openExtraActivity(locOutput);
                        }
                    }
                });
        originSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addOrigin = true;
                } else {
                    addOrigin = false;
                }
            }
        });
    }

    private void openCalculatingActivity(ArrayList<String> locOutput) {
        Intent intent = new Intent(this, CalculatingActivity.class);
        intent.putExtra("locationArray", locOutput);
        intent.putExtra("addOrigin", addOrigin);
        System.out.println("addOriginMain: " + addOrigin);
        startActivity(intent);
    }

    private void openExtraActivity(ArrayList<String> locOutput) {
        Intent intent = new Intent(this, ExtraActivity.class);
        intent.putExtra("locationArray", locOutput);
        intent.putExtra("addOrigin", addOrigin);
        startActivity(intent);
    }

    private int findNumEntries(ArrayList<String> locOutput) {
        int numEntries = 0;
        for (int i = 0; i < locOutput.size(); i++) {
            if (!locOutput.get(i).equals("")) {
                numEntries += 1;
            }
        }
        return numEntries;
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
