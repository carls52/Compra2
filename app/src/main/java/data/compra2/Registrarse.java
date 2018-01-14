package data.compra2;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registrarse extends AppCompatActivity {
    ImageButton atras;
    EditText Rmail;
    EditText Rpass;
    EditText Rpass2;
    Button Rguardar;
    DatabaseReference dbuser;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        atras = findViewById(R.id.atrasR);
        atras.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dbuser = FirebaseDatabase.getInstance().getReference("user");

        Rmail = findViewById(R.id.Rmail);
        Rpass = findViewById(R.id.Rpass);
        Rpass2 = findViewById(R.id.Rpass2);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
        Rguardar = findViewById(R.id.Rguardar);
        Rguardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean ok = addUser();
                if (ok == true) {
                    //addUser();
                    Rmail.setText("");
                    Rpass.setText("");
                    Rpass2.setText("");
                    finish();
                }
            }
        });

    }


    private boolean addUser() {
        int good;
        String m = Rmail.getText().toString().trim();
        String p = Rpass.getText().toString().trim();
        String p2 = Rpass2.getText().toString().trim();


        if (TextUtils.isEmpty(m) || TextUtils.isEmpty(p) || TextUtils.isEmpty(p2)) {
            Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_LONG).show();
            if(p2.isEmpty())
                Rpass2.requestFocus();
            if(p.isEmpty())
                Rpass.requestFocus();
            if(m.isEmpty())
                Rmail.requestFocus();


            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(m).matches())
        {
            //Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
            Rmail.setError("Correo electrónico no válido");
            Rmail.requestFocus();
            return false;
        }
        if (p.length()<6)
        {
            Rpass.setError("La contraseña debe tener más de 6 caracteres");
            Rpass.requestFocus();
           // Toast.makeText(this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            if (!(p2.equals(p)))
            {
                //Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                Rpass.setError("Las contraseñas no coinciden");
                Rpass2.setError("Las contraseñas no coinciden");
                Rpass.requestFocus();
                Rpass2.requestFocus();
                return false;
            }
            else
            {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(m,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Registrarse.this, "Registrado con éxito", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                //Toast.makeText(Registrarse.this, "El correo utilizado ya esta registrado", Toast.LENGTH_SHORT).show();
                                Rmail.setError("El correo utilizado ya esta registrado");
                                Rmail.requestFocus();
                            }
                            else {
                                //Toast.makeText(Registrarse.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Registrarse.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
                    return false;
            }

        }
    }



}



