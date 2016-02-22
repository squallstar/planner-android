package it.squallstar.planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.squallstar.planner.models.Trip;

public class AddTripDetailsActivity extends AppCompatActivity {
    @Bind(R.id.createButton)
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_details);
        ButterKnife.bind(this);

        final String tripName = getIntent().getStringExtra("tripName");
        getSupportActionBar().setTitle(tripName);


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trip trip = new Trip();
                trip.name = tripName;
                trip.save();

                Intent main = new Intent(getApplicationContext(), ProjectsActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(main);
                finish();
            }
        });
    }
}
