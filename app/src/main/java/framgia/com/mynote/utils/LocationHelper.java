package framgia.com.mynote.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.provider.Settings;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Locale;

public class LocationHelper {
    private static final int ADDRESS_LINE = 0;
    private static final int MAX_RESULT = 1;
    private Context mContext;

    public LocationHelper(Context context) {
        mContext = context;
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation(FusedLocationProviderClient providerClient,
                                OnSuccessListener<Location> onSuccessListener,
                                OnFailureListener onFailureListener) {
        providerClient.getLastLocation()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public boolean isTurnOnInternet() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean b = false;
        if (cm.getActiveNetworkInfo() != null) {
            b = cm.getActiveNetworkInfo().isConnected();
        }
        return b;
    }

    public boolean isTurnOnGPS() {
        return getLocationMode(mContext) != Settings.Secure.LOCATION_MODE_OFF;
    }

    private static int getLocationMode(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
    }

    public String getPlace(double lat, double lng) throws IOException {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        return geocoder.getFromLocation(lat, lng, MAX_RESULT).get(0).getAddressLine(ADDRESS_LINE);
    }
}
