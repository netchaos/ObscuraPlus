package com.cubematrixsystems.obscuraplus;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private ImageView openButton;
    private ImageView demoImageView;
    private static final int SELECT_PICTURE = 1;
    private String selectedImageWithPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openButton = (ImageView) findViewById(R.id.openButton);

        openButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Open gallery to select an image
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, SELECT_PICTURE);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == SELECT_PICTURE){
            Uri imageUri = data.getData();
            //selectedImageWithPath = imageUri.getPath();

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            selectedImageWithPath = cursor.getString(columnIndex);
            cursor.close();

            Intent pictureDetails = new Intent(this, PictureDetailsActivity.class);
            pictureDetails.putExtra("imageWithPath", selectedImageWithPath);
            startActivity(pictureDetails);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
