package xyz.ravitripathi.interncart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.ravitripathi.interncart.R;
import xyz.ravitripathi.interncart.networking.SignupAPI;
import xyz.ravitripathi.interncart.pojo.AuthResponse;
import xyz.ravitripathi.interncart.pojo.SignupPOJO;

public class SignupActivity extends AppCompatActivity {

    Button signup;
    EditText eFname, eLname, ePassword, eRPassword, eEmail;
    String fname, lname, password, rpassword, email;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bindView();
    }

    private void bindView() {
        signup = findViewById(R.id.signup);
        eFname = findViewById(R.id.fname);
        eLname = findViewById(R.id.lname);
        ePassword = findViewById(R.id.password);
        eRPassword = findViewById(R.id.repassword);
        eEmail = findViewById(R.id.email);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.error);
        awesomeValidation.addValidation(this, R.id.lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.error);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.error);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = ePassword.getText().toString();
                String repass = eRPassword.getText().toString();


                if (awesomeValidation.validate() && password!=null && repass!=null && password.equals(repass)) {
                    fname = eFname.getText().toString();
                    lname = eLname.getText().toString();
                    email = eEmail.getText().toString();
                    signUpUser(fname, lname, password, email);
                }
            }
        });
    }

    private void signUpUser(String fname, String lname, String password, String username) {
        final SignupAPI auth = SignupAPI.retrofit.create(SignupAPI.class);
        Call<AuthResponse> call = auth.signupUser(new SignupPOJO(username, password, fname, lname));
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.isSuccessful()) {
                    Log.e("Success", String.valueOf(response.code()));
                    try {
                        Toast.makeText(SignupActivity.this, response.body().getUid(), Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("uid", response.body().getUid());
                        editor.commit();
                        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } else {

                    if(response.code()==406){
                        Toast.makeText(SignupActivity.this, "Already Exists", Toast.LENGTH_SHORT).show();
                    }


                    Log.e("Not success", String.valueOf(response.code()));
                    Toast.makeText(SignupActivity.this, "Call not successful: "+response.code(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e("FAIL", "FAIL");
                Toast.makeText(SignupActivity.this, "Call Failure", Toast.LENGTH_SHORT);
            }
        });

    }


}
