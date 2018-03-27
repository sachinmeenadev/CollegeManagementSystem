package in.webguides.collegeManagementSystem.model;

/**
 * Created by Jerry on 14-06-2017.
 */

public class SubjectList {
    public int subjectId;
    public String subjectName;
    public String subjectAbbr;
    public String subjectCode;
    public String subjectCreatedAt;
    public String subjectUpdatedAt;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectAbbr() {
        return subjectAbbr;
    }

    public void setSubjectAbbr(String subjectAbbr) {
        this.subjectAbbr = subjectAbbr;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectCreatedAt() {
        return subjectCreatedAt;
    }

    public void setSubjectCreatedAt(String subjectCreatedAt) {
        this.subjectCreatedAt = subjectCreatedAt;
    }

    public String getSubjectUpdatedAt() {
        return subjectUpdatedAt;
    }

    public void setSubjectUpdatedAt(String subjectUpdatedAt) {
        this.subjectUpdatedAt = subjectUpdatedAt;
    }
}
