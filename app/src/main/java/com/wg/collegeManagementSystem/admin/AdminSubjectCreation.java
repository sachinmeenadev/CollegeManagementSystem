package com.wg.collegeManagementSystem.admin;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.wg.collegeManagementSystem.app.config.AppURL;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.Subject;
import com.wg.collegeManagementSystem.data.repo.SubjectRepo;
import com.wg.collegeManagementSystem.model.SubjectList;

import java.util.List;

/**
 * Created by Jerry on 20-06-2017.
 */

public class AdminSubjectCreation extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AdminSubjectCreation.class.getSimpleName();
    private String URL = AppURL.ADMIN_SUBJECT;
    private ListView listView;
    private EditText inputSubjectName, inputSubjectAbbr, inputSubjectCode;
    private AppCompatButton adminFragmentSubjectBtnInsert;
    private AdminSubjectCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblSubjectName, newLblSubjectName, lblSubjectAbbr, newLblSubjectAbbr, lblSubjectCode, newLblSubjectCode;
    private int lblSubjectId;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentSubjectUpdateInputSubjectName, adminFragmentSubjectUpdateInputSubjectAbbr, adminFragmentSubjectUpdateInputSubjectCode;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_subject_creation, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        builder = new MaterialDialog.Builder(getActivity());
        inputSubjectName = (EditText) view.findViewById(R.id.admin_fragment_subject_input_subject_name);
        inputSubjectAbbr = (EditText) view.findViewById(R.id.admin_fragment_subject_input_subject_abbr);
        inputSubjectCode = (EditText) view.findViewById(R.id.admin_fragment_subject_input_subject_code);
        listView = (ListView) view.findViewById(R.id.admin_fragment_subject_list);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_subject_update, null);

                lblSubjectId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.admin_fragment_subject_list_subject_id)).getText().toString());
                lblSubjectName = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_subject_list_subject_name)).getText().toString();
                lblSubjectAbbr = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_subject_list_subject_abbr)).getText().toString();
                lblSubjectCode = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_subject_list_subject_code)).getText().toString();
                adminFragmentSubjectUpdateInputSubjectName = (EditText) mView.findViewById(R.id.admin_fragment_subject_update_input_subject_name);
                adminFragmentSubjectUpdateInputSubjectName.setText(lblSubjectName);
                adminFragmentSubjectUpdateInputSubjectAbbr = (EditText) mView.findViewById(R.id.admin_fragment_subject_update_input_subject_abbr);
                adminFragmentSubjectUpdateInputSubjectAbbr.setText(lblSubjectAbbr);
                adminFragmentSubjectUpdateInputSubjectCode = (EditText) mView.findViewById(R.id.admin_fragment_subject_update_input_subject_code);
                adminFragmentSubjectUpdateInputSubjectCode.setText(lblSubjectCode);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newLblSubjectName = adminFragmentSubjectUpdateInputSubjectName.getText().toString().trim();
                        newLblSubjectAbbr = adminFragmentSubjectUpdateInputSubjectAbbr.getText().toString().trim();
                        newLblSubjectCode = adminFragmentSubjectUpdateInputSubjectCode.getText().toString().trim();
                        update(newLblSubjectName, newLblSubjectAbbr, newLblSubjectCode);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.admin_fragment_subject_list_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        show_data();
                                    }
                                }
        );

        adminFragmentSubjectBtnInsert = (AppCompatButton) view.findViewById(R.id.admin_fragment_subject_btn_insert);
        adminFragmentSubjectBtnInsert.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle("Subject Creation");
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

            SubjectRepo subjectRepo = new SubjectRepo();
            List<SubjectList> list = subjectRepo.getSubject(response);

            int[] subjectId = new int[list.size()];
            String[] subjectName = new String[list.size()];
            String[] subjectAbbr = new String[list.size()];
            String[] subjectCode = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    subjectId[i] = list.get(i).getSubjectId();
                    subjectName[i] = list.get(i).getSubjectName();
                    subjectAbbr[i] = list.get(i).getSubjectAbbr();
                    subjectCode[i] = list.get(i).getSubjectCode();
                }
                customAdapter = new AdminSubjectCreation.CustomAdapter(getActivity(), subjectId, subjectName, subjectAbbr, subjectCode);
                listView.setAdapter(customAdapter);
            } else {
                Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * For inserting in database
     */
    public void insert() {
        String subjectName = inputSubjectName.getText().toString().trim();
        String subjectAbbr = inputSubjectAbbr.getText().toString().trim();
        String subjectCode = inputSubjectCode.getText().toString().trim();

        if (subjectName.isEmpty() || subjectAbbr.isEmpty() || subjectCode.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (subjectName.equals(lblSubjectName) && subjectAbbr.equals(lblSubjectAbbr) && subjectCode.equals(lblSubjectCode)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                String url = URL;

                SubjectRepo subjectRepo = new SubjectRepo();
                Subject subject = new Subject();

                subject.setSubjectName(subjectName);
                subject.setSubjectAbbr(subjectAbbr);
                subject.setSubjectCode(subjectCode);
                String status = subjectRepo.insert(subject, url);
                String[] statusArray = status.replaceAll("[{}]", "").split(",");
                if (statusArray[0].equals("\"error\":false")) {
                    inputSubjectName.setText("");
                    inputSubjectAbbr.setText("");
                    inputSubjectCode.setText("");
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
    public void update(String newLblSubjectName, String newLblSubjectAbbr, String newLblSubjectCode) {
        if (newLblSubjectName.isEmpty() || newLblSubjectAbbr.isEmpty() || newLblSubjectCode.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            String url = URL;

            SubjectRepo subjectRepo = new SubjectRepo();
            Subject subject = new Subject();

            subject.setSubjectId(lblSubjectId);
            subject.setSubjectName(newLblSubjectName);
            subject.setSubjectAbbr(newLblSubjectAbbr);
            subject.setSubjectCode(newLblSubjectCode);
            String status = subjectRepo.update(subject, url);
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
     * For deleting in database
     */
    public void delete() {
        String url = URL;

        SubjectRepo subjectRepo = new SubjectRepo();
        Subject subject = new Subject();

        subject.setSubjectId(lblSubjectId);
        String status = subjectRepo.delete(subject, url);
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
        AppCompatTextView lblSlNo, lblSubjectId, lblSubjectName, lblSubjectAbbr, lblSubjectCode;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_subject_list_sl_no);
            lblSubjectId = (AppCompatTextView) v.findViewById(R.id.admin_fragment_subject_list_subject_id);
            lblSubjectName = (AppCompatTextView) v.findViewById(R.id.admin_fragment_subject_list_subject_name);
            lblSubjectAbbr = (AppCompatTextView) v.findViewById(R.id.admin_fragment_subject_list_subject_abbr);
            lblSubjectCode = (AppCompatTextView) v.findViewById(R.id.admin_fragment_subject_list_subject_code);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mSubjectId;
        String[] mSubjectName;
        String[] mSubjectAbbr;
        String[] mSubjectCode;
        Context mContext;

        public CustomAdapter(Context context, int[] subjectId, String[] subjectName, String[] subjectAbbr, String[] subjectCode) {
            super(context, R.layout.admin_fragment_subject_row, R.id.admin_fragment_subject_list_subject_name, subjectName);
            mSubjectId = subjectId;
            mSubjectName = subjectName;
            mSubjectAbbr = subjectAbbr;
            mSubjectCode = subjectCode;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            AdminSubjectCreation.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.admin_fragment_subject_row, parent, false);
                viewHolder = new AdminSubjectCreation.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (AdminSubjectCreation.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblSubjectId.setText(String.valueOf(mSubjectId[position]));
            viewHolder.lblSubjectName.setText(mSubjectName[position]);
            viewHolder.lblSubjectAbbr.setText(mSubjectAbbr[position]);
            viewHolder.lblSubjectCode.setText(mSubjectCode[position]);

            return row;
        }
    }
}