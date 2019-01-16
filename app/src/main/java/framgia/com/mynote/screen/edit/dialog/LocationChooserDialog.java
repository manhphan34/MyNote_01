package framgia.com.mynote.screen.edit.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.IOException;

import framgia.com.mynote.R;
import framgia.com.mynote.databinding.DialogLocationBinding;
import framgia.com.mynote.screen.edit.HandlerClick;
import framgia.com.mynote.utils.DialogHelper;
import framgia.com.mynote.utils.LocationHelper;

public class LocationChooserDialog extends BaseDialog {
    public static final double DIALOG_WIDTH = 0.9;
    public static final double DIALOG_HEIGHT = 0.22;
    private HandlerClick.LocationHandledListener mLocationHandledListener;
    private DialogLocationBinding mBinding;
    private Context mContext;
    private FusedLocationProviderClient mProviderClient;

    public LocationChooserDialog(Context context,
                                 HandlerClick.LocationHandledListener locationHandledListener,
                                 FusedLocationProviderClient providerClient) {
        mContext = context;
        mProviderClient = providerClient;
        mDialogHelper = DialogHelper.getInstance(mContext);
        mLocationHandledListener = locationHandledListener;
    }

    @Override
    public void setLocation() {
        Window window = mDialogHelper.getWindow();
        WindowManager.LayoutParams wlp;
        if (window != null) {
            wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        }
    }

    public void showDialog() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_location, null, false);
        mDialogHelper.setView(mBinding.getRoot());
        init(DIALOG_WIDTH, DIALOG_HEIGHT);
        mBinding.setLocation(this);
        setLocation();
        show();
    }

    public void getCurrentLocation() {
        LocationHelper locationHelper = new LocationHelper(mContext);
        if (!locationHelper.isTurnOnGPS()) {
            mLocationHandledListener.onGPSTurnOff();
            return;
        }
        if (!locationHelper.isTurnOnInternet()) {
            mLocationHandledListener.onNetWorkTurnOff();
            return;
        }
        locationHelper.getLastLocation(mProviderClient, location -> {
            try {
                mLocationHandledListener.onGetLocationSuccess(locationHelper.getPlace(location.getLatitude(), location.getLongitude()));
            } catch (IOException e) {
                mLocationHandledListener.onGEtLocationFail(e);
            }
        }, e -> {
            mLocationHandledListener.onGEtLocationFail(e);
        });
    }

    public void onGetCurrentLocation() {
        getCurrentLocation();
    }

    public void onSubmitLocation() {
        if (mBinding.editLocation.getText().toString().isEmpty()) {
            mLocationHandledListener.onLocationEmpty();
            return;
        }
        mLocationHandledListener.onGetLocationSuccess(mBinding.editLocation.getText().toString());
    }
}
