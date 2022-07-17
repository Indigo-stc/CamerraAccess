package com.ista.camera;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton iBPhoto;
    ImageView imgVieew;
    Button btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iBPhoto = findViewById(R.id.imageButton);
        imgVieew = findViewById(R.id.imageView);
        btnGuardar = findViewById(R.id.button);
        btnGuardar.setOnClickListener(this);
        iBPhoto.setOnClickListener(this);
    }

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Bundle info = result.getData().getExtras();
            Bitmap imagen = (Bitmap) info.get("data");
            imgVieew.setImageBitmap(imagen);
        }
    });

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageButton) {
            cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
        } else if (view.getId() == R.id.button) {
            if (imgVieew.getDrawable() != null) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgVieew.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                saveImageToGallery(bitmap);
            } else {
                Toast.makeText(this, "Tomar una foto", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveImageToGallery(Bitmap bitmap){
        OutputStream fos;
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver resolver = getContentResolver();
                ContentValues contentValues =  new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "TestFolder");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "Image not saved \n" + e, Toast.LENGTH_SHORT).show();
        }


    }

    /*private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }

    private void saveImage() {
        File dir = new File(Environment.getExternalStorageDirectory(),"SaveImage");

        if (!dir.exists()){
            dir.mkdir();
        }

        BitmapDrawable drawable = (BitmapDrawable) imgVieew.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File file = new File(dir,System.currentTimeMillis()+".jpg");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            Toast.makeText(MainActivity.this,"Successfuly Saved",Toast.LENGTH_SHORT).show();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
            }else {
                Toast.makeText(MainActivity.this,"Please provide the required permissions",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    public void takePhoto() {
        Intent cameraIntnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntnt.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntnt, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle info = data.getExtras();
            Bitmap imagen = (Bitmap)info.get("data");
            iVPhoto.setImageBitmap(imagen);
        }
    }*/
}