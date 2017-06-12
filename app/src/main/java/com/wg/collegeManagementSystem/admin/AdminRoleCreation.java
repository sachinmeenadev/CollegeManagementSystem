package com.wg.collegeManagementSystem.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.data.model.Role;
import com.wg.collegeManagementSystem.data.repo.RoleRepo;
import com.wg.collegeManagementSystem.model.RoleList;

import java.util.List;

public class AdminRoleCreation extends Fragment {

    public static final String TAG = AdminRoleCreation.class.getSimpleName();
    private ListView listView;
    private EditText input_role;
    private AppCompatButton fragmentAdminRoleBtnInsert;
    private CustomAdapter customAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_role_creation, container, false);

        input_role = (EditText) view.findViewById(R.id.fragment_admin_role_input_role);
        listView = (ListView) view.findViewById(R.id.fragment_admin_role_list);
        fragmentAdminRoleBtnInsert = (AppCompatButton) view.findViewById(R.id.fragment_admin_role_btn_insert);
        fragmentAdminRoleBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertRoleType();
            }
        });
        show_data();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("PIET-AIM, Role Creation");
    }

    /**
     * For inserting roles in database
     */
    public void insertRoleType() {
        String roleType = input_role.getText().toString().trim();

        RoleRepo roleRepo = new RoleRepo();
        Role role = new Role();

        role.setRoleType(roleType);
        int status = roleRepo.insert(role);
        input_role.setText("");
        Toast.makeText(getActivity(), "Role Added Successfully", Toast.LENGTH_SHORT).show();
        show_data();
    }

    /**
     * For showing added roles from database
     */
    public void show_data() {
        RoleRepo roleRepo = new RoleRepo();
        List<RoleList> list = roleRepo.getRole();
        String[] roleType;
        roleType = new String[list.size()];
        Log.d(TAG, String.format("%-11s", "Role ID") +
                String.format("%-35s", "Role Type")
        );
        Log.d(TAG, "=============================================================");
        for (int i = 0; i < list.size(); i++) {
            Log.d(TAG, list.get(i).getRoleId() +
                    " " + String.format("%-35s", list.get(i).getRoleType())
            );
        }
        Log.d(TAG, "=============================================================");

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                roleType[i] = list.get(i).getRoleType();
            }
            customAdapter = new CustomAdapter(getActivity(), roleType);
            listView.setAdapter(customAdapter);
        } else {
            Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblRoleType;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.fragment_admin_role_list_sl_no);
            lblRoleType = (AppCompatTextView) v.findViewById(R.id.fragment_admin_role_list_role_type);
        }
    }

    /**
     * Custom adapter to fill list view
     * */
    public class CustomAdapter extends ArrayAdapter<String> {
        String[] mRoleType;
        Context mContext;

        public CustomAdapter(Context context, String[] roleType) {
            super(context, R.layout.fragment_admin_role_row, R.id.fragment_admin_role_list_role_type, roleType);
            mRoleType = roleType;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.fragment_admin_role_row, parent, false);
                viewHolder = new ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblRoleType.setText(mRoleType[position]);

            return row;
        }
    }
}
