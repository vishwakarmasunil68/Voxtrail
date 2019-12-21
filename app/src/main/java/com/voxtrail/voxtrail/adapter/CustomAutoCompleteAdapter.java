package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.pojo.pojo.DevicePOJO;

import java.util.ArrayList;


public class CustomAutoCompleteAdapter extends ArrayAdapter<DevicePOJO> {
    ArrayList<DevicePOJO> customers, tempCustomer, suggestions;
    Activity activity;

    public CustomAutoCompleteAdapter(Activity activity, ArrayList<DevicePOJO> objects) {
        super(activity, android.R.layout.simple_list_item_1, objects);
        this.activity = activity;
        this.customers = objects;
        this.tempCustomer = new ArrayList<DevicePOJO>(objects);
        this.suggestions = new ArrayList<DevicePOJO>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DevicePOJO customer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inflate_custom_devices, parent, false);
        }
        LinearLayout ll_device = (LinearLayout) convertView.findViewById(R.id.ll_device);
        TextView tv_device_name = (TextView) convertView.findViewById(R.id.tv_device_name);
        tv_device_name.setText(customer.getDeviceDetailPOJO().getName());

        ll_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null && activity instanceof HomeActivity) {
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.getSelectAutoCompleteDevice(customer);
                }
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            DevicePOJO customer = (DevicePOJO) resultValue;
            return customer.getDeviceDetailPOJO().getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Object people : tempCustomer) {
                    DevicePOJO favoriteResultPOJO = (DevicePOJO) people;
                    if (favoriteResultPOJO.getDeviceDetailPOJO().getName().toLowerCase().startsWith(constraint.toString().toLowerCase())
                            || favoriteResultPOJO.getImei().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(favoriteResultPOJO);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<DevicePOJO> c = (ArrayList<DevicePOJO>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (DevicePOJO cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}