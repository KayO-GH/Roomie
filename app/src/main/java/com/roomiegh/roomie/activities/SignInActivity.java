package com.roomiegh.roomie.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roomiegh.roomie.R;
import com.roomiegh.roomie.database.SignInManager;
import com.roomiegh.roomie.models.SignIn;
import com.roomiegh.roomie.util.PushUserUtil;


public class SignInActivity extends ActionBarActivity {
    private EditText etSignInEmail, etSignInPassword;
    private CheckBox cbRemember;
    private Button btSignIn;
    private SignIn signIn, signedIn;
    private SignInManager signInManager;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "authentication_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                //check email against password in signIn table
                signIn.setEmail(etSignInEmail.getText()+"");
                signedIn = signInManager.getSignIn(signIn);

                if(signedIn!=null){//if a sign in session matching the email address given is actually found

                        if ((signedIn.getPassword() + "").equals(etSignInPassword.getText() + "")) {
                            Intent proceedIntent = new Intent(getApplicationContext(), MainActivity.class);
                            Bundle pushUser = new Bundle();
                            pushUser.putString(PushUserUtil.USER_EMAIL,etSignInEmail.getText()+"");

                            proceedIntent.putExtra(PushUserUtil.PUSH_INTENT_KEY,pushUser);

                            startActivity(proceedIntent);
                            finishAffinity();
                        } else
                            Toast.makeText(getApplicationContext(),
                                    "Wrong Password",Toast.LENGTH_SHORT).show();


                }else
                    Toast.makeText(getApplicationContext(),
                            "No such email address",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void init() {
        etSignInEmail = (EditText) findViewById(R.id.etSignInEmail);
        etSignInPassword = (EditText) findViewById(R.id.etSignInPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        btSignIn = (Button) findViewById(R.id.btSignIn);
        signIn = new SignIn();
        signedIn = new SignIn();
        signInManager = new SignInManager(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in_screen, menu);
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
