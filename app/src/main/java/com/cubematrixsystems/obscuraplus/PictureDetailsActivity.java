package com.cubematrixsystems.obscuraplus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;


public class PictureDetailsActivity extends ActionBarActivity implements View.OnClickListener{

    Bundle extras;
    String imageWithPath, imageName;
    ImageView selectedImageView, popupImageView;
    ImageExif imageExif;
    LinkedHashMap exifMap;
    ListView list;
    Bitmap bitmap;
    LinearLayout mainLayout;
    View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_details);
        mainLayout = new LinearLayout(this);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);
        selectedImageView.setOnClickListener(this);

        extras = getIntent().getExtras();

        if(extras != null){
            imageWithPath = extras.getString("imageWithPath");
        }

        imageExif = new ImageExif(imageWithPath);
        exifMap = imageExif.getImageExif();
        bitmap = imageExif.getImageThumb();

        selectedImageView.setImageBitmap(bitmap);

        ExifAdapter exifAdapter = new ExifAdapter(exifMap);
        list = (ListView) findViewById(R.id.exifListView);
        list.setAdapter(exifAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picture_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_clear) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Clear Exif Data")
                    .setItems(R.array.clear_options_array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            copyImageFile(i);

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();
            return true;
        }
        if (id == R.id.action_map) {
            float[] latLong = new float[2];
            try {
                ExifInterface exif = new ExifInterface(imageWithPath);
                if(exif.getLatLong(latLong)) {
                    String uri = "geo:" + latLong[0] + "," + latLong[1];

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "No GPS Information", Toast.LENGTH_SHORT).show();

                }
            }
            catch (Exception e){

            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   @Override
    public void onClick(View view) {
         /*int id = view.getId();
        if(id == R.id.selectedImageView ){
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            popupView = layoutInflater.inflate(R.layout.image_popup, null);
            popupImageView = (ImageView) popupView.findViewById(R.id.popupImageView);
            final PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    900);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            bitmap = BitmapFactory.decodeFile(imageWithPath);
            try{
                popupImageView.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                Log.d("Error: ", e.toString());
            }
            popupWindow.showAtLocation(mainLayout, Gravity.LEFT| Gravity.TOP, 0, 0);
            popupWindow.update(0, 0, ViewGroup.LayoutParams.MATCH_PARENT, 900);
        }*/
    }

    public void copyImageFile(int type){
        String filePreface, newimageWithPath;
        if(type == 0) filePreface = "g";
        else filePreface = "a";

        File oldImage = new File(imageWithPath);
        imageName = oldImage.getName();

        File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ObscuraPlus");

        newimageWithPath = newFolder + "/" + filePreface + "_" + imageName;


        boolean success = true;
        if (!newFolder.exists()) {
            success = newFolder.mkdir();
        }
        if (success) {
            try{
                InputStream inputStream = new FileInputStream(imageWithPath);
                OutputStream outputStream = new FileOutputStream(newimageWithPath);

                //Transfer
                byte[] buffer = new byte[1024];
                int length;
                while((length = inputStream.read(buffer)) > 0){
                    outputStream.write(buffer, 0, length);
                }
                inputStream.close();
                outputStream.close();
                MediaScannerConnection.scanFile(this,
                        new String[]{newimageWithPath}, null,
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            @Override
                            public void onMediaScannerConnected() {

                            }

                            @Override
                            public void onScanCompleted(String s, Uri uri) {

                            }
                        });

            }
            catch (IOException io){
                //Todo
            }

            if(type == 0){
                ImageExif newImageExif = new ImageExif(newimageWithPath);
                newImageExif.ClearGPSData();
                Toast.makeText(getApplicationContext(), "Image saved to Pictures/ObscuraPlus/" + filePreface + "_" + imageName, Toast.LENGTH_LONG).show();
            }
            else if(type == 1){
                ImageExif newImageExif = new ImageExif(newimageWithPath);
                newImageExif.ClearAllData();
                Toast.makeText(getApplicationContext(), "Image saved to Pictures/ObscuraPlus/" + filePreface + "_" + imageName, Toast.LENGTH_LONG).show();
            }

        }
    }
}
