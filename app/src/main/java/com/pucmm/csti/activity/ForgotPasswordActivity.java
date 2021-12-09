package com.pucmm.csti.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.pucmm.csti.databinding.ActivityForgotPasswordBinding;
import com.pucmm.csti.model.Userr;
import com.pucmm.csti.retrofit.UserApiService;
import com.pucmm.csti.utils.Constants;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            finish();
        });

        binding.register.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPasswordActivity.this, RegisterActivity.class));
            finish();
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptForgotPassword();
            }
        });
    }

    private void attemptForgotPassword() {

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String newPassword = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


        final JsonObject user = new JsonObject();
        user.addProperty("email", binding.forgotPassEmail.getText().toString().trim());
        user.addProperty("password", newPassword.trim());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Userr.class, dateJsonDeserializer);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();

        final Call<Void> userChangePasswordCall = retrofit.create(UserApiService.class).changePassword(user);

        userChangePasswordCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 204:

//                        FancyToast.makeText(LoginActivity.this, "Successfully changed", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
//                        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                        Toast.makeText(ForgotPasswordActivity.this, newPassword, Toast.LENGTH_LONG).show();
//                        finish();
                        break;
                    default:
                        Toast.makeText(ForgotPasswordActivity.this, "The email doesn't exist", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable error) {
                FancyToast.makeText(ForgotPasswordActivity.this, error.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            }
        });
    }



    private final JsonDeserializer<Userr> dateJsonDeserializer = (json, typeOfT, context) -> {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        final JsonObject jo = (JsonObject) json;

        return new Userr()
                .setUid(jo.get("uid").getAsInt())
                .setFirstName(jo.get("firstName").getAsString())
                .setLastName(jo.get("lastName").getAsString())
                .setEmail(jo.get("email").getAsString())
                .setRol(Userr.ROL.valueOf(jo.get("rol").getAsString()))
                .setContact(jo.get("contact").getAsString())
                .setPhoto(jo.get("photo").getAsString())
                .setBirthday(dateFormat.format(new Date(jo.get("birthday").getAsLong())));
    };


}