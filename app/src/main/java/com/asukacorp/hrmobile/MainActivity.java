package com.asukacorp.hrmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.asukacorp.hrmobile.Agenda.AgendaFragment;
import com.asukacorp.hrmobile.Home.HomeFragment;
import com.asukacorp.hrmobile.Menu.MenuFragment;
import com.asukacorp.hrmobile.Profile.ProfileFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;


public class MainActivity extends AppCompatActivity implements AnimatedBottomBar.OnTabSelectListener {

    private boolean doubleBackToExitPressedOnce = false;
    private AnimatedBottomBar bottomBar;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(this);

        swapFragment(R.id.tab_home);
    }

    @Override
    public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {
    }

    @Override
    public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
        swapFragment(tab1.getId());
    }

    private void swapFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        if (id == R.id.tab_home) {
            HomeFragment fragment = HomeFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, fragment);
        } else if (id == R.id.tab_menu) {
            MenuFragment fragment = MenuFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, fragment);
        } else if (id == R.id.tab_agenda) {
            AgendaFragment fragment = AgendaFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, fragment);
        } else if (id == R.id.tab_profile) {
            ProfileFragment fragment = ProfileFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, fragment);
        } else {
            HomeFragment fragment = HomeFragment.newInstance();
            fragmentTransaction.replace(R.id.containerFragment, fragment);
        }
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}