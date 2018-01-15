package xyz.ravitripathi.interncart.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.POJO.authPOJO;
import xyz.ravitripathi.interncart.POJO.authResponse;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.networking.authAPI;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private RelativeLayout container;
    private Button button;
    private Context c = this;
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();

    }

    private void bindViews() {
        button = findViewById(R.id.login);
        username = findViewById(R.id.userName);
        animationView = findViewById(R.id.animation_view);
        password = findViewById(R.id.password);
        container = findViewById(R.id.container);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username.setError(null);
                password.setError(null);

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(c.INPUT_METHOD_SERVICE);

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
        final authAPI auth = authAPI.retrofit.create(authAPI.class);
        Call<authResponse> call = auth.postUser(new authPOJO(username, password));
        call.enqueue(new Callback<authResponse>() {
            @Override
            public void onResponse(Call<authResponse> call, Response<authResponse> response) {
                container.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, response.body().getUid(), Toast.LENGTH_SHORT).show();
                }
                else{
                    if (response.code() == 401) {
                        Toast.makeText(LoginActivity.this, "Sorry, this user does not exits", Toast.LENGTH_SHORT).show();
                    }

                    else
                        Toast.makeText(LoginActivity.this,"response in not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<authResponse> call, Throwable t) {
                container.setVisibility(View.VISIBLE);
                animationView.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Failure", Toast.LENGTH_SHORT);
            }
        });
    }
}
