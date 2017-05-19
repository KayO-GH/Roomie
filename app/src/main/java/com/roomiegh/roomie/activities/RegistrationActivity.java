package com.roomiegh.roomie.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.roomiegh.roomie.R;
import com.roomiegh.roomie.models.User;
import com.roomiegh.roomie.util.PushUserUtil;


public class RegistrationActivity extends AppCompatActivity {
    private EditText etRegFirstName, etRegLastName,
            etRegPhone, etRegYear, etRegMonth, etRegDay, etRegRefNo;
    private Button btRegProceed, btRegCancel;
    private CheckBox cbRegMale, cbRegFemale;
    private boolean requiredFilled;
    private String gender;
    private User user;
    //private SignInManager signInManager;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();

        Bundle receivedInfo = getIntent().getBundleExtra(PushUserUtil.PUSH_INTENT_KEY);
        email = receivedInfo.getString(PushUserUtil.USER_EMAIL);
        password = receivedInfo.getString("password");

        //making CheckBoxes mutually exclusive
        cbRegMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbRegFemale.setSelected(false);
                cbRegFemale.setChecked(false);
            }
        });
        cbRegFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbRegMale.setSelected(false);
                cbRegMale.setChecked(false);
            }
        });

        //cancel button
        btRegCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //proceed button
        btRegProceed.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {

                if (checkRequiredFilled()) {
                    //user.setfName();


                    if (etRegRefNo.getText().length() == 8) {

                        if (cbRegFemale.isChecked() || cbRegMale.isChecked()) {
                            //check gender
                            if (cbRegMale.isChecked())
                                gender = "male";
                            else
                                gender = "female";

                            //assigning data to required variables for user
                            user.setfName(etRegFirstName.getText().toString());
                            user.setlName(etRegLastName.getText().toString());
                            user.setEmail(email);
                            user.setPhone(etRegPhone.getText().toString());
                            user.setGender(gender);
                            user.setRefNo(Integer.parseInt(etRegRefNo.getText().toString()));
                            //putting data into database at required tables

                            //Send user email to necessary activities and fragments
                            Intent proceedIntent = new Intent(getApplicationContext(), MainActivity.class);
                            Bundle pushUser = new Bundle();
                            pushUser.putString(PushUserUtil.USER_EMAIL, email);

                            proceedIntent.putExtra(PushUserUtil.PUSH_INTENT_KEY, pushUser);

                            startActivity(proceedIntent);
                            finishAffinity();


                        } else
                            Toast.makeText(getApplicationContext(),
                                    "Please select your gender", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(),
                                "There seems to be a problem with your reference number", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please fill required fields, shown with '*'", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean checkRequiredFilled() {
        if (etRegFirstName.getText().length() == 0 ||
                etRegLastName.getText().length() == 0 ||
                etRegRefNo.getText().length() == 0) {
            requiredFilled = false;
        } else {
            requiredFilled = true;
        }
        return requiredFilled;
    }

    private void init() {
        etRegFirstName = (EditText) findViewById(R.id.etRegFirstName);
        etRegLastName = (EditText) findViewById(R.id.etRegLastName);
        etRegPhone = (EditText) findViewById(R.id.etRegPhone);
        etRegYear = (EditText) findViewById(R.id.etRegYear);
        etRegMonth = (EditText) findViewById(R.id.etRegMonth);
        etRegDay = (EditText) findViewById(R.id.etRegDay);
        etRegRefNo = (EditText) findViewById(R.id.etRegRefNo);
        btRegProceed = (Button) findViewById(R.id.btRegProceed);
        btRegCancel = (Button) findViewById(R.id.btRegCancel);
        cbRegMale = (CheckBox) findViewById(R.id.cbRegMale);
        cbRegFemale = (CheckBox) findViewById(R.id.cbRegFemale);
        user = new User();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reg_screen, menu);
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
