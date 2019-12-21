package com.voxtrail.voxtrail.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.voxtrail.voxtrail.R;
import com.voxtrail.voxtrail.activity.HomeActivity;
import com.voxtrail.voxtrail.fragment.report.ImageViewFragment;
import com.voxtrail.voxtrail.pojo.ImagesPOJO;
import com.voxtrail.voxtrail.util.TagUtils;
import com.voxtrail.voxtrail.webservice.WebServicesUrls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private List<ImagesPOJO> items;
    Activity activity;
    Fragment fragment;

    public ImagesAdapter(Activity activity, Fragment fragment, List<ImagesPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_images_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TagUtils.getTag(), "images url:-" + WebServicesUrls.DEVICES_IMAGE_BASE_URL + items.get(position).getImgFile());
        Picasso.get()
                .load(WebServicesUrls.DEVICES_IMAGE_BASE_URL + items.get(position).getImgFile())
                .placeholder(R.drawable.ic_save_image)
                .error(R.drawable.ic_save_image)
                .into(holder.iv_img);

        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof HomeActivity){
                    HomeActivity homeActivity= (HomeActivity) activity;
                    homeActivity.startFragment(R.id.frame_home,new ImageViewFragment("Image",WebServicesUrls.DEVICES_IMAGE_BASE_URL + items.get(position).getImgFile()));
                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_img)
        ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
