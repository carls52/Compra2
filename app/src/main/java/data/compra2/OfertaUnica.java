package data.compra2;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;



public class OfertaUnica extends AppCompatActivity {
    Button atras;
    TextView nombreOU;
    TextView precioOU;
    TextView descripcionOU;
    TextView contactoOU;
    TextView mailOU;
    ImageView imagenOU;
    Oferta N;
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
        mailOU= findViewById(R.id.mailOU);
        mailOU.setText("");
        nombreOU = findViewById(R.id.nombreOU);
        precioOU = findViewById(R.id.precioOU);
        descripcionOU = findViewById(R.id.descripcionOU);
        contactoOU = findViewById(R.id.contactoOU);
        imagenOU = findViewById(R.id.imageViewOU);

        N = (Oferta) getIntent().getExtras().getSerializable("obj");
        nombreOU.setText(N.getNombre());
        precioOU.setText(N.getPrecio()+"€");
        descripcionOU.setText(N.getDescripcion());
        if((N.getPhone()!=null)) {
                contactoOU.setText(N.getPhone());
                mailOU.setText(N.getDueño());
        }
        else {
            if (N.getDueño() != null) {
                contactoOU.setText(N.getDueño());
                mailOU.setText("");
            }
            else
                contactoOU.setText("Error");
        }

        Glide.with(this)
                .load(N.getImagen().toString())
                .into(imagenOU);


        FloatingActionButton buttonCorreo;
        buttonCorreo = findViewById(R.id.MandarMail);
        buttonCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{N.getDueño()});
                i.putExtra(Intent.EXTRA_SUBJECT, N.getNombre());
                i.putExtra(Intent.EXTRA_TEXT   , "Hola, me interesa tu oferta de "+N.getNombre());
                try {
                    startActivity(Intent.createChooser(i, "Selecciona cliente de correo electrónico"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(OfertaUnica.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton llamar;
        llamar = findViewById(R.id.llamar);
        if(N.getPhone().length()>=9)
        {
            //String phone = "+34666777888";
            llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", N.getPhone(), null));
                    startActivity(intent);
                }
            });
        }
        else
        {
            llamar.setVisibility(View.GONE);
        }
    }

}
