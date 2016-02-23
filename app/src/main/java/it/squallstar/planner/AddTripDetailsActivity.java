package it.squallstar.planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.squallstar.planner.helpers.DatePickerFragment;
import it.squallstar.planner.models.Trip;

public class AddTripDetailsActivity extends AppCompatActivity {
    @Bind(R.id.createButton)
    Button createButton;

    @Bind(R.id.dateFrom)
    Button dateFrom;

    @Bind(R.id.dateTo)
    Button dateTo;

    private GregorianCalendar current_start_date;
    private GregorianCalendar current_end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_details);
        ButterKnife.bind(this);

        final String tripName = getIntent().getStringExtra("tripName");
        getSupportActionBar().setTitle(tripName);

        final Trip trip = new Trip();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trip.name = tripName;
                trip.save();

                Intent main = new Intent(getApplicationContext(), ProjectsActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(main);
                finish();
            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();

        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                trip.location = place.getName().toString();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });
    }

    public void showDatePickerDialog(final View v) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getFragmentManager(), "datePicker");

        datePickerFragment.setListener(new DatePickerFragment.OnDateListener() {
            @Override
            public void onDateSet(int year, int month, int day) {
                GregorianCalendar calendar = new GregorianCalendar(year, month, day);

                if (v.getId() == R.id.dateFrom) {
                    current_start_date = calendar;
                    if (current_end_date != null) {
                        if (current_end_date.compareTo(calendar) == -1) {
                            return;
                        }
                    }
                } else {
                    if (current_start_date != null) {
                        if (current_start_date.compareTo(calendar) == 1) {
                            return;
                        }
                    }
                    current_end_date = calendar;
                }

                updateDateFilterUI();
            }
        });
    }

    private void updateDateFilterUI() {

        if (current_start_date != null) {
            dateFrom.setText(formatDate(current_start_date));
        } else {
            dateFrom.setText("To");
        }

        if (current_end_date != null) {
            dateTo.setText(formatDate(current_end_date));
        } else {
            dateTo.setText("-");
        }
    }

    private String formatDate(GregorianCalendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy");
        return format.format(calendar.getTime());
    }
}
