package in.webguides.collegeManagementSystem.hod;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.webguides.collegeManagementSystem.R;
import in.webguides.collegeManagementSystem.app.config.AppURL;
import in.webguides.collegeManagementSystem.app.helper.SessionManager;
import in.webguides.collegeManagementSystem.app.helper.UrlRequest;
import in.webguides.collegeManagementSystem.data.model.FacultyMemberAllotClass;
import in.webguides.collegeManagementSystem.data.repo.FacultyMemberAllotClassRepo;
import in.webguides.collegeManagementSystem.data.repo.FacultyMemberRepo;
import in.webguides.collegeManagementSystem.data.repo.SubjectRepo;
import in.webguides.collegeManagementSystem.model.FacultyMemberAllotClassList;
import in.webguides.collegeManagementSystem.model.FacultyMemberList;
import in.webguides.collegeManagementSystem.model.SubjectList;

/**
 * Created by Jerry on 27-06-2017.
 */

public class HodFacultyMemberAllotClass extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = HodFacultyMemberAllotClass.class.getSimpleName();
    public SessionManager session;
    private String URL = AppURL.HOD_FACULTY_MEMBER_ALLOT_CLASS_SUBJECT;
    private String FACULTY_URL = AppURL.HOD_FACULTY_MEMBER;
    private String SUBJECT_URL = AppURL.ADMIN_SUBJECT;
    private ListView listView;
    private EditText inputClass, inputSection, inputBatch;
    private AppCompatButton hodFragmentBtnInsert;
    private HodFacultyMemberAllotClass.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblClass, newLblClass, lblSection, newLblSection, lblBatch, newLblBatch, newLblFacultyName, newLblSubjectName;
    private EditText hodFragmentFacultyClassAllotmentUpdateClass, hodFragmentFacultyClassAllotmentUpdateSection, hodFragmentFacultyClassAllotmentUpdateBatch;
    private int lblFmsId;
    private boolean wrapInScrollView = true;
    private AppCompatSpinner inputFacultySpinner, inputSubjectSpinner;
    private AppCompatSpinner hodFragmentFacultyClassAllotmentUpdateInputFacultyNameSpinner, hodFragmentFacultyClassAllotmentUpdateInputSubjectNameSpinner;
    private HashMap<Integer, String> facultyNameMap;
    private HashMap<Integer, String> subjectNameMap;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hod_fragment_faculty_member_class_allot, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        session = new SessionManager(getActivity().getApplicationContext());

        builder = new MaterialDialog.Builder(getActivity());
        inputClass = (EditText) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_class);
        inputSection = (EditText) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_section);
        inputBatch = (EditText) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_batch);

        inputFacultySpinner = (AppCompatSpinner) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_faculty_spinner);
        inputSubjectSpinner = (AppCompatSpinner) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_subject_spinner);
        setHodFacultyAllotmentSpinner(0);

        listView = (ListView) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.hod_fragment_faculty_member_class_allot_update, null);

                lblFmsId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_fms_id)).getText().toString());
                lblClass = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_class)).getText().toString();
                lblSection = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_section)).getText().toString();
                lblBatch = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_batch)).getText().toString();

                hodFragmentFacultyClassAllotmentUpdateClass = (EditText) mView.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_update_class);
                hodFragmentFacultyClassAllotmentUpdateClass.setText(lblClass);
                hodFragmentFacultyClassAllotmentUpdateSection = (EditText) mView.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_update_section);
                hodFragmentFacultyClassAllotmentUpdateSection.setText(lblSection);
                hodFragmentFacultyClassAllotmentUpdateBatch = (EditText) mView.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_update_batch);
                hodFragmentFacultyClassAllotmentUpdateBatch.setText(lblBatch);

                hodFragmentFacultyClassAllotmentUpdateInputFacultyNameSpinner = (AppCompatSpinner) mView.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_update_faculty_spinner);
                hodFragmentFacultyClassAllotmentUpdateInputSubjectNameSpinner = (AppCompatSpinner) mView.findViewById(R.id.hod_fragment_faculty_member_class_allot_input_update_subject_spinner);
                setHodFacultyAllotmentSpinner(1);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newLblClass = hodFragmentFacultyClassAllotmentUpdateClass.getText().toString().trim();
                        newLblSection = hodFragmentFacultyClassAllotmentUpdateSection.getText().toString().trim();
                        newLblBatch = hodFragmentFacultyClassAllotmentUpdateBatch.getText().toString().trim();

                        newLblFacultyName = hodFragmentFacultyClassAllotmentUpdateInputFacultyNameSpinner.getSelectedItem().toString().trim();
                        newLblSubjectName = hodFragmentFacultyClassAllotmentUpdateInputSubjectNameSpinner.getSelectedItem().toString().trim();

                        update(newLblFacultyName, newLblSubjectName, newLblClass, newLblSection, newLblBatch);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_swipe_refresh_layout);
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
        show_data();
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
     * For sending request to web server and get data
     */
    public String getSubjectRequest(String url) {
        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.getUrlData(url);
        return response;
    }

    /**
     * For showing data inside spinner
     */
    public void setHodFacultyAllotmentSpinner(int id) {
        /**
         * 1. "0" For initial spinner
         * 2. "1" For update spinner
         */
        //For creating a array for spinner
        List<String> faclutyMemberSpinnerArray = new ArrayList<String>();
        List<String> subjectSpinnerArray = new ArrayList<String>();
        //For creating a associative array for comparing it with values later to get th key
        facultyNameMap = new HashMap<Integer, String>();
        subjectNameMap = new HashMap<Integer, String>();

        FacultyMemberRepo facultyMemberRepo = new FacultyMemberRepo();
        SubjectRepo subjectRepo = new SubjectRepo();

        List<FacultyMemberList> list = facultyMemberRepo.getFacultyMember(getFacultyRequest(FACULTY_URL));
        List<SubjectList> subjectList = subjectRepo.getSubject(getSubjectRequest(SUBJECT_URL));

        if (list.size() > 0) {
            faclutyMemberSpinnerArray.add("Select Faculty");
            facultyNameMap.put(0, "Select Faculty");
            for (int i = 0; i < list.size(); i++) {
                facultyNameMap.put(list.get(i).getFacultyMemberId(), list.get(i).getFacultyMemberName() + ", " + list.get(i).getFacultyMemberEmail());
                faclutyMemberSpinnerArray.add(list.get(i).getFacultyMemberName() + ", " + list.get(i).getFacultyMemberEmail());
            }
        }

        if (subjectList.size() > 0) {
            subjectSpinnerArray.add("Select Subject");
            subjectNameMap.put(0, "Select Subject");
            for (int i = 0; i < subjectList.size(); i++) {
                subjectNameMap.put(subjectList.get(i).getSubjectId(), subjectList.get(i).getSubjectAbbr() + ", " + subjectList.get(i).getSubjectCode());
                subjectSpinnerArray.add(subjectList.get(i).getSubjectAbbr() + ", " + subjectList.get(i).getSubjectCode());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, faclutyMemberSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjectSpinnerArray);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (id == 0) {
            inputFacultySpinner.setAdapter(adapter);
            inputSubjectSpinner.setAdapter(subjectAdapter);
        } else if (id == 1) {
            hodFragmentFacultyClassAllotmentUpdateInputFacultyNameSpinner.setAdapter(adapter);
            hodFragmentFacultyClassAllotmentUpdateInputSubjectNameSpinner.setAdapter(subjectAdapter);
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

    public int getSubjectNameSpinner(String subject) {
        int subjectId = 0;
        for (Map.Entry entry : subjectNameMap.entrySet()) {
            if (subject.equals(entry.getValue())) {
                subjectId = Integer.parseInt(entry.getKey().toString());
                break; //breaking because its one to one map
            } else {
                subjectId = 0;
            }
        }
        return subjectId;
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

            FacultyMemberAllotClassRepo facultyMemberAllotClassRepo = new FacultyMemberAllotClassRepo();
            List<FacultyMemberAllotClassList> list = facultyMemberAllotClassRepo.getFacultyMemberAllotClass(response);

            int[] facultyFmsId = new int[list.size()];
            String[] facultyName = new String[list.size()];
            String[] facultySubjectName = new String[list.size()];
            String[] facultyClass = new String[list.size()];
            String[] facultySection = new String[list.size()];
            String[] facultyBatch = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    facultyFmsId[i] = list.get(i).getFmsId();
                    facultyName[i] = list.get(i).getFacultyMemberName();
                    facultySubjectName[i] = list.get(i).getSubjectAbbr() + " , " + list.get(i).getSubjectCode();
                    facultyClass[i] = list.get(i).getFmsClass();
                    facultySection[i] = list.get(i).getFmsSection();
                    facultyBatch[i] = list.get(i).getFmsBatch();
                }
                customAdapter = new HodFacultyMemberAllotClass.CustomAdapter(getActivity(), facultyFmsId, facultyName, facultySubjectName, facultyClass, facultySection, facultyBatch);
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
        String fmsClass = inputClass.getText().toString().trim();
        String fmsSection = inputSection.getText().toString().trim();
        String fmsBatch = inputBatch.getText().toString().trim();
        String fmsFacultyName = inputFacultySpinner.getSelectedItem().toString().trim();
        String fmsSubjectName = inputSubjectSpinner.getSelectedItem().toString().trim();
        int fmsFacultyId = getFacultyMemberSpinner(fmsFacultyName);
        int fmsSubjectId = getFacultyMemberSpinner(fmsSubjectName);
        if (fmsClass.isEmpty() || fmsSection.isEmpty() || fmsBatch.isEmpty() || fmsFacultyId == 0 || fmsSubjectId == 0) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            String url = URL + "/insert";

            FacultyMemberAllotClassRepo facultyMemberAllotClassRepo = new FacultyMemberAllotClassRepo();
            FacultyMemberAllotClass facultyMemberAllotClass = new FacultyMemberAllotClass();

            facultyMemberAllotClass.setFmsFacultyId(fmsFacultyId);
            facultyMemberAllotClass.setFmsSubjectId(fmsSubjectId);
            facultyMemberAllotClass.setFmsClass(fmsClass);
            facultyMemberAllotClass.setFmsSection(fmsSection);
            facultyMemberAllotClass.setFmsBatch(fmsBatch);
            String status = facultyMemberAllotClassRepo.insert(facultyMemberAllotClass, url);
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

    /**
     * For updating in database
     */
    public void update(String newLblFacultyName, String newLblSubjectName, String newLblClass, String newLblSection, String newLblBatch) {
        int fmsFacultyId = getFacultyMemberSpinner(newLblFacultyName);
        int fmsSubjectId = getSubjectNameSpinner(newLblSubjectName);
        int updateFacultyNameStatus = 0;
        int updateSubjectNameStatus = 0;
        if (fmsFacultyId != 0) {
            updateFacultyNameStatus = 1;
        }
        if (fmsSubjectId != 0) {
            updateSubjectNameStatus = 1;
        }
        if (newLblClass.isEmpty() || newLblSection.isEmpty() || newLblBatch.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            String url = URL;

            FacultyMemberAllotClassRepo facultyMemberAllotClassRepo = new FacultyMemberAllotClassRepo();
            FacultyMemberAllotClass facultyMemberAllotClass = new FacultyMemberAllotClass();

            facultyMemberAllotClass.setFmsId(lblFmsId);
            facultyMemberAllotClass.setFmsFacultyId(fmsFacultyId);
            facultyMemberAllotClass.setFmsSubjectId(fmsSubjectId);
            facultyMemberAllotClass.setFmsClass(newLblClass);
            facultyMemberAllotClass.setFmsSection(newLblSection);
            facultyMemberAllotClass.setFmsBatch(newLblBatch);
            String status = facultyMemberAllotClassRepo.update(facultyMemberAllotClass, url, updateFacultyNameStatus, updateSubjectNameStatus);
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

        FacultyMemberAllotClassRepo facultyMemberAllotClassRepo = new FacultyMemberAllotClassRepo();
        FacultyMemberAllotClass facultyMemberAllotClass = new FacultyMemberAllotClass();

        facultyMemberAllotClass.setFmsId(lblFmsId);
        String status = facultyMemberAllotClassRepo.delete(facultyMemberAllotClass, url);
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
        AppCompatTextView lblSlNo, lblFacultyFmsId, lblFacultyName, lblFacultySubjectName, lblFacultyClass, lblFacultySection, lblFacultyBatch;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_sl_no);
            lblFacultyFmsId = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_fms_id);
            lblFacultyName = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_faculty_name);
            lblFacultySubjectName = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_subject);
            lblFacultyClass = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_class);
            lblFacultySection = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_section);
            lblFacultyBatch = (AppCompatTextView) v.findViewById(R.id.hod_fragment_faculty_member_class_allot_list_batch);
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
            HodFacultyMemberAllotClass.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.hod_fragment_faculty_member_class_allot_row, parent, false);
                viewHolder = new HodFacultyMemberAllotClass.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (HodFacultyMemberAllotClass.ViewHolder) row.getTag();
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
