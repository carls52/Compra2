package data.compra2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAnuncio extends AppCompatActivity {
    EditText nombre;
    EditText precio;
    EditText descripcion;
    EditText phone;
    Button guardar;
    Button atras;
    DatabaseReference dbuser;

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
        dbuser = FirebaseDatabase.getInstance().getReference("Anuncio");

        nombre = findViewById(R.id.nombreOU);
        precio = findViewById(R.id.precio);
        descripcion = findViewById(R.id.descript);
        phone = findViewById(R.id.phone);

        guardar = findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
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


    private boolean addOferta() {
        String n = nombre.getText().toString().trim();
        String p = precio.getText().toString().trim();
        String d = descripcion.getText().toString().trim();
        String ph = phone.getText().toString().trim();

        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(p) || TextUtils.isEmpty(d) || TextUtils.isEmpty(ph)) {
            Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String id = dbuser.push().getKey();
            Oferta Offer = new Oferta(n, p, d, ph);

            dbuser.child(id).setValue(Offer);

            Toast.makeText(this, "Anuncio subido con éxito", Toast.LENGTH_SHORT).show();
            return true;

        }

    }

}