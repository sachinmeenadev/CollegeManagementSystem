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
import android.util.Log;
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
import com.wg.collegeManagementSystem.app.AppConfig;
import com.wg.collegeManagementSystem.app.UrlRequest;
import com.wg.collegeManagementSystem.model.RoleList;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class AdminRoleCreation extends Fragment {

    private static final String TAG = AdminRoleCreation.class.getSimpleName();
    private ListView listView;
    private EditText inputRole;
    private AppCompatButton adminFragmentRoleBtnInsert;
    private CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String oldRoleType, newRoleType;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentRoleUpdateInputRoleType;
    private ProgressDialog pDialog;
    private List<RoleList> roleList;

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

                oldRoleType = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_role_list_role_type)).getText().toString();
                adminFragmentRoleUpdateInputRoleType = (EditText) mView.findViewById(R.id.admin_fragment_role_update_input_role);
                adminFragmentRoleUpdateInputRoleType.setText(oldRoleType);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newRoleType = adminFragmentRoleUpdateInputRoleType.getText().toString().trim();
                        //update(oldRoleType, newRoleType);
                    }
                });
                builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //delete();
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
                // insert();
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

    public String sendRequest(String url) {
        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        try {
            response = urlRequest.getUrlData(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, response);
        return response;
    }

    /**
     * For showing added roles from database
     */
    public void show_data() {
        String url = AppConfig.ADMIN_BASE_URL + "roles";
        String response = sendRequest(url);
        try {
            JSONObject role = (new JSONObject(response)).getJSONObject("role");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] roleType = new String[roleList.size()];

        if (roleList.size() > 0) {
            for (int i = 0; i < roleList.size(); i++) {
                roleType[i] = roleList.get(i).getRoleType();
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
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_role_list_sl_no);
            lblRoleType = (AppCompatTextView) v.findViewById(R.id.admin_fragment_role_list_role_type);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        String[] mRoleType;
        Context mContext;

        public CustomAdapter(Context context, String[] roleType) {
            super(context, R.layout.admin_fragment_role_row, R.id.admin_fragment_role_list_role_type, roleType);
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
            viewHolder.lblRoleType.setText(mRoleType[position]);

            return row;
        }
    }
}
