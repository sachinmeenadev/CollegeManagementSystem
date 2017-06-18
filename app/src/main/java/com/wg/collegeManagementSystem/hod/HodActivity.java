package com.wg.collegeManagementSystem.hod;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.wg.collegeManagementSystem.LoginActivity;
import com.wg.collegeManagementSystem.R;
import com.wg.collegeManagementSystem.app.helper.SessionManager;

public class HodActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public SessionManager session;
    private Fragment fragment = null;
    private TextView navDrawerHeaderLblName, navDrawerHeaderLblEmail;
    private AppCompatButton navDrawerHeaderBtnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod);

        session = new SessionManager(getApplicationContext());
        if (!session.isLoggedIn()) {
            session.setLogin(false);
        }
        fragment = new HodWelcome();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_hod_frame, fragment);
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.hod_toolbar);
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
        menu.add(R.id.drawer_layout_menu_items, 20, Menu.NONE, "Title1");
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
            case 10:
                Toast.makeText(this, "You selected " + itemId, Toast.LENGTH_SHORT).show();
                break;
        }
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_hod_frame, fragment);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
