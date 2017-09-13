package com.wg.collegeManagementSystem.hod;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.app.config.AppURL;
import com.wg.collegeManagementSystem.app.helper.SessionManager;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.repo.StudentSearchRepo;
import com.wg.collegeManagementSystem.model.StudentSearchList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jerry on 27-06-2017.
 */

public class HodStudentList extends Fragment {
    public static final String TAG = HodStudentList.class.getSimpleName();
    public SessionManager session;
    private String URL = AppURL.HOD_STUDENT;
    private ListView listView;
    private AppCompatButton hodFragmentBtnSearch;
    private HodStudentList.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblStudentName, lblStudentRegNo, lblStudentClass, lblStudentBatch, lblStudentContact;
    private int lblStudentId;
    private boolean wrapInScrollView = true;
    private AppCompatTextView hodFragmentStudentName, hodFragmentStudentRegNo, hodFragmentStudentClass, hodFragmentStudentBatch, hodFragmentStudentContact;
    private AppCompatSpinner inputClassSpinner, inputSectionSpinner, inputBatchSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hod_fragment_student_filter_list, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        session = new SessionManager(getActivity().getApplicationContext());

        builder = new MaterialDialog.Builder(getActivity());

        inputClassSpinner = (AppCompatSpinner) view.findViewById(R.id.hod_fragment_student_list_input_class_spinner);
        inputSectionSpinner = (AppCompatSpinner) view.findViewById(R.id.hod_fragment_student_list_input_section_spinner);
        inputBatchSpinner = (AppCompatSpinner) view.findViewById(R.id.hod_fragment_student_list_input_batch_spinner);
        setClassSectionBatchSpinner();

        listView = (ListView) view.findViewById(R.id.hod_fragment_student_filter_list);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.hod_fragment_student_search_popup_builder, null);

                lblStudentId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.hod_fragment_student_search_list_student_id)).getText().toString());
                lblStudentName = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_student_search_list_student_name)).getText().toString();
                lblStudentRegNo = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_student_search_list_registration_no)).getText().toString();
                lblStudentClass = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_student_search_list_class)).getText().toString();
                lblStudentBatch = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_student_search_list_batch)).getText().toString();
                lblStudentContact = ((AppCompatTextView) view.findViewById(R.id.hod_fragment_student_search_list_contact)).getText().toString();

                hodFragmentStudentName = (AppCompatTextView) mView.findViewById(R.id.hod_fragment_student_search_popup_student_name);
                hodFragmentStudentRegNo = (AppCompatTextView) mView.findViewById(R.id.hod_fragment_student_search_popup_student_registration_no);
                hodFragmentStudentClass = (AppCompatTextView) mView.findViewById(R.id.hod_fragment_student_search_popup_student_class);
                hodFragmentStudentBatch = (AppCompatTextView) mView.findViewById(R.id.hod_fragment_student_search_popup_student_batch);
                hodFragmentStudentContact = (AppCompatTextView) mView.findViewById(R.id.hod_fragment_student_search_popup_student_contact);

                hodFragmentStudentName.setText(lblStudentName);
                hodFragmentStudentRegNo.setText(lblStudentRegNo);
                hodFragmentStudentClass.setText(lblStudentClass);
                hodFragmentStudentBatch.setText(lblStudentBatch);
                hodFragmentStudentContact.setText(lblStudentContact);

                builder.title("Action");
                builder.positiveText("Open Profile");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Fragment hodStudentProfile = new HodStudentProfile();

                        Bundle args = new Bundle();
                        args.putString("studentId", String.valueOf(lblStudentId));
                        hodStudentProfile.setArguments(args);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_hod_frame, hodStudentProfile);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
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


        hodFragmentBtnSearch = (AppCompatButton) view.findViewById(R.id.hod_fragment_student_list_search_btn_search);
        hodFragmentBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Student List Searching");
    }

    /**
     * For sending request to web server and get data
     */
    public String sendBranchRequest(String url) {
        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.getUrlData(url + "/list/" + session.getBranchId());
        return response;
    }

    public String sendRequest(String url, String studentSem, String studentSemSection, String studentSemBatch) {
        HashMap<String, String> params = new HashMap<>();
        params.put("studentSem", studentSem);
        params.put("studentSemSection", studentSemSection);
        params.put("studentSemBatch", studentSemBatch);

        String response = null;
        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        return response;
    }

    /**
     * For showing data inside spinner
     */
    public void setClassSectionBatchSpinner() {
        //For creating a array for spinner
        List<String> classSpinnerArray = new ArrayList<String>();
        List<String> sectionSpinnerArray = new ArrayList<String>();
        List<String> batchSpinnerArray = new ArrayList<String>();

        StudentSearchRepo studentSearchRepo = new StudentSearchRepo();
        List<StudentSearchList> list = studentSearchRepo.getStudentSearch(sendBranchRequest(URL));
        if (list.size() > 0) {
            classSpinnerArray.add("Select Class");
            sectionSpinnerArray.add("Select Section");
            batchSpinnerArray.add("Select batch");

            for (int i = 0; i < list.size(); i++) {
                classSpinnerArray.add(list.get(i).getStudentSem());
                sectionSpinnerArray.add(list.get(i).getStudentSemSection());
                batchSpinnerArray.add(list.get(i).getStudentSemBatch());
            }
        }
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, classSpinnerArray);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> sectionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sectionSpinnerArray);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> batchAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, batchSpinnerArray);
        batchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputClassSpinner.setAdapter(classAdapter);
        inputSectionSpinner.setAdapter(sectionAdapter);
        inputBatchSpinner.setAdapter(batchAdapter);

    }

    /**
     * For showing data from database
     */
    public void search() {
        String studentSem = inputClassSpinner.getSelectedItem().toString().trim();
        String studentSemSection = inputSectionSpinner.getSelectedItem().toString().trim();
        String studentSemBatch = inputBatchSpinner.getSelectedItem().toString().trim();
        if (studentSem.equals("Select Class") || studentSemSection.equals("Select Section") || studentSemBatch.equals("Select Batch")) {
            Toast.makeText(getActivity(), "Please select all field", Toast.LENGTH_SHORT).show();
        } else {
            String url = URL + "/list";
            String response = sendRequest(url, studentSem, studentSemSection, studentSemBatch);
            String[] responseArray = response.split(" ");
            if (responseArray[0].equals("ERROR")) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
            } else {
                StudentSearchRepo studentSearchRepo = new StudentSearchRepo();
                List<StudentSearchList> list = studentSearchRepo.getStudentSearch(response);

                int[] studentId = new int[list.size()];
                String[] studentName = new String[list.size()];
                String[] studentRegNo = new String[list.size()];
                String[] studentClass = new String[list.size()];
                String[] studentBatch = new String[list.size()];
                String[] studentContactNo = new String[list.size()];
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        studentId[i] = list.get(i).getStudentId();
                        studentName[i] = list.get(i).getStudentName();
                        studentRegNo[i] = list.get(i).getStudentRegNumber();
                        studentClass[i] = list.get(i).getStudentSem();
                        studentBatch[i] = list.get(i).getStudentSemBatch();
                        studentContactNo[i] = list.get(i).getStudentContact();
                    }
                    customAdapter = new HodStudentList.CustomAdapter(getActivity(), studentId, studentName, studentRegNo, studentClass, studentBatch, studentContactNo);
                    listView.setAdapter(customAdapter);
                } else {
                    Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblStudentId, lblStudentName, lblStudentRegNo, lblStudentClass, lblStudentBatch, lblStudentContact;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.hod_fragment_student_search_list_sl_no);
            lblStudentId = (AppCompatTextView) v.findViewById(R.id.hod_fragment_student_search_list_student_id);
            lblStudentName = (AppCompatTextView) v.findViewById(R.id.hod_fragment_student_search_list_student_name);
            lblStudentRegNo = (AppCompatTextView) v.findViewById(R.id.hod_fragment_student_search_list_registration_no);
            lblStudentClass = (AppCompatTextView) v.findViewById(R.id.hod_fragment_student_search_list_class);
            lblStudentBatch = (AppCompatTextView) v.findViewById(R.id.hod_fragment_student_search_list_batch);
            lblStudentContact = (AppCompatTextView) v.findViewById(R.id.hod_fragment_student_search_list_contact);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mStudentId;
        String[] mStudentName;
        String[] mStudentRegNo;
        String[] mStudentClass;
        String[] mStudentBatch;
        String[] mStudentContact;
        Context mContext;

        public CustomAdapter(Context context, int[] studentId, String[] studentName, String[] studentRegNo, String[] studentClass, String[] studentBatch, String[] studentContact) {
            super(context, R.layout.hod_fragment_student_serach_row, R.id.hod_fragment_student_search_list_student_name, studentName);
            mStudentId = studentId;
            mStudentName = studentName;
            mStudentRegNo = studentRegNo;
            mStudentClass = studentClass;
            mStudentBatch = studentBatch;
            mStudentContact = studentContact;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            HodStudentList.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.hod_fragment_student_serach_row, parent, false);
                viewHolder = new HodStudentList.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (HodStudentList.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblStudentId.setText(String.valueOf(mStudentId[position]));
            viewHolder.lblStudentName.setText(mStudentName[position]);
            viewHolder.lblStudentRegNo.setText(mStudentRegNo[position]);
            viewHolder.lblStudentClass.setText(mStudentClass[position] + " Sem ");
            viewHolder.lblStudentBatch.setText(mStudentBatch[position]);
            viewHolder.lblStudentContact.setText(mStudentContact[position]);

            return row;
        }
    }
}
