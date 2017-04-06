package mypocketvakil.example.com.letmenotify.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import mypocketvakil.example.com.letmenotify.NetworkClass.NetworkCall;
import mypocketvakil.example.com.letmenotify.ResponseBean.SignUpResponseBean;
import mypocketvakil.example.com.letmenotify.Sign_Up;

/**
 * Created by sanyam jain on 07-10-2016.
 */
public class SignUpAsyncTask extends AsyncTask<SignUpResponseBean,SignUpResponseBean,SignUpResponseBean> {
    private HashMap<String,String> postdataparams;
    private Context context;
    private static final String url = "http://192.168.43.219:138/userjson/";
    ProgressDialog progressDialog;
    private String responseString;


    public SignUpAsyncTask(Context context, HashMap<String,String> postdataparams)
    {
        this.context=context;
        this.postdataparams=postdataparams;
    }



    @Override
    protected SignUpResponseBean doInBackground(SignUpResponseBean... params) {
        return NetworkCall.getInstance(context).signupData(url,postdataparams);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(SignUpResponseBean signUpResponseBean) {
        super.onPostExecute(signUpResponseBean);
        if (progressDialog!=null&&progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
        if (signUpResponseBean !=null && signUpResponseBean.getErrorCode()==0 ) {
            responseString = signUpResponseBean.getResponseString();
//            Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Sign_Up)context).onSuccessfulSignUp(responseString);

        }
        else if (signUpResponseBean !=null) {
            responseString = signUpResponseBean.getResponseString();
            // Toast.makeText(context, loginResponseBean.getResponseString(), Toast.LENGTH_LONG).show();
            ((Sign_Up)context).onSignUpFailed(responseString);
        }
        else {
            //Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
            ((Sign_Up)context).onSignUpFailed("Network error");
        }
    }
}
