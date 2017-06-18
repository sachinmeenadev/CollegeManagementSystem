package com.wg.collegeManagementSystem.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
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
import com.wg.collegeManagementSystem.app.config.AppConfig;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.Role;
import com.wg.collegeManagementSystem.data.repo.RoleRepo;
import com.wg.collegeManagementSystem.model.RoleList;

import java.io.IOException;
import java.util.List;

public class AdminRoleCreation extends Fragment {

    private static final String TAG = AdminRoleCreation.class.getSimpleName();
    private String URL = AppConfig.ADMIN_ROLE_URL;
    private ListView listView;
    private EditText inputRole;
    private AppCompatButton adminFragmentRoleBtnInsert;
    private CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblRoleType, newLblRoleType;
    private int lblRoleId;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentRoleUpdateInputRoleType;
    private ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_role_creation, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        builder = new MaterialDialog.Builder(getActivity());
        inputRole = (EditText) view.findViewById(R.id.admin_fragment_role_input_role);
        listView = (ListView) view.findViewById(R.id.admin_fragment_role_list);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_role_update, null);

                lblRoleId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.admin_fragment_role_list_role_id)).getText().toString());
                lblRoleType = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_role_list_role_type)).getText().toString();
                adminFragmentRoleUpdateInputRoleType = (EditText) mView.findViewById(R.id.admin_fragment_role_update_input_role);
                adminFragmentRoleUpdateInputRoleType.setText(lblRoleType);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newLblRoleType = adminFragmentRoleUpdateInputRoleType.getText().toString().trim();
                        update(lblRoleId, newLblRoleType);
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

        adminFragmentRoleBtnInsert = (AppCompatButton) view.findViewById(R.id.admin_fragment_role_btn_insert);
        adminFragmentRoleBtnInsert.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle("User Role Creation");
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

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
        pDialog.setMessage("Loading ...");
        showDialog();
        String url = URL;
        String response = sendRequest(url);
        String[] responseArray = response.split(" ");
        if (responseArray[0].equals("ERROR")) {
            hideDialog();
            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
        } else {
            hideDialog();

            RoleRepo roleRepo = new RoleRepo();
            List<RoleList> list = roleRepo.getRole(response);

            int[] roleId = new int[list.size()];
            String[] roleType = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    roleId[i] = list.get(i).getRoleId();
                    roleType[i] = list.get(i).getRoleType();
                }
                customAdapter = new CustomAdapter(getActivity(), roleId, roleType);
                listView.setAdapter(customAdapter);
            } else {
                Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * For inserting data
     */
    public void insert() {
        String roleType = inputRole.getText().toString().trim();

        if (roleType.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (roleType.equals(lblRoleType)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                String url = URL;

                RoleRepo roleRepo = new RoleRepo();
                Role role = new Role();

                role.setRoleType(roleType);
                String status = roleRepo.insert(role, url);
                String[] statusArray = status.replaceAll("[{}]", "").split(",");
                if (statusArray[0].equals("\"error\":false")) {
                    inputRole.setText("");
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
    public void update(int lblRoleId, String newLblRoleType) {

        if (newLblRoleType.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {

            String url = URL;

            RoleRepo roleRepo = new RoleRepo();
            Role role = new Role();

            role.setRoleId(lblRoleId);
            role.setRoleType(newLblRoleType);
            String status = roleRepo.update(role, url);
            String[] statusArray = status.replaceAll("[{}]", "").split(",");
            if (statusArray[0].equals("\"error\":false")) {
                inputRole.setText("");
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

        RoleRepo roleRepo = new RoleRepo();
        Role role = new Role();

        role.setRoleId(lblRoleId);
        String status = roleRepo.delete(role, url);
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
        AppCompatTextView lblSlNo, lblRoleId, lblRoleType;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_role_list_sl_no);
            lblRoleId = (AppCompatTextView) v.findViewById(R.id.admin_fragment_role_list_role_id);
            lblRoleType = (AppCompatTextView) v.findViewById(R.id.admin_fragment_role_list_role_type);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mRoleId;
        String[] mRoleType;
        Context mContext;

        public CustomAdapter(Context context, int[] roleId, String[] roleType) {
            super(context, R.layout.admin_fragment_role_row, R.id.admin_fragment_role_list_role_type, roleType);
            mRoleId = roleId;
            mRoleType = roleType;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.admin_fragment_role_row, parent, false);
                viewHolder = new ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblRoleId.setText(String.valueOf(mRoleId[position]));
            viewHolder.lblRoleType.setText(mRoleType[position]);

            return row;
        }
    }
}
