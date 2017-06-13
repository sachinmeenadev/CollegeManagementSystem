package com.wg.collegeManagementSystem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wg.collegeManagementSystem.LoginActivity;
import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.helper.SessionManager;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public SessionManager session;
    private Fragment fragment = null;
    private LinearLayout linearLayout;
    private TextView navDrawerHeaderLblName, navDrawerHeaderLblEmail;
    private AppCompatButton navDrawerHeaderBtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            session.setLogin(false);
        }
        fragment = new AdminWelcome();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_admin_frame, fragment);
        fragmentTransaction.commit();

        linearLayout = (LinearLayout) findViewById(R.id.admin_welcome_linear_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        navDrawerHeaderLblName = (TextView) headerView.findViewById(R.id.nav_drawer_header_lbl_name);
        navDrawerHeaderLblEmail = (TextView) headerView.findViewById(R.id.nav_drawer_header_lbl_email);
        navDrawerHeaderLblName.setText("Welcome, " + session.getUserName());
        navDrawerHeaderLblEmail.setText(session.getUserEmail());

        navDrawerHeaderBtnLogout = (AppCompatButton) headerView.findViewById(R.id.nav_drawer_header_btn_logout);
        navDrawerHeaderBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        menu.add(R.id.drawer_layout_menu_items, 120, Menu.NONE, "Role Creation");
        menu.add(R.id.drawer_layout_menu_items, 121, Menu.NONE, "College Branch Creation");
        menu.add(R.id.drawer_layout_menu_items, 122, Menu.NONE, "Subject Creation");
        menu.add(R.id.drawer_layout_menu_items, 123, Menu.NONE, "User Creation");
        menu.add(R.id.drawer_layout_menu_items, 124, Menu.NONE, "Faculty Member Creation");
        menu.add(R.id.drawer_layout_menu_items, 125, Menu.NONE, "Faculty Member Subject Creation");
        menu.add(R.id.drawer_layout_menu_items, 126, Menu.NONE, "Tutor Creation");
        menu.add(R.id.drawer_layout_menu_items, 127, Menu.NONE, "Student Creation");
        menu.add(R.id.drawer_layout_menu_items, 128, Menu.NONE, "Student Academic Creation");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int itemId = item.getItemId();
        String itemName = item.getTitle().toString();
        //initializing the fragment object which is selected
        switch (itemId) {
            case 120:
                fragment = new AdminRoleCreation();
                break;
            case 121:
                fragment = new AdminCollegeBranchCreation();
                break;
            case 122:
                fragment = new AdminSubjectCreation();
                break;
        }
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_admin_frame, fragment);
            fragmentTransaction.commit();
        }
        Snackbar snackbar = Snackbar.make(linearLayout, "You selected " + itemName, Snackbar.LENGTH_LONG);
        snackbar.show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
