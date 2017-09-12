package com.wg.collegeManagementSystem.hod;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.app.config.AppURL;
import com.wg.collegeManagementSystem.app.helper.RoundedTransformation;
import com.wg.collegeManagementSystem.app.helper.UrlRequest;
import com.wg.collegeManagementSystem.data.repo.StudentProfileRepo;
import com.wg.collegeManagementSystem.model.StudentProfileList;

import java.util.List;

/**
 * Created by Jerry on 12-09-2017.
 */

public class HodStudentProfile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = HodStudentProfile.class.getSimpleName();
    AppCompatTextView lblStudentName, lblStudentRegNo, lblCollegeBranchName, lblStudentResidentType, lblStudentClass, lblStudentBatch, lblStudentSemSection, lblStudentEmail, lblStudentContact, lblStudentFatherName, lblStudentFatherContact, lblStudentFatherEmail, lblStudentFatherOccupation, lblStudentMotherName, lblStudentMotherContact, lblStudentMotherEmail, lblStudentMotherOccupation, lblStudentLocalGuardianName, lblStudentLocalGuardianContact, lblStudentLocalGuardianEmail, lblStudentLocalGuardianRelation, lblStudentLocalAddress, lblStudentPermanentAddress, lblStudentCity, lblStudentState, lblStudentPinCode, lblStudentHobbies;
    private String URL = AppURL.HOD_STUDENT;
    private String studentId;
    private ImageView imageView;
    private String studentName, studentRegNo, collegeBranchName, studentClass, studentBatch, studentSemSection, studentEmail, studentContact, studentFatherName, studentFatherContact, studentFatherEmail, studentFatherOccupation, studentMotherName, studentMotherContact, studentMotherEmail, studentMotherOccupation, studentLocalGuardianName, studentLocalGuardianContact, studentLocalGuardianEmail, studentLocalGuardianRelation, studentResidentType, studentLocalAddress, studentPermanentAddress, studentCity, studentState, studentPinCode, studentHobbies;
    private String studentProfilePicture = "";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.hod_fragment_student_profile, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        studentId = getArguments().getString("studentId");
        imageView = (ImageView) view.findViewById(R.id.student_profile_img);
        lblStudentName = (AppCompatTextView) view.findViewById(R.id.student_profile_name);
        lblStudentRegNo = (AppCompatTextView) view.findViewById(R.id.student_profile_registration_number);
        lblCollegeBranchName = (AppCompatTextView) view.findViewById(R.id.student_profile_branch);
        lblStudentResidentType = (AppCompatTextView) view.findViewById(R.id.student_profile_resident_type);
        lblStudentClass = (AppCompatTextView) view.findViewById(R.id.student_profile_semester);
        lblStudentBatch = (AppCompatTextView) view.findViewById(R.id.student_profile_batch);
        lblStudentSemSection = (AppCompatTextView) view.findViewById(R.id.student_profile_section);
        lblStudentEmail = (AppCompatTextView) view.findViewById(R.id.student_profile_email);
        lblStudentContact = (AppCompatTextView) view.findViewById(R.id.student_profile_contact);
        lblStudentFatherName = (AppCompatTextView) view.findViewById(R.id.student_profile_father_name);
        lblStudentFatherContact = (AppCompatTextView) view.findViewById(R.id.student_profile_father_contact);
        lblStudentFatherEmail = (AppCompatTextView) view.findViewById(R.id.student_profile_father_email);
        lblStudentFatherOccupation = (AppCompatTextView) view.findViewById(R.id.student_profile_father_occupation);
        lblStudentMotherName = (AppCompatTextView) view.findViewById(R.id.student_profile_mother_name);
        lblStudentMotherContact = (AppCompatTextView) view.findViewById(R.id.student_profile_mother_contact);
        lblStudentMotherEmail = (AppCompatTextView) view.findViewById(R.id.student_profile_mother_email);
        lblStudentMotherOccupation = (AppCompatTextView) view.findViewById(R.id.student_profile_mother_occupation);
        lblStudentLocalGuardianName = (AppCompatTextView) view.findViewById(R.id.student_profile_guardian_name);
        lblStudentLocalGuardianContact = (AppCompatTextView) view.findViewById(R.id.student_profile_guardian_contact);
        lblStudentLocalGuardianEmail = (AppCompatTextView) view.findViewById(R.id.student_profile_guardian_email);
        lblStudentLocalGuardianRelation = (AppCompatTextView) view.findViewById(R.id.student_profile_guardian_relation);
        lblStudentLocalAddress = (AppCompatTextView) view.findViewById(R.id.student_profile_local_address);
        lblStudentPermanentAddress = (AppCompatTextView) view.findViewById(R.id.student_profile_permanent_address);
        lblStudentCity = (AppCompatTextView) view.findViewById(R.id.student_profile_city);
        lblStudentState = (AppCompatTextView) view.findViewById(R.id.student_profile_state);
        lblStudentPinCode = (AppCompatTextView) view.findViewById(R.id.student_profile_pin_code);
        lblStudentHobbies = (AppCompatTextView) view.findViewById(R.id.student_profile_hobbies);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.hod_fragment_student_profile_view_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.indigo, R.color.lime);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        showStudent();
                                    }
                                }
        );
        showStudent();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Student Profile");
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        showStudent();
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
     * For showing data from database
     */
    public void showStudent() {
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        String url = URL + "/" + studentId;
        String response = sendRequest(url);
        String[] responseArray = response.split(" ");
        if (responseArray[0].equals("ERROR")) {
            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
        } else {
            // stopping swipe refresh
            swipeRefreshLayout.setRefreshing(false);
            StudentProfileRepo studentProfileRepo = new StudentProfileRepo();
            List<StudentProfileList> studentBasicList = studentProfileRepo.getStudentProfile(response);

            if (studentBasicList.size() > 0) {
                for (int i = 0; i < studentBasicList.size(); i++) {
                    studentName = studentBasicList.get(i).getStudentName();
                    studentRegNo = studentBasicList.get(i).getStudentRegNumber();
                    studentClass = studentBasicList.get(i).getStudentSem();
                    studentBatch = studentBasicList.get(i).getStudentSemBatch();
                    studentSemSection = studentBasicList.get(i).getStudentSemSection();
                    studentEmail = studentBasicList.get(i).getStudentEmail();
                    studentContact = studentBasicList.get(i).getStudentContact();
                    if (studentBasicList.get(i).getStudentProfilePicture().isEmpty()) {
                        studentProfilePicture = AppURL.PUBLIC_URL + "/img/user.png";
                    } else {
                        studentProfilePicture = AppURL.PUBLIC_URL + studentBasicList.get(i).getStudentProfilePicture();
                    }
                    studentFatherName = studentBasicList.get(i).getStudentFatherName();
                    studentFatherContact = studentBasicList.get(i).getStudentFatherContact();
                    studentFatherEmail = studentBasicList.get(i).getStudentFatherEmail();
                    studentFatherOccupation = studentBasicList.get(i).getStudentFatherOccupation();
                    studentMotherName = studentBasicList.get(i).getStudentMotherName();
                    studentMotherContact = studentBasicList.get(i).getStudentMotherContact();
                    studentMotherEmail = studentBasicList.get(i).getStudentMotherEmail();
                    studentMotherOccupation = studentBasicList.get(i).getStudentMotherOccupation();
                    studentLocalGuardianName = studentBasicList.get(i).getStudentLocalGuardianName();
                    studentLocalGuardianContact = studentBasicList.get(i).getStudentLocalGuardianContact();
                    studentLocalGuardianEmail = studentBasicList.get(i).getStudentLocalGuardianEmail();
                    studentLocalGuardianRelation = studentBasicList.get(i).getStudentLocalGuardianRelation();
                    studentResidentType = studentBasicList.get(i).getStudentResidentType();
                    studentLocalAddress = studentBasicList.get(i).getStudentLocalAddress();
                    studentPermanentAddress = studentBasicList.get(i).getStudentPermanentAddress();
                    studentCity = studentBasicList.get(i).getStudentCity();
                    studentState = studentBasicList.get(i).getStudentState();
                    studentPinCode = studentBasicList.get(i).getStudentPinCode();
                    studentHobbies = studentBasicList.get(i).getStudentHobbies();
                    collegeBranchName = studentBasicList.get(i).getCollegeBranchName();
                }
            } else {
                Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
            }
            Picasso.with(getActivity())
                    .load(studentProfilePicture)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .transform(new RoundedTransformation(10, 2))
                    .into(imageView);
            lblStudentName.setText(studentName);
            lblStudentRegNo.setText("Reg. No. : " + studentRegNo);
            lblCollegeBranchName.setText("Branch : " + collegeBranchName);
            lblStudentResidentType.setText("Resident Type : " + studentResidentType);
            lblStudentClass.setText(studentClass);
            lblStudentBatch.setText(studentBatch);
            lblStudentSemSection.setText(studentSemSection);
            lblStudentEmail.setText(studentEmail);
            lblStudentContact.setText(studentContact);
            lblStudentFatherName.setText(studentFatherName);
            lblStudentFatherContact.setText(studentFatherContact);
            lblStudentFatherEmail.setText(studentFatherEmail);
            lblStudentFatherOccupation.setText(studentFatherOccupation);
            lblStudentMotherName.setText(studentMotherName);
            lblStudentMotherContact.setText(studentMotherContact);
            lblStudentMotherEmail.setText(studentMotherEmail);
            lblStudentMotherOccupation.setText(studentMotherOccupation);
            lblStudentLocalGuardianName.setText(studentLocalGuardianName);
            lblStudentLocalGuardianContact.setText(studentLocalGuardianContact);
            lblStudentLocalGuardianEmail.setText(studentLocalGuardianEmail);
            lblStudentLocalGuardianRelation.setText(studentLocalGuardianRelation);
            lblStudentLocalAddress.setText(studentLocalAddress);
            lblStudentPermanentAddress.setText(studentPermanentAddress);
            lblStudentCity.setText(studentCity);
            lblStudentState.setText(studentState);
            lblStudentPinCode.setText(studentPinCode);
            lblStudentHobbies.setText(studentHobbies);
        }
    }
}
