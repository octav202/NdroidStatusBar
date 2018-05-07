package com.ndroid.ndroidstatusbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import static android.widget.LinearLayout.HORIZONTAL;

public class NdroidStatusBar extends RelativeLayout {

    private static final String TAG = "Ndroid_StatusBar";

    private Context mContext;

    // Dimensions
    private int SETTINGS_HEIGHT = 500;
    private int BAR_HEIGHT = 90;
    private int BUTTON_MARGIN = 10;
    private int ICON_SIZE = 60;
    private int ICON_MARGIN = 25;
    private int ICON_MARGIN_END = 50;
    private int SETTINGS_TOP_MARGIN_COLLAPSED = -500;

    // [_____ Settings _____]
    private RelativeLayout mSettingsLayout;
    private int mSettingsLayoutId = 5;

    // Buttons
    private LinearLayout mButtonsLayout;
    private int mButtonsLayoutId = 10;
    private Button mWifiButton;
    private Button mMobileDataButton;
    private Button mRingtoneButton;
    private Button mBluetoothButton;
    private Button mOrientationButton;

    // Brightness
    private RelativeLayout mBrightnessLayout;
    private int mBrightnessLayoutId = 11;
    private SeekBar mBrightnessBar;
    private View mBrightnessStart;
    private int mBrightnessStartId = 12;
    private View mBrightnessEnd;
    private int mBrightnessEndId = 13;
    private TextView mBrightnessText;

    // Ringtone
    private RelativeLayout mRingtoneLayout;
    private int mRingtoneLayoutId = 14;
    private SeekBar mRingtoneBar;
    private View mRingtoneStart;
    private int mRingtoneStartId = 15;
    private View mRingtoneEnd;
    private int mRingtoneEndId = 16;
    private TextView mRingtoneText;

    // [_____ Status Bar _____] //

    // StatusBar
    private RelativeLayout mIconLayout;
    private int mIconLayoutId = 6;

    // Carrier
    private TextView mCarrier;

    // Battery
    private int mBatteryLevel = 100;
    private View mBatteryView;

    // Clock
    private TextView mClockText;
    private int mClockId = 1;

    // Wifi
    private View mWifiIcon;
    private int mWifiIconId = 2;

    // Bluetooth
    private View mBluetoothIcon;
    private int mBluetoothIconId = 3;

    // Ringtone
    private View mRingtoneIcon;
    private int mRingtoneIconId = 4;
    private static final int ON = 1;
    private static final int OFF = 2;
    private static final int VIBRATE = 3;

    // Touch Events
    private float mStartPoint;
    private int mOffset = 0;
    private static final int MIN_OFFSET = -500;
    private static final int MAX_OFFSET = 500;

    public NdroidStatusBar(Context context) {
        super(context);
        Log.d(TAG, "NdroidStatusBar()");

        mContext = context;

        initSettingsLayout();
        initIconLayout();

        LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, SETTINGS_TOP_MARGIN_COLLAPSED, 0, 0);
        setLayoutParams(params);
        setBackgroundColor(Color.TRANSPARENT);
        addView(mSettingsLayout);
        addView(mIconLayout);
    }

    // Init Settings Layout
    private void initSettingsLayout() {
        mSettingsLayout = new RelativeLayout(mContext);
        LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, SETTINGS_HEIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mSettingsLayout.setLayoutParams(params);
        mSettingsLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.status_bar_background));
        mSettingsLayout.setId(mSettingsLayoutId);

        initButtonsLayout();
        initBrightnessLayout();
        initRingtoneLayout();
    }

    private void initButtonsLayout() {
        // Buttons Layout
        mButtonsLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mButtonsLayout.setLayoutParams(lParams);
        mButtonsLayout.setOrientation(HORIZONTAL);
        mButtonsLayout.setWeightSum(5);
        mButtonsLayout.setId(mButtonsLayoutId);

        // Wifi
        mWifiButton = new Button(mContext);
        mWifiButton.setText("WI");
        LinearLayout.LayoutParams wParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        mWifiButton.setLayoutParams(wParams);
        wParams.setMarginStart(BUTTON_MARGIN);
        wParams.setMarginEnd(BUTTON_MARGIN);
        wParams.weight = 1;
        mWifiButton.setTextColor(Color.BLACK);
        mWifiButton.setBackgroundColor(Color.TRANSPARENT);
        mButtonsLayout.addView(mWifiButton);

        // Mobile Data
        mMobileDataButton = new Button(mContext);
        mMobileDataButton.setText("MD");
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        mParams.setMarginEnd(BUTTON_MARGIN);
        mParams.weight = 1;
        mMobileDataButton.setLayoutParams(mParams);
        mMobileDataButton.setTextColor(Color.BLACK);
        mMobileDataButton.setBackgroundColor(Color.TRANSPARENT);
        mButtonsLayout.addView(mMobileDataButton);

        // Ringtone
        mRingtoneButton = new Button(mContext);
        mRingtoneButton.setText("RING");
        LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        rParams.setMarginEnd(BUTTON_MARGIN);
        rParams.weight = 1;
        mRingtoneButton.setLayoutParams(rParams);
        mRingtoneButton.setTextColor(Color.BLACK);
        mRingtoneButton.setBackgroundColor(Color.TRANSPARENT);
        mButtonsLayout.addView(mRingtoneButton);

        // Bluetooth
        mBluetoothButton = new Button(mContext);
        mBluetoothButton.setText("BT");
        LinearLayout.LayoutParams bParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        bParams.setMarginEnd(BUTTON_MARGIN);
        bParams.weight = 1;
        mBluetoothButton.setLayoutParams(bParams);
        mBluetoothButton.setTextColor(Color.BLACK);
        mBluetoothButton.setBackgroundColor(Color.TRANSPARENT);
        mButtonsLayout.addView(mBluetoothButton);

        // Screen Rotation
        mOrientationButton = new Button(mContext);
        mOrientationButton.setText("SC");
        LinearLayout.LayoutParams oParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        oParams.setMarginEnd(BUTTON_MARGIN);
        oParams.weight = 1;
        mOrientationButton.setLayoutParams(oParams);
        mOrientationButton.setTextColor(Color.BLACK);
        mOrientationButton.setBackgroundColor(Color.TRANSPARENT);
        mButtonsLayout.addView(mOrientationButton);
        mSettingsLayout.addView(mButtonsLayout);

    }

    private void initBrightnessLayout() {
        // Brightness Layout
        mBrightnessLayout = new RelativeLayout(mContext);
        mBrightnessLayout.setId(mBrightnessLayoutId);
        mBrightnessLayout.setBackgroundColor(Color.TRANSPARENT);
        RelativeLayout.LayoutParams brParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        brParams.addRule(BELOW, mButtonsLayoutId);
        brParams.setMargins(ICON_MARGIN, ICON_MARGIN, ICON_MARGIN, 0);
        mBrightnessLayout.setLayoutParams(brParams);

        // Brightness Start Icon
        mBrightnessStart = new View(mContext);
        RelativeLayout.LayoutParams brStartParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        brStartParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        brStartParams.setMarginStart(ICON_MARGIN);
        mBrightnessStart.setLayoutParams(brStartParams);
        mBrightnessStart.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_off));
        mBrightnessStart.setId(mBrightnessStartId);
        mBrightnessLayout.addView(mBrightnessStart);

        // Brightness End Icon
        mBrightnessEnd = new View(mContext);
        RelativeLayout.LayoutParams brEndParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        brEndParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        brEndParams.setMarginEnd(ICON_MARGIN);
        mBrightnessEnd.setLayoutParams(brEndParams);
        mBrightnessEnd.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_up));
        mBrightnessEnd.setId(mBrightnessEndId);
        mBrightnessLayout.addView(mBrightnessEnd);

        // Brightness TextView
        mBrightnessText = new TextView(mContext);
        mBrightnessText.setText("Brightness");
        mBrightnessText.setBackgroundColor(Color.TRANSPARENT);
        mBrightnessText.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams bTextParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        bTextParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mBrightnessText.setLayoutParams(bTextParams);
        mBrightnessLayout.addView(mBrightnessText);

        // Brightness SeekBar
        mBrightnessBar = new SeekBar(mContext);
        RelativeLayout.LayoutParams barParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        barParams.setMargins(ICON_MARGIN, ICON_MARGIN, ICON_MARGIN, ICON_MARGIN);
        barParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        barParams.addRule(RelativeLayout.BELOW, mBrightnessStartId);
        barParams.addRule(RelativeLayout.BELOW, mBrightnessEndId);
        mBrightnessBar.setLayoutParams(barParams);
        mBrightnessLayout.addView(mBrightnessBar);

        mSettingsLayout.addView(mBrightnessLayout);
    }

    private void initRingtoneLayout() {
        // Ringtone Layout
        mRingtoneLayout = new RelativeLayout(mContext);
        mRingtoneLayout.setId(mRingtoneLayoutId);
        mRingtoneLayout.setBackgroundColor(Color.TRANSPARENT);
        RelativeLayout.LayoutParams rParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        rParams.addRule(BELOW, mBrightnessLayoutId);
        rParams.setMargins(ICON_MARGIN, 0, ICON_MARGIN, 0);
        mRingtoneLayout.setLayoutParams(rParams);

        // Ringtone Start Icon
        mRingtoneStart = new View(mContext);
        RelativeLayout.LayoutParams rStartParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        rStartParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        rStartParams.setMarginStart(ICON_MARGIN);
        mRingtoneStart.setLayoutParams(rStartParams);
        mRingtoneStart.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_off));
        mRingtoneStart.setId(mRingtoneStartId);
        mRingtoneLayout.addView(mRingtoneStart);

        // Ringtone End Icon
        mRingtoneEnd = new View(mContext);
        RelativeLayout.LayoutParams rEndParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        rEndParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        rEndParams.setMarginEnd(ICON_MARGIN);
        mRingtoneEnd.setLayoutParams(rEndParams);
        mRingtoneEnd.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_up));
        mRingtoneEnd.setId(mRingtoneEndId);
        mRingtoneLayout.addView(mRingtoneEnd);

        // Ringtone TextView
        mRingtoneText = new TextView(mContext);
        mRingtoneText.setText("Ringtone");
        mRingtoneText.setBackgroundColor(Color.TRANSPARENT);
        mRingtoneText.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams rTextParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        rTextParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mRingtoneText.setLayoutParams(rTextParams);
        mRingtoneLayout.addView(mRingtoneText);

        // Ringtone SeekBar
        mRingtoneBar = new SeekBar(mContext);
        RelativeLayout.LayoutParams barParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        barParams.setMargins(ICON_MARGIN, ICON_MARGIN, ICON_MARGIN, ICON_MARGIN);
        barParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        barParams.addRule(RelativeLayout.BELOW, mRingtoneStartId);
        barParams.addRule(RelativeLayout.BELOW, mRingtoneEndId);
        mRingtoneBar.setLayoutParams(barParams);
        mRingtoneLayout.addView(mRingtoneBar);

        mSettingsLayout.addView(mRingtoneLayout);
    }

    // Init Status Bar Layout
    private void initIconLayout() {
        RelativeLayout.LayoutParams iparam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, BAR_HEIGHT);
        iparam.addRule(RelativeLayout.BELOW, mSettingsLayoutId);
        mIconLayout = new RelativeLayout(mContext);
        mIconLayout.setLayoutParams(iparam);
        mIconLayout.setId(mIconLayoutId);
        mIconLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.status_bar_shape));


        // Battery Level
        mBatteryView = new View(mContext);
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
        mRingtoneIcon = new View(mContext);
        RelativeLayout.LayoutParams rParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        rParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        rParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rParams.setMarginEnd(ICON_MARGIN_END);
        mRingtoneIcon.setLayoutParams(rParams);
        mRingtoneIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_up));
        mRingtoneIcon.setId(mRingtoneIconId);
        mIconLayout.addView(mRingtoneIcon);

        // Wifi
        mWifiIcon = new View(mContext);
        RelativeLayout.LayoutParams wParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        wParams.addRule(RelativeLayout.START_OF, mRingtoneIconId);
        wParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        wParams.setMarginEnd(ICON_MARGIN);
        mWifiIcon.setLayoutParams(wParams);
        mWifiIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_wifi));
        mWifiIcon.setId(mWifiIconId);
        mIconLayout.addView(mWifiIcon);

        // Bluetooth
        mBluetoothIcon = new View(mContext);
        RelativeLayout.LayoutParams bParams = new RelativeLayout.LayoutParams(ICON_SIZE, ICON_SIZE);
        bParams.addRule(RelativeLayout.START_OF, mWifiIconId);
        bParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        bParams.setMarginEnd(ICON_MARGIN);
        mBluetoothIcon.setLayoutParams(bParams);
        mBluetoothIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_bluetooth_yes));
        mBluetoothIcon.setId(mBluetoothIconId);
        mIconLayout.addView(mBluetoothIcon);
    }

    public void setBatteryLevel(int level) {
        Log.d(TAG, "setBatteryLevel() " + level);

        mBatteryLevel = level;
        mIconLayout.removeView(mBatteryView);
        mBatteryView = new View(mContext);
        mBatteryView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                mBatteryLevel));
        if (mBatteryLevel >= 60) {
            mBatteryView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.battery_view_green));
        } else if (mBatteryLevel >= 30) {
            mBatteryView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.battery_view_yellow));
        } else {
            mBatteryView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.battery_view_red));
        }
        mIconLayout.addView(mBatteryView);

        // Brint text and icons on top of battery level
        bringViewsToFront();
    }

    public void setWifi(boolean status) {
        Log.d(TAG, "setWifi() " + status);
        if (status) {
            mWifiIcon.setVisibility(VISIBLE);
        } else {
            mWifiIcon.setVisibility(GONE);
        }
    }

    public void setRingtone(int ringtone) {
        Log.d(TAG, "setRingtone() " + ringtone);
        mIconLayout.removeView(mRingtoneIcon);
        switch (ringtone) {
            case ON:
                mRingtoneIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_up));
                break;
            case OFF:
                mRingtoneIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_volume_off));
                break;
            case VIBRATE:
                mRingtoneIcon.setBackground(ContextCompat.getDrawable(mContext, R.drawable.ic_vibrate));
                break;
            default:
                break;
        }
        mIconLayout.addView(mRingtoneIcon);
    }

    public void setBluetooth(boolean status) {
        Log.d(TAG, "setBluetooth() " + status);
        if (status) {
            mBluetoothIcon.setVisibility(View.VISIBLE);
        } else {
            mBluetoothIcon.setVisibility(View.GONE);
        }
    }

    private void bringViewsToFront() {
        mCarrier.bringToFront();
        mClockText.bringToFront();
        mWifiIcon.bringToFront();
        mRingtoneIcon.bringToFront();
        mBluetoothIcon.bringToFront();
    }

    /**
     * Touch Events
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartPoint = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                int margin = params.topMargin;
                Log.d(TAG, "ACTION_UP margin =" + margin);

                // Expand
                if (margin >= SETTINGS_TOP_MARGIN_COLLAPSED / 2) {
                    mOffset = 0;
                    expandStatusBar();
                } else if (margin <= SETTINGS_TOP_MARGIN_COLLAPSED / 2) {
                    // Collapse
                    mOffset = MIN_OFFSET;
                    collapseStatusBar();
                }

                // Reset values
                mStartPoint = 0;
                mOffset = 0;

                break;
            case MotionEvent.ACTION_MOVE:
                mOffset = Math.round(event.getY() - mStartPoint);
                // Check Offset limits
                if (mOffset < MIN_OFFSET) {
                    mOffset = MIN_OFFSET;
                } else if (mOffset >= MAX_OFFSET) {
                    mOffset = MAX_OFFSET;
                }
                Log.d(TAG, "ACTION_MOVE Offset =" + mOffset);
                updateLayoutOffset(mOffset);
                break;
        }

        return true;
    }

    /**
     * Update StatusBarLayout - Expand/Collapse
     *
     * @param offset
     */
    private void updateLayoutOffset(int offset) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        int margin = params.topMargin;

        margin += offset;
        if (margin < SETTINGS_TOP_MARGIN_COLLAPSED) {
            margin = MIN_OFFSET;
        } else if (margin >= 0) {
            margin = 0;
        }

        params.setMargins(0, margin, 0, 0);
        setLayoutParams(params);
        requestLayout();
    }

    /**
     * Expand Status Bar and show Settings.
     */
    private void expandStatusBar() {
        Log.d(TAG, "expandStatusBar()");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        setLayoutParams(params);
        requestLayout();
    }

    /**
     * Collapse Status bar and hide Settings.
     */
    private void collapseStatusBar() {
        Log.d(TAG, "collapseStatusBar()");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        params.setMargins(0, SETTINGS_TOP_MARGIN_COLLAPSED, 0, 0);
        setLayoutParams(params);
        requestLayout();
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
