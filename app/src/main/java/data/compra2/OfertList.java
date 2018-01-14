package data.compra2;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by Carlos Gil Sabrido on 12/01/2018.
 */

public class OfertList extends ArrayAdapter<Oferta>{
    private Activity context;
    private List<Oferta>ofList;

    ImageView foto;

    public OfertList(Activity context, List<Oferta> ofeList) {
        super(context, R.layout.list_layout, ofeList);
        this.context = context;
        this.ofList = ofeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View gridVIewItem = inflater.inflate(R.layout.list_layout, null , true);

        TextView tt1 = gridVIewItem.findViewById(R.id.name);
        TextView tt2 = gridVIewItem.findViewById(R.id.name2);
        foto = gridVIewItem.findViewById(R.id.imageView3);

        Oferta u = ofList.get(position);
        Glide.with(this.getContext())
                .load(u.getImagen().toString())
                .into(foto);

        tt1.setText(u.getNombre());
        tt2.setText(u.getPrecio()+"€");

        return gridVIewItem;
    }

}

