package com.savannapeguins.droid.placefinder;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.savannapeguins.droid.placefinder.Adapter.HomeRecyclerViewAdapter;
import com.savannapeguins.droid.placefinder.Fragments.CreateAccountFragment;
import com.savannapeguins.droid.placefinder.Fragments.HomeFragment;
import com.savannapeguins.droid.placefinder.Fragments.LoginFragment;
import com.savannapeguins.droid.placefinder.Model.Listings;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private DrawerLayout drawer;
private static final String TAG="MainActivity";
private RecyclerView recyclerView;
private HomeRecyclerViewAdapter mAdapter;
private FirebaseFirestore firestoreDB;
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

        //code goes here.....
        recyclerView=findViewById(R.id.recyclerViewHome);
        firestoreDB=FirebaseFirestore.getInstance();

        //loadListings();

    }

    private void loadListings() {
        firestoreDB.collection("lists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            List<Listings>homeList=new ArrayList<>();
                            for (DocumentSnapshot doc:task.getResult())
                            {
                                Listings list=doc.toObject(Listings.class);
                                homeList.add(list);
                            }

                            mAdapter=new HomeRecyclerViewAdapter(homeList,getApplicationContext(),firestoreDB);
                            RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }else{
                            Log.d(TAG, "Error getting documents:",task.getException());
                        }
                    }
                });

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
           case R.id.nav_login:
               fragmentClass= LoginFragment.class;
               break;
           case R.id.nav_create_ac:
               fragmentClass= CreateAccountFragment.class;
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
