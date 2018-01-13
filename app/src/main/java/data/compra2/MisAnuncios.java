package data.compra2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MisAnuncios extends AppCompatActivity {
Button atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_anuncios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMA);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        atras = findViewById(R.id.atrasMA);
        atras.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
