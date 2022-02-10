package com.steady.project7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.steady.project7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    String myStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        // clickable pada button di custom header
        NavigationView navigationView = findViewById(R.id.navView);
        View headerView = getLayoutInflater().inflate(R.layout.nav_header_layout,
                navigationView, false);
        navigationView.addHeaderView(headerView);

        Button btn_manage_account = headerView.findViewById(R.id.btn_manage_account);
        btn_manage_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Test Header Click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ManageAccountActivity.class));

            }
        });

        // custom toolbar
        setSupportActionBar(binding.toolbar);

        //default fragment dibuka pertama kali
        getSupportActionBar().setTitle("Home");
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
        binding.navView.setCheckedItem(R.id.nav_home);
        //set menu
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Fragment fragment = null;
        myStr = "home";
        if(extras != null)
            if(extras != null){
                myStr = extras.getString("keyName");
            } else {
                myStr = "home";
            }

        switch (myStr){
            case "home":
                //default fragment dibuka pertama kali
                getSupportActionBar().setTitle("Home");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, new HomeFragment())
                        .commit();
                binding.navView.setCheckedItem(R.id.nav_home);
                break;
            case "instruktur":
                getSupportActionBar().setTitle("Instruktur");
                fragment = new InstrukturFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragment);
                binding.navView.setCheckedItem(R.id.nav_instruktur);
                break;
            case "materi":
                getSupportActionBar().setTitle("Materi");
                fragment = new MateriFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragment);
                binding.navView.setCheckedItem(R.id.nav_materi);
                break;
            case "peserta":
                getSupportActionBar().setTitle("Peserta");
                fragment = new PesertaFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragment);
                binding.navView.setCheckedItem(R.id.nav_peserta);
                break;
            case "kelas":
                getSupportActionBar().setTitle("Kelas");
                fragment = new KelasFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragment);
                binding.navView.setCheckedItem(R.id.nav_kelas);
                break;
            case "detail kelas":
                getSupportActionBar().setTitle("Detail Kelas");
                fragment = new KelasDetailFragment();
                binding.drawer.closeDrawer(GravityCompat.START);
                callFragment(fragment);
                binding.navView.setCheckedItem(R.id.nav_kelas_detail);
                break;
        }
        // membuka drawer
        toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolbar, R.string.open, R.string.close);

        //drawer back button
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        //sinkronisasi drawer
        toggle.syncState();


        // salah satu menu navigasi dipilih
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        getSupportActionBar().setTitle("Home");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_instruktur:
                        fragment = new InstrukturFragment();
                        getSupportActionBar().setTitle("Daftar Instruktur");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_peserta:
                        fragment = new PesertaFragment();
                        getSupportActionBar().setTitle("Daftar Peserta");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_materi:
                        fragment = new MateriFragment();
                        getSupportActionBar().setTitle("Daftar Materi");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_kelas:
                        fragment = new KelasFragment();
                        getSupportActionBar().setTitle("Daftar Kelas");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                    case R.id.nav_kelas_detail:
                        fragment = new KelasDetailFragment();
                        getSupportActionBar().setTitle("Kelas Detail");
                        binding.drawer.closeDrawer(GravityCompat.START);
                        callFragment(fragment);
                        break;
                }
                return true;
            }

        });

    }

    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}