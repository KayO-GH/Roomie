package com.roomiegh.roomie.activities.myProfileActivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomiegh.roomie.R;
import com.roomiegh.roomie.database.GuardianManager;
import com.roomiegh.roomie.database.TenantManager;
import com.roomiegh.roomie.models.Guardian;
import com.roomiegh.roomie.models.Tenant;
import com.roomiegh.roomie.util.CameraUtil;
import com.roomiegh.roomie.util.PushUserUtil;

public class EditProfileActivity extends AppCompatActivity {
    private Button btEditProfileSave;
    private ImageView ivEditProfilePic;
    private EditText etEditProfilePhone, etEditProfilePhone2, etEditProfileEmail,
            etEditProfileInstitution, etEditProfileProgramme, etEditProfileYear, etEditProfileMonth,
            etEditProfileDay, etEditProfileGuardName, etEditProfileGuardPhone;
    private TextView tvEditProfileRefNo, tvEditProfileName, tvEditProfileHostel, tvEditProfileRoomNum;
    private byte[] imageData = new byte[1000];
    private Bitmap photo;
    private Tenant currentTenant, displayedCurrentTenant;
    private Guardian currentGuardian, displayedCurrentGuardian;
    private TenantManager tenantManager;
    private GuardianManager guardianManager;
    private boolean isUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //receive pushed user info
        Bundle receivedInfo = getIntent().getBundleExtra(PushUserUtil.PUSH_INTENT_KEY);
        String currentUserEmail = receivedInfo.getString(PushUserUtil.USER_EMAIL);

        init();

        currentTenant.setEmail(currentUserEmail);
        displayedCurrentTenant =
                new TenantManager(getApplicationContext()).getTenantByEmail(currentTenant);

        currentGuardian.setRefNo(displayedCurrentTenant.getRefNo());
        displayedCurrentGuardian =
                new GuardianManager(getApplicationContext()).getGuardian(currentGuardian);

        //setting profile text for the tenant
        if (displayedCurrentTenant != null) {
            if (displayedCurrentTenant.getPhoto() != null) {
                ivEditProfilePic.setImageBitmap(CameraUtil.convertByteArrayToPhoto(displayedCurrentTenant.getPhoto()));
            }
            tvEditProfileName.setText(displayedCurrentTenant.getfName() + " " + displayedCurrentTenant.getlName());
            tvEditProfileRefNo.setText(displayedCurrentTenant.getRefNo() + "");
            etEditProfileEmail.setText(displayedCurrentTenant.getEmail());
            etEditProfilePhone.setText(displayedCurrentTenant.getPhone());
            if (displayedCurrentTenant.getPhone2() != null) {
                etEditProfilePhone2.setText(displayedCurrentTenant.getPhone2());
            }
            StringBuffer dateCutter = new StringBuffer(displayedCurrentTenant.getDob());
            etEditProfileYear.setText(dateCutter.subSequence(0, 4));
            etEditProfileMonth.setText(dateCutter.subSequence(5, 7));
            etEditProfileDay.setText(dateCutter.subSequence(8, 10));

        } else
            Toast.makeText(getApplicationContext(), "Profile not found", Toast.LENGTH_SHORT).show();

        //setting profile text for the guardian
        if (displayedCurrentGuardian != null) {
            etEditProfileGuardPhone.setText(displayedCurrentGuardian.getGuardPhone());
            etEditProfileGuardName.setText(displayedCurrentGuardian.getGuardFName() + " " + displayedCurrentGuardian.getGuardLName());
        } else
            Toast.makeText(getApplicationContext(), "Guardian not found", Toast.LENGTH_SHORT).show();

        //Toast.makeText(this,currentUserEmail,Toast.LENGTH_SHORT).show();

        ivEditProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CameraUtil.CAMERA_REQUEST_CODE);

            }
        });
        btEditProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTenant.setPhoto(imageData);
                currentTenant.setfName(displayedCurrentTenant.getfName());
                currentTenant.setlName(displayedCurrentTenant.getlName());
                currentTenant.setRefNo(displayedCurrentTenant.getRefNo());

                currentTenant.setPhone(etEditProfilePhone.getText() + "");
                currentTenant.setPhone2(etEditProfilePhone2.getText() + "");
                currentTenant.setEmail(etEditProfileEmail.getText().toString());
                currentTenant.setDob(
                        etEditProfileYear.getText() + "-" + etEditProfileMonth.getText()
                                + "-" + etEditProfileDay.getText());
                //saving into db
                if (tenantManager.updateTenant(currentTenant)) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    isUpdated = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG);
                }
                Intent sendStatus = new Intent();
                sendStatus.putExtra("bool",isUpdated);
                setResult(RESULT_OK, sendStatus);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtil.CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivEditProfilePic.setImageBitmap(photo);
            imageData = CameraUtil.convertPhotoToByteArray(photo);
        }
    }

    private void init() {
        ivEditProfilePic = (ImageView) findViewById(R.id.ivEditProfilePic);

        tvEditProfileRefNo = (TextView) findViewById(R.id.tvEditProfileRefNo);
        tvEditProfileName = (TextView) findViewById(R.id.tvEditProfileName);
        tvEditProfileHostel = (TextView) findViewById(R.id.tvEditProfileHostel);
        tvEditProfileRoomNum = (TextView) findViewById(R.id.tvEditProfileRoomNum);

        etEditProfilePhone = (EditText) findViewById(R.id.etEditProfilePhone);
        etEditProfilePhone2 = (EditText) findViewById(R.id.etEditProfilePhone2);
        etEditProfileYear = (EditText) findViewById(R.id.etEditProfileYear);
        etEditProfileMonth = (EditText) findViewById(R.id.etEditProfileMonth);
        etEditProfileDay = (EditText) findViewById(R.id.etEditProfileDay);
        etEditProfileEmail = (EditText) findViewById(R.id.etEditProfileEmail);
        etEditProfileInstitution = (EditText) findViewById(R.id.etEditProfileInstitution);
        etEditProfileProgramme = (EditText) findViewById(R.id.etEditProfileProgramme);
        etEditProfileGuardName = (EditText) findViewById(R.id.etEditProfileGuardName);
        etEditProfileGuardPhone = (EditText) findViewById(R.id.etEditProfileGuardPhone);

        btEditProfileSave = (Button) findViewById(R.id.btEditProfileSave);

        currentTenant = new Tenant();
        currentGuardian = new Guardian();

        tenantManager = new TenantManager(this);
        guardianManager = new GuardianManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
