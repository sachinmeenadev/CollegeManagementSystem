package com.wg.collegeManagementSystem.hod;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.app.config.AppURL;
import com.wg.collegeManagementSystem.app.helper.SessionManager;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.repo.FacultyMemberRepo;
import com.wg.collegeManagementSystem.model.FacultyMemberList;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry on 15-06-2017.
 */

public class HodFacultyMemberList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = HodFacultyMemberList.class.getSimpleName();
    public SessionManager session;
    private String URL = AppURL.HOD_FACULTY_MEMBER;
    private ListView listView;
    private HodFacultyMemberList.CustomAdapter customAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hod_fragment_faculty_member, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        session = new SessionManager(getActivity().getApplicationContext());

        listView = (ListView) view.findViewById(R.id.hod_fragment_faculty_member_list);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hod_fragment_faculty_member_list_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        show_data();
                                    }
                                }
        );
        show_data();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Faculty Member's List");
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
        HashMap<String, String> params = new HashMap<>();
        params.put("branchId", session.getBranchId());

        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
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

            String[] facultyName = new String[list.size()];
            String[] facultyDesignation = new String[list.size()];
            String[] facultyContact = new String[list.size()];
            String[] facultyEmail = new String[list.size()];
            String[] facultyBranch = new String[list.size()];
            String[] facultyCurrentBranch = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    facultyName[i] = list.get(i).getFacultyMemberName();
                    facultyDesignation[i] = list.get(i).getFacultyMemberDesignation();
                    facultyContact[i] = list.get(i).getFacultyMemberContact();
                    facultyEmail[i] = list.get(i).getFacultyMemberEmail();
                    facultyBranch[i] = list.get(i).getCollegeBranchName() + ", " + list.get(i).getCollegeBranchAbbr();
                    facultyCurrentBranch[i] = list.get(i).getCurrentBranchName() + ", " + list.get(i).getCurrentBranchAbbr();
                }
                customAdapter = new HodFacultyMemberList.CustomAdapter(getActivity(), facultyName, facultyDesignation, facultyContact, facultyEmail, facultyBranch, facultyCurrentBranch);
                listView.setAdapter(customAdapter);
            } else {
                Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblFacultyName, lblFacultyDesignation, lblFacultyEmail, lblFacultyContact, lblFacultyBranch, lblFacultyCurrentBranch;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_sl_no);
            lblFacultyName = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_name);
            lblFacultyDesignation = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_designation);
            lblFacultyEmail = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_contact);
            lblFacultyContact = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_email);
            lblFacultyBranch = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_branch);
            lblFacultyCurrentBranch = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_current_branch);
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
        String[] mFacultyCurrentBranch;
        Context mContext;

        public CustomAdapter(Context context, String[] facultyName, String[] facultyDesignation, String[] facultyContact, String[] facultyEmail, String[] facultyBranch, String[] facultyCurrentBranch) {
            super(context, R.layout.hod_fragment_faculty_member_row, R.id.hod_fragment_faculty_member_list_name, facultyName);
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
            HodFacultyMemberList.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.hod_fragment_faculty_member_row, parent, false);
                viewHolder = new HodFacultyMemberList.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (HodFacultyMemberList.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
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
