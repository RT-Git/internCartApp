package xyz.ravitripathi.interncart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.networking.AuthAPI;
import xyz.ravitripathi.interncart.pojo.AuthPOJO;
import xyz.ravitripathi.interncart.pojo.AuthResponse;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private RelativeLayout container;
    private Button button, signup;
    private Context c = this;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("shared", 0);
        String uidFromStorage = sharedPref.getString("uid", null);
        if (uidFromStorage != null) {
            Log.d("uidLogin",uidFromStorage);
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("uid", uidFromStorage);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }else
            Log.d("uidLogin","null value");

        bindViews();

    }

    private void bindViews() {
        button = findViewById(R.id.login);
        username = findViewById(R.id.userName);
        animationView = findViewById(R.id.animation_view);
        password = findViewById(R.id.password);
        container = findViewById(R.id.container);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username.setError(null);
                password.setError(null);

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null
                        : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (user.isEmpty()) {
                    username.setError("Enter the username");

                    return;
                }

                if (pass.isEmpty()) {
                    password.setError("Enter the password");
                    return;
                }

                authenticate(user, pass);

            }
        });
    }

    private void authenticate(String username, String password) {
        container.setVisibility(View.GONE);
        animationView.setVisibility(View.VISIBLE);
        final AuthAPI auth = AuthAPI.retrofit.create(AuthAPI.class);
        Call<AuthResponse> call = auth.postUser(new AuthPOJO(username, password));
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                container.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    Log.e("Success", String.valueOf(response.code()));
                    try {
                        String resp = response.body().getUid();
                        Toast.makeText(LoginActivity.this, resp, Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getApplicationContext()
                                .getSharedPreferences("shared", 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("uid", resp);
                        editor.commit();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("uid", response.body().getUid());
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.code() == 401) {
                        Log.e("Not success", String.valueOf(response.code()));
                        Toast.makeText(LoginActivity.this, "Sorry, this user does not exits", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Not success", String.valueOf(response.code()));
                        Toast.makeText(LoginActivity.this, "response in not successful", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e("FAIL", "FAIL");
                container.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Call Failure", Toast.LENGTH_SHORT);
            }
        });
    }
}
