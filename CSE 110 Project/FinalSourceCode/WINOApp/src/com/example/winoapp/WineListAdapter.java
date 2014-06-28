package com.example.winoapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class WineListAdapter extends BaseAdapter
								implements Filterable{
    
    private LayoutInflater inflater;
    private List<Wine> wineList;
    private List<Wine> oriWineList;
    
    private ViewHolder holder;
    private Filter filter;
    
    public WineListAdapter(Activity act, List<Wine> wineList) {
    	// the adapter uses this to display row
    	// changes dynamically in response to search query
        this.wineList = wineList;
        // this saves the original wine list and should not be changed
        // used to restore wine list
        this.oriWineList = wineList;
        
        inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return wineList.get(position).getID();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
         
        View view = convertView;
        if(convertView == null){
         
            view = inflater.inflate(R.layout.winelist_row, null);
            holder = new ViewHolder();

            holder.wine  	  = (TextView)view.findViewById(R.id.wineName);
            holder.note 	  = (TextView)view.findViewById(R.id.wineDesc);
            holder.rate		  = (TextView)view.findViewById(R.id.wineRating);
            holder.year       = (TextView)view.findViewById(R.id.wineYear);
            holder.wineImage  = (ImageView)view.findViewById(R.id.list_image); // thumbnail
     
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)view.getTag();
        }

        Wine wine = wineList.get(position);
        // set image
        if (!wine.getImagePath().equals("")) {
        	Bitmap b = ImageHandler.loadImage(wine.getImagePath(), 1.0, 4);
            holder.wineImage.setImageBitmap(b);
        }
        else {
	        holder.wineImage.setImageResource(R.drawable.inv_sample_holder);
        }
        // Setting all values in listview
        holder.wine.setText(wine.getName());
        holder.note.setText(wine.getNotes());
        holder.year.setText(wine.getVintage());
        holder.rate.setText(""+wine.getRating());
        return view;
    }
    
    /**
     * Holder used to decide what should be displayed on a row item 
     */
    static class ViewHolder{
        
        TextView wine;
        TextView year;
        TextView rate;
        TextView note;
        ImageView wineImage;
    }

    // these are not needed so far
	@Override //unimplemented
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
    
	@Override
    public int getCount() {
        return wineList.size();
    }

	@Override
	public Filter getFilter() {
		if (filter == null)
			filter = new WineFilter();
		
		return filter;
	}
	
	public void resetAdapter() {
		wineList = oriWineList;
	}

	public List<Wine> getWineList() {
		return this.wineList;
	}
	
	// custom filter class used to filter wine list in response to search query
	private class WineFilter extends Filter {
		
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = oriWineList;
				results.count = oriWineList.size();
			}
			else {
				// We perform filtering operation
				List<Wine> newWineList = new ArrayList<Wine>();
				
				for (Wine w : wineList) {
					// here we just filter wine name, we can do more if you want
					if (w.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
						newWineList.add(w);
				}
				results.values = newWineList;
				results.count = newWineList.size();
			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			
			// Now we have to inform the adapter about the new list filtered
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				wineList = (List<Wine>) results.values;
				notifyDataSetChanged();
			}
		}
	} // END of filter class
}