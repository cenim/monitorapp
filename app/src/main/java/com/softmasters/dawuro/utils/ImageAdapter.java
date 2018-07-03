package com.softmasters.dawuro.utils;

import android.content.Context;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.softmasters.dawuro.ImagePath;
import com.softmasters.dawuro.R;

import java.util.List;

/**
 * Created by Softmasters on 23-Jun-17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.CustomViewHolder> {

    List<String> picturePaths;
    List<ImagePath> imagePaths;
    Context context;

//    public ImageAdapter(Context context, List<String> picturePaths){
//        this.context = context;
//        this.picturePaths = picturePaths;
//    }

    public ImageAdapter(Context context, List<ImagePath> imagePaths){
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        System.out.println("Video: "+imagePaths.get(position).isVideo());
        if(imagePaths.get(position).isVideo()){
            System.out.println("Video");
            holder.imagePicture.setImageBitmap(ThumbnailUtils.createVideoThumbnail(imagePaths.get(position).getImagePath(), 0));
        }else{
            System.out.println("Not Video");
            holder.imagePicture.setImageURI(Uri.fromFile(MonitorUtils.compressImage(imagePaths.get(position).getImagePath())));
        }
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView imagePicture;

        public CustomViewHolder(View view){
            super(view);
            this.imagePicture = (ImageView) view.findViewById(R.id.imagePicture);
        }
    }
}
