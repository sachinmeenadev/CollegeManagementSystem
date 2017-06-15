package com.wg.collegeManagementSystem.admin;

import android.content.Context;
import android.os.Bundle;
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
import com.wg.collegeManagementSystem.data.model.CollegeBranch;
import com.wg.collegeManagementSystem.data.repo.CollegeBranchRepo;
import com.wg.collegeManagementSystem.model.CollegeBranchList;

import java.util.List;

/**
 * Created by Jerry on 13-06-2017.
 */

public class AdminCollegeBranchCreation extends Fragment {

    private static final String TAG = AdminCollegeBranchCreation.class.getSimpleName();
    private ListView listView;
    private EditText inputBranchName, inputBranchAbbr;
    private AppCompatButton adminFragmentCollegeBranchBtnInsert;
    private AdminCollegeBranchCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String oldBranchName, newBranchName, oldBranchAbbr, newBranchAbbr;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentCollegeBranchUpdateInputBranchName, adminFragmentCollegeBranchUpdateInputBranchAbbr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_college_branch_creation, container, false);

        builder = new MaterialDialog.Builder(getActivity());
        inputBranchName = (EditText) view.findViewById(R.id.admin_fragment_college_branch_input_branch_name);
        inputBranchAbbr = (EditText) view.findViewById(R.id.admin_fragment_college_branch_input_branch_abbr);
        listView = (ListView) view.findViewById(R.id.admin_fragment_college_branch_list);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_college_branch_update, null);

                oldBranchName = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_college_branch_list_college_name)).getText().toString();
                oldBranchAbbr = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_college_branch_list_college_abbr)).getText().toString();
                adminFragmentCollegeBranchUpdateInputBranchName = (EditText) mView.findViewById(R.id.admin_fragment_college_branch_update_input_branch_name);
                adminFragmentCollegeBranchUpdateInputBranchName.setText(oldBranchName);
                adminFragmentCollegeBranchUpdateInputBranchAbbr = (EditText) mView.findViewById(R.id.admin_fragment_college_branch_update_input_branch_abbr);
                adminFragmentCollegeBranchUpdateInputBranchAbbr.setText(oldBranchAbbr);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newBranchName = adminFragmentCollegeBranchUpdateInputBranchName.getText().toString().trim();
                        newBranchAbbr = adminFragmentCollegeBranchUpdateInputBranchAbbr.getText().toString().trim();
                        update(oldBranchName, newBranchName, oldBranchAbbr, newBranchAbbr);
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

        adminFragmentCollegeBranchBtnInsert = (AppCompatButton) view.findViewById(R.id.admin_fragment_college_branch_btn_insert);
        adminFragmentCollegeBranchBtnInsert.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle("College Branch Creation");
    }

    /**
     * For inserting in database
     */
    public void insert() {
        String branchName = inputBranchName.getText().toString().trim();
        String branchAbbr = inputBranchAbbr.getText().toString().trim();
        if (branchName.isEmpty() || branchAbbr.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (branchName.equals(oldBranchName) && branchAbbr.equals(oldBranchAbbr)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
                CollegeBranch collegeBranch = new CollegeBranch();

                collegeBranch.setCollegeBranchName(branchName);
                collegeBranch.setCollegeBranchAbbr(branchAbbr);
                int status = collegeBranchRepo.insert(collegeBranch);

                inputBranchName.setText("");
                inputBranchAbbr.setText("");

                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                show_data();
            }
        }
    }

    /**
     * For updating in database
     */
    public void update(String oldBranchName, String newBranchName, String oldBranchAbbr, String newBranchAbbr) {
        if (newBranchName.isEmpty() || newBranchAbbr.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
            CollegeBranch collegeBranch = new CollegeBranch();

            collegeBranch.setOldCollegeBranchName(oldBranchName);
            collegeBranch.setNewCollegeBranchName(newBranchName);
            collegeBranch.setOldCollegeBranchAbbr(oldBranchAbbr);
            collegeBranch.setNewCollegeBranchAbbr(newBranchAbbr);
            collegeBranchRepo.update(collegeBranch);

            Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            show_data();
        }
    }

    /**
     * For deleting in database
     */
    public void delete() {
        CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
        CollegeBranch collegeBranch = new CollegeBranch();

        collegeBranch.setCollegeBranchName(oldBranchName);
        collegeBranch.setCollegeBranchAbbr(oldBranchAbbr);
        collegeBranchRepo.delete(collegeBranch);

        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
        show_data();
    }

    /**
     * For showing added roles from database
     */
    public void show_data() {
        CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
        List<CollegeBranchList> list = collegeBranchRepo.getCollegeBranch();

        String[] branchName;
        String[] branchAbbr;

        branchName = new String[list.size()];
        branchAbbr = new String[list.size()];

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                branchName[i] = list.get(i).getCollegeBranchName();
                branchAbbr[i] = list.get(i).getCollegeBranchAbbr();
            }
            customAdapter = new AdminCollegeBranchCreation.CustomAdapter(getActivity(), branchName, branchAbbr);
            listView.setAdapter(customAdapter);
        } else {
            Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblBranchName, lblBranchAbbr;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_college_branch_list_sl_no);
            lblBranchName = (AppCompatTextView) v.findViewById(R.id.admin_fragment_college_branch_list_college_name);
            lblBranchAbbr = (AppCompatTextView) v.findViewById(R.id.admin_fragment_college_branch_list_college_abbr);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        String[] mBranchName;
        String[] mBranchAbbr;
        Context mContext;

        public CustomAdapter(Context context, String[] branchName, String[] branchAbbr) {
            super(context, R.layout.admin_fragment_college_branch_row, R.id.admin_fragment_college_branch_list_college_name, branchName);
            mBranchName = branchName;
            mBranchAbbr = branchAbbr;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            AdminCollegeBranchCreation.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.admin_fragment_college_branch_row, parent, false);
                viewHolder = new AdminCollegeBranchCreation.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (AdminCollegeBranchCreation.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblBranchName.setText(mBranchName[position]);
            viewHolder.lblBranchAbbr.setText(mBranchAbbr[position]);

            return row;
        }
    }
}

