package com.softmasters.dawuro.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softmasters.dawuro.R;
import com.softmasters.dawuro.umid.MessageDetails;

import java.util.List;

/**
 * Created by Softmasters on 16-May-17.
 */

public class SavedItemsAdapter extends BaseAdapter {

    List<MessageDetails> messageDetails;
    Context context;
    LayoutInflater inflater;

    public SavedItemsAdapter(Context context, List<MessageDetails> messageDetails){
        this.context = context;
        this.messageDetails = messageDetails;
        inflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        public ImageView image;
        public TextView textLocation;
        public TextView textDate;
        public TextView textAddress;
        public TextView textComments;
        public ImageView imageStatus;

    }

    @Override
    public int getCount() {
        return messageDetails.size();
    }

    @Override
    public MessageDetails getItem(int position) {
        return messageDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("Position : "+ position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.saved_item_row, null);

            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.textAddress = (TextView) convertView.findViewById(R.id.textLocation);
            holder.textComments = (TextView) convertView.findViewById(R.id.textComments);
            holder.textDate = (TextView) convertView.findViewById(R.id.textDate);
            holder.textLocation = (TextView) convertView.findViewById(R.id.textCoordinates);
            holder.imageStatus = (ImageView) convertView.findViewById(R.id.imgDelivered);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            byte[] bytes = messageDetails.get(position).getGallery().get(0).getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
            holder.image.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Comments : "+ messageDetails.get(position).getComments().getComment());
        holder.textComments.setText(messageDetails.get(position).getComments().getComment());
//        if(messageDetails.get(position).getComments().getApplieddate()) == ){
//
//        }
        holder.textDate.setText(MonitorUtils.convertDatetoString(messageDetails.get(position).getComments().getApplieddate()));
        holder.textAddress.setText(
                messageDetails.get(position).getLocation().get(0).getCity()+
                ", "+messageDetails.get(position).getLocation().get(0).getRegion()+
                ", "+ messageDetails.get(position).getLocation().get(0).getCountry());
        holder.textLocation.setText(
                messageDetails.get(position).getLocation().get(0).getLatitude()+ ", "+
                                    messageDetails.get(position).getLocation().get(0).getLongitude());
        if(messageDetails.get(position).getComments().getStatus().equals(Config.SEND_STATUS)){
            holder.imageStatus.setImageResource(R.drawable.check);
        }else if(messageDetails.get(position).getComments().getStatus().equals(Config.UPDATE_STATUS)){
            holder.imageStatus.setImageResource(R.drawable.double_checking);
        }

        return convertView;
    }
}
