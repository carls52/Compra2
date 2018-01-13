package data.compra2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Inicio extends AppCompatActivity {

    Button Registrarse;
    Button Acceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
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
                Intent t = new Intent(Inicio.this, MainActivity.class);
                startActivity(t);
            }

        });
    }
}