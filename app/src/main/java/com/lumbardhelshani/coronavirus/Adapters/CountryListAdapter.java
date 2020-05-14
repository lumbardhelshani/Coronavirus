package com.lumbardhelshani.coronavirus.Adapters;
/* user: lumba
   date: 5/8/2020
   time: 21:51
*/
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.lumbardhelshani.coronavirus.Activities.CountriesActivity;
import com.lumbardhelshani.coronavirus.Models.Country;
import com.lumbardhelshani.coronavirus.R;
import java.util.ArrayList;
import java.util.List;

public class CountryListAdapter extends ArrayAdapter<Country> {
    private Context context;
    private List<Country> countryModelsList;
    private List<Country> countryModelsListSearched;
    public CountryListAdapter( Context context, List<Country> countryModelsList) {
        super(context, R.layout.list_country_item, countryModelsList);
        this.context = context;
        this.countryModelsList = countryModelsList;
        this.countryModelsListSearched = countryModelsList;
    }


    //This method is used to return a populated view with data from list country item layout
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_country_item,null,true);
        TextView countryNameTxt = view.findViewById(R.id.countryNameTxt);
        ImageView flagImg = view.findViewById(R.id.flagImg);
        TextView todayCasesTxt = view.findViewById(R.id.todayCasesTxt);
        int length = (countryModelsListSearched.get(position).getCountryName().length());
        String countryName = countryModelsListSearched.get(position).getCountryName().substring(0, Math.min(length ,16));
        countryNameTxt.setText(countryName);
        todayCasesTxt.setText(countryModelsListSearched.get(position).getTodayCases());
        Glide.with(context).load(countryModelsListSearched.get(position).getFlag()).into(flagImg);
        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListSearched.size();
    }

    @Nullable
    @Override
    public Country getItem(int position) {
        return countryModelsListSearched.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    //This method is used to get the filtered results when we search on the edit text
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModelsList.size();
                    filterResults.values = countryModelsList;
                }else{
                    List<Country> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Country itemsModel:countryModelsList){
                        if(itemsModel.getCountryName().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryModelsListSearched = (List<Country>) results.values;
                CountriesActivity.countryModelsList = (List<Country>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
