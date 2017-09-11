package com.wg.collegeManagementSystem.data.repo;

import com.wg.collegeManagementSystem.data.model.Tutor;
import com.wg.collegeManagementSystem.model.TutorList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry on 09-07-2017.
 */

public class TutorRepo {
    private final String TAG = TutorRepo.class.getSimpleName().toString();
    private Tutor tutor;

    public TutorRepo() {

    }

    public List<TutorList> getTutor(String response) {
        TutorList tutorList;
        List<TutorList> tutorLists = new ArrayList<TutorList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("tutors");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                tutorList = new TutorList();
                tutorList.setTutorId(user.getInt("tutorId"));
                tutorList.setFacultyMemberName(user.getString("facultyMemberName"));
                tutorList.setFacultyMemberDesignation(user.getString("facultyMemberDesignation"));
                tutorList.setFacultyMemberContact(user.getString("facultyMemberContact"));
                tutorList.setFacultyMemberEmail(user.getString("facultyMemberEmail"));
                tutorList.setTutorBatch(user.getString("tutorBatch"));
                tutorList.setTutorClass(user.getString("tutorClass"));
                tutorList.setTutorSection(user.getString("tutorSection"));

                tutorLists.add(tutorList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tutorLists;
    }

}
