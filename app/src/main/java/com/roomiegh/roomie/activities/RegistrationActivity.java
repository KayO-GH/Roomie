package com.roomiegh.roomie.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.roomiegh.roomie.R;
import com.roomiegh.roomie.models.User;
import com.roomiegh.roomie.util.PushUserUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class RegistrationActivity extends AppCompatActivity {
    private static final int PICK_FROM_STORAGE = 100;
    private static final int TAKE_IMAGE = 200;
    private EditText etRegFirstName, etRegLastName, etRegPhone, etRegEmail, etRegProgramme, etRegNOK, etRegNOKPhone;
    private Button btRegSave, btRegCancel;
    private CheckBox cbRegMale, cbRegFemale;
    private boolean requiredFilled;
    private String gender;
    private User user;
    //private SignInManager signInManager;
    private String email;
    private ImageView ivRegImage;
    private ProgressBar pbUploadingPic;

    private StorageReference mStorageRef;
    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Bundle receivedInfo = getIntent().getBundleExtra(PushUserUtil.PUSH_INTENT_KEY);
        email = receivedInfo.getString(PushUserUtil.USER_EMAIL);

        if (!email.equals("")) {
            etRegEmail.setText(email);
            etRegEmail.setEnabled(false);
        }

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

        //Save profile button
        btRegSave.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {

                if (checkRequiredFilled()) {

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
                        //putting data into database at required tables
                        //TODO make POST request to

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
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please fill required fields, shown with '*'", Toast.LENGTH_LONG).show();
                }
            }
        });

        ivRegImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);

// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Change Picture");
                builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Camera button
                        //TODO take picture and upload
                        takePicture();
                    }
                });
                builder.setNegativeButton("Upload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO upload picture from storage
                        Intent gallery =
                                new Intent(Intent.ACTION_PICK,
                                        //android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, PICK_FROM_STORAGE);
                    }
                });
// 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, TAKE_IMAGE);
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            pbUploadingPic.setVisibility(View.VISIBLE);
            Uri fileFromStorage = data.getData();
            if (requestCode == PICK_FROM_STORAGE) {
                uploadAndSet(fileFromStorage);
            } else if (requestCode == TAKE_IMAGE) {
                uploadAndSet(file);
            }
        } else {
            Toast.makeText(RegistrationActivity.this, "Problem getting image", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadAndSet(final Uri fileUri) {
        StorageReference profileRef = mStorageRef.child("profile_pic/rivers.jpg");

        profileRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //TODO upload the download url as photopath for this user
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Picasso.with(RegistrationActivity.this).load(fileUri).fit().centerCrop().into(ivRegImage);
                        pbUploadingPic.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(RegistrationActivity.this, "Usuccessful upload", Toast.LENGTH_SHORT).show();
                        pbUploadingPic.setVisibility(View.GONE);
                    }
                });
    }

    private boolean checkRequiredFilled() {
        if ((etRegFirstName.getText().length() == 0) ||
                (etRegLastName.getText().length() == 0) ||
                (!cbRegFemale.isChecked() && !cbRegMale.isChecked()) ||
                (etRegPhone.getText().length() == 0) ||
                (etRegProgramme.getText().length() == 0)) {
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
        btRegSave = (Button) findViewById(R.id.btEditProfileSave);
        btRegCancel = (Button) findViewById(R.id.btEditProfileCancel);
        cbRegMale = (CheckBox) findViewById(R.id.cbRegMale);
        cbRegFemale = (CheckBox) findViewById(R.id.cbRegFemale);
        user = new User();
        etRegEmail = (EditText) findViewById(R.id.etEditProfileEmail);
        ivRegImage = (ImageView) findViewById(R.id.ivEditProfilePic);
        pbUploadingPic = (ProgressBar) findViewById(R.id.pbUploadingProfilePic);
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
