package data.compra2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddAnuncio extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    EditText nombre;
    EditText precio;
    EditText descripcion;
    EditText phone;
    Button guardar;
    Button atras;
    ImageButton camara;
    DatabaseReference dbuser;
    private StorageReference mStorageRef;
    ProgressBar barra;
    String ImagenUrl = null;
    ImageView imagen;
    FirebaseUser yo;

    Uri uriProfileImage;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anuncio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarA);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        atras = findViewById(R.id.atrasA);
        atras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        yo = mAuth.getCurrentUser();
        dbuser = FirebaseDatabase.getInstance().getReference("Anuncios");


        imagen = findViewById(R.id.imagen);
        barra = findViewById(R.id.progressBarA);
        nombre = findViewById(R.id.nombre);
        precio = findViewById(R.id.precio);
        descripcion = findViewById(R.id.descript);
        phone = findViewById(R.id.phone);

        camara = findViewById(R.id.camara);
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Imagenes();

            }
        });

        guardar = findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //uploadImageToFirebaseStorage();
                boolean ok = addOferta();
                if (ok == true) {
                    nombre.setText("");
                    precio.setText("");
                    descripcion.setText("");
                    phone.setText("");
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfileImage=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imagen.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        mStorageRef = FirebaseStorage.getInstance().getReference(
                "Anuncios/"+System.currentTimeMillis()+".jpg");
        if(uriProfileImage != null){
            barra.setVisibility(View.VISIBLE);
            mStorageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            barra.setVisibility(View.GONE);
                            ImagenUrl = taskSnapshot.getDownloadUrl().toString().trim();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            barra.setVisibility(View.GONE);
                            Toast.makeText(AddAnuncio.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void Imagenes()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Seleccione una imagen"),CHOOSE_IMAGE);
    }

    private boolean addOferta() {
        String n = nombre.getText().toString().trim();
        String p = precio.getText().toString().trim();
        String d = descripcion.getText().toString().trim();
        String ph = phone.getText().toString().trim();
        String dueño = yo.getEmail().toString().trim();

        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(p) || TextUtils.isEmpty(d) || TextUtils.isEmpty(dueño)) {
            Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ImagenUrl == null) {
            Toast.makeText(this, "Debe añadir una imagen", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!(ph.isEmpty())&&(ph.length()<9)){
            phone.setError("Introduzca un teléfono válido");
            phone.requestFocus();
            return false;
        }

        else {
            String id = dbuser.push().getKey();
            Oferta Offer = new Oferta(n, p, d, ph,dueño,ImagenUrl);

            dbuser.child(id).setValue(Offer);

            Toast.makeText(this, "Anuncio subido con éxito", Toast.LENGTH_SHORT).show();
            return true;

        }

    }

}