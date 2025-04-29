package com.example.elogbook;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Integer IFIP_REQUEST_CODE = 1990;
    TextView tvIndex,tvSpecies, tvLocation, tvAccuracy, tvTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button_open_ifip);
//        tvIndex = findViewById(R.id.text_index);
        tvSpecies = findViewById(R.id.text_species);
        tvLocation = findViewById(R.id.text_gps_location);
        tvAccuracy = findViewById(R.id.text_accuracy);
        tvTimestamp = findViewById(R.id.text_timestamp);

        String packageName = "com.intelion.ifip";
        String action = "com.intelion.ifip.ELOGBOOK_ACTION_ACTIVITY";

        button.setOnClickListener(v -> {
            Intent intent = new Intent(action);
            intent.setPackage(packageName);

            // Clear/reset previous data
            tvSpecies.setText(getString(R.string.species_name));
            tvLocation.setText(getString(R.string.gps_location));
            tvAccuracy.setText(getString(R.string.accuracy));
            tvTimestamp.setText(getString(R.string.timestamp));

            if (intent != null) {
                startActivityForResult(intent, IFIP_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IFIP_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("imageData");
                if (bundle != null) {
//                    String id = bundle.getString("id");
                    String species = bundle.getString("species");
                    String location = bundle.getString("location");
                    String accuracy = bundle.getString("accuracy");
                    String timestamp = bundle.getString("timestamp");
//                    tvIndex.setText(tvIndex.getText() + ": " + id);
                    tvSpecies.setText(tvSpecies.getText() + ": " + species);
                    tvLocation.setText(tvLocation.getText() + ": " + location);
                    tvTimestamp.setText(tvTimestamp.getText() + ": " + timestamp);
                    try {
                        double accuracyValue = Double.parseDouble(accuracy);
                        String formattedAccuracy = String.format("%s: %.2f%%", getString(R.string.accuracy), accuracyValue * 100);
                        tvAccuracy.setText(formattedAccuracy);
                    } catch (NumberFormatException e) {
                        tvAccuracy.setText(String.format("%s: %s", getString(R.string.accuracy), accuracy)); // fallback
                    }


                }
            }
        }
    }
}