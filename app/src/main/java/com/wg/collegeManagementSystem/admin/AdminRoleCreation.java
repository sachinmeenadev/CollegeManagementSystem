package com.wg.collegeManagementSystem.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
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

public class AdminRoleCreation extends AppCompatActivity {

    private ListView listView;
    private EditText input_role;
    private CustomAdapter customAdapter;
    private RoleRepo roleRepo;
    private List<RoleList> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_role_creation);

        input_role = (EditText) findViewById(R.id.admin_activity_role_input_role);
        listView = (ListView) findViewById(R.id.admin_activity_role_list);
        roleRepo = new RoleRepo();

        show_data();
    }

    public void admin_activity_role_btn_insert(View v) {
        String roleType = input_role.getText().toString().trim();

        Role role = new Role();

        role.setRoleType(roleType);
        int status = roleRepo.insert(role);

        show_data();
    }

    public void show_data() {
        list = roleRepo.getStudentCourse();
        String[] roleType;
        roleType = new String[list.size()];
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                roleType[i] = list.get(i).getRoleType();
            }
            customAdapter = new CustomAdapter(this, roleType);
            listView.setAdapter(customAdapter);
        } else {
            Toast.makeText(this, "No data in database", Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewHolder {
        AppCompatTextView lblSlNo, lblRoleType;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_activity_role_list_sl_no);
            lblRoleType = (AppCompatTextView) v.findViewById(R.id.admin_activity_role_list_role_type);
        }
    }

    public class CustomAdapter extends ArrayAdapter<String> {
        String[] mRoleType;
        Context mContext;

        public CustomAdapter(Context context, String[] roleType) {
            super(context, R.layout.activity_admin_role_row, R.id.admin_activity_role_list_role_type, roleType);
            mRoleType = roleType;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder viewHolder = null;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_admin_role_row, parent, false);
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
