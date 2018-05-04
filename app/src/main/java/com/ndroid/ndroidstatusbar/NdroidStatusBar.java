package com.ndroid.ndroidstatusbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NdroidStatusBar extends RelativeLayout {

    private static final String TAG = "Ndroid_StatusBar";

    private Context mContext;

    // Dimensions
    private int BAR_HEIGHT = 90;
    private int ICON_SIZE = 60;
    private int ICON_MARGIN  = 25;
    private int ICON_MARGIN_END = 50;

    private RelativeLayout mSettingsLayout;
    private RelativeLayout mIconLayout;

    // Carrier
    private TextView mCarrier;

    // Battery
    private int mBatteryLevel = 100;
    private View mBatteryView;

    // Clock
    private TextView mClockText;
    private int mClockId = 1;

    // Wifi
    private View mWifi;
    private int mWifiId = 2;

    // Bluetooth
    private View mBluetooth;
    private int mBluetoothId = 3;

    // Ringtone
    private View mRingtone;
    private int mRingtoneId = 4;
    private static final int ON = 1;
    private static final int OFF = 2;
    private static final int VIBRATE = 3;


    public NdroidStatusBar(Context context) {
        super(context);
        Log.d(TAG, "NdroidStatusBar()");

        mContext = context;

        initSettingsLayout();
        initIconLayout();

        setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mSettingsLayout);
        addView(mIconLayout);
    }

    private void initSettingsLayout() {
        mSettingsLayout = new RelativeLayout(mContext);
    }


    private void initIconLayout() {
        RelativeLayout.LayoutParams iparam= new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, BAR_HEIGHT);
        mIconLayout = new RelativeLayout(mContext);
        mIconLayout.setLayoutParams(iparam);
        setBackground(ContextCompat.getDrawable(mContext, R.drawable.status_bar_shape));


        // Battery Level
        mBatteryView  = new View(mContext);
        mBatteryView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                mBatteryLevel));
        mBatteryView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.battery_view_green));
        mIconLayout.addView(mBatteryView);

        // Carrier
        mCarrier = new TextView(mContext);
        mCarrier.setText("Orange");
        RelativeLayout.LayoutParams cParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        cParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        cParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        mCarrier.setLayoutParams(cParams);
        cParams.setMarginStart(ICON_MARGIN_END);
        mCarrier.setTextColor(Color.BLACK);
        mCarrier.setBackgroundColor(Color.TRANSPARENT);
        mIconLayout.addView(mCarrier);

        // Clock
        mClockText = new TextView(mContext);
        mClockText.setText("12:33");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mClockText.setLayoutParams(params);
        mClockText.setBackgroundColor(Color.TRANSPARENT);
        mClockText.setTextColor(Color.BLACK);
        mClockText.setTypeface(mClockText.getTypeface(), Typeface.BOLD);
        mClockText.setId(mClockId);
        mIconLayout.addView(mClockText);


        // Ringtone
        mRingtone = new View(mContext);
        RelativeLayout.LayoutParams rParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        rParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        rParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rParams.setMarginEnd(ICON_MARGIN_END);
        mRingtone.setLayoutParams(rParams);
        mRingtone.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_up));
        mRingtone.setId(mRingtoneId);
        mIconLayout.addView(mRingtone);

        // Wifi
        mWifi = new View(mContext);
        RelativeLayout.LayoutParams wParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        wParams.addRule(RelativeLayout.START_OF, mRingtoneId);
        wParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        wParams.setMarginEnd(ICON_MARGIN);
        mWifi.setLayoutParams(wParams);
        mWifi.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_wifi));
        mWifi.setId(mWifiId);
        mIconLayout.addView(mWifi);

        // Bluetooth
        mBluetooth = new View(mContext);
        RelativeLayout.LayoutParams bParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        bParams.addRule(RelativeLayout.START_OF, mWifiId);
        bParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        bParams.setMarginEnd(ICON_MARGIN);
        mBluetooth.setLayoutParams(bParams);
        mBluetooth.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bluetooth_yes));
        mBluetooth.setId(mBluetoothId);
        mIconLayout.addView(mBluetooth);
    }

    public void setBatteryLevel(int level) {
        Log.d(TAG, "setBatteryLevel() " + level);

        mBatteryLevel = level;
        mIconLayout.removeView(mBatteryView);
        mBatteryView  = new View(mContext);
        mBatteryView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                mBatteryLevel));
        if (mBatteryLevel >= 60) {
            mBatteryView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.battery_view_green));
        } else if (mBatteryLevel>=30){
            mBatteryView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.battery_view_yellow));
        } else {
            mBatteryView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.battery_view_red));
        }

        mIconLayout.addView(mBatteryView);

        // Brint text and icons on top of battery level
        bringViewsToFront();
    }

    public void setWifi(boolean status){
        Log.d(TAG, "setWifi() " + status);
        if (status) {
            mWifi.setVisibility(VISIBLE);
        } else {
            mWifi.setVisibility(GONE);
        }
    }

    public void setRingtone(int ringtone) {
        Log.d(TAG, "setRingtone() " + ringtone);
        mIconLayout.removeView(mRingtone);
        switch (ringtone) {
            case ON:
                mRingtone.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_up));
                break;
            case OFF:
                mRingtone.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_off));
                break;
            case VIBRATE:
                mRingtone.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_vibrate));
                break;
            default:
                break;
        }
        mIconLayout.addView(mRingtone);
    }

    public void setBluetooth(boolean status) {
        Log.d(TAG, "setBluetooth() " + status);
        if (status) {
            mBluetooth.setVisibility(View.VISIBLE);
        } else {
            mBluetooth.setVisibility(View.GONE);
        }
    }

    private void bringViewsToFront() {
        mCarrier.bringToFront();
        mClockText.bringToFront();
        mWifi.bringToFront();
        mRingtone.bringToFront();
        mBluetooth.bringToFront();
    }

    // Mandatory Constructors
    public NdroidStatusBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NdroidStatusBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NdroidStatusBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
