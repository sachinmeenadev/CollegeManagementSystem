package com.wg.collegeManagementSystem.admin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.data.model.Role;
import com.wg.collegeManagementSystem.data.repo.RoleRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Jerry on 15-06-2017.
 */

public class AdminImportData extends Fragment {

    private static final String TAG = AdminImportData.class.getSimpleName();
    private static final int REQUEST_CODE = 1;
    private AppCompatButton importRole;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_import_data, container, false);

        importRole = (AppCompatButton) view.findViewById(R.id.admin_fragment_import_data_btn_import_role);
        importRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("*/*");
                try {
                    startActivityForResult(fileIntent, REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No app found for importing the file.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Import CSV Files");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        } else {
            if (requestCode == REQUEST_CODE) {
                String filepath = data.getData().getPath();
                Log.d(TAG, data.getData().getPath());
                try {
                    if (resultCode == RESULT_OK) {
                        try {
                            Log.d(TAG, "CHECK 1");
                            FileReader file = new FileReader(filepath);
                            Log.d(TAG, "CHECK 2");
                            BufferedReader buffer = new BufferedReader(file);
                            Log.d(TAG, "CHECK 3");
                            String line = "";
                            while ((line = buffer.readLine()) != null) {
                                String[] str = line.split(",", 1);
                                String roleType = str[0].toString();
                                RoleRepo roleRepo = new RoleRepo();
                                Role role = new Role();
                                role.setRoleType(roleType);
                                int status = roleRepo.insert(role);
                                Toast.makeText(getActivity(), "Successfully Inserted.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            Toast.makeText(getActivity(), e.getMessage().toString() + "first", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Only CSV allowed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage().toString() + "second", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
