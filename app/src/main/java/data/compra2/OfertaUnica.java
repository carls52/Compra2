package data.compra2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OfertaUnica extends AppCompatActivity {
    Button atras;
    TextView nombreOU;
    TextView precioOU;
    TextView descripcionOU;
    TextView contactoOU;
    ImageView imagenOU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta_unica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOU);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        atras = findViewById(R.id.atrasOU);
        atras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nombreOU = findViewById(R.id.nombreOU);
        precioOU = findViewById(R.id.precioOU);
        descripcionOU = findViewById(R.id.descripcionOU);
        contactoOU = findViewById(R.id.contactoOU);
        imagenOU = findViewById(R.id.imageViewOU);
        Intent i= getIntent();
        //Oferta N = (Oferta) i.getSerializableExtra("Nombre");
        //nombreOU.setText(N.getNombre());

        imagenOU.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent t = new Intent(OfertaUnica.this, Imagen.class);
                startActivity(t);
            }
        });


    }

}
