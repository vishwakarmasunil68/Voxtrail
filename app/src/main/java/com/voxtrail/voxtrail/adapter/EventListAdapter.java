package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.fragment.SettingFragment;
import com.voxtrail.voxtrail.fragment.events.EventListFragment;
import com.voxtrail.voxtrail.listener.AdapterListener;
import com.voxtrail.voxtrail.pojo.event.LastEventPOJO;
import com.voxtrail.voxtrail.pojo.event.UserEventPOJO;
import com.voxtrail.voxtrail.util.Pref;
import com.voxtrail.voxtrail.util.StringUtils;
import com.voxtrail.voxtrail.util.ToastClass;
import com.voxtrail.voxtrail.util.UtilityFunction;
import com.voxtrail.voxtrail.webservice.WebServiceBase;
import com.voxtrail.voxtrail.webservice.WebServicesCallBack;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private List<UserEventPOJO> items;
    Activity activity;
    Fragment fragment;
    AdapterListener adapterListener;

    public void setAdapterItemAdapter(AdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public EventListAdapter(Activity activity, Fragment fragment, List<UserEventPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_user_events, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_event_name.setText(items.get(position).getName());
        holder.tv_type.setText(items.get(position).getType());

        holder.iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu menu = new PopupMenu(activity, v);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_edit:
                                if(fragment !=null &&fragment instanceof EventListFragment){
                                    EventListFragment eventListFragment= (EventListFragment) fragment;
                                    eventListFragment.eventDialog(items.get(position));
                                }
                                break;
                            case R.id.popup_delete:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
                                alertDialogBuilder.setTitle("Alert").setMessage("Do you want to delete this event?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                                        nameValuePairs.add(new BasicNameValuePair("", ""));

                                        new WebServiceBase(nameValuePairs, null, activity, new WebServicesCallBack() {
                                            @Override
                                            public void onGetMsg(String apicall, String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (jsonObject.optString("status").equals("1")) {
                                                        ToastClass.showShortToast(activity.getApplicationContext(), jsonObject.optString("message"));
                                                        items.remove(position);
                                                        notifyDataSetChanged();
                                                    } else {
                                                        ToastClass.showShortToast(activity.getApplicationContext(), jsonObject.optString("message"));
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, "DELET_EVENT", true).execute(WebServicesUrls.DELETE_USER_EVENTS);

                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_edit_delete);
                menu.show();
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_menu)
        ImageView iv_menu;
        @BindView(R.id.tv_event_name)
        TextView tv_event_name;
        @BindView(R.id.tv_type)
        TextView tv_type;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
