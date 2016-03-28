package com.example.pratibha.smartstreetapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


/**
 * Created by Lakshmi on 3/3/2016.
 */
public class PlacesPickerActivity extends AppCompatActivity {
        private static final int PLACE_PICKER_REQUEST = 1;
        private TextView mName;
        private TextView mAddress;
        private TextView mAttributions;
        private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
                new LatLng(40, -100), new LatLng(40, -120));

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_place_picker);
            mName = (TextView) findViewById(R.id.textView);
            mAddress = (TextView) findViewById(R.id.textView2);
            mAttributions = (TextView) findViewById(R.id.textView3);
            Button pickerButton = (Button) findViewById(R.id.pickerButton);
            pickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        PlacePicker.IntentBuilder intentBuilder =
                                new PlacePicker.IntentBuilder();
                        intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                        Intent intent = intentBuilder.build(PlacesPickerActivity.this);
                        startActivityForResult(intent, PLACE_PICKER_REQUEST);

                    } catch (GooglePlayServicesRepairableException
                            | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode,
                                        int resultCode, Intent data) {

            if (requestCode == PLACE_PICKER_REQUEST
                    && resultCode == Activity.RESULT_OK) {

                final Place place = PlacePicker.getPlace(data, this);
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                String attributions = (String) place.getAttributions();
                if (attributions == null) {
                    attributions = "";
                }

                mName.setText(name);
                mAddress.setText(address);
                mAttributions.setText(Html.fromHtml(attributions));

                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

