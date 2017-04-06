package mypocketvakil.example.com.letmenotify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.HashMap;

import mypocketvakil.example.com.letmenotify.AsyncTask.SignUpAsyncTask;

public class Sign_Up extends AppCompatActivity {
    EditText firstname,password,email;
    private HashMap postdataparams;
    Button button;
    RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        firstname=(EditText)findViewById(R.id.full_name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.signuppassword);
        button=(Button)findViewById(R.id.signupbutton);
        rel=(RelativeLayout)findViewById(R.id.rlsignup);

    }
    public void sendQuery(View view)
    {
        postdataparams=new HashMap<String,String>();
        postdataparams.put("name",firstname.getText().toString());
        postdataparams.put("password",password.getText().toString());
        postdataparams.put("email",email.getText().toString());
        SignUpAsyncTask task=new SignUpAsyncTask(Sign_Up.this,postdataparams);
        task.execute();
    }

    public void onSuccessfulSignUp(String responseString) {
        Intent intent=new Intent(Sign_Up.this,Login.class);
        startActivity(intent);
        this.finish();
    }

    public void onSignUpFailed(String responseString) {
        Snackbar snack=Snackbar.make(rel,responseString,Snackbar.LENGTH_LONG);
        snack.show();
    }
}
