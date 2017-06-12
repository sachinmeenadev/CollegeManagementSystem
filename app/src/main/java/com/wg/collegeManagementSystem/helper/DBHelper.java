package com.wg.collegeManagementSystem.helper;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wg.collegeManagementSystem.app.App;
import com.wg.collegeManagementSystem.data.model.CollegeBranch;
import com.wg.collegeManagementSystem.data.model.FacultyMember;
import com.wg.collegeManagementSystem.data.model.FacultyMemberSubject;
import com.wg.collegeManagementSystem.data.model.Role;
import com.wg.collegeManagementSystem.data.model.Student;
import com.wg.collegeManagementSystem.data.model.StudentAcademic;
import com.wg.collegeManagementSystem.data.model.Subject;
import com.wg.collegeManagementSystem.data.model.Tutor;
import com.wg.collegeManagementSystem.data.model.User;
import com.wg.collegeManagementSystem.data.repo.CollegeBranchRepo;
import com.wg.collegeManagementSystem.data.repo.FacultyMemberRepo;
import com.wg.collegeManagementSystem.data.repo.FacultyMemberSubjectRepo;
import com.wg.collegeManagementSystem.data.repo.RoleRepo;
import com.wg.collegeManagementSystem.data.repo.StudentAcademicRepo;
import com.wg.collegeManagementSystem.data.repo.StudentRepo;
import com.wg.collegeManagementSystem.data.repo.SubjectRepo;
import com.wg.collegeManagementSystem.data.repo.TutorRepo;
import com.wg.collegeManagementSystem.data.repo.UserRepo;

/**
 * Created by Jerry on 11-06-2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "CollegeManagementSystem";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(RoleRepo.createTable());
        db.execSQL(UserRepo.createTable());
        db.execSQL(CollegeBranchRepo.createTable());
        db.execSQL(SubjectRepo.createTable());
        db.execSQL(FacultyMemberRepo.createTable());
        db.execSQL(FacultyMemberSubjectRepo.createTable());
        db.execSQL(TutorRepo.createTable());
        db.execSQL(StudentAcademicRepo.createTable());
        db.execSQL(StudentRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Role.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CollegeBranch.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Subject.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FacultyMember.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FacultyMemberSubject.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Tutor.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + StudentAcademic.TABLE);
        onCreate(db);
    }

}
