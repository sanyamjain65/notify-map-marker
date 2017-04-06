package mypocketvakil.example.com.letmenotify;

import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback {
double latitude;
    double longitude;
    GPSTraker gps;
    private static  String url="http://192.168.43.219:138/locationjson/";
    private String TAG=MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private ProgressBar spinner;

    MapFragment mapFragment;

GoogleMap map;

    int l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean lowPowerMoreImportantThanAccurancy = true;
        LocationRequest request = LocationRequest.create();
        request.setPriority(lowPowerMoreImportantThanAccurancy ?
                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY :
                LocationRequest.PRIORITY_HIGH_ACCURACY);

//        SupportMapFragment map = ((SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map)).getMap();
//        map = ((SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map)).getMap();


        spinner=(ProgressBar)findViewById(R.id.progressBar1);


        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);

        gps=new GPSTraker(this);
         latitude=gps.getLatitude();
         longitude=gps.getLongitude();




    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map=googleMap;
        LatLng latLng = new LatLng(latitude, longitude);


        LatLng latLng1=new LatLng(28.6314220,77.3731342);
        spinner.setVisibility(View.GONE);
        new getContacts().execute();
       Marker marker= googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                );


        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(17));




        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {

                        View v = getLayoutInflater().inflate(R.layout.info_window, null);
                        return v;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.info_window, null);





                        return v;
                    }
                });
                return false;
            }
        });




        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        map.setMyLocationEnabled(true);


    }




//    @Override
//    public void onMapReady(final GoogleMap map) {












//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        // Enabling MyLocation Layer of Google Map
        // map.setMyLocationEnabled(true);

//        map.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0)).draggable(true)
        // Set Opacity specified as a float between 0.0 and 1.0,
        // where 0 is fully transparent and 1 is fully opaque.
//                );



//    }

    private class getContacts extends AsyncTask<Void,Void,Void>
    {

        String title[]=new String[2000];
        double lat[]=new double[2000];
        double longi[]=new double[2000];





        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog=new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Loading");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
           // spinner.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh=new HttpHandler();
            String jsonStr= sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if(jsonStr!=null)
            {
                try {
                    //JSONObject object=new JSONObject(jsonStr);
                    JSONArray contacts=new JSONArray(jsonStr);
//
                    l=contacts.length();


                    for (int i=0;i<contacts.length(); i++)
                    {
                        JSONObject c=contacts.getJSONObject(i);

                        title[i]= c.getString("name");
                        lat[i] = Double.parseDouble(c.getString("latitude"));
                        longi[i]= Double.parseDouble(c.getString("longitude"));






                    }

                }catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Json nai mila",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            if(progressDialog.isShowing())
//            {
//                progressDialog.dismiss();
//            }
            if(spinner.isShown())
            {
                spinner.setVisibility(View.GONE);
            }
            for ( int i=0;i<l;i++)
            {
//            drawMarker(new LatLng(lat[i],longi[i]));
                LatLng ll=new LatLng(lat[i],longi[i]);
                map.addMarker(new MarkerOptions()
                        .position(ll)

                );
            }

        }



    }

}
