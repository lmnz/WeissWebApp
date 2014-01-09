package com.weissdb;

import java.util.ArrayList;

import com.weissdb.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AutoCompleteAdapter extends ArrayAdapter<String>
{
	private ArrayList<String> items;
	private ArrayList<String> itemsAll;
    private String limit;
    private Context context;

    @SuppressWarnings("unchecked")
	public AutoCompleteAdapter(Context context, int viewResourceId, ArrayList<String> items, String limit)
    {
    	super(context, viewResourceId, items);
    	this.context = context;
        this.items = items;
        this.itemsAll = (ArrayList<String>) items.clone();
        this.limit = limit;
    }
    
    public static class ViewHolder
    {
        public TextView item1;
    }
    
    public View getView(int position, View convertView, ViewGroup parent)
	{
    	View v = convertView;
        ViewHolder holder;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.auto_complete_item, null);
            holder = new ViewHolder();
            holder.item1 = (TextView) v.findViewById(R.id.suggestion);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)v.getTag();
        }
 
        final String item = items.get(position);
        if (item != null)
        {
            holder.item1.setText(item.trim());
        }
        return v;
    }

    @Override
    public android.widget.Filter getFilter()
    {
        return nameFilter;
    }

    @SuppressLint("DefaultLocale")
	Filter nameFilter = new Filter()
    {
        public String convertResultToString(Object resultValue)
        {
            String str = (String) (resultValue);
            return str;
        }

        @SuppressLint("DefaultLocale")
		@Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            if (constraint != null)
            {
            	ArrayList<String> suggestions = new ArrayList<String>();
            	if (limit.equals("No limit"))
            	{
	                for (String card : itemsAll)
	                {
	                    if (card.toString().toLowerCase().contains(constraint.toString().toLowerCase()))
	                    {
	                        suggestions.add(card);
	                    }
	                }
            	}
            	else
            	{
            		for (int i = 0, itemsFound = 0; i < itemsAll.size() && itemsFound < Integer.valueOf(limit); i++)
            		{
            			if (itemsAll.get(i).toString().toLowerCase().contains(constraint.toString().toLowerCase()))
	                    {
	                        itemsFound++;
            				suggestions.add(itemsAll.get(i));
	                    }
            		}
            	}
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }
            else
            {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            @SuppressWarnings("unchecked")
            ArrayList<String> filteredList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0)
            {
                clear();
                for (String c : filteredList)
                {
                	add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
