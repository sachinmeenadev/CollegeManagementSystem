package in.webguides.collegeManagementSystem.data.repo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.webguides.collegeManagementSystem.data.model.StudentSearch;
import in.webguides.collegeManagementSystem.model.StudentSearchList;

/**
 * Created by Jerry on 12-09-2017.
 */

public class StudentSearchRepo {
    private final String TAG = StudentSearchRepo.class.getSimpleName().toString();
    private StudentSearch studentSearch;

    public StudentSearchRepo() {

    }

    public List<StudentSearchList> getStudentSearch(String response) {
        StudentSearchList studentSearchList;
        List<StudentSearchList> studentSearchLists = new ArrayList<StudentSearchList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("students");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                studentSearchList = new StudentSearchList();
                studentSearchList.setStudentId(user.getInt("studentId"));
                studentSearchList.setStudentName(user.getString("studentName"));
                studentSearchList.setStudentRegNumber(user.getString("studentRegNumber"));
                studentSearchList.setStudentSem(user.getString("studentSem"));
                studentSearchList.setStudentSemBatch(user.getString("studentSemBatch"));
                studentSearchList.setStudentSemSection(user.getString("studentSemSection"));
                studentSearchList.setStudentContact(user.getString("studentContact"));

                studentSearchLists.add(studentSearchList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentSearchLists;
    }
}
