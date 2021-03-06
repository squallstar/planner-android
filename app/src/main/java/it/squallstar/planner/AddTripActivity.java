package it.squallstar.planner;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.squallstar.planner.models.Trip;

public class AddTripActivity extends AppCompatActivity {
    @Bind(R.id.tripName) EditText tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ButterKnife.bind(this);

        if(tripName.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

        final int mainColor = Color.parseColor("#FFFFFF");
        final int altColor = getResources().getColor(R.color.colorPrimary);

        tripName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tripName.setTextColor(s.length() > 0 ? mainColor : altColor);
                tripName.getRootView().setBackgroundColor(s.length() > 0 ? altColor : mainColor);
            }
        });

        tripName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    Intent details = new Intent(getApplicationContext(), AddTripDetailsActivity.class);
                    details.putExtra("tripName", tripName.getText().toString());

                    startActivity(details);
                    return true;
                }
                return false;
            }
        });
    }
}
