package com.madhav.iShoutStudent;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.net.URI;

/**
 * Created by madhav on 09-02-2016.
 */
public class timetable extends Fragment {


    Button button;
    ImageButton but;
    ImageView imageView;
    private static int RESULT_LOAD_IMAGE = 1;
    String str;
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.timetable,
                container, false);


        button = (Button) rootView.findViewById(R.id.button1);
        imageView = (ImageView) rootView.findViewById(R.id.imageView1);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String picturePath = prefs.getString("profilePic", "");

        if (!picturePath.equals("")) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView1);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            });



        return rootView;

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) getView().findViewById(R.id.imageView1);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);


            SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor edit=shre.edit();
            edit.putString("profilePic", picturePath);
            edit.commit();

        }
    }



}
