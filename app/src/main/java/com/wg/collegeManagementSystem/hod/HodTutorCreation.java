package com.wg.collegeManagementSystem.hod;

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
import com.wg.collegeManagementSystem.app.helper.SessionManager;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.model.Tutor;
import com.wg.collegeManagementSystem.data.repo.FacultyMemberRepo;
import com.wg.collegeManagementSystem.data.repo.TutorRepo;
import com.wg.collegeManagementSystem.model.FacultyMemberList;
import com.wg.collegeManagementSystem.model.TutorList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jerry on 27-06-2017.
 */

public class HodTutorCreation extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = HodTutorCreation.class.getSimpleName();
    public SessionManager session;
    private String URL = AppURL.HOD_TUTOR;
    private String FACULTY_URL = AppURL.HOD_FACULTY_MEMBER;
    private ListView listView;
    private EditText inputClass, inputSection, inputBatch;
    private AppCompatButton hodFragmentBtnInsert;
    private HodTutorCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblClass, newLblClass, lblSection, newLblSection, lblBatch, newLblBatch, newLblFacultyName;
    private int lblTutorId;
    private boolean wrapInScrollView = true;
    private EditText hodFragmentTutorUpdateClass, hodFragmentTutorUpdateSection, hodFragmentTutorUpdateBatch;
    private AppCompatSpinner inputFacultySpinner;
    private AppCompatSpinner hodFragmentTutorUpdateInputTutorNameSpinner;
    private HashMap<Integer, String> facultyNameMap;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hod_fragment_tutor_creation, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        session = new SessionManager(getActivity().getApplicationContext());

        builder = new MaterialDialog.Builder(getActivity());
        inputClass = (EditText) view.findViewById(R.id.hod_fragment_tutor_input_tutor_class);
        inputSection = (EditText) view.findViewById(R.id.hod_fragment_tutor_input_tutor_section);
        inputBatch = (EditText) view.findViewById(R.id.hod_fragment_tutor_input_tutor_batch);
        inputFacultySpinner = (AppCompatSpinner) view.findViewById(R.id.hod_fragment_tutor_input_tutor_name_spinner);
        setFacultyMemberSpinner(0);
        listView = (ListView) view.findViewById(R.id.hod_fragment_tutor_list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.hod_fragment_tutor_update, null);

                lblTutorId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.hod_fragment_tutor_list_tutor_id)).getText().toString());
                lblClass = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_tutor_list_class)).getText().toString();
                lblSection = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_tutor_list_section)).getText().toString();
                lblBatch = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_tutor_list_batch)).getText().toString();

                hodFragmentTutorUpdateClass = (EditText) mView.findViewById(R.id.hod_fragment_tutor_input_update_tutor_class);
                hodFragmentTutorUpdateClass.setText(lblClass);
                hodFragmentTutorUpdateSection = (EditText) mView.findViewById(R.id.hod_fragment_tutor_input_update_tutor_section);
                hodFragmentTutorUpdateSection.setText(lblSection);
                hodFragmentTutorUpdateBatch = (EditText) mView.findViewById(R.id.hod_fragment_tutor_input_update_tutor_batch);
                hodFragmentTutorUpdateBatch.setText(lblBatch);

                hodFragmentTutorUpdateInputTutorNameSpinner = (AppCompatSpinner) mView.findViewById(R.id.hod_fragment_tutor_input_update_tutor_name_spinner);
                setFacultyMemberSpinner(1);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newLblClass = hodFragmentTutorUpdateClass.getText().toString().trim();
                        newLblSection = hodFragmentTutorUpdateSection.getText().toString().trim();
                        newLblBatch = hodFragmentTutorUpdateBatch.getText().toString().trim();
                        newLblFacultyName = hodFragmentTutorUpdateInputTutorNameSpinner.getSelectedItem().toString().trim();
                        update(newLblFacultyName, newLblClass, newLblSection, newLblBatch);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hod_fragment_tutor_list_swipe_refresh_layout);
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

        hodFragmentBtnInsert = (AppCompatButton) view.findViewById(R.id.hod_fragment_tutor_btn_insert);
        hodFragmentBtnInsert.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle("Tutor Allotment");
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
     * For sending request to web server and get data
     */
    public String getFacultyRequest(String url) {
        HashMap<String, String> params = new HashMap<>();
        params.put("branchId", session.getBranchId());
        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        return response;
    }

    /**
     * For showing data inside spinner
     */
    public void setFacultyMemberSpinner(int id) {
        /**
         * 1. "0" For initial spinner
         * 2. "1" For update spinner
         */
        //For creating a array for spinner
        List<String> faclutyMemberSpinnerArray = new ArrayList<String>();
        //For creating a associative array for comparing it with values later to get th key
        facultyNameMap = new HashMap<Integer, String>();
        FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
        List<FacultyMemberList> list = facultyMemberRepo.getFacultyMember(getFacultyRequest(FACULTY_URL));
        if (list.size() > 0) {
            faclutyMemberSpinnerArray.add("Select Faculty");
            facultyNameMap.put(0, "Select Faculty");
            for (int i = 0; i < list.size(); i++) {
                facultyNameMap.put(list.get(i).getFacultyMemberId(), list.get(i).getFacultyMemberName() + ", " + list.get(i).getFacultyMemberEmail());
                faclutyMemberSpinnerArray.add(list.get(i).getFacultyMemberName() + ", " + list.get(i).getFacultyMemberEmail());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, faclutyMemberSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (id == 0) {
            inputFacultySpinner.setAdapter(adapter);
        } else if (id == 1) {
            hodFragmentTutorUpdateInputTutorNameSpinner.setAdapter(adapter);
        }
    }

    /**
     * For getting value from spinner
     */
    public int getFacultyMemberSpinner(String faculty) {
        int facultyId = 0;
        for (Map.Entry entry : facultyNameMap.entrySet()) {
            if (faculty.equals(entry.getValue())) {
                facultyId = Integer.parseInt(entry.getKey().toString());
                break; //breaking because its one to one map
            } else {
                facultyId = 0;
            }
        }
        return facultyId;
    }

    /**
     * For showing data from database
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

            TutorRepo tutorRepo = new TutorRepo();
            List<TutorList> list = tutorRepo.getTutor(response);

            int[] tutorId = new int[list.size()];
            String[] tutorName = new String[list.size()];
            String[] tutorClass = new String[list.size()];
            String[] tutorSection = new String[list.size()];
            String[] tutorBatch = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    tutorId[i] = list.get(i).getTutorId();
                    tutorName[i] = list.get(i).getFacultyMemberName();
                    tutorClass[i] = list.get(i).getTutorClass();
                    tutorSection[i] = list.get(i).getTutorSection();
                    tutorBatch[i] = list.get(i).getTutorBatch();
                }
                customAdapter = new HodTutorCreation.CustomAdapter(getActivity(), tutorId, tutorName, tutorClass, tutorSection, tutorBatch);
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
        String tutorClass = inputClass.getText().toString().trim();
        String tutorSection = inputSection.getText().toString().trim();
        String tutorBatch = inputBatch.getText().toString().trim();
        String tutorName = inputFacultySpinner.getSelectedItem().toString().trim();
        int tutorFacultyId = getFacultyMemberSpinner(tutorName);
        if (tutorClass.isEmpty() || tutorSection.isEmpty() || tutorBatch.isEmpty() || tutorFacultyId == 0) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (tutorFacultyId == lblTutorId) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                String url = URL + "/insert";

                TutorRepo tutorRepo = new TutorRepo();
                Tutor tutor = new Tutor();

                tutor.setTutorFacultyId(tutorFacultyId);
                tutor.setTutorClass(tutorClass);
                tutor.setTutorSection(tutorSection);
                tutor.setTutorBatch(tutorBatch);
                String status = tutorRepo.insert(tutor, url);
                String[] statusArray = status.replaceAll("[{}]", "").split(",");
                if (statusArray[0].equals("\"error\":false")) {
                    inputClass.setText("");
                    inputSection.setText("");
                    inputBatch.setText("");
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
    public void update(String newLblFacultyName, String newLblClass, String newLblSection, String newLblBatch) {
        int tutorFacultyId = getFacultyMemberSpinner(newLblFacultyName);
        int updateTutorNameStatus = 0;
        if (tutorFacultyId != 0) {
            updateTutorNameStatus = 1;
        }
        if (newLblFacultyName.isEmpty() || newLblClass.isEmpty() || newLblSection.isEmpty() || newLblBatch.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            String url = URL;

            TutorRepo tutorRepo = new TutorRepo();
            Tutor tutor = new Tutor();

            tutor.setTutorId(lblTutorId);
            tutor.setTutorFacultyId(tutorFacultyId);
            tutor.setTutorClass(newLblClass);
            tutor.setTutorSection(newLblSection);
            tutor.setTutorBatch(newLblBatch);
            String status = tutorRepo.update(tutor, url, updateTutorNameStatus);
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

        TutorRepo tutorRepo = new TutorRepo();
        Tutor tutor = new Tutor();

        tutor.setTutorId(lblTutorId);
        String status = tutorRepo.delete(tutor, url);
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
        AppCompatTextView lblSlNo, lblTutorId, lblTutorName, lblTutorClass, lblTutorSection, lblTutorBatch;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.hod_fragment_tutor_list_sl_no);
            lblTutorId = (AppCompatTextView) v.findViewById(R.id.hod_fragment_tutor_list_tutor_id);
            lblTutorName = (AppCompatTextView) v.findViewById(R.id.hod_fragment_tutor_list_tutor_name);
            lblTutorClass = (AppCompatTextView) v.findViewById(R.id.hod_fragment_tutor_list_class);
            lblTutorSection = (AppCompatTextView) v.findViewById(R.id.hod_fragment_tutor_list_section);
            lblTutorBatch = (AppCompatTextView) v.findViewById(R.id.hod_fragment_tutor_list_batch);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mTutorId;
        String[] mTutorName;
        String[] mTutorClass;
        String[] mTutorSection;
        String[] mTutorBatch;
        Context mContext;

        public CustomAdapter(Context context, int[] tutorId, String[] tutorName, String[] tutorClass, String[] tutorSection, String[] tutorBatch) {
            super(context, R.layout.hod_fragment_tutor_row, R.id.hod_fragment_tutor_list_tutor_name, tutorName);
            mTutorId = tutorId;
            mTutorName = tutorName;
            mTutorClass = tutorClass;
            mTutorSection = tutorSection;
            mTutorBatch = tutorBatch;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            HodTutorCreation.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.hod_fragment_tutor_row, parent, false);
                viewHolder = new HodTutorCreation.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (HodTutorCreation.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblTutorId.setText(String.valueOf(mTutorId[position]));
            viewHolder.lblTutorName.setText(mTutorName[position]);
            viewHolder.lblTutorClass.setText(mTutorClass[position]);
            viewHolder.lblTutorSection.setText(mTutorSection[position]);
            viewHolder.lblTutorBatch.setText(mTutorBatch[position]);

            return row;
        }
    }
}
