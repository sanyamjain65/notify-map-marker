package mypocketvakil.example.com.letmenotify.NetworkClass;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import mypocketvakil.example.com.letmenotify.ResponseBean.LoginResponseBean;
import mypocketvakil.example.com.letmenotify.ResponseBean.SignUpResponseBean;
import mypocketvakil.example.com.letmenotify.NetworkParser.NetworkParser;

/**
 * Created by sanyam jain on 06-10-2016.
 */
public class NetworkCall
{
    private static NetworkCall instance = null;

    int responseCode;
    private NetworkParser NetworkParser;

    public static NetworkCall getInstance(Context context) {
        if (instance == null)
            instance = new NetworkCall(context);
        return instance;
    }
    private NetworkCall(Context context) {
        NetworkParser = new NetworkParser(context.getApplicationContext());
    }

    public SignUpResponseBean signupData(String requestURL, HashMap<String, String> postDataParameters) {
        String result = performPostCall(requestURL, postDataParameters);
        Log.d("Response: ", "> " + result);

        return NetworkParser.parseSignUpData(result);
    }




    public LoginResponseBean loginData(String url, HashMap postdataparams) {
        String result = performPostCall(url, postdataparams);
        Log.d("Response: ", "> " + result);

        return NetworkParser.parseLoginData(result);

    }


    private String performPostCall(String url, HashMap<String, String> postDataParams)
    {
        String response="";
        try {
            URL url1;

            url1=new URL(url);

            HttpURLConnection conn=(HttpURLConnection) url1.openConnection();
            conn.setReadTimeout(25000);
            conn.setConnectTimeout(25000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(getPostDataString(postDataParams));
            wr.flush();
            wr.close();
            responseCode = conn.getResponseCode();

            if(responseCode==HttpURLConnection.HTTP_OK)
            {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line=br.readLine())!= null)
                {response+=line;}
            }
            else if (responseCode==HttpURLConnection.HTTP_CREATED)
            {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((line=br.readLine())!= null)
                {response+=line;}
            }

            else
            {
                response="";
            }

        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
        return response;








    }

    private  String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        StringBuilder result=new StringBuilder();
        boolean first=true;
        for(Map.Entry<String,String> entry :params.entrySet())
        {
            if(first)
                first=false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));




        }
        return result.toString();
    }



}


