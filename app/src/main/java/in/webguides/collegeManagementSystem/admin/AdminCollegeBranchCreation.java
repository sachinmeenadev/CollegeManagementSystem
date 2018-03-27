package in.webguides.collegeManagementSystem.admin;

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

import java.util.List;

import in.webguides.collegeManagementSystem.R;
import in.webguides.collegeManagementSystem.app.config.AppURL;
import in.webguides.collegeManagementSystem.app.helper.UrlRequest;
import in.webguides.collegeManagementSystem.data.model.CollegeBranch;
import in.webguides.collegeManagementSystem.data.repo.CollegeBranchRepo;
import in.webguides.collegeManagementSystem.model.CollegeBranchList;

/**
 * Created by Jerry on 20-06-2017.
 */

public class AdminCollegeBranchCreation extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = AdminCollegeBranchCreation.class.getSimpleName();
    private String URL = AppURL.ADMIN_COLLEGE_BRANCH;
    private ListView listView;
    private EditText inputBranchName, inputBranchAbbr;
    private AppCompatButton adminFragmentCollegeBranchBtnInsert;
    private AdminCollegeBranchCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String lblBranchName, newLblBranchName, lblBranchAbbr, newLblBranchAbbr;
    private int lblCollegeBranchId;
    private boolean wrapInScrollView = true;
    private EditText adminFragmentCollegeBranchUpdateInputBranchName, adminFragmentCollegeBranchUpdateInputBranchAbbr;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_college_branch_creation, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        builder = new MaterialDialog.Builder(getActivity());
        inputBranchName = (EditText) view.findViewById(R.id.admin_fragment_college_branch_input_branch_name);
        inputBranchAbbr = (EditText) view.findViewById(R.id.admin_fragment_college_branch_input_branch_abbr);
        listView = (ListView) view.findViewById(R.id.admin_fragment_college_branch_list);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_college_branch_update, null);

                lblCollegeBranchId = Integer.parseInt(((AppCompatTextView) view.findViewById(R.id.admin_fragment_college_branch_list_college_branch_id)).getText().toString());
                lblBranchName = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_college_branch_list_college_name)).getText().toString();
                lblBranchAbbr = ((AppCompatTextView) view.findViewById(R.id.admin_fragment_college_branch_list_college_abbr)).getText().toString();
                adminFragmentCollegeBranchUpdateInputBranchName = (EditText) mView.findViewById(R.id.admin_fragment_college_branch_update_input_branch_name);
                adminFragmentCollegeBranchUpdateInputBranchName.setText(lblBranchName);
                adminFragmentCollegeBranchUpdateInputBranchAbbr = (EditText) mView.findViewById(R.id.admin_fragment_college_branch_update_input_branch_abbr);
                adminFragmentCollegeBranchUpdateInputBranchAbbr.setText(lblBranchAbbr);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newLblBranchName = adminFragmentCollegeBranchUpdateInputBranchName.getText().toString().trim();
                        newLblBranchAbbr = adminFragmentCollegeBranchUpdateInputBranchAbbr.getText().toString().trim();
                        update(newLblBranchName, newLblBranchAbbr);
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

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.admin_fragment_college_branch_list_swipe_refresh_layout);
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

        adminFragmentCollegeBranchBtnInsert = (AppCompatButton) view.findViewById(R.id.admin_fragment_college_branch_btn_insert);
        adminFragmentCollegeBranchBtnInsert.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle("College Branch Creation");
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

            CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
            List<CollegeBranchList> list = collegeBranchRepo.getBranch(response);

            int[] collegeBranchId = new int[list.size()];
            String[] collegeBranchName = new String[list.size()];
            String[] collegeBranchAbbr = new String[list.size()];
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    collegeBranchId[i] = list.get(i).getCollegeBranchId();
                    collegeBranchName[i] = list.get(i).getCollegeBranchName();
                    collegeBranchAbbr[i] = list.get(i).getCollegeBranchAbbr();
                }
                customAdapter = new AdminCollegeBranchCreation.CustomAdapter(getActivity(), collegeBranchId, collegeBranchName, collegeBranchAbbr);
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
        String branchName = inputBranchName.getText().toString().trim();
        String branchAbbr = inputBranchAbbr.getText().toString().trim();
        if (branchName.isEmpty() || branchAbbr.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (branchName.equals(lblBranchName) && branchAbbr.equals(lblBranchAbbr)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                String url = URL;

                CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
                CollegeBranch collegeBranch = new CollegeBranch();

                collegeBranch.setCollegeBranchName(branchName);
                collegeBranch.setCollegeBranchAbbr(branchAbbr);
                String status = collegeBranchRepo.insert(collegeBranch, url);
                String[] statusArray = status.replaceAll("[{}]", "").split(",");
                if (statusArray[0].equals("\"error\":false")) {
                    inputBranchName.setText("");
                    inputBranchAbbr.setText("");
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
     * For updating data
     */
    public void update(String newLblBranchName, String newLblBranchAbbr) {

        if (newLblBranchName.isEmpty() || newLblBranchAbbr.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            String url = URL;

            CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
            CollegeBranch collegeBranch = new CollegeBranch();

            collegeBranch.setCollegeBranchId(lblCollegeBranchId);
            collegeBranch.setCollegeBranchName(newLblBranchName);
            collegeBranch.setCollegeBranchAbbr(newLblBranchAbbr);
            String status = collegeBranchRepo.update(collegeBranch, url);
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

        CollegeBranchRepo collegeBranchRepo = new CollegeBranchRepo();
        CollegeBranch collegeBranch = new CollegeBranch();

        collegeBranch.setCollegeBranchId(lblCollegeBranchId);
        String status = collegeBranchRepo.delete(collegeBranch, url);
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
        AppCompatTextView lblSlNo, lblCollegeBranchId, lblCollegeBranchName, lblCollegeBranchAbbr;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.admin_fragment_college_branch_list_sl_no);
            lblCollegeBranchId = (AppCompatTextView) v.findViewById(R.id.admin_fragment_college_branch_list_college_branch_id);
            lblCollegeBranchName = (AppCompatTextView) v.findViewById(R.id.admin_fragment_college_branch_list_college_name);
            lblCollegeBranchAbbr = (AppCompatTextView) v.findViewById(R.id.admin_fragment_college_branch_list_college_abbr);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        int[] mCollegeBranchId;
        String[] mCollegeBranchName;
        String[] mCollegeBranchAbbr;
        Context mContext;

        public CustomAdapter(Context context, int[] collegeBranchId, String[] collegeBranchName, String[] collegeBranchAbbr) {
            super(context, R.layout.admin_fragment_college_branch_row, R.id.admin_fragment_college_branch_list_college_name, collegeBranchName);
            mCollegeBranchId = collegeBranchId;
            mCollegeBranchName = collegeBranchName;
            mCollegeBranchAbbr = collegeBranchAbbr;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            AdminCollegeBranchCreation.ViewHolder viewHolder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.admin_fragment_college_branch_row, parent, false);
                viewHolder = new AdminCollegeBranchCreation.ViewHolder(row);
                row.setTag(viewHolder);
            } else {
                viewHolder = (AdminCollegeBranchCreation.ViewHolder) row.getTag();
            }

            viewHolder.lblSlNo.setText(String.valueOf(position + 1));
            viewHolder.lblCollegeBranchId.setText(String.valueOf(mCollegeBranchId[position]));
            viewHolder.lblCollegeBranchName.setText(mCollegeBranchName[position]);
            viewHolder.lblCollegeBranchAbbr.setText(mCollegeBranchAbbr[position]);

            return row;
        }
    }
}
