package com.ista.camera;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton iBPhoto = findViewById(R.id.imageButton);
        iBPhoto.setOnClickListener(v -> {
            cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            //takePhoto();
        });
    }

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Bundle info = result.getData().getExtras();
            Bitmap imagen = (Bitmap) info.get("data");
            ImageView imgVieew = findViewById(R.id.imageView);
            imgVieew.setImageBitmap(imagen);
        }
    });

    /*public void takePhoto() {
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