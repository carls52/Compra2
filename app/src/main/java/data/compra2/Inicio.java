package data.compra2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Inicio extends AppCompatActivity {

    Button Registrarse;
    Button Acceder;
    FirebaseAuth mAuth;
    EditText mail,pass;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mAuth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.iCorreo);
        pass = findViewById(R.id.iPass);
        progressBar = findViewById(R.id.iProgressBar);

        Registrarse = findViewById(R.id.Registrarse);
        Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(Inicio.this, Registrarse.class);
                startActivity(t);
            }

        });
        Acceder = findViewById(R.id.Acceder);
        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
                Boolean ok = Acceder();

            }
        });
    }

    private Boolean Acceder() {

        String m = mail.getText().toString().trim();
        String p = pass.getText().toString().trim();

        if (TextUtils.isEmpty(m) || TextUtils.isEmpty(p)) {
            Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_LONG).show();
            if(p.isEmpty())
                pass.requestFocus();
            if(m.isEmpty())
                mail.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(m).matches())
        {
            //Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
            mail.setError("Correo electrónico no válido");
            mail.requestFocus();
            return false;
        }
        if (p.length()<6)
        {
            pass.setError("La contraseña debe tener más de 6 caracteres");
            pass.requestFocus();
            // Toast.makeText(this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(m, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        finish();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(Inicio.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
            return false;
        }
    }
}


