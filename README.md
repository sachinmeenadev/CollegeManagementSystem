# College Management System

## Welcome to https://sachinmeenadev.github.io/CollegeManagementSystem/

This is a small College ERP system demo android application. It is the first demo version of this app. This version is completely based on SQLite Database (Local DB). By modifying this, one can build basic ERP projects, such as Employee Management System etc.
    
    >IF you want to suggest any changes, then let me know.

#### Structure of project (Functional Requirements)
    1. Splash
	2. Shared preference
	3. Login
	5. Welcome
	6. Faculty (By class selection, default shown own class)
		6.1 Student List
		//Can see list of students of particular class or section
		6.2 Student Profile
		//Can see individual student profile
		6.3 Student Update
		//Can update individual student profile
	7. Placement (Only having placement access)
		7.1 Student List
			7.1.1 By Criteria
			//Like Medium of language, Percentage criteria
			7.1.2 By Specific Student
			//By names, etc
		7.2 Student Profile
		//For checking individual student for their eligibility 
		7.3 Student Volunteer
		//Can check list of students, who can help in placement related activities
	8. HOD
		8.1 Student List
			8.1.1 By class selection
			8.1.2 By Criteria (Placement eligibility)
			8.1.3 By Specific Student
		8.2 Student Profile
		8.3 Tutor list
		8.4 Faculty List	
			8.4.1 Subject Wise
			8.4.2 Class Wise
		8.5 Assign tutor
		8.6 Assign faculty
	9. Admin
		9.1 Manage Roles
		9.2 Manage College Branches list
		9.3 Manage Subjects list
		9.4 Manage Users list		
		9.5 Manage Faculty Members list

#### Project database structure
    > All column name ending with Id are INTEGER Type, rest all ate VRCHAR

    *users
		->userId 
		->userName
		->userEmail
		->userPassword
		->userRoleId
		
	*roles
		->roleId
		->roleType
		
	*student 
		->studentId
		->studentName
		->studentRegNumber
		->studentBranch
		->studentSem
		->studentSemSection
		->studentSemBatch
		->studentEmail
		->studentContact
		->studentFatherName
		->studentFatherContact
		->studentFatherEmail
		->studentFatherOccupation
		->studentFatherIncome
		->studentMotherName
		->studentMotherContact
		->studentMotherEmail
		->studentMotherOccupation
		->studentMotherIncome
		->studentLocalGuardianName
		->studentLocalGuardianContact
		->studentLocalGuardianEmail
		->studentResidentType
		->studentLocalAddress
		->studentPermanentAddress
		->studentCity
		->studentState
		->studentCountry
		->studentPinCode
		
	*studentAcademics
		->studentAcademicId
		->studentAcademicStudentId
		->studentAcademicSecPercentage
		->studentAcademicSecBoard
		->studentAcademicSecMedium
		->studentAcademicSecSchoolName
		->studentAcademicSrSecPercentage
		->studentAcademicSrSecBoard
		->studentAcademicSrSecMedium
		->studentAcademicSrSecSchoolName
		->studentAcademicDiplomaPercentage
		->studentAcademicDiplomaBoard
		->studentAcademicDiplomaMedium
		->studentAcademicDiplomaInstituteName
		->studentAcademicCollegeAgg
		->studentAcademicCollegeBackCount
		->studentAcademicCollegeBackSubject
		->studentAcademicHobbies
		
	*collegeBranches
		->collegeBranchId
		->collegeBranchName
		->collegeBranchAbbr
		
	*tutors
		->tutorId
		->tutorFacultyId
		->tutorSection
		->tutorBatch
		
	*facultyMembers
		->facultyMemberId
		->facultyMemberName
		->facultyMemberBranchId
		->facultyMemberDesignation
		->facultyMemberContact
		->facultyMemberEmail
		
	*subjects
		->subjectId
		->subjectName
		->subjectAbbr
		->subjectCode
		
	*facultyMemberSubjects
		->fmsId
		->fmsFacultyId
		->fmsSubjectId

#### Project configuration
    Compile Sdk Version => 25
    Build Tools Version => 25.0.3
    
#### Used dependencies
    1.  https://github.com/afollestad/material-dialogs 
    ->For Material Alert Dialog 
      
#### Taken references from
    1. http://instinctcoder.com/android-studio-sqlite-database-multiple-tables-example/
    ->For multiple database table use
    
    2. http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/
    ->For session management
    
    3. http://www.materialdoc.com/
    ->For material design guides

#### Feedback

Thank you for all the great feedback so far. If you have further questions you can either create an issue on the item here on github that we can discuss via comments, or you can reach out to me on either Twitter or Email.

    Twitter: Twitter @_sachinmeena (Be sure to follow me too)
    Email: sachinmeena.dev@gmail.com
