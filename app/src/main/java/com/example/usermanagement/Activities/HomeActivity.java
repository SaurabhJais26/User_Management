package com.example.usermanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usermanagement.ModelResponse.DeleteAccountResponse;
import com.example.usermanagement.NavFragments.DashboardFragment;
import com.example.usermanagement.NavFragments.ProfileFragment;
import com.example.usermanagement.NavFragments.UsersFragment;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;
import com.example.usermanagement.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new DashboardFragment());
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        int itemId = menuItem.getItemId();
        if (itemId == R.id.dashboard) {
            fragment = new DashboardFragment();
        } else if (itemId == R.id.users) {
            fragment = new UsersFragment();
        } else if (itemId == R.id.profile) {
            fragment = new ProfileFragment();
        }

        if (fragment!=null){
            loadFragment(fragment);
        }
        return true;
    }

    void loadFragment(Fragment fragment){

        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout2, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logout) {
            logoutUser();
        } else if (itemId == R.id.deleteAccount) {
            deleteUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteUser() {
        Call<DeleteAccountResponse> deleteAccountResponseCall = RetrofitClient
                .getInstance()
                .getApi().deleteUserAccount(sharedPrefManager.getUser().getId());

        deleteAccountResponseCall.enqueue(new Callback<DeleteAccountResponse>() {
            @Override
            public void onResponse(Call<DeleteAccountResponse> call, Response<DeleteAccountResponse> response) {
                DeleteAccountResponse deleteAccountResponse = response.body();
                if (response.isSuccessful()){
                    if (deleteAccountResponse.getStatus().equals("200")){
                        logoutUser();
                        Toast.makeText(HomeActivity.this,deleteAccountResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(HomeActivity.this,deleteAccountResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DeleteAccountResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void logoutUser() {
        sharedPrefManager.logout();
        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();
    }
}