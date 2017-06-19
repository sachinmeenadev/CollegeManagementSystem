package com.wg.collegeManagementSystem.admin;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.wg.collegeManagementSystem.app.config.AppURL;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.User;
import com.wg.collegeManagementSystem.data.repo.RoleRepo;
import com.wg.collegeManagementSystem.data.repo.UserRepo;
import com.wg.collegeManagementSystem.model.RoleList;
import com.wg.collegeManagementSystem.model.UserList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jerry on 19-06-2017.
 */

public class AdminUserCreation extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = AdminUserCreation.class.getSimpleName();
    private String URL = AppURL.ADMIN_USER_URL;
    private String ROLE_URL = AppURL.ADMIN_ROLE_URL;
    private ListView listView;
    private EditText inputUserName, inputUserEmail, inputUserPassword;
    private AppCompatButton adminFragmentUserBtnInsert;
    private AdminUserCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblUserName, newLblUserName, lblUserEmail, newLblUserEmail, newLblUserPassword, newLblUserRole;
    private int lblUserId;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentUserUpdateInputUserName, adminFragmentUserUpdateInputUserEmail, adminFragmentUserUpdateInputUserPassword;
    private AppCompatSpinner inputUserRoleSpinner;
    private AppCompatSpinner adminFragmentUserUpdateInputUserRoleSpinner;
    private HashMap<Integer, String> roleMap;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_user_creation, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        builder = new MaterialDialog.Builder(getActivity());
        inputUserName = (EditText) view.findViewById(R.id.admin_fragment_user_input_user_name);
        inputUserEmail = (EditText) view.findViewById(R.id.admin_fragment_user_input_user_email);
        inputUserPassword = (EditText) view.findViewById(R.id.admin_fragment_user_input_user_password);
        inputUserRoleSpinner = (AppCompatSpinner) view.findViewById(R.id.admin_fragment_user_input_user_role_spinner);
        setRoleSpinner(0);
        listView = (ListView) view.findViewById(R.id.admin_fragment_user_list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_user_update, null);

                lblUserId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.admin_fragment_user_list_user_id)).getText().toString());
                lblUserName = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_user_list_user_name)).getText().toString();
                lblUserEmail = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_user_list_user_email)).getText().toString();
                adminFragmentUserUpdateInputUserName = (EditText) mView.findViewById(R.id.admin_fragment_user_update_input_user_name);
                adminFragmentUserUpdateInputUserName.setText(lblUserName);
                adminFragmentUserUpdateInputUserEmail = (EditText) mView.findViewById(R.id.admin_fragment_user_update_input_user_email);
                adminFragmentUserUpdateInputUserEmail.setText(lblUserEmail);
                adminFragmentUserUpdateInputUserPassword = (EditText) mView.findViewById(R.id.admin_fragment_user_update_input_user_password);
                adminFragmentUserUpdateInputUserRoleSpinner = (AppCompatSpinner) mView.findViewById(R.id.admin_fragment_user_update_input_user_role_spinner);
                setRoleSpinner(1);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newLblUserName = adminFragmentUserUpdateInputUserName.getText().toString().trim();
                        newLblUserEmail = adminFragmentUserUpdateInputUserEmail.getText().toString().trim();
                        newLblUserPassword = adminFragmentUserUpdateInputUserPassword.getText().toString().trim();
                        newLblUserRole = adminFragmentUserUpdateInputUserRoleSpinner.getSelectedItem().toString().trim();
                        update(lblUserId, newLblUserName, newLblUserEmail, newLblUserPassword, newLblUserRole);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.admin_fragment_user_list_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        show_data();
                                    }
                                }
        );

        adminFragmentUserBtnInsert = (AppCompatButton) view.findViewById(R.id.admin_fragment_user_btn_insert);
        adminFragmentUserBtnInsert.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle("User Creation");
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        show_data();
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
        List<RoleList> list = roleRepo.getRole(sendRequest(ROLE_URL));
        if (list.size() > 0) {
            roleSpinnerArray.add("Select Role");
            roleMap.put(0, "Select Role");
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
            adminFragmentUserUpdateInputUserRoleSpinner.setAdapter(adapter);
        }
    }

    /**
     * For sending request to web server and get data
     */
    public String sendRequest(String url) {
        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        try {
            response = urlRequest.getUrlData(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * For showing added roles from database
     */
    public void show_data() {
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        String url = URL;
        String response = sendRequest(url);
        String[] responseArray = response.split(" ");
        if (responseArray[0].equals("ERROR")) {
            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
        } else {
            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);

            UserRepo userRepo = new UserRepo();
            List<UserList> list = userRepo.getUser(response);

            int[] userId = new int[list.size()];
            String[] userName = new String[list.size()];
            String[] userEmail = new String[list.size()];
            String[] userRole = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    userId[i] = list.get(i).getUserId();
                    userName[i] = list.get(i).getUserName();
                    userEmail[i] = list.get(i).getUserEmail();
                    userRole[i] = list.get(i).getRoleType();
                }
                customAdapter = new AdminUserCreation.CustomAdapter(getActivity(), userId, userName, userEmail, userRole);
                listView.setAdapter(customAdapter);
            } else {
                Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
            }
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
            if (userName.equals(lblUserName) && userEmail.equals(lblUserEmail)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                String url = URL;

                UserRepo userRepo = new UserRepo();
                User user = new User();

                user.setUserName(userName);
                user.setUserEmail(userEmail);
                user.setUserPassword(userPassword);
                user.setUserRoleId(userRoleId);
                String status = userRepo.insert(user, url);
                String[] statusArray = status.replaceAll("[{}]", "").split(",");
                if (statusArray[0].equals("\"error\":false")) {
                    inputUserName.setText("");
                    inputUserEmail.setText("");
                    inputUserPassword.setText("");
                    Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    show_data();
                } else {
                    Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                    show_data();
                }
            }
        }
    }

    /**
     * For updating data
     */
    public void update(int lblUserId, String newLblUserName, String newLblUserEmail, String newLblUserPassword, String newLblUserRole) {
        int userRoleId = getRoleSpinner(newLblUserRole);
        int updateUserRoleStatus = 0, updateUserPasswordStatus = 0;
        if (userRoleId != 0) {
            updateUserRoleStatus = 1;
        }
        if (!newLblUserPassword.isEmpty()) {
            updateUserPasswordStatus = 1;
        }
        if (newLblUserName.isEmpty() || newLblUserEmail.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {

            String url = URL;

            UserRepo userRepo = new UserRepo();
            User user = new User();

            user.setUserName(newLblUserName);
            user.setUserEmail(newLblUserEmail);
            user.setUserPassword(newLblUserPassword);
            user.setUserRoleId(userRoleId);
            String status = userRepo.update(user, url, updateUserRoleStatus, updateUserPasswordStatus);
            String[] statusArray = status.replaceAll("[{}]", "").split(",");
            if (statusArray[0].equals("\"error\":false")) {
                Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                show_data();
            } else {
                Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
                show_data();
            }
        }
    }

    /**
     * For deleting roles in database
     */
    public void delete() {
        String url = URL;

        UserRepo userRepo = new UserRepo();
        User user = new User();

        user.setUserId(lblUserId);
        String status = userRepo.delete(user, url);
        String[] statusArray = status.replaceAll("[{}]", "").split(",");
        if (statusArray[0].equals("\"error\":false")) {
            Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
            show_data();
        } else {
            Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
            show_data();
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblUserId, lblUserName, lblUserEmail, lblUserRole;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_user_list_sl_no);
            lblUserId = (AppCompatTextView) v.findViewById(R.id.admin_fragment_user_list_user_id);
            lblUserName = (AppCompatTextView) v.findViewById(R.id.admin_fragment_user_list_user_name);
            lblUserEmail = (AppCompatTextView) v.findViewById(R.id.admin_fragment_user_list_user_email);
            lblUserRole = (AppCompatTextView) v.findViewById(R.id.admin_fragment_user_list_user_role);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mUserId;
        String[] mUserName;
        String[] mUserEmail;
        String[] mUserRole;
        Context mContext;

        public CustomAdapter(Context context, int[] userId, String[] userName, String[] userEmail, String[] userRole) {
            super(context, R.layout.admin_fragment_user_row, R.id.admin_fragment_user_list_user_name, userName);
            mUserId = userId;
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
                row = inflater.inflate(R.layout.admin_fragment_user_row, parent, false);
                viewHolder = new AdminUserCreation.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (AdminUserCreation.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblUserId.setText(String.valueOf(mUserId[position]));
            viewHolder.lblUserName.setText(mUserName[position]);
            viewHolder.lblUserEmail.setText(mUserEmail[position]);
            viewHolder.lblUserRole.setText(mUserRole[position]);

            return row;
        }
    }
}
