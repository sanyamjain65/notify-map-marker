package mypocketvakil.example.com.letmenotify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import mypocketvakil.example.com.letmenotify.AsyncTask.LoginAsyncTask;

public class Login extends AppCompatActivity {
    Button button;
    TextView textView;
    private EditText et_login_email;
    private EditText et_login_password;
    private HashMap postdataparams;
    RelativeLayout rel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button=(Button)findViewById(R.id.button1);
        textView=(TextView)findViewById(R.id.enter);
        et_login_email=(EditText)findViewById(R.id.edit1);
        et_login_password=(EditText)findViewById(R.id.password);
        rel=(RelativeLayout)findViewById(R.id.rllogin);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Sign_Up.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdataparams=new HashMap<String,String>();
                postdataparams.put("email",et_login_email.getText().toString());
                postdataparams.put("password",et_login_password.getText().toString());
                LoginAsyncTask task=new LoginAsyncTask(Login.this,postdataparams);
                task.execute();
            }
        });

    }

    public void onSuccessfulSignUp(String responseString) {
        Intent intent=new Intent(Login.this,MainActivity.class);
        startActivity(intent);
//        SharedPreferences sharedPref = PreferenceManager
//                .getDefaultSharedPreferences(Login.this);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("access_token", "kjdskjcnsdk");
//        editor.commit();
//        editor.apply();
        this.finish();
    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack=Snackbar.make(rel,responseString,Snackbar.LENGTH_LONG);
        snack.show();
    }
}
