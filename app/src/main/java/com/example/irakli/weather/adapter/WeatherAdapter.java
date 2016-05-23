package com.example.irakli.weather.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irakli.weather.DataModel;
import com.example.irakli.weather.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

/**
 * Created by Irakli on 22.05.2016.
 */
public class WeatherAdapter extends BaseAdapter{

    private ArrayList<DataModel> list;
    private Context context;

    public WeatherAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView;
        DataModel data = (DataModel) getItem(position);
        ViewHolder holder;

        if (convertView == null){
            listItemView = View.inflate(context, R.layout.list_view_item, null);
            TextView scrollDescription = (TextView) listItemView.findViewById(R.id.scrollViewDescription);
            TextView scrollDayNight = (TextView) listItemView.findViewById(R.id.dayNightWeather);
            ImageView scrollIcon = (ImageView) listItemView.findViewById(R.id.scrollViewIcon);
            TextView scrollViewDt = (TextView) listItemView.findViewById(R.id.scrollViewDt);

            holder = new ViewHolder();

            holder.description = scrollDescription;
            holder.dayNight = scrollDayNight;
            holder.icon = scrollIcon;
            holder.dt = scrollViewDt;

            listItemView.setTag(holder);
        }else {
            listItemView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        holder.description.setText(data.getWeatherDescription());
        holder.dayNight.setText(data.getDayTemperature() + "°" + "/" + data.getNightTemperature() + "°" );
        Picasso.with(context).load("http://openweathermap.org/img/w/" + data.getIcon() + ".png").into(holder.icon);
        holder.dt.setText("" + data.getDt());

        return listItemView;
    }

    class ViewHolder {
        TextView description;
        TextView dayNight;
        ImageView icon;
        TextView dt;
    }
}
