package com.wg.collegeManagementSystem.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.data.model.User;
import com.wg.collegeManagementSystem.data.repo.RoleRepo;
import com.wg.collegeManagementSystem.data.repo.UserRepo;
import com.wg.collegeManagementSystem.model.RoleList;
import com.wg.collegeManagementSystem.model.UserList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jerry on 14-06-2017.
 */

public class AdminUserCreation extends Fragment {

    public static final String TAG = AdminUserCreation.class.getSimpleName();
    private ListView listView;
    private EditText inputUserName, inputUserEmail, inputUserPassword;
    private AppCompatButton fragmentAdminRoleBtnInsert;
    private AdminUserCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String oldUserName, newUserName, oldUserEmail, newUserEmail, newUserPassword, newUserRole;
    private boolean wrapInScrollView = true;
    private EditText fragmentAdminUserUpdateInputUserName, fragmentAdminUserUpdateInputUserEmail, fragmentAdminUserUpdateInputUserPassword;
    private AppCompatSpinner inputUserRoleSpinner;
    private AppCompatSpinner fragmentAdminUserUpdateInputUserRoleSpinner;
    private HashMap<Integer, String> roleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_user_creation, container, false);

        builder = new MaterialDialog.Builder(getActivity());
        inputUserName = (EditText) view.findViewById(R.id.fragment_admin_user_input_user_name);
        inputUserEmail = (EditText) view.findViewById(R.id.fragment_admin_user_input_user_email);
        inputUserPassword = (EditText) view.findViewById(R.id.fragment_admin_user_input_user_password);
        inputUserRoleSpinner = (AppCompatSpinner) view.findViewById(R.id.fragment_admin_user_input_user_role_spinner);
        setRoleSpinner(0);
        listView = (ListView) view.findViewById(R.id.fragment_admin_user_list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.fragment_admin_user_update, null);

                oldUserName = ((AppCompatTextView) view.findViewById(R.id.fragment_admin_user_list_user_name)).getText().toString();
                oldUserEmail = ((AppCompatTextView) view.findViewById(R.id.fragment_admin_user_list_user_email)).getText().toString();
                fragmentAdminUserUpdateInputUserRoleSpinner = (AppCompatSpinner) view.findViewById(R.id.fragment_admin_user_update_input_user_role_spinner);
                setRoleSpinner(1);
                fragmentAdminUserUpdateInputUserName = (EditText) mView.findViewById(R.id.fragment_admin_user_update_input_user_name);
                fragmentAdminUserUpdateInputUserName.setText(oldUserName);
                fragmentAdminUserUpdateInputUserEmail = (EditText) mView.findViewById(R.id.fragment_admin_user_update_input_user_email);
                fragmentAdminUserUpdateInputUserEmail.setText(oldUserEmail);
                fragmentAdminUserUpdateInputUserPassword = (EditText) mView.findViewById(R.id.fragment_admin_user_update_input_user_password);
                fragmentAdminUserUpdateInputUserRoleSpinner = (AppCompatSpinner) mView.findViewById(R.id.fragment_admin_user_update_input_user_role_spinner);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newUserName = fragmentAdminUserUpdateInputUserName.getText().toString().trim();
                        newUserEmail = fragmentAdminUserUpdateInputUserEmail.getText().toString().trim();
                        newUserPassword = fragmentAdminUserUpdateInputUserPassword.getText().toString().trim();
                        newUserRole = fragmentAdminUserUpdateInputUserRoleSpinner.getSelectedItem().toString().trim();
                        update(newUserName, newUserEmail, newUserPassword, newUserRole);
                    }
                });
                builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        delete();
                    }
                });
                builder.onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        return;
                    }
                });

                MaterialDialog dialog = builder.build();
                dialog.show();

                return true;
            }
        });

        fragmentAdminRoleBtnInsert = (AppCompatButton) view.findViewById(R.id.fragment_admin_user_btn_insert);
        fragmentAdminRoleBtnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        show_data();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("PIET-AIM, User Creation");
    }

    /**
     * For showing data inside spinner
     */
    public void setRoleSpinner(int id) {
        /**
         * 1. "0" For initial spinner
         * 2. "1" For update spinner
         */
        //For creating a array for spinner
        List<String> roleSpinnerArray = new ArrayList<String>();
        //For creating a associative array for comparing it with values later to get th key
        roleMap = new HashMap<Integer, String>();
        RoleRepo roleRepo = new RoleRepo();
        List<RoleList> list = roleRepo.getRole();
        if (list.size() > 0) {
            roleSpinnerArray.add("Select Role");
            for (int i = 0; i < list.size(); i++) {
                roleMap.put(list.get(i).getRoleId(), list.get(i).getRoleType());
                roleSpinnerArray.add(list.get(i).getRoleType());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, roleSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (id == 0) {
            inputUserRoleSpinner.setAdapter(adapter);
        } else if (id == 1) {
            fragmentAdminUserUpdateInputUserRoleSpinner.setAdapter(adapter);
        }
    }

    /**
     * For getting value from spinner
     */
    public int getRoleSpinner(String userRole) {
        int roleId = 0;
        for (Map.Entry entry : roleMap.entrySet()) {
            if (userRole.equals(entry.getValue())) {
                roleId = Integer.parseInt(entry.getKey().toString());
                break; //breaking because its one to one map
            } else {
                roleId = 0;
            }
        }
        return roleId;
    }

    /**
     * For inserting in database
     */
    public void insert() {
        String userName = inputUserName.getText().toString().trim();
        String userEmail = inputUserEmail.getText().toString().trim();
        String userPassword = inputUserPassword.getText().toString().trim();
        String userRole = inputUserRoleSpinner.getSelectedItem().toString().trim();
        int userRoleId = getRoleSpinner(userRole);
        if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userRoleId == 0) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            UserRepo userRepo = new UserRepo();
            User user = new User();

            user.setUserName(userName);
            user.setUserEmail(userEmail);
            user.setUserPassword(userPassword);
            user.setUserRoleId(userRoleId);
            int status = userRepo.insert(user);

            inputUserName.setText("");
            inputUserEmail.setText("");
            inputUserPassword.setText("");

            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
            show_data();
        }
    }

    /**
     * For updating in database
     */
    public void update(String newUserName, String newUserEmail, String newUserPassword, String newUserRole) {
        int userRoleId = getRoleSpinner(newUserRole);
        int updateUserRoleStatus = 0, updateUserPasswordStatus = 0;
        if (userRoleId != 0) {
            updateUserRoleStatus = 1;
        }
        if (!newUserPassword.isEmpty()) {
            updateUserPasswordStatus = 1;
        }
        if (newUserName.isEmpty() || newUserEmail.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            UserRepo userRepo = new UserRepo();
            User user = new User();

            user.setOldUserName(oldUserName);
            user.setNewUserName(newUserName);
            user.setOldUserEmail(oldUserEmail);
            user.setNewUserEmail(newUserEmail);
            user.setUserPassword(newUserPassword);
            user.setUserRoleId(userRoleId);

            userRepo.update(user, updateUserRoleStatus, updateUserPasswordStatus);

            Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            show_data();
        }
    }

    /**
     * For deleting in database
     */
    public void delete() {
        UserRepo userRepo = new UserRepo();
        User user = new User();

        user.setUserName(oldUserName);
        user.setUserEmail(oldUserEmail);
        userRepo.delete(user);

        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
        show_data();
    }

    /**
     * For showing added roles from database
     */
    public void show_data() {
        UserRepo userRepo = new UserRepo();
        List<UserList> list = userRepo.getUser();

        String[] userName;
        String[] userEmail;
        String[] userRole;

        userName = new String[list.size()];
        userEmail = new String[list.size()];
        userRole = new String[list.size()];

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                userName[i] = list.get(i).getUserName();
                userEmail[i] = list.get(i).getUserEmail();
                userRole[i] = list.get(i).getRoleType();
            }
            customAdapter = new AdminUserCreation.CustomAdapter(getActivity(), userName, userEmail, userRole);
            listView.setAdapter(customAdapter);
        } else {
            Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblUserName, lblUserEmail, lblUserRole;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.fragment_admin_user_list_sl_no);
            lblUserName = (AppCompatTextView) v.findViewById(R.id.fragment_admin_user_list_user_name);
            lblUserEmail = (AppCompatTextView) v.findViewById(R.id.fragment_admin_user_list_user_email);
            lblUserRole = (AppCompatTextView) v.findViewById(R.id.fragment_admin_user_list_user_role);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        String[] mUserName;
        String[] mUserEmail;
        String[] mUserRole;
        Context mContext;

        public CustomAdapter(Context context, String[] userName, String[] userEmail, String[] userRole) {
            super(context, R.layout.fragment_admin_user_row, R.id.fragment_admin_user_list_user_name, userName);
            mUserName = userName;
            mUserEmail = userEmail;
            mUserRole = userRole;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            AdminUserCreation.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.fragment_admin_user_row, parent, false);
                viewHolder = new AdminUserCreation.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (AdminUserCreation.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblUserName.setText(mUserName[position]);
            viewHolder.lblUserEmail.setText(mUserEmail[position]);
            viewHolder.lblUserRole.setText(mUserRole[position]);

            return row;
        }
    }
}


