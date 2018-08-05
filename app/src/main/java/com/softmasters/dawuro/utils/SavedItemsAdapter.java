package com.softmasters.dawuro.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softmasters.dawuro.R;
import com.softmasters.dawuro.activities.IncidentActivity;
import com.softmasters.dawuro.umid.MessageDetails;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Softmasters on 16-May-17.
 */

public class SavedItemsAdapter extends BaseAdapter {

    List<MessageDetails> messageDetails;
    Context context;
    LayoutInflater inflater;
    private String latitude_TextLocation;
    private String longitude_TextLocation;
    private Geocoder geo;
    List<Address> locationDetails;
    String city, region, country, latitude, longitude;
    private String comments;
    byte[] bytes;

    public SavedItemsAdapter(Context context, List<MessageDetails> messageDetails) {
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
        LinearLayout linearLayout;


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        System.out.println("Position : " + position);
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
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.incidentClick);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            bytes = messageDetails.get(position).getGallery().get(0).getPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.image.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            latitude_TextLocation = messageDetails.get(position).getLocation().get(0).getLatitude();
            longitude_TextLocation = messageDetails.get(position).getLocation().get(0).getLongitude();
            geo = new Geocoder(context, Locale.getDefault());
            locationDetails = null;

            comments = messageDetails.get(position).getComments().getComment();
            latitude = messageDetails.get(0).getLocation().get(0).getLatitude();
            longitude = messageDetails.get(0).getLocation().get(0).getLongitude();


        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
            Log.e("Error Here ", e.getMessage());
        }
        if (comments == null)
            holder.textComments.setText("No comments");
        else
            holder.textComments.setText(comments);


        try {
            locationDetails = geo.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);

            city = locationDetails.get(0).getLocality();
            region = locationDetails.get(0).getAdminArea();
            country = locationDetails.get(0).getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.textAddress.setText(city + "," + region + "," + country);

        holder.textDate.setText(MonitorUtils.convertDatetoString(messageDetails.get(position).getComments().getApplieddate()));


        holder.textLocation.setText(latitude_TextLocation + ", " + longitude_TextLocation);
        if (messageDetails.get(position).getComments().getStatus().equals(Config.SEND_STATUS)) {
            holder.imageStatus.setImageResource(R.drawable.check);
        } else if (messageDetails.get(position).getComments().getStatus().equals(Config.UPDATE_STATUS)) {
            holder.imageStatus.setImageResource(R.drawable.double_checking);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            String applcantID, date, comments, status;
            byte[] pic;

            @Override
            public void onClick(View v) {
                applcantID = messageDetails.get(position).getComments().getApplicantid();
                pic = messageDetails.get(position).getGallery().get(0).getPicture();
                date = messageDetails.get(position).getComments().getApplieddate().toString();
                comments = messageDetails.get(position).getComments().getComment();
                status = messageDetails.get(position).getComments().getStatus();
                String paths=messageDetails.get(position).getGallery().get(0).getImagepath();
                if (comments == null)
                    comments = "No comments";
                try {


                    Intent intent = new Intent(context, IncidentActivity.class);
                    intent.putExtra("picture", pic);
                    intent.putExtra("applicantID", applcantID);
                    intent.putExtra("galleryID", applcantID);
                    intent.putExtra("path",paths);

                    intent.putExtra("date", date);
                    intent.putExtra("comments", comments);
                    intent.putExtra("status", status);


                    context.startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;
    }
}
