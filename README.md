# College Management System

## Welcome to https://sachinmeenadev.github.io/CollegeManagementSystem/

This is a small College ERP system demo android application. 
This is the first version of this app. This version is completely based on SQLite Database (Local DB).
By modifying this one can build basic ERP projects, such as Employee Management System etc.
    
    >IF you want to suggest any changes, then let me know.

#### Stucture of project (Functional Requirements)
    1. Splash
	2. Shared preference
	3. Login
	5. Welcome
	6. Faculty (By class selection, default shown own class)
		6.1 Student List  //Can see all list of student for particular class or section.
		6.2 Student Profile //Can see individual profile of student.
		6.3 Student Update // Can update individual student profile.
	7. Placement (Only having placement access)
		7.1 Student List  //Can see all list of student for particular class or section.
			7.1.1 By Criteria  //Medium of language, Percentage criteria.
			7.1.2 By Specific Student Name
		7.2 Student Profile  //Can see individual profile of student.
		7.3 Student Volunteer
	8. HOD
		8.1 Student List
			8.1.1 By class selection  //Can see all list of student for particular class or section.
			8.1.2 By Criteria (Placement eligiblity)  //Medium of language, Percentage criteria.
			8.1.3 By Specific Student  //Can see individual profile of student.
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

#### Project configuration
    Compile Sdk Version => 25
    Build Tools Version => 25.0.3
    
#### Used dependencies
    1.  https://github.com/afollestad/material-dialogs 
    ->For Material Alert Dialog 
      
#### Taken reference from 
    1. http://instinctcoder.com/android-studio-sqlite-database-multiple-tables-example/
    ->For multiple database table use
    
    2. http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/
    ->For session management
    
    3. http://www.materialdoc.com/
    ->For material design guides
