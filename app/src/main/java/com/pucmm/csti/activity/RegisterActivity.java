package com.pucmm.csti.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.pucmm.csti.R;
import com.pucmm.csti.databinding.ActivityRegisterBinding;
import com.pucmm.csti.model.Userr;
import com.pucmm.csti.networksync.FirebaseNetwork;
import com.pucmm.csti.networksync.NetResponse;
import com.pucmm.csti.retrofit.UserApiService;
import com.pucmm.csti.utils.Constants;
import com.pucmm.csti.utils.UserSession;
import com.pucmm.csti.utils.ValidUtil;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private ActivityRegisterBinding binding;
    private Uri uri;
    private UserSession session;
    private Userr userr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.auto_complete_item, Arrays.asList("SELLER", "CUSTOMER"));
        binding.rol.setAdapter(adapter);

        binding.login.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        binding.forgot.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, ForgotPasswordActivity.class));
            finish();
        });

        retrieveSession();

        binding.goBack.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));

        });

        if (session.isLoggedIn()) {
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIII");
            System.out.println(userr.getUid());
            System.out.println(Userr.ROL.SELLER);
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIII");

            binding.firstName.setText(userr.getFirstName());
            binding.lastName.setText(userr.getLastName());
            binding.email.setText(userr.getEmail());
            binding.passwordWrapper.setVisibility(View.INVISIBLE);
            binding.passwordRepeatWrapper.setVisibility(View.INVISIBLE);
            binding.contact.setText(userr.getContact());
            binding.rol.setText(userr.getRol().toString(), false);

            binding.forgot.setVisibility(View.INVISIBLE);
            binding.login.setVisibility(View.INVISIBLE);
            binding.goBack.setVisibility(View.VISIBLE);
            binding.register.setText("UPDATE");
        } else {
            binding.forgot.setVisibility(View.VISIBLE);
            binding.login.setVisibility(View.VISIBLE);
            binding.goBack.setVisibility(View.INVISIBLE);
            binding.passwordWrapper.setVisibility(View.VISIBLE);
            binding.passwordRepeatWrapper.setVisibility(View.VISIBLE);
            binding.register.setText("REGISTER");
        }


        int year = 1992, month = 0, day = 3;
        binding.birthday.updateDate(year, month, day);

        binding.register.setOnClickListener(view -> attemptRegister());

        binding.profile.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            imageResultLauncher.launch(intent);
        });


    }

    private void attemptRegister() {
        if (!session.isLoggedIn()) {
            if (ValidUtil.isEmpty(this, this.binding.firstName, this.binding.lastName, this.binding.email, this.binding.password, this.binding.repeatPassword, this.binding.rol)) {
                return;
            }
        } else {
            if (ValidUtil.isEmpty(this, this.binding.firstName, this.binding.lastName, this.binding.email, this.binding.rol)) {
                return;
            }
        }

        // Store values at the time of the login attempt.
        final String email = this.binding.email.getText().toString();
        final String password = this.binding.password.getText().toString();
        final String repeatPassword = this.binding.repeatPassword.getText().toString();

        if (!password.equals(repeatPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!session.isLoggedIn()) {
            if (ValidUtil.isEmailValid(this.binding.email, email) && ValidUtil.isPasswordValid(this.binding.password, password)) {
                final KProgressHUD progressDialog = KProgressHUD.create(RegisterActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getString(R.string.please_wait))
                        .setDetailsLabel(getString(R.string.connecting))
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();

                final Userr user = new Userr()
                        .setFirstName(binding.firstName.getText().toString().trim())
                        .setLastName(binding.lastName.getText().toString().trim())
                        .setEmail(binding.email.getText().toString().trim())
                        .setPassword(binding.password.getText().toString().trim())
                        .setRol(Userr.ROL.valueOf(binding.rol.getText().toString().trim()))
                        .setContact(binding.contact.getText().toString().trim())
                        .setBirthday(getBirthday(binding.birthday));

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                user.setPhoto("");
                final Call<Userr> userCreateCall = retrofit.create(UserApiService.class).create(user);

                call(userCreateCall, error -> {
                    if (error) {
                        progressDialog.dismiss();
                        return;
                    }
                    progressDialog.dismiss();

//                    FirebaseNetwork.obtain().upload(uri, String.format("profile/%s.jpg", user.getUid()),
//                            new NetResponse<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    FancyToast.makeText(RegisterActivity.this, "Successfully upload image", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//
//                                    user.setPhoto(response);
//                                    final Call<Userr> userUpdateCall = retrofit.create(UserApiService.class).update(user);
//
//                                    call(userUpdateCall, res1 -> progressDialog.dismiss());
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t) {
//                                    progressDialog.dismiss();
//                                    FancyToast.makeText(RegisterActivity.this, t.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                                }
//                            });
                });

            }
        } else {
            if (ValidUtil.isEmailValid(this.binding.email, email)) {
                final KProgressHUD progressDialog = KProgressHUD.create(RegisterActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getString(R.string.please_wait))
                        .setDetailsLabel(getString(R.string.connecting))
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();

                final Userr user = new Userr()
                        .setFirstName(binding.firstName.getText().toString().trim())
                        .setLastName(binding.lastName.getText().toString().trim())
                        .setEmail(binding.email.getText().toString().trim())
                        .setPassword(binding.password.getText().toString().trim())
                        .setRol(Userr.ROL.valueOf(binding.rol.getText().toString().trim()))
                        .setContact(binding.contact.getText().toString().trim())
                        .setBirthday(getBirthday(binding.birthday));

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                user.setPhoto("");
                user.setUid(userr.getUid());

                final Call<Userr> userUpdateCall = retrofit.create(UserApiService.class).update(user);

                call(userUpdateCall, error -> {
                    if (error) {
                        progressDialog.dismiss();
                        return;
                    }
                    session.updateLoggedUser(user);
                    progressDialog.dismiss();

//                    FirebaseNetwork.obtain().upload(uri, String.format("profile/%s.jpg", user.getUid()),
//                            new NetResponse<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    FancyToast.makeText(RegisterActivity.this, "Successfully upload image", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//
//                                    user.setPhoto(response);
//                                    final Call<Userr> userUpdateCall = retrofit.create(UserApiService.class).update(user);
//
//                                    call(userUpdateCall, res1 -> progressDialog.dismiss());
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t) {
//                                    progressDialog.dismiss();
//                                    FancyToast.makeText(RegisterActivity.this, t.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
//                                }
//                            });
                });

            }
        }
    }

    private void call(Call<Userr> call, Consumer<Boolean> error) {
        call.enqueue(new Callback<Userr>() {
            @Override
            public void onResponse(Call<Userr> call, Response<Userr> response) {
                switch (response.code()) {
                    case 201:
                        FancyToast.makeText(RegisterActivity.this, "Successfully registered", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        error.accept(false);
                        break;
                    case 204:
                        FancyToast.makeText(RegisterActivity.this, "Successfully updated", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        error.accept(false);
                        break;
                    default:
                        try {
                            error.accept(true);
                            FancyToast.makeText(RegisterActivity.this, response.errorBody().string(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onFailure(Call<Userr> call, Throwable t) {
                error.accept(true);
                FancyToast.makeText(RegisterActivity.this, t.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                //Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private ActivityResultLauncher<Intent> imageResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            uri = result.getData().getData();
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                            binding.profile.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    private String getBirthday(DatePicker datePicker) {
        final int day = datePicker.getDayOfMonth();
        final int month = datePicker.getMonth();
        final int year = datePicker.getYear();

        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        return dateFormat.format(calendar.getTime());
    }

    private void retrieveSession() {
        //create new session object by passing application context
        session = new UserSession(getApplicationContext());
        //get User details if logged in
        userr = session.getUserSession();
    }

}