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
import com.wg.collegeManagementSystem.data.model.FacultyMember;
import com.wg.collegeManagementSystem.data.repo.CollegeBranchRepo;
import com.wg.collegeManagementSystem.data.repo.FacultyMemberRepo;
import com.wg.collegeManagementSystem.model.CollegeBranchList;
import com.wg.collegeManagementSystem.model.FacultyMemberList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jerry on 14-06-2017.
 */

public class AdminFacultyMemberCreation extends Fragment {

    public static final String TAG = AdminFacultyMemberCreation.class.getSimpleName();
    private ListView listView;
    private EditText inputFacultyName, inputFacultyDesignation, inputFacultyEmail, inputFacultyContact;
    private AppCompatButton adminFragmentRoleBtnInsert;
    private AdminFacultyMemberCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String oldFacultyName, newFacultyName, oldFacultyDesignation, newFacultyDesignation, oldFacultyEmail, newFacultyEmail, oldFacultyContact, newFacultyContact, newFacultyBranch;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentFacultyUpdateInputName, adminFragmentFacultyUpdateInputDesignation, adminFragmentFacultyUpdateInputEmail, adminFragmentFacultyUpdateInputContact;
    private AppCompatSpinner inputFacultyBranchSpinner;
    private AppCompatSpinner adminFragmentFacultyUpdateInputFacultyBranchSpinner;
    private HashMap<Integer, String> collegeBranchMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_faculty_member_creation, container, false);

        builder = new MaterialDialog.Builder(getActivity());
        inputFacultyName = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_name);
        inputFacultyDesignation = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_designation);
        inputFacultyEmail = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_email);
        inputFacultyContact = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_contact);
        inputFacultyBranchSpinner = (AppCompatSpinner) view.findViewById(R.id.admin_fragment_faculty_member_input_branch_spinner);
        setBranchSpinner(0);
        listView = (ListView) view.findViewById(R.id.admin_fragment_faculty_member_list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_faculty_member_update, null);

                oldFacultyName = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_name)).getText().toString();
                oldFacultyDesignation = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_designation)).getText().toString();
                oldFacultyEmail = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_email)).getText().toString();
                oldFacultyContact = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_contact)).getText().toString();

                adminFragmentFacultyUpdateInputName = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_name);
                adminFragmentFacultyUpdateInputName.setText(oldFacultyName);
                adminFragmentFacultyUpdateInputDesignation = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_designation);
                adminFragmentFacultyUpdateInputDesignation.setText(oldFacultyDesignation);
                adminFragmentFacultyUpdateInputEmail = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_email);
                adminFragmentFacultyUpdateInputEmail.setText(oldFacultyEmail);
                adminFragmentFacultyUpdateInputContact = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_contact);
                adminFragmentFacultyUpdateInputContact.setText(oldFacultyContact);

                adminFragmentFacultyUpdateInputFacultyBranchSpinner = (AppCompatSpinner) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_branch_spinner);
                setBranchSpinner(1);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newFacultyName = adminFragmentFacultyUpdateInputName.getText().toString().trim();
                        newFacultyDesignation = adminFragmentFacultyUpdateInputDesignation.getText().toString().trim();
                        newFacultyEmail = adminFragmentFacultyUpdateInputEmail.getText().toString().trim();
                        newFacultyContact = adminFragmentFacultyUpdateInputContact.getText().toString().trim();
                        newFacultyBranch = adminFragmentFacultyUpdateInputFacultyBranchSpinner.getSelectedItem().toString().trim();
                        update(newFacultyName, newFacultyDesignation, newFacultyEmail, newFacultyContact, newFacultyBranch);
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

        adminFragmentRoleBtnInsert = (AppCompatButton) view.findViewById(R.id.admin_fragment_faculty_member_btn_insert);
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
        getActivity().setTitle("Faculty Member Creation");
    }

    /**
     * For showing data inside spinner
     */
    public void setBranchSpinner(int id) {
        /**
         * 1. "0" For initial spinner
         * 2. "1" For update spinner
         */
        //For creating a array for spinner
        List<String> roleSpinnerArray = new ArrayList<String>();
        //For creating a associative array for comparing it with values later to get th key
        collegeBranchMap = new HashMap<Integer, String>();
        CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
        List<CollegeBranchList> list = collegeBranchRepo.getCollegeBranch();
        if (list.size() > 0) {
            roleSpinnerArray.add("Select Branch");
            collegeBranchMap.put(0, "Select Branch");
            for (int i = 0; i < list.size(); i++) {
                collegeBranchMap.put(list.get(i).getCollegeBranchId(), list.get(i).getCollegeBranchName() + ", " + list.get(i).getCollegeBranchAbbr());
                roleSpinnerArray.add(list.get(i).getCollegeBranchName() + ", " + list.get(i).getCollegeBranchAbbr());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, roleSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (id == 0) {
            inputFacultyBranchSpinner.setAdapter(adapter);
        } else if (id == 1) {
            adminFragmentFacultyUpdateInputFacultyBranchSpinner.setAdapter(adapter);
        }
    }

    /**
     * For getting value from spinner
     */
    public int getBranchSpinner(String facultyBranch) {
        int branchId = 0;
        for (Map.Entry entry : collegeBranchMap.entrySet()) {
            if (facultyBranch.equals(entry.getValue())) {
                branchId = Integer.parseInt(entry.getKey().toString());
                break; //breaking because its one to one map
            } else {
                branchId = 0;
            }
        }
        return branchId;
    }

    /**
     * For inserting in database
     */
    public void insert() {
        String facultyName = inputFacultyName.getText().toString().trim();
        String facultyDesignation = inputFacultyDesignation.getText().toString().trim();
        String facultyContact = inputFacultyContact.getText().toString().trim();
        String facultyEmail = inputFacultyEmail.getText().toString().trim();
        String facultyBranch = inputFacultyBranchSpinner.getSelectedItem().toString().trim();
        int facultyBranchId = getBranchSpinner(facultyBranch);
        if (facultyName.isEmpty() || facultyDesignation.isEmpty() || facultyContact.isEmpty() || facultyEmail.isEmpty() || facultyBranchId == 0) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (facultyName.equals(oldFacultyName) && facultyEmail.equals(oldFacultyEmail) && facultyDesignation.equals(oldFacultyDesignation)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
                FacultyMember facultyMember = new FacultyMember();

                facultyMember.setFacultyMemberName(facultyName);
                facultyMember.setFacultyMemberDesignation(facultyDesignation);
                facultyMember.setFacultyMemberContact(facultyContact);
                facultyMember.setFacultyMemberEmail(facultyEmail);
                facultyMember.setFacultyMemberBranchId(facultyBranchId);
                int status = facultyMemberRepo.insert(facultyMember);

                inputFacultyName.setText("");
                inputFacultyDesignation.setText("");
                inputFacultyContact.setText("");
                inputFacultyEmail.setText("");

                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                show_data();
            }
        }
    }

    /**
     * For updating in database
     */
    public void update(String newFacultyName, String newFacultyDesignation, String newFacultyEmail, String newFacultyContact, String newFacultyBranch) {
        int facultyBranchId = getBranchSpinner(newFacultyBranch);
        int updateFacultyBranchStatus = 0;
        if (facultyBranchId != 0) {
            updateFacultyBranchStatus = 1;
        }
        if (newFacultyName.isEmpty() || newFacultyDesignation.isEmpty() || newFacultyEmail.isEmpty() || newFacultyContact.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
            FacultyMember facultyMember = new FacultyMember();

            facultyMember.setOldFacultyMemberName(oldFacultyName);
            facultyMember.setNewFacultyMemberName(newFacultyName);
            facultyMember.setOldFacultyMemberDesignation(oldFacultyDesignation);
            facultyMember.setNewFacultyMemberDesignation(newFacultyDesignation);
            facultyMember.setOldFacultyMemberContact(oldFacultyContact);
            facultyMember.setNewFacultyMemberContact(newFacultyContact);
            facultyMember.setOldFacultyMemberEmail(oldFacultyEmail);
            facultyMember.setNewFacultyMemberEmail(newFacultyEmail);
            facultyMember.setFacultyMemberBranchId(facultyBranchId);

            facultyMemberRepo.update(facultyMember, updateFacultyBranchStatus);

            Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            show_data();
        }
    }

    /**
     * For deleting in database
     */
    public void delete() {
        FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
        FacultyMember facultyMember = new FacultyMember();

        facultyMember.setFacultyMemberName(oldFacultyName);
        facultyMember.setFacultyMemberDesignation(oldFacultyDesignation);
        facultyMember.setFacultyMemberEmail(oldFacultyEmail);
        facultyMemberRepo.delete(facultyMember);

        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
        show_data();
    }

    /**
     * For showing added roles from database
     */
    public void show_data() {
        FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
        List<FacultyMemberList> list = facultyMemberRepo.getFacultyMember();

        String[] facultyName;
        String[] facultyDesignation;
        String[] facultyContact;
        String[] facultyEmail;
        String[] facultyBranch;

        facultyName = new String[list.size()];
        facultyDesignation = new String[list.size()];
        facultyContact = new String[list.size()];
        facultyEmail = new String[list.size()];
        facultyBranch = new String[list.size()];

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                facultyName[i] = list.get(i).getFacultyMemberName();
                facultyDesignation[i] = list.get(i).getFacultyMemberDesignation();
                facultyContact[i] = list.get(i).getFacultyMemberContact();
                facultyEmail[i] = list.get(i).getFacultyMemberEmail();
                facultyBranch[i] = list.get(i).getCollegeBranchName() + ", " + list.get(i).getCollegeBranchAbbr();
            }
            customAdapter = new AdminFacultyMemberCreation.CustomAdapter(getActivity(), facultyName, facultyDesignation, facultyContact, facultyEmail, facultyBranch);
            listView.setAdapter(customAdapter);
        } else {
            Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblFacultyName, lblFacultyDesignation, lblFacultyEmail, lblFacultyContact, lblFacultyBranch;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_sl_no);
            lblFacultyName = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_name);
            lblFacultyDesignation = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_designation);
            lblFacultyEmail = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_contact);
            lblFacultyContact = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_email);
            lblFacultyBranch = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_branch);
        }
    }


    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        String[] mFacultyName;
        String[] mFacultyDesignation;
        String[] mFacultyContact;
        String[] mFacultyEmail;
        String[] mFacultyBranch;
        Context mContext;

        public CustomAdapter(Context context, String[] facultyName, String[] facultyDesignation, String[] facultyContact, String[] facultyEmail, String[] facultyBranch) {
            super(context, R.layout.admin_fragment_faculty_member_row, R.id.admin_fragment_faculty_member_list_name, facultyName);
            mFacultyName = facultyName;
            mFacultyDesignation = facultyDesignation;
            mFacultyContact = facultyContact;
            mFacultyEmail = facultyEmail;
            mFacultyBranch = facultyBranch;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            AdminFacultyMemberCreation.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.admin_fragment_faculty_member_row, parent, false);
                viewHolder = new AdminFacultyMemberCreation.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (AdminFacultyMemberCreation.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblFacultyName.setText(mFacultyName[position]);
            viewHolder.lblFacultyDesignation.setText(mFacultyDesignation[position]);
            viewHolder.lblFacultyEmail.setText(mFacultyContact[position]);
            viewHolder.lblFacultyContact.setText(mFacultyEmail[position]);
            viewHolder.lblFacultyBranch.setText(mFacultyBranch[position]);

            return row;
        }
    }
}
