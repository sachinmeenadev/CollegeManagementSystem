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
 * Created by Jerry on 21-06-2017.
 */

public class AdminFacultyMemberCreation extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = AdminFacultyMemberCreation.class.getSimpleName();
    private String URL = AppURL.ADMIN_FACULTY_MEMBER;
    private String COLLEGE_BRANCH_URL = AppURL.ADMIN_COLLEGE_BRANCH;
    private ListView listView;
    private EditText inputFacultyName, inputFacultyDesignation, inputFacultyEmail, inputFacultyContact;
    private AppCompatButton adminFragmentRoleBtnInsert;
    private AdminFacultyMemberCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblFacultyName, newLblFacultyName, lblFacultyDesignation, newLblFacultyDesignation, lblFacultyEmail, newLblFacultyEmail, lblFacultyContact, newLblFacultyContact, newLblFacultyBranch, newLblFacultyCurrentBranch;
    private int lblFacultyMemberId;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentFacultyUpdateInputName, adminFragmentFacultyUpdateInputDesignation, adminFragmentFacultyUpdateInputEmail, adminFragmentFacultyUpdateInputContact;
    private AppCompatSpinner inputFacultyBranchSpinner, inputFacultyCurrentBranchSpinner;
    private AppCompatSpinner adminFragmentFacultyUpdateInputFacultyBranchSpinner, adminFragmentFacultyUpdateInputFacultyCurrentBranchSpinner;
    private HashMap<Integer, String> collegeBranchMap;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_faculty_member_creation, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        builder = new MaterialDialog.Builder(getActivity());
        inputFacultyName = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_name);
        inputFacultyDesignation = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_designation);
        inputFacultyEmail = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_email);
        inputFacultyContact = (EditText) view.findViewById(R.id.admin_fragment_faculty_member_input_contact);

        inputFacultyBranchSpinner = (AppCompatSpinner) view.findViewById(R.id.admin_fragment_faculty_member_input_branch_spinner);
        inputFacultyCurrentBranchSpinner = (AppCompatSpinner) view.findViewById(R.id.admin_fragment_faculty_member_input_current_branch_spinner);
        setBranchSpinner(0);

        listView = (ListView) view.findViewById(R.id.admin_fragment_faculty_member_list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_faculty_member_update, null);

                lblFacultyMemberId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_faculty_member_id)).getText().toString());
                lblFacultyName = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_name)).getText().toString();
                lblFacultyDesignation = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_designation)).getText().toString();
                lblFacultyEmail = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_email)).getText().toString();
                lblFacultyContact = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_faculty_member_list_contact)).getText().toString();

                adminFragmentFacultyUpdateInputName = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_name);
                adminFragmentFacultyUpdateInputName.setText(lblFacultyName);
                adminFragmentFacultyUpdateInputDesignation = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_designation);
                adminFragmentFacultyUpdateInputDesignation.setText(lblFacultyDesignation);
                adminFragmentFacultyUpdateInputEmail = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_email);
                adminFragmentFacultyUpdateInputEmail.setText(lblFacultyEmail);
                adminFragmentFacultyUpdateInputContact = (EditText) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_contact);
                adminFragmentFacultyUpdateInputContact.setText(lblFacultyContact);

                adminFragmentFacultyUpdateInputFacultyBranchSpinner = (AppCompatSpinner) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_branch_spinner);
                adminFragmentFacultyUpdateInputFacultyCurrentBranchSpinner = (AppCompatSpinner) mView.findViewById(R.id.admin_fragment_faculty_member_update_input_current_branch_spinner);
                setBranchSpinner(1);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newLblFacultyName = adminFragmentFacultyUpdateInputName.getText().toString().trim();
                        newLblFacultyDesignation = adminFragmentFacultyUpdateInputDesignation.getText().toString().trim();
                        newLblFacultyEmail = adminFragmentFacultyUpdateInputEmail.getText().toString().trim();
                        newLblFacultyContact = adminFragmentFacultyUpdateInputContact.getText().toString().trim();

                        newLblFacultyBranch = adminFragmentFacultyUpdateInputFacultyBranchSpinner.getSelectedItem().toString().trim();
                        newLblFacultyCurrentBranch = adminFragmentFacultyUpdateInputFacultyCurrentBranchSpinner.getSelectedItem().toString().trim();

                        update(newLblFacultyName, newLblFacultyDesignation, newLblFacultyEmail, newLblFacultyContact, newLblFacultyBranch, newLblFacultyCurrentBranch);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.admin_fragment_faculty_member_list_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.indigo, R.color.lime);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        show_data();
                                    }
                                }
        );

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
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        show_data();
    }

    /**
     * For sending request to web server and get data
     */
    public String sendRequest(String url) {
        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.getUrlData(url);
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

            FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
            List<FacultyMemberList> list = facultyMemberRepo.getFacultyMember(response);

            int[] facultyMemberId = new int[list.size()];
            String[] facultyName = new String[list.size()];
            String[] facultyDesignation = new String[list.size()];
            String[] facultyContact = new String[list.size()];
            String[] facultyEmail = new String[list.size()];
            String[] facultyBranch = new String[list.size()];
            String[] facultyCurrentBranch = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    facultyMemberId[i] = list.get(i).getFacultyMemberId();
                    facultyName[i] = list.get(i).getFacultyMemberName();
                    facultyDesignation[i] = list.get(i).getFacultyMemberDesignation();
                    facultyContact[i] = list.get(i).getFacultyMemberContact();
                    facultyEmail[i] = list.get(i).getFacultyMemberEmail();
                    facultyBranch[i] = list.get(i).getCollegeBranchName() + ", " + list.get(i).getCollegeBranchAbbr();
                    facultyCurrentBranch[i] = list.get(i).getCurrentBranchName() + ", " + list.get(i).getCurrentBranchAbbr();
                }
                customAdapter = new AdminFacultyMemberCreation.CustomAdapter(getActivity(), facultyMemberId, facultyName, facultyDesignation, facultyContact, facultyEmail, facultyBranch, facultyCurrentBranch);
                listView.setAdapter(customAdapter);
            } else {
                Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
            }
        }
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
        List<CollegeBranchList> list = collegeBranchRepo.getBranch(sendRequest(COLLEGE_BRANCH_URL));
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
            inputFacultyCurrentBranchSpinner.setAdapter(adapter);
        } else if (id == 1) {
            adminFragmentFacultyUpdateInputFacultyBranchSpinner.setAdapter(adapter);
            adminFragmentFacultyUpdateInputFacultyCurrentBranchSpinner.setAdapter(adapter);
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
        String facultyCurrentBranch = inputFacultyCurrentBranchSpinner.getSelectedItem().toString().trim();
        int facultyBranchId = getBranchSpinner(facultyBranch);
        int facultyCurrentBranchId = getBranchSpinner(facultyCurrentBranch);
        if (facultyName.isEmpty() || facultyDesignation.isEmpty() || facultyContact.isEmpty() || facultyEmail.isEmpty() || facultyBranchId == 0 || facultyCurrentBranchId == 0) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (facultyName.equals(lblFacultyName) && facultyEmail.equals(lblFacultyEmail) && facultyDesignation.equals(lblFacultyDesignation)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                String url = URL;

                FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
                FacultyMember facultyMember = new FacultyMember();

                facultyMember.setFacultyMemberName(facultyName);
                facultyMember.setFacultyMemberDesignation(facultyDesignation);
                facultyMember.setFacultyMemberContact(facultyContact);
                facultyMember.setFacultyMemberEmail(facultyEmail);
                facultyMember.setFacultyMemberBranchId(facultyBranchId);
                facultyMember.setFacultyMemberCurrentBranchId(facultyCurrentBranchId);
                String status = facultyMemberRepo.insert(facultyMember, url);
                String[] statusArray = status.replaceAll("[{}]", "").split(",");
                if (statusArray[0].equals("\"error\":false")) {
                    inputFacultyName.setText("");
                    inputFacultyDesignation.setText("");
                    inputFacultyContact.setText("");
                    inputFacultyEmail.setText("");
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
     * For updating in database
     */
    public void update(String newLblFacultyName, String newLblFacultyDesignation, String newLblFacultyEmail, String newLblFacultyContact, String newLblFacultyBranch, String newLblFacultyCurrentBranch) {
        int facultyBranchId = getBranchSpinner(newLblFacultyBranch);
        int facultyCurrentBranchId = getBranchSpinner(newLblFacultyCurrentBranch);
        int updateFacultyBranchStatus = 0;
        int updateFacultyCurrentBranchStatus = 0;
        if (facultyBranchId != 0) {
            updateFacultyBranchStatus = 1;
        }
        if (facultyCurrentBranchId != 0) {
            updateFacultyCurrentBranchStatus = 1;
        }
        if (newLblFacultyName.isEmpty() || newLblFacultyDesignation.isEmpty() || newLblFacultyEmail.isEmpty() || newLblFacultyContact.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            String url = URL;

            FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
            FacultyMember facultyMember = new FacultyMember();

            facultyMember.setFacultyMemberId(lblFacultyMemberId);
            facultyMember.setFacultyMemberName(newLblFacultyName);
            facultyMember.setFacultyMemberDesignation(newLblFacultyDesignation);
            facultyMember.setFacultyMemberContact(newLblFacultyContact);
            facultyMember.setFacultyMemberEmail(newLblFacultyEmail);
            facultyMember.setFacultyMemberBranchId(facultyBranchId);
            facultyMember.setFacultyMemberCurrentBranchId(facultyCurrentBranchId);
            String status = facultyMemberRepo.update(facultyMember, url, updateFacultyBranchStatus, updateFacultyCurrentBranchStatus);
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

        FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
        FacultyMember facultyMember = new FacultyMember();

        facultyMember.setFacultyMemberId(lblFacultyMemberId);
        String status = facultyMemberRepo.delete(facultyMember, url);
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
        AppCompatTextView lblSlNo, lblFacultyMemberId, lblFacultyName, lblFacultyDesignation, lblFacultyEmail, lblFacultyContact, lblFacultyBranch, lblFacultyCurrentBranch;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_sl_no);
            lblFacultyMemberId = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_faculty_member_id);
            lblFacultyName = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_name);
            lblFacultyDesignation = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_designation);
            lblFacultyEmail = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_contact);
            lblFacultyContact = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_email);
            lblFacultyBranch = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_branch);
            lblFacultyCurrentBranch = (AppCompatTextView) v.findViewById(R.id.admin_fragment_faculty_member_list_current_branch);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mFacultyMemberId;
        String[] mFacultyName;
        String[] mFacultyDesignation;
        String[] mFacultyContact;
        String[] mFacultyEmail;
        String[] mFacultyBranch;
        String[] mFacultyCurrentBranch;
        Context mContext;

        public CustomAdapter(Context context, int[] facultyMemberId, String[] facultyName, String[] facultyDesignation, String[] facultyContact, String[] facultyEmail, String[] facultyBranch, String[] facultyCurrentBranch) {
            super(context, R.layout.admin_fragment_faculty_member_row, R.id.admin_fragment_faculty_member_list_name, facultyName);
            mFacultyMemberId = facultyMemberId;
            mFacultyName = facultyName;
            mFacultyDesignation = facultyDesignation;
            mFacultyContact = facultyContact;
            mFacultyEmail = facultyEmail;
            mFacultyBranch = facultyBranch;
            mFacultyCurrentBranch = facultyCurrentBranch;
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
            viewHolder.lblFacultyMemberId.setText(String.valueOf(mFacultyMemberId[position]));
            viewHolder.lblFacultyName.setText(mFacultyName[position]);
            viewHolder.lblFacultyDesignation.setText(mFacultyDesignation[position]);
            viewHolder.lblFacultyEmail.setText(mFacultyContact[position]);
            viewHolder.lblFacultyContact.setText(mFacultyEmail[position]);
            viewHolder.lblFacultyBranch.setText(mFacultyBranch[position]);
            viewHolder.lblFacultyCurrentBranch.setText(mFacultyCurrentBranch[position]);

            return row;
        }
    }
}
