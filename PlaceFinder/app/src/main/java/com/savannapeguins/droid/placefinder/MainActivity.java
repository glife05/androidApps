package com.savannapeguins.droid.placefinder;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.savannapeguins.droid.placefinder.Fragments.HomeFragment;
import com.savannapeguins.droid.placefinder.Fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {
private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawerLayout);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navDrawer=(NavigationView)findViewById(R.id.nav_menu);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navDrawer);
    }

    /*this method handles menu items click events
    * to open the various fragments*/
    public void menuItemClick(MenuItem menuItem)
    {
       Fragment myFragment=null;
       Class fragmentClass;
       switch (menuItem.getItemId())
       {
           case R.id.nav_home:
               fragmentClass= HomeFragment.class;
               break;
           case R.id.nav_account:
               fragmentClass= LoginFragment.class;
               break;
           default:
               fragmentClass=HomeFragment.class;

       }

       try {
           myFragment=(Fragment)fragmentClass.newInstance();
       }catch (Exception e){ e.printStackTrace();

       }
        FragmentManager fragmentManger=getSupportFragmentManager();
        fragmentManger.beginTransaction().replace(R.id.frame_layout,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawer.closeDrawers();

    }


    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuItemClick(item);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(Gravity.START))
          {
              drawer.closeDrawer(Gravity.START);
          }else {
            super.onBackPressed();
                }
    }
}
