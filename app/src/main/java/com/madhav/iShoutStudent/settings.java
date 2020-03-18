package com.madhav.iShoutStudent;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by madhav on 09-02-2016.
 */
public class settings extends Fragment {

    @Nullable
    EditText name,work,rollno;
    String str;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.settings , container , false);

        name = (EditText)rootView.findViewById(R.id.nametag);

        work = (EditText)rootView.findViewById(R.id.workat);
        rollno = (EditText)rootView.findViewById(R.id.rollno);
        final SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String disname , diswork , disroll;
        disname = spf.getString("strname","");
        diswork = spf.getString("strwork","");
        disroll = spf.getString("roll","");

        if(!disname.isEmpty())
        {
            name.setText(disname);
        }
        if(!diswork.isEmpty())
        {
            work.setText(diswork);
        }
        if(!disroll.isEmpty())
        {
            rollno.setText(disroll);
        }


        Button update = (Button)rootView.findViewById(R.id.update);
        Button rolbut = (Button)rootView.findViewById(R.id.rolbut);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strname = name.getText().toString();
                String strwork = work.getText().toString();



                SharedPreferences.Editor edit = spf.edit();
                edit.putString("strname",strname);
                edit.putString("strwork",strwork);

                edit.commit();

                Toast.makeText(getActivity(), "Profile Updated!", Toast.LENGTH_SHORT).show();
            }
        });

        rolbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String strrol = rollno.getText().toString();

                SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor edit = spf.edit();

                edit.putString("roll", strrol);
                edit.commit();

                Toast.makeText(getActivity(), "RollNo/ID Updated!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }


}
