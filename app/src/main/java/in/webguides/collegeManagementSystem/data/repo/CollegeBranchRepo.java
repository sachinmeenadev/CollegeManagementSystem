package in.webguides.collegeManagementSystem.data.repo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.webguides.collegeManagementSystem.app.helper.UrlRequest;
import in.webguides.collegeManagementSystem.data.model.CollegeBranch;
import in.webguides.collegeManagementSystem.model.CollegeBranchList;

/**
 * Created by Jerry on 20-06-2017.
 */

public class CollegeBranchRepo {
    private final String TAG = CollegeBranchRepo.class.getSimpleName().toString();
    private CollegeBranch collegeBranch;

    public CollegeBranchRepo() {

    }

    public List<CollegeBranchList> getBranch(String response) {
        CollegeBranchList collegeBranchList;
        List<CollegeBranchList> collegeBranchLists = new ArrayList<CollegeBranchList>();

        try {
            JSONObject userResponse = (new JSONObject(response));
            JSONArray userArray = userResponse.getJSONArray("branches");
            for (int i = 0; i < userArray.length(); i++) {
                JSONObject user = userArray.getJSONObject(i);

                collegeBranchList = new CollegeBranchList();
                collegeBranchList.setCollegeBranchId(user.getInt("collegeBranchId"));
                collegeBranchList.setCollegeBranchName(user.getString("collegeBranchName"));
                collegeBranchList.setCollegeBranchAbbr(user.getString("collegeBranchAbbr"));

                collegeBranchLists.add(collegeBranchList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return collegeBranchLists;
    }

    public String insert(CollegeBranch collegeBranch, String url) {
        String response = null;

        String collegeBranchName = collegeBranch.getCollegeBranchName();
        String collegeBranchAbbr = collegeBranch.getCollegeBranchAbbr();

        HashMap<String, String> params = new HashMap<>();
        params.put("collegeBranchName", collegeBranchName);
        params.put("collegeBranchAbbr", collegeBranchAbbr);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.postUrlData(url, params);
        Log.d(TAG, response);
        return response;
    }

    public String update(CollegeBranch collegeBranch, String url) {
        String response = null;

        int collegeBranchId = collegeBranch.getCollegeBranchId();
        String collegeBranchName = collegeBranch.getCollegeBranchName();
        String collegeBranchAbbr = collegeBranch.getCollegeBranchAbbr();

        HashMap<String, String> params = new HashMap<>();
        params.put("collegeBranchName", collegeBranchName);
        params.put("collegeBranchAbbr", collegeBranchAbbr);

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.putUrlData(url, collegeBranchId, params);
        Log.d(TAG, response);
        return response;
    }

    public String delete(CollegeBranch collegeBranch, String url) {
        String response = null;

        int collegeBranchId = collegeBranch.getCollegeBranchId();

        UrlRequest urlRequest = new UrlRequest();
        response = urlRequest.deleteUrlData(url, collegeBranchId);
        Log.d(TAG, response);
        return response;
    }
}
