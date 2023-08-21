package com.example.johncinema.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;

import com.example.johncinema.Fragments.AccountFragment;
import com.example.johncinema.Fragments.AdminFragment;
import com.example.johncinema.Fragments.HomeFragment;
import com.example.johncinema.Fragments.RewardFragment;
import com.example.johncinema.HandleData;
import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Tab Bar
    BottomNavigationView bottomNavigationView;
    Menu menu;

    // Fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // Role
    String role = "Customer";

    // Data
    HandleData handleData;
    Integer id_user;

    // SQlite
    SQLHandle sqlHandle;
    ArrayList<UserClass> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottomNavigationViewTabBarHome);
        bottomNavigationView.setSelectedItemId(R.id.home_tab_bar);

        sqlHandle = new SQLHandle(this);
        listUser = new ArrayList<>();
        handleData = new HandleData(this);

        switchFragment();
        setFragment(new HomeFragment());


        devideRole();
    }

    Integer findUser()
    {
        listUser.clear();
        listUser = sqlHandle.UserRead();

        id_user = handleData.getDataInt("id_user");

        for (int i = 0; i < listUser.size(); i++)
        {
            if (id_user.equals(listUser.get(i).getId()))
            {
                return i;
            }
        }

        return -1;
    }

    void devideRole()
    {
        Integer position = findUser();

        role = listUser.get(position).getRole();

        menu = bottomNavigationView.getMenu();
        if (role.equals("Admin"))
        {
            menu.findItem(R.id.admin_tab_bar).setVisible(true);
        }
        else if (role.equals("Customer"))
        {
            menu.findItem(R.id.admin_tab_bar).setVisible(false);
        }
    }

    void setFragment(Fragment newFragment)
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutMain, newFragment);
        fragmentTransaction.commit();
    }

    public void switchFragment()
    {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getTitle().toString())
                {
                    case "Home":
                        setFragment(new HomeFragment());
                        break;
                    case "Reward":
                        setFragment(new RewardFragment());
                        break;
                    case "Account":
                        setFragment(new AccountFragment());
                        break;
                    case "Admin":
                        setFragment(new AdminFragment());
                        break;
                }

                return true;
            }
        });
    }

}