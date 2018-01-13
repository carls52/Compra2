package data.compra2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference dbuser;

    GridView gridOfertas;
    private List<Oferta> listOfertas ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbuser = FirebaseDatabase.getInstance().getReference("Anuncio");
        listOfertas = new ArrayList<Oferta>();



        //dbuser = FirebaseDatabase.getInstance().getReference("user");

        gridOfertas = (GridView) findViewById(R.id.gridOfertas);

        comprobarArticulos();


        gridOfertas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), OfertaUnica.class);
                Oferta obj = listOfertas.get(i);
                //intent.putExtra("Nombre",obj);
                startActivity(intent);
                Toast.makeText(MainActivity.this,listOfertas.get(i).getNombre(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //     return true;
        //    }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_offer) {
            Intent t = new Intent(this, AddAnuncio.class);
            startActivity(t);
            // Handle the camera action
        } else if (id == R.id.perfil) {
            Intent t = new Intent(this, Perfil.class);
            startActivity(t);

        } else if (id == R.id.mis_anuncios) {
            Intent t = new Intent(this, MisAnuncios.class);
            startActivity(t);

        } else if (id == R.id.nav_manage) {
            Intent t = new Intent(this, Inicio.class);
            startActivity(t);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void comprobarArticulos()
    {
        Oferta a = new Oferta ("COSAS","100€","pollas","pollas");
        Oferta b = new Oferta ("COSAS","100€","pollas","pollas");
        Oferta c = new Oferta ("COSAS","100€","pollas","pollas");
        Oferta d = new Oferta ("COSAS","100€","pollas","pollas");
        Oferta aa = new Oferta ("COSAS","100€","pollas","pollas");
        Oferta ba = new Oferta ("COSAS","100€","pollas","pollas");
        Oferta ca = new Oferta ("COSAS","100€","pollas","pollas");
        Oferta da = new Oferta ("COSAS","100€","pollas","pollas");
        listOfertas.add(a);
        listOfertas.add(b);
        listOfertas.add(c);
        listOfertas.add(d);
        listOfertas.add(aa);
        listOfertas.add(ba);
        listOfertas.add(ca);
        listOfertas.add(da);

        OfertList adapter = new OfertList(MainActivity.this,listOfertas);
        gridOfertas.setAdapter(adapter);
        /*dbuser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfertas.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Oferta o = snapshot.getValue(Oferta.class);
                    listOfertas.add(o);
                }

                OfertList adapter = new OfertList(MainActivity.this, listOfertas);
                gridOfertas.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
*/
    }
}

