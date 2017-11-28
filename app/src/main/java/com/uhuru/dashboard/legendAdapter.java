package com.uhuru.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Thibaut on 15/01/16.
 */
public class legendAdapter extends ArrayAdapter<legend_item> {

    //tweets est la liste des models à afficher
    public legendAdapter(Context context, List<legend_item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_legend,parent, false);
        }

        legendViewHolder viewHolder = (legendViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new legendViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.legend_title);
            viewHolder.nb = (TextView) convertView.findViewById(R.id.legend_nb);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<cellule> cell
        legend_item cell = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(cell.getTitle());
        viewHolder.nb.setText("" + cell.getNb());
        viewHolder.nb.setTextColor(cell.getColor());

        return convertView;
    }

    private class legendViewHolder{
        public TextView title;
        public TextView nb;
    }
}