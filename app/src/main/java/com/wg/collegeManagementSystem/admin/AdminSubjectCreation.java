package com.wg.collegeManagementSystem.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.wg.collegeManagementSystem.data.model.Subject;
import com.wg.collegeManagementSystem.data.repo.SubjectRepo;
import com.wg.collegeManagementSystem.model.SubjectList;

import java.util.List;


/**
 * Created by Jerry on 13-06-2017.
 */

public class AdminSubjectCreation extends Fragment {
    private static final String TAG = AdminSubjectCreation.class.getSimpleName();
    private ListView listView;
    private EditText inputSubjectName, inputSubjectAbbr, inputSubjectCode;
    private AppCompatButton fragmentAdminSubjectBtnInsert;
    private AdminSubjectCreation.CustomAdapter customAdapter;
    private MaterialDialog.Builder builder;
    private String oldSubjectName, newSubjectName, oldSubjectAbbr, newSubjectAbbr, oldSubjectCode, newSubjectCode;
    private boolean wrapInScrollView = true;
    private EditText fragmentAdminSubjectUpdateInputSubjectName, fragmentAdminSubjectUpdateInputSubjectAbbr, fragmentAdminSubjectUpdateInputSubjectCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragment_subject_creation, container, false);

        builder = new MaterialDialog.Builder(getActivity());
        inputSubjectName = (EditText) view.findViewById(R.id.fragment_admin_subject_input_subject_name);
        inputSubjectAbbr = (EditText) view.findViewById(R.id.fragment_admin_subject_input_subject_abbr);
        inputSubjectCode = (EditText) view.findViewById(R.id.fragment_admin_subject_input_subject_code);
        listView = (ListView) view.findViewById(R.id.fragment_admin_subject_list);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mView = inflater.inflate(R.layout.admin_fragment_subject_update, null);

                oldSubjectName = ((AppCompatTextView) view.findViewById(R.id.fragment_admin_subject_list_subject_name)).getText().toString();
                oldSubjectAbbr = ((AppCompatTextView) view.findViewById(R.id.fragment_admin_subject_list_subject_abbr)).getText().toString();
                oldSubjectCode = ((AppCompatTextView) view.findViewById(R.id.fragment_admin_subject_list_subject_code)).getText().toString();
                fragmentAdminSubjectUpdateInputSubjectName = (EditText) mView.findViewById(R.id.fragment_admin_subject_update_input_subject_name);
                fragmentAdminSubjectUpdateInputSubjectName.setText(oldSubjectName);
                fragmentAdminSubjectUpdateInputSubjectAbbr = (EditText) mView.findViewById(R.id.fragment_admin_subject_update_input_subject_abbr);
                fragmentAdminSubjectUpdateInputSubjectAbbr.setText(oldSubjectAbbr);
                fragmentAdminSubjectUpdateInputSubjectCode = (EditText) mView.findViewById(R.id.fragment_admin_subject_update_input_subject_code);
                fragmentAdminSubjectUpdateInputSubjectCode.setText(oldSubjectCode);

                builder.title("Action");
                builder.positiveText("Update");
                builder.negativeText("Delete");
                builder.neutralText("Cancel");
                builder.customView(mView, wrapInScrollView);
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        newSubjectName = fragmentAdminSubjectUpdateInputSubjectName.getText().toString().trim();
                        newSubjectAbbr = fragmentAdminSubjectUpdateInputSubjectAbbr.getText().toString().trim();
                        newSubjectCode = fragmentAdminSubjectUpdateInputSubjectCode.getText().toString().trim();
                        update(oldSubjectName, newSubjectName, oldSubjectAbbr, newSubjectAbbr, oldSubjectCode, newSubjectCode);
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

        fragmentAdminSubjectBtnInsert = (AppCompatButton) view.findViewById(R.id.fragment_admin_subject_btn_insert);
        fragmentAdminSubjectBtnInsert.setOnClickListener(new View.OnClickListener() {
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
     * For inserting in database
     */
    public void insert() {
        String subjectName = inputSubjectName.getText().toString().trim();
        String subjectAbbr = inputSubjectAbbr.getText().toString().trim();
        String subjectCode = inputSubjectCode.getText().toString().trim();

        if (subjectName.isEmpty() || subjectAbbr.isEmpty() || subjectCode.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            if (subjectName.equals(oldSubjectName) && subjectAbbr.equals(oldSubjectAbbr) && subjectCode.equals(oldSubjectCode)) {
                Toast.makeText(getActivity(), "You already made an entry for this", Toast.LENGTH_SHORT).show();
            } else {
                SubjectRepo subjectRepo = new SubjectRepo();
                Subject subject = new Subject();

                subject.setSubjectName(subjectName);
                subject.setSubjectAbbr(subjectAbbr);
                subject.setSubjectCode(subjectCode);
                int status = subjectRepo.insert(subject);

                inputSubjectName.setText("");
                inputSubjectAbbr.setText("");
                inputSubjectCode.setText("");

                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                show_data();
            }
        }
    }

    /**
     * For updating in database
     */
    public void update(String oldSubjectName, String newSubjectName, String oldSubjectAbbr, String newSubjectAbbr, String oldSubjectCode, String newSubjectCode) {
        if (newSubjectName.isEmpty() || newSubjectAbbr.isEmpty() || newSubjectCode.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill the input field", Toast.LENGTH_SHORT).show();
        } else {
            SubjectRepo subjectRepo = new SubjectRepo();
            Subject subject = new Subject();

            subject.setOldSubjectName(oldSubjectName);
            subject.setNewSubjectName(newSubjectName);
            subject.setOldSubjectAbbr(oldSubjectAbbr);
            subject.setNewSubjectAbbr(newSubjectAbbr);
            subject.setOldSubjectCode(oldSubjectCode);
            subject.setNewSubjectCode(newSubjectCode);
            subjectRepo.update(subject);

            Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            show_data();
        }
    }

    /**
     * For deleting in database
     */
    public void delete() {
        SubjectRepo subjectRepo = new SubjectRepo();
        Subject subject = new Subject();

        subject.setSubjectName(oldSubjectName);
        subject.setSubjectAbbr(oldSubjectAbbr);
        subject.setSubjectCode(oldSubjectCode);
        subjectRepo.delete(subject);

        Toast.makeText(getActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
        show_data();
    }

    /**
     * For showing added roles from database
     */
    public void show_data() {
        SubjectRepo subjectRepo = new SubjectRepo();
        List<SubjectList> list = subjectRepo.getSubject();

        String[] subjectName;
        String[] subjectAbbr;
        String[] subjectCode;

        subjectName = new String[list.size()];
        subjectAbbr = new String[list.size()];
        subjectCode = new String[list.size()];

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                subjectName[i] = list.get(i).getSubjectName();
                subjectAbbr[i] = list.get(i).getSubjectAbbr();
                subjectCode[i] = list.get(i).getSubjectCode();
            }
            customAdapter = new AdminSubjectCreation.CustomAdapter(getActivity(), subjectName, subjectAbbr, subjectCode);
            listView.setAdapter(customAdapter);
        } else {
            Toast.makeText(getActivity(), "No data in database", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ViewHolder to hold elements from custom row layout
     */
    public class ViewHolder {
        AppCompatTextView lblSlNo, lblSubjectName, lblSubjectAbbr, lblSubjectCode;

        public ViewHolder(View v) {
            lblSlNo = (AppCompatTextView) v.findViewById(R.id.fragment_admin_subject_list_sl_no);
            lblSubjectName = (AppCompatTextView) v.findViewById(R.id.fragment_admin_subject_list_subject_name);
            lblSubjectAbbr = (AppCompatTextView) v.findViewById(R.id.fragment_admin_subject_list_subject_abbr);
            lblSubjectCode = (AppCompatTextView) v.findViewById(R.id.fragment_admin_subject_list_subject_code);
        }
    }

    /**
     * Custom adapter to fill list view
     */
    public class CustomAdapter extends ArrayAdapter<String> {
        String[] mSubjectName;
        String[] mSubjectAbbr;
        String[] mSubjectCode;
        Context mContext;

        public CustomAdapter(Context context, String[] subjectName, String[] subjectAbbr, String[] subjectCode) {
            super(context, R.layout.admin_fragment_subject_row, R.id.fragment_admin_subject_list_subject_name, subjectName);
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
            viewHolder.lblSubjectName.setText(mSubjectName[position]);
            viewHolder.lblSubjectAbbr.setText(mSubjectAbbr[position]);
            viewHolder.lblSubjectCode.setText(mSubjectCode[position]);

            return row;
        }
    }
}
