package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.util.Pref;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class LanguageSettingAdapter extends RecyclerView.Adapter<LanguageSettingAdapter.ViewHolder> {
    private List<String> items;
    Activity activity;
    Fragment fragment;
    String pref_type = "";
    List<CustomCheckBox> customCheckBoxes = new ArrayList<>();

    public LanguageSettingAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    public void setPreferenceString(String pref_type) {
        this.pref_type = pref_type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_language_setting_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (!customCheckBoxes.contains(holder.check)) {
            customCheckBoxes.add(holder.check);
        }

        holder.tv_language.setText(items.get(position));

        holder.check.setTag(String.valueOf(items.get(position)));

        holder.check.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    for (CustomCheckBox customCheckBox : customCheckBoxes) {
                        if (!customCheckBox.equals(holder.check)) {
                            customCheckBox.setChecked(false);
                        }
                    }
                }
                Pref.SetStringPref(activity.getApplicationContext(), pref_type, "");
                if (isChecked) {
                    Pref.SetStringPref(activity.getApplicationContext(), pref_type, items.get(position));
                }
            }
        });


        holder.itemView.setTag(items.get(position));
    }

    public void setPreCheck(String checked) {
        if (checked.length() > 0) {
            for (CustomCheckBox customCheckBox : customCheckBoxes) {
                if (customCheckBox.getTag().toString().equals(checked)) {
                    customCheckBox.setChecked(true);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check)
        CustomCheckBox check;
        @BindView(R.id.tv_language)
        TextView tv_language;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
