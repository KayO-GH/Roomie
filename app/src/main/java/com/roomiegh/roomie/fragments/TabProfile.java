package com.roomiegh.roomie.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/**
 * Created by Kwadwo Agyapon-Ntra on 06/10/2015.
 */
// TODO: 23/03/2017  Check from database if user has a profile on the device.
// TODO: If not, give the user a means to login or sign up

public class TabProfile extends Fragment{
    private Button btMyProfileEdit;
    private TextView tvProfileName /*concatenated fName and lName*/,
            tvProfileHostel, tvProfileRoomNum, tvProfilePhone, tvProfilePhone2,
            tvProfileEmail, tvProfileRefNo, tvProfileInstitution,
            tvProfileProgramme, tvProfileDOB, tvProfileGuardName, tvProfileGuardPhone;
    private ImageView ivProfilePic;
    private Tenant currentTenant, displayedCurrentTenant;
    private Guardian currentGuardian, displayedCurrentGuardian;
    private String currentUserEmail;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            boolean receivedStatus = data.getBooleanExtra("bool", false);

            if(receivedStatus){

                //refresh profile
                doNow();
            }
            // if (receivedStatus) {


            //getFragmentManager().beginTransaction().detach(this).attach(new MyProfileFragment());
            // }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment/tab
        View view = inflater.inflate(R.layout.tab_profile,container,false);
        
        init(view);

        //receiving current user email from registration screen
        //receiving arguments from passed bundle and contained information
        //doNow();

        /*btMyProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle pushUser = new Bundle();
                pushUser.putString(PushUserUtil.USER_EMAIL, currentUserEmail);
                Intent editIntent = new Intent(getActivity().getApplicationContext(),
                        EditProfileActivity.class);
                editIntent.putExtra(PushUserUtil.PUSH_INTENT_KEY, pushUser);
                startActivityForResult(editIntent, 100);//using 100 as requestCode
            }
        });*/
        
        return view;
    }

    private void doNow() {
        currentUserEmail = this.getArguments().getString(new PushUserUtil().USER_EMAIL);

        currentTenant.setEmail(currentUserEmail);
        displayedCurrentTenant =
                new TenantManager(getActivity().getApplicationContext()).getTenantByEmail(currentTenant);

        currentGuardian.setRefNo(displayedCurrentTenant.getRefNo());
        displayedCurrentGuardian =
                new GuardianManager(getActivity().getApplicationContext()).getGuardian(currentGuardian);

        //setting profile text for the tenant
        if (displayedCurrentTenant != null) {
            if (displayedCurrentTenant.getPhoto() != null) {
                ivProfilePic.setImageBitmap(CameraUtil.convertByteArrayToPhoto(displayedCurrentTenant.getPhoto()));
            }
            tvProfileName.setText(displayedCurrentTenant.getfName() + " " + displayedCurrentTenant.getlName());
            tvProfileDOB.setText(displayedCurrentTenant.getDob());
            tvProfileEmail.setText(displayedCurrentTenant.getEmail());
            tvProfilePhone.setText(displayedCurrentTenant.getPhone());
            if (displayedCurrentTenant.getPhone2() != null) {
                tvProfilePhone2.setText(displayedCurrentTenant.getPhone2());
            }
            tvProfileRefNo.setText(displayedCurrentTenant.getRefNo() + "");

        } else
            Toast.makeText(getActivity().getApplicationContext(), "Profile not found", Toast.LENGTH_SHORT).show();

        //setting profile text for the guardian
        if (displayedCurrentGuardian != null) {
            tvProfileGuardPhone.setText(displayedCurrentGuardian.getGuardPhone());
            tvProfileGuardName.setText(displayedCurrentGuardian.getGuardFName() + " " + displayedCurrentGuardian.getGuardLName());
        } else
            Toast.makeText(getActivity().getApplicationContext(), "Guardian not found", Toast.LENGTH_SHORT).show();
    }

    private void init(View view) {
        btMyProfileEdit = (Button) view.findViewById(R.id.btMyProfileEdit);
        ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
        tvProfileName = (TextView) view.findViewById(R.id.tvProfileName);
        tvProfileHostel = (TextView) view.findViewById(R.id.tvProfileHostel);
        tvProfileRoomNum = (TextView) view.findViewById(R.id.tvProfileRoomNum);
        tvProfilePhone = (TextView) view.findViewById(R.id.tvProfilePhone);
        tvProfilePhone2 = (TextView) view.findViewById(R.id.tvProfilePhone2);
        tvProfileEmail = (TextView) view.findViewById(R.id.tvProfileEmail);
        tvProfileRefNo = (TextView) view.findViewById(R.id.tvProfileRefNo);
        tvProfileInstitution = (TextView) view.findViewById(R.id.tvProfileInstitution);
        tvProfileProgramme = (TextView) view.findViewById(R.id.tvProfileProgramme);
        tvProfileDOB = (TextView) view.findViewById(R.id.tvProfileDOB);
        tvProfileGuardName = (TextView) view.findViewById(R.id.tvProfileGuardName);
        tvProfileGuardPhone = (TextView) view.findViewById(R.id.tvProfileGuardPhone);
        currentTenant = new Tenant();
        currentGuardian = new Guardian();

    }
}
