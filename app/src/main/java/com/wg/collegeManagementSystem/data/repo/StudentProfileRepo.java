package com.wg.collegeManagementSystem.data.repo;

import com.wg.collegeManagementSystem.data.model.StudentProfile;
import com.wg.collegeManagementSystem.model.StudentProfileList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 12-09-2017.
 */

public class StudentProfileRepo {
    private final String TAG = StudentSearchRepo.class.getSimpleName().toString();
    private StudentProfile studentProfile;

    public StudentProfileRepo() {

    }

    public List<StudentProfileList> getStudentProfile(String response) {
        StudentProfileList studentProfileList;
        List<StudentProfileList> studentProfileLists = new ArrayList<StudentProfileList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("student");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                studentProfileList = new StudentProfileList();
                studentProfileList.setStudentId(user.getInt("studentId"));
                studentProfileList.setStudentName(user.getString("studentName"));
                studentProfileList.setStudentRegNumber(user.getString("studentRegNumber"));
                studentProfileList.setStudentSem(user.getString("studentSem"));
                studentProfileList.setStudentSemBatch(user.getString("studentSemBatch"));
                studentProfileList.setStudentContact(user.getString("studentContact"));
                studentProfileList.setStudentProfilePicture(user.getString("studentProfilePicture"));
                studentProfileList.setStudentSemSection(user.getString("studentSemSection"));
                studentProfileList.setStudentEmail(user.getString("studentEmail"));
                studentProfileList.setStudentFatherName(user.getString("studentFatherName"));
                studentProfileList.setStudentFatherContact(user.getString("studentFatherContact"));
                studentProfileList.setStudentFatherEmail(user.getString("studentFatherEmail"));
                studentProfileList.setStudentFatherOccupation(user.getString("studentFatherOccupation"));
                studentProfileList.setStudentMotherName(user.getString("studentMotherName"));
                studentProfileList.setStudentMotherContact(user.getString("studentMotherContact"));
                studentProfileList.setStudentMotherEmail(user.getString("studentMotherEmail"));
                studentProfileList.setStudentMotherOccupation(user.getString("studentMotherOccupation"));
                studentProfileList.setStudentLocalGuardianName(user.getString("studentLocalGuardianName"));
                studentProfileList.setStudentLocalGuardianContact(user.getString("studentLocalGuardianContact"));
                studentProfileList.setStudentLocalGuardianEmail(user.getString("studentLocalGuardianEmail"));
                studentProfileList.setStudentLocalGuardianRelation(user.getString("studentLocalGuardianRelation"));
                studentProfileList.setStudentResidentType(user.getString("studentResidentType"));
                studentProfileList.setStudentLocalAddress(user.getString("studentLocalAddress"));
                studentProfileList.setStudentPermanentAddress(user.getString("studentPermanentAddress"));
                studentProfileList.setStudentCity(user.getString("studentCity"));
                studentProfileList.setStudentState(user.getString("studentState"));
                studentProfileList.setStudentPinCode(user.getString("studentPincode"));
                studentProfileList.setStudentHobbies(user.getString("studentHobbies"));
                studentProfileList.setCollegeBranchName(user.getString("collegeBranchName"));

                studentProfileLists.add(studentProfileList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentProfileLists;
    }
}
