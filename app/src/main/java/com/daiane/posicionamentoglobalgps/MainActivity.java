package com.daiane.posicionamentoglobalgps;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buscarInformacoesGPS(View v) {
        LocationManager mLocManager;
        LocationListener mLocListener;
        mLocManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocListener = new MinhaLocalizacaoListener();


        if (ActivityCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            ACCESS_FINE_LOCATION
                    }, 1);
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            ACCESS_NETWORK_STATE
                    }, 1);
            return;
        }


        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
         if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
             String texto = "Latitude: " + MinhaLocalizacaoListener.latitude + "\n" +
                     "Longitude: " + MinhaLocalizacaoListener.longitude + "\n";
             Toast.makeText(MainActivity.this, texto, Toast.LENGTH_LONG).show();
             } else {
             Toast.makeText(MainActivity.this, "GPS DESABILITADO.", Toast.LENGTH_LONG).show();
             }

         this.mostrarGoogleMaps(MinhaLocalizacaoListener.latitude, MinhaLocalizacaoListener.longitude);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void mostrarGoogleMaps(double latitude, double longitude){
         WebView wv = findViewById(R.id.webv);
         wv.getSettings().setJavaScriptEnabled(true);
         wv.loadUrl("https://www.google.com/maps/search/?api=1&query=" +
                latitude + "," + longitude);
        }

}