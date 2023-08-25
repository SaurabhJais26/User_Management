package com.example.usermanagement.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usermanagement.ModelResponse.LoginResponse;
import com.example.usermanagement.ModelResponse.UpdatePasswordResponse;
import com.example.usermanagement.R;
import com.example.usermanagement.RetrofitClient;
import com.example.usermanagement.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;


public class ProfileFragment extends Fragment implements View.OnClickListener{

   EditText etuserName, etuserEmail,etcurrentPassword,etnewPassword;
   Button updateUserAccountBtn,updateUserPasswordBtn;
   SharedPrefManager sharedPrefManager;
    int userID;
    String userEmailId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //for update account

        etuserName = view.findViewById(R.id.userName);
        etuserEmail = view.findViewById(R.id.userEmail);
        updateUserAccountBtn = view.findViewById(R.id.updateAccountBtn);
        
        //for update password
        etcurrentPassword = view.findViewById(R.id.currentPassword);
        etnewPassword = view.findViewById(R.id.newPassword);
        updateUserPasswordBtn = view.findViewById(R.id.updatePasswordBtn);


        sharedPrefManager = new SharedPrefManager(getActivity());
        userID = sharedPrefManager.getUser().getId();

        userEmailId = sharedPrefManager.getUser().getEmail();

        updateUserAccountBtn.setOnClickListener(this);
        updateUserPasswordBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.updateAccountBtn) {
            updateUserAccount();
        }
        else if (view.getId() == R.id.updatePasswordBtn){
            updateUserPassword();
        }

    }

    private void updateUserPassword() {
        String currentPass = etcurrentPassword.getText().toString().trim();
        String newPass = etnewPassword.getText().toString().trim();

        if (currentPass.isEmpty()){
            etcurrentPassword.setError("Please enter the current password");
            etcurrentPassword.requestFocus();
            return;
        }
        if (currentPass.length()<8){
            etcurrentPassword.setError("Enter 8 digit password");
            etcurrentPassword.requestFocus();
            return;
        }
        if (newPass.isEmpty()){
            etnewPassword.setError("Please enter the new password");
            etnewPassword.requestFocus();
            return;
        }
        if (newPass.length()<8){
            etnewPassword.setError("Enter 8 digit password");
            etnewPassword.requestFocus();
            return;
        }
        Call<UpdatePasswordResponse> updatePasswordResponseCall = RetrofitClient
                .getInstance()
                .getApi()
                .updateUserPassword(userEmailId,currentPass,newPass);
        updatePasswordResponseCall.enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {

                UpdatePasswordResponse passwordResponse = response.body();
                if (response.isSuccessful()){

                    if (passwordResponse.getStatus().equals("200")){
                        Toast.makeText(getActivity(),passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateUserAccount() {

        String username = etuserName.getText().toString().trim();
        String email = etuserEmail.getText().toString().trim();

        if (username.isEmpty()){
            etuserName.setError("Please enter the user name");
            etuserName.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etuserEmail.requestFocus();
            etuserEmail.setError("Please enter correct email");
            return;
        }
        Call<LoginResponse> loginResponseCall = RetrofitClient
                .getInstance()
                .getApi()
                .updateUserAccount(userID,username,email);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                LoginResponse updateResponse = response.body();
                if (response.isSuccessful()){
                    if (updateResponse.getStatus().equals("200")){

                        sharedPrefManager.saveUser(updateResponse.getUser());
                        Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}