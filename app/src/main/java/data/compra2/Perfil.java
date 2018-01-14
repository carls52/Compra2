package data.compra2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Perfil extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    FirebaseUser yo;
    FirebaseAuth auth;
    ImageView FotoPerfil;
    TextView mail;
    ImageButton camaraP,lapiz;
    Button atras,guardarP;
    StorageReference mStorageRef;
    Uri uriProfileImage;
    ProgressBar barraP;
    String ImagenUrl;
    Uri UriOnIce;
    Boolean good,good2;
    EditText NewPass,NewMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        NewPass = findViewById(R.id.NewPass);
        NewMail = findViewById(R.id.NewMail);
        guardarP = findViewById(R.id.GuardarP);
        lapiz = findViewById(R.id.lapiz);

        mail = findViewById(R.id.ENOM);
       // contraseña = findViewById(R.id.ENOM);
        FotoPerfil = findViewById(R.id.imageView2);
        atras = findViewById(R.id.atrasP);
        barraP = findViewById(R.id.barraP);
        auth = FirebaseAuth.getInstance();
        yo = auth.getCurrentUser();
        good = true;
        good2 = true;


        lapiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewMail.setVisibility(View.VISIBLE);
                NewPass.setVisibility(View.VISIBLE);
                guardarP.setVisibility(View.VISIBLE);
            }
        });
        guardarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!NewMail.getText().toString().isEmpty())
                {
                    if(!Patterns.EMAIL_ADDRESS.matcher(NewMail.getText().toString().trim()).matches())
                    {
                        Toast.makeText(Perfil.this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        yo.updateEmail(NewMail.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            good = false;
                                        }
                                    }
                                });
                    }
                }
                if(!NewPass.getText().toString().trim().isEmpty())
                {
                    if(NewPass.getText().toString().trim().length()<6)
                    {
                        Toast.makeText(Perfil.this, "La contraseña debe ser mayor de 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        yo.updatePassword(NewPass.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            good2 = false;
                                        }
                                    }
                                });
                    }
                }
                if(good==false || good2==false)
                {
                    Toast.makeText(Perfil.this, "Cambios realizados", Toast.LENGTH_SHORT).show();
                    good = true;
                    good2 = true;
                    NewMail.setVisibility(View.GONE);
                    NewPass.setVisibility(View.GONE);
                    guardarP.setVisibility(View.GONE);
                }
                else
                {
                    NewMail.setVisibility(View.GONE);
                    NewPass.setVisibility(View.GONE);
                    guardarP.setVisibility(View.GONE);
                }
            }
        });

        mail.setText(yo.getEmail().toString());
       // contraseña.setText(yo.get)
        UriOnIce = yo.getPhotoUrl();
        if(UriOnIce!=null) {
            String a = UriOnIce.toString();
            if (!(a.isEmpty())) {
                barraP.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(yo.getPhotoUrl())
                        .into(FotoPerfil);
            }
            barraP.setVisibility(View.GONE);
        }
        atras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        camaraP = findViewById(R.id.camaraP);
        camaraP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Imagenes();

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
                FotoPerfil.setImageBitmap(bitmap);

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
            barraP.setVisibility(View.VISIBLE);
            mStorageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            barraP.setVisibility(View.GONE);
                            ImagenUrl = taskSnapshot.getDownloadUrl().toString().trim();
                            UserProfileChangeRequest update = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(taskSnapshot.getDownloadUrl())
                                    .build();
                            yo.updateProfile(update)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Perfil.this, "Foto cambiada", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            barraP.setVisibility(View.GONE);
                            Toast.makeText(Perfil.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
}
