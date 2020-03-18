package com.madhav.iShoutStudent;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.skyfishjy.library.RippleBackground;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by madhav on 09-02-2016.
 */
public class home extends Fragment {

    @Nullable
    BroadcastReceiver reciver;
    String str;
    String wifiname;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.home , container , false);


        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());



        final RippleBackground rippleBackground=(RippleBackground)getView().findViewById(R.id.content);

         final String[] array = new String[]{"image0" , "image1" ,"image2" ,"image3" ,"image4" ,"image5" ,"image6" , "image7"};
         final EditText text = (EditText)getView().findViewById(R.id.editText1);
         final TextView textname = (TextView)getView().findViewById(R.id.textView4);
         ToggleButton but = (ToggleButton)getView().findViewById(R.id.toggleButton1);
        final WifiManager wifi = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);

        String rollnow = spf.getString("roll", "");
        if(rollnow != "") {
            text.setText(rollnow);
        }

        if(wifi.isWifiEnabled() == true)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Wifi Is Enabled , Let me Turn it Off First", Toast.LENGTH_SHORT).show();
            wifi.setWifiEnabled(false);
        }


        if(ApManager.isApOn(getActivity()) == true)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Hotspot is Already Enabled, Let me Turn it Off First", Toast.LENGTH_SHORT).show();
            str = text.getText().toString();
            ApManager.configApState(getActivity() , str);

        }




        final WifiConfiguration wificonfiguration = new WifiConfiguration();
        final ImageView img = (ImageView)getView().findViewById(R.id.imageView2);
        img.setVisibility(View.GONE);


        but.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if (isChecked) {

                    Toast.makeText(getActivity().getApplicationContext(), "Enabling AP , Please Wait...", Toast.LENGTH_LONG).show();
                    str = text.getText().toString();
                    ApManager.configApState(getActivity(), str);

                    IntentFilter mIntentFilter = new IntentFilter("android.net.wifi.WIFI_AP_STATE_CHANGED");
                    getActivity().registerReceiver(reciver, mIntentFilter);

                    //Image Changing Algo
                    Random range = new Random();
                    int imagerandomno = range.nextInt(7);
                    int resID = getResources().getIdentifier(array[imagerandomno], "drawable", getActivity().getPackageName());
                    img.setImageResource(resID);


                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Turning Off the AP..", Toast.LENGTH_SHORT).show();
                    str = text.getText().toString();
                    ApManager.configApState(getActivity(), str);
                    getActivity().unregisterReceiver(reciver);
                    textname.setText("");
                    img.setVisibility(View.GONE);
                    rippleBackground.stopRippleAnimation();

                }


            }
        });






         reciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {



                WifiManager wifimanager = (WifiManager) getActivity().getSystemService(context.WIFI_SERVICE);
                Method[] methods = wifimanager.getClass().getDeclaredMethods();
                for (Method m: methods) {
                    if (m.getName().equals("getWifiApConfiguration")) {
                        try {
                            WifiConfiguration config = (WifiConfiguration)m.invoke(wifimanager);
                            String name = config.SSID;
                            textname.setText("Shouting with Rollno/ID : " + name);


                            img.setVisibility(View.VISIBLE);
                            rippleBackground.startRippleAnimation();

                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }



            }
        };



    }
}

