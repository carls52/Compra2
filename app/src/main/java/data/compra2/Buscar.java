package data.compra2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Buscar extends AppCompatActivity {

    Button atras;
    DatabaseReference dbuser;
    FirebaseAuth auth;
    FirebaseUser yo;
    String MiMail;
    GridView grid;
    List<Oferta> list ;

    TextView NoResults;
    EditText busqueda;
    ImageView lupa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarB);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        auth = FirebaseAuth.getInstance();
        yo = auth.getCurrentUser();
        MiMail = yo.getEmail();

        NoResults = findViewById(R.id.NoResults);
        lupa = findViewById(R.id.lupa);
        busqueda = findViewById(R.id.busqueda);
        dbuser = FirebaseDatabase.getInstance().getReference("Anuncios");
        grid = findViewById(R.id.MiGridB);
        list = new ArrayList<>();

        atras = findViewById(R.id.atrasB);
        atras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbuser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        list.clear();
                        for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                            Oferta us = userSnapshot.getValue(Oferta.class);
                                if(us.getNombre().contains(busqueda.getText().toString().trim())) {
                                    NoResults.setVisibility(View.GONE);
                                    list.add(us);
                                }
                        }
                        if(list.isEmpty())
                        {
                            NoResults.setText("NO HAY RESULTADOS");
                            NoResults.setVisibility(View.VISIBLE);
                        }
                        OfertList adapter = new OfertList(Buscar.this, list);
                        grid.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), OfertaUnica.class);
                Oferta obj = list.get(i);
                intent.putExtra("obj",obj);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        dbuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    Oferta us = userSnapshot.getValue(Oferta.class);
                    if(us.getDueño()!= null)
                    {
                        if(us.getNombre().equals(" ")) {
                            list.add(us);
                        }
                    }

                }
                OfertList adapter = new OfertList(Buscar.this, list);
                grid.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}