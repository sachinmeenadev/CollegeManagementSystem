package com.wg.collegeManagementSystem.hod;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.app.config.AppURL;
import com.wg.collegeManagementSystem.app.helper.SessionManager;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;

import java.util.HashMap;

/**
 * Created by Jerry on 27-06-2017.
 */

public class HodFacultyMemberAllotClassSubject extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = HodFacultyMemberAllotClassSubject.class.getSimpleName();
    public SessionManager session;
    private String URL = AppURL.HOD_FACULTY_MEMBER_ALLOT_CLASS_SUBJECT;
    private ListView listView;
    private EditText inputClass, inputSection, inputBatch;
    private AppCompatButton hodFragmentBtnInsert;
    private HodFacultyMemberAllotClassSubject.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblClass, lblSection, lblBatch;
    private int lblFmsId;
    private boolean wrapInScrollView = true;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hod_fragment_faculty_member_class_allot, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        session = new SessionManager(getActivity().getApplicationContext());

        listView = (ListView) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.indigo, R.color.lime);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        //show_data();
                                    }
                                }
        );
        //show_data();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Faculty Member's Class Allotment");
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        //show_data();
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
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblFacultyFmsId, lblFacultyName, lblFacultySubjectName, lblFacultyClass, lblFacultySection, lblFacultyBatch;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_sl_no);
            lblFacultyFmsId = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_name);
            lblFacultyName = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_designation);
            lblFacultySubjectName = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_contact);
            lblFacultyClass = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_email);
            lblFacultySection = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_branch);
            lblFacultyBatch = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_list_current_branch);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mFacultyFmsId;
        String[] mFacultyName;
        String[] mFacultySubjectName;
        String[] mFacultyClass;
        String[] mFacultySection;
        String[] mFacultyBatch;
        Context mContext;

        public CustomAdapter(Context context, int[] facultyFmsId, String[] facultyName, String[] facultySubjectName, String[] facultyClass, String[] facultySection, String[] facultyBatch) {
            super(context, R.layout.hod_fragment_faculty_member_class_allot_row, R.id.hod_fragment_faculty_member_class_allot_list_faculty_name, facultyName);
            mFacultyFmsId = facultyFmsId;
            mFacultyName = facultyName;
            mFacultySubjectName = facultySubjectName;
            mFacultyClass = facultyClass;
            mFacultySection = facultySection;
            mFacultyBatch = facultyBatch;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            HodFacultyMemberAllotClassSubject.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.hod_fragment_faculty_member_class_allot_row, parent, false);
                viewHolder = new HodFacultyMemberAllotClassSubject.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (HodFacultyMemberAllotClassSubject.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblFacultyFmsId.setText(String.valueOf(mFacultyFmsId[position]));
            viewHolder.lblFacultyName.setText(mFacultyName[position]);
            viewHolder.lblFacultySubjectName.setText(mFacultySubjectName[position]);
            viewHolder.lblFacultyClass.setText(mFacultyClass[position]);
            viewHolder.lblFacultySection.setText(mFacultySection[position]);
            viewHolder.lblFacultyBatch.setText(mFacultyBatch[position]);

            return row;
        }
    }
}
