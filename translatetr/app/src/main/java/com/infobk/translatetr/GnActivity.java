package com.infobk.translatetr;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class GnActivity extends AppCompatActivity {


BottomNavigationView bt ;
Fragment f1 ;
FrameLayout tutucu ;
EditText aranan;
ImageButton ara ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt = (BottomNavigationView) findViewById(R.id.buttom_navigation);
        tutucu = (FrameLayout) findViewById(R.id.fragment_tutucu);
        bt.setOnNavigationItemSelectedListener(navListener);


    }

        private BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null ;
                        switch ( item.getItemId()){

                            case R.id.nav_birinci:
                                selectedFragment = new EnFragment();
                                break;

                            case R.id.nav_ucuncu:
                                selectedFragment = new StartScreenFragment();
                                break;

                        }
                         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, selectedFragment).commit();

                        return true;
                    }
                };

    }









