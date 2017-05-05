package com.roomiegh.roomie.activities.browseActivities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import com.roomiegh.roomie.R;

/*Prototyping by price page - Friday, 05/05/2017*/
public class ByPrice extends ActionBarActivity {
    private Toolbar toolbar;
    private SeekBar sbMax, sbMin;
    private EditText etMaxPrice, etMinPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_price);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        init();

        //max price seekbar
        sbMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etMaxPrice.setText(progress + "");
                queryHostelsByPrice(sbMax.getProgress(), sbMin.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //min price seekbar
        sbMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                etMinPrice.setText(progress + "");
                queryHostelsByPrice(sbMax.getProgress(), sbMin.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        etMaxPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(etMaxPrice.getText().toString().equals("")))
                    sbMax.setProgress(Integer.valueOf(etMaxPrice.getText().toString()));
                else
                    etMaxPrice.setText(0+"");
                etMaxPrice.setSelection(etMaxPrice.getText().length());
                queryHostelsByPrice(sbMax.getProgress(), sbMin.getProgress());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMinPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(etMinPrice.getText().toString().equals("")))
                    sbMin.setProgress(Integer.valueOf(etMinPrice.getText().toString()));
                else
                    etMinPrice.setText(0+"");
                etMinPrice.setSelection(etMinPrice.getText().length());
                queryHostelsByPrice(sbMax.getProgress(), sbMin.getProgress());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void queryHostelsByPrice(int maxPrice, int minPrice) {
        // TODO: 05/05/2017 Insert code to query the API
    }

    private void init() {
        sbMax = (SeekBar) findViewById(R.id.sbMax);
        sbMin = (SeekBar) findViewById(R.id.sbMin);
        etMaxPrice = (EditText) findViewById(R.id.etMaxPrice);
        etMinPrice = (EditText) findViewById(R.id.etMinPrice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
