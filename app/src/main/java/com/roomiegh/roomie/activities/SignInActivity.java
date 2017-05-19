package com.roomiegh.roomie.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roomiegh.roomie.R;
import com.roomiegh.roomie.util.PreferenceData;
import com.roomiegh.roomie.util.PushUserUtil;


public class SignInActivity extends AppCompatActivity {
    private EditText etSignInEmail, etSignInPassword, etConfirmPassword;
    private Button btSignIn, btSignUpInstead;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "authentication_tag";
    private TextView tvForgotPassword, tvCreateAccountPrompt;
    boolean remember = false;
    ProgressDialog pdSignin;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        SignInActivity.this.setTitle("Sign In");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        init();

        mAuth = FirebaseAuth.getInstance();

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
            @Override
            public void onClick(View v) {
                pdSignin.setMessage("Signing in...");
                pdSignin.show();
                mAuth.signInWithEmailAndPassword(etSignInEmail.getText().toString(), etSignInPassword.getText().toString())
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pdSignin.dismiss();
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail", task.getException());
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    PreferenceData.setLoggedInUserEmail(getApplicationContext(), etSignInEmail.getText().toString());
                                    PreferenceData.setUserLoggedInStatus(getApplicationContext(), true);

                                    setResult(RESULT_OK);
                                    Toast.makeText(SignInActivity.this, "Welcome",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(etSignInEmail.getText().toString()).matches()) {
                    mAuth.sendPasswordResetEmail(etSignInEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignInActivity.this, "A reset email has been sent to " + etSignInEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignInActivity.this, "Enter your email address and hit 'Forgot Password' again", Toast.LENGTH_LONG).show();
                    etSignInEmail.requestFocus();
                }
            }
        });

        btSignUpInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btSignIn.getVisibility() == View.VISIBLE) {
                    //prepare screen to act as sign up page
                    btSignIn.setVisibility(View.GONE);
                    tvForgotPassword.setVisibility(View.GONE);
                    tvCreateAccountPrompt.setVisibility(View.GONE);
                    etConfirmPassword.setVisibility(View.VISIBLE);
                    etSignInEmail.requestFocus();
                    SignInActivity.this.setTitle("Sign Up");
                } else {
                    //validate password and email and attempt sign up
                    validateAndSignUp();
                }
                /*Intent registerInsteadIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivityForResult(registerInsteadIntent, 200);*/
            }
        });
    }

    private void validateAndSignUp() {
        String pswd = etSignInPassword.getText().toString();
        if (pswd.equals(etConfirmPassword.getText().toString()) && pswd.length() >= 8) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(etSignInEmail.getText().toString()).matches()) {
                pdSignin.setMessage("Signing Up...");
                pdSignin.show();
                mAuth.createUserWithEmailAndPassword(etSignInEmail.getText().toString(), pswd)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pdSignin.dismiss();
                                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    PreferenceData.setLoggedInUserEmail(getApplicationContext(), etSignInEmail.getText().toString());
                                    PreferenceData.setUserLoggedInStatus(getApplicationContext(), true);

                                    setResult(RESULT_OK);
                                    Toast.makeText(SignInActivity.this, "Welcome",
                                            Toast.LENGTH_SHORT).show();
                                    Intent updateProfileIntent = new Intent(getApplicationContext(),RegistrationActivity.class);
                                    Bundle emailBundle = new Bundle();
                                    emailBundle.putString(PushUserUtil.USER_EMAIL,etSignInEmail.getText().toString());
                                    updateProfileIntent.putExtra(PushUserUtil.PUSH_INTENT_KEY,emailBundle);
                                    startActivity(updateProfileIntent);
                                    finish();
                                }

                                // ...
                            }
                        });
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            //successful request, set result ok and finish activity
            setResult(RESULT_OK);
            finish();
        }
    }

    private void init() {
        etSignInEmail = (EditText) findViewById(R.id.etSignInEmail);
        etSignInPassword = (EditText) findViewById(R.id.etSignInPassword);
        btSignIn = (Button) findViewById(R.id.btSignIn);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        btSignUpInstead = (Button) findViewById(R.id.btSignUpInstead);
        pdSignin = new ProgressDialog(SignInActivity.this);

        tvCreateAccountPrompt = (TextView) findViewById(R.id.tvCreateAccountPrompt);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
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
        }else if(id == android.R.id.home){
            if (!(btSignIn.getVisibility() == View.VISIBLE)) {
                //prepare screen to act as sign up page
                btSignIn.setVisibility(View.VISIBLE);
                tvForgotPassword.setVisibility(View.VISIBLE);
                tvCreateAccountPrompt.setVisibility(View.VISIBLE);
                etConfirmPassword.setVisibility(View.GONE);
                etSignInEmail.setText("");
                etSignInPassword.setText("");
                SignInActivity.this.setTitle("Sign UI");
            } else {
                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
