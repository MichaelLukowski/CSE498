package com.vuforia.samples.Books.ui.ActivityList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.vuforia.samples.Books.R;

import java.util.ArrayList;

/**
 * Created by Michael on 3/21/2017.
 */

public class CompanyAdapter extends BaseAdapter implements Filterable {
    public Context context;
    public ArrayList<Company> companyArrayList;
    public ArrayList<Company> orig;

    public CompanyAdapter(Context context, ArrayList<Company> companyArrayList){
        super();
        this.context = context;
        this.companyArrayList = companyArrayList;
    }

    public class CompanyHolder
    {
        TextView name;
    }

    public Filter getFilter(){
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Company> results = new ArrayList<Company>();
                if (orig == null)
                    orig = companyArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Company g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                companyArrayList = (ArrayList<Company>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return companyArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return companyArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CompanyHolder holder;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.row, parent, false);
            holder=new CompanyHolder();
            holder.name=(TextView) convertView.findViewById(R.id.txtName);
            convertView.setTag(holder);
        }
        else
        {
            holder=(CompanyHolder) convertView.getTag();
        }

        holder.name.setText(companyArrayList.get(position).getName());

        return convertView;

    }
}
