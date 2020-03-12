package ca.tristensmith.gpssimple;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView tvCurrentLong;
    TextView tvCurrentLat;
    TextView tvHomeLong;
    TextView tvHomeLat;
    TextView tvMeters;

    double homeLat = 53.5679256239122;
    double homrLong = 113.500055138580501;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCurrentLat = findViewById(R.id.tv_current_lat);
        tvCurrentLong = findViewById(R.id.tv_current_long);
        tvHomeLat = findViewById(R.id.tv_home_lat);
        tvHomeLong = findViewById(R.id.tv_home_long);
        tvMeters = findViewById(R.id.tv_meters);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener());

    }

    public class MyLocationListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location)
        {
            double meters = 33;
            double dLong = location.getLongitude() * -1;
            double dLat = location.getLatitude();

            meters = calculateDifferenceInMeters(dLong, dLat);

            NumberFormat formatter = new DecimalFormat("0.00");
            String strMeters = formatter.format(meters);

            tvHomeLat.setText("Home Latitude = " + homeLat);
            tvHomeLong.setText("Home Longitude = " + homrLong);
            tvCurrentLat.setText("Current Latitude = " + dLat);
            tvCurrentLong.setText("Current Longitude = " + dLong);
            tvMeters.setText("Distence is = " + strMeters);
        }

        private double calculateDifferenceInMeters(double dLong, double dLat)
        {
            double distanceInMeters = 45;

            double earthRaduis = 3958.75;
            double dLatitude = Math.toRadians(dLat - homeLat);
            double dLongitude = Math.toRadians(dLong - homrLong);
            double sindLat = Math.sin(dLatitude / 2);
            double sindLon = Math.sin(dLongitude / 2);
            double a = Math.pow(sindLat, 2) + Math.pow(sindLon, 2) * Math.cos(dLat) * Math.cos(homeLat);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
            double distInMiles = earthRaduis * c;

            distanceInMeters = (distInMiles * 1.609344 * 1000);

            return  distanceInMeters;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "GPS is enabled", Toast.LENGTH_LONG).show();
            Log.d(TAG, "GPS is enabled");
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "GPS is disabled", Toast.LENGTH_LONG).show();
            Log.d(TAG, "GPS is disabled");
        }
    }
}
