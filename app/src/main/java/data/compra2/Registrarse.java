package data.compra2;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registrarse extends AppCompatActivity {
    ImageButton atras;
    EditText Rmail;
    EditText Rpass;
    EditText Rpass2;
    Button Rguardar;
    DatabaseReference dbuser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        atras = findViewById(R.id.atrasR);
        atras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dbuser = FirebaseDatabase.getInstance().getReference("user");

        Rmail = findViewById(R.id.Rmail);
        Rpass = findViewById(R.id.Rpass);
        Rpass2 = findViewById(R.id.Rpass2);


        Rguardar = findViewById(R.id.Rguardar);
        Rguardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean ok = addUser();
                if (ok == true) {
                    addUser();
                    Rmail.setText("");
                    Rpass.setText("");
                    Rpass2.setText("");
                    finish();
                }
            }
        });

    }


    private boolean addUser() {
        String m = Rmail.getText().toString().trim();
        String p = Rpass.getText().toString().trim();
        String p2 = Rpass2.getText().toString().trim();


        if (TextUtils.isEmpty(m) || TextUtils.isEmpty(p) || TextUtils.isEmpty(p2)) {
            Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_LONG).show();
            return false;
        }
        else
        {
            if (!(p2.equals(p)))
            {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                return false;
            }
            else
            {

                String id = dbuser.push().getKey();
                Usuario user = new Usuario(m, p);

                dbuser.child(id).setValue(user);

                Toast.makeText(this, "Registrado con éxito", Toast.LENGTH_LONG).show();
                return true;
            }

        }
    }

}



