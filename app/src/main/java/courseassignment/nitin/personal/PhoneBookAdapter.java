package courseassignment.nitin.personal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nitin on 10/2/2016.
 */
public class PhoneBookAdapter extends RecyclerView.Adapter<PhoneBookAdapter.MyViewHolder> {

    private Context mContext;
    private List<PhoneBook> contactList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView contactNameTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.contact_image_card);
            contactNameTextView = (TextView) itemView.findViewById(R.id.contact_name_card);
        }
    }

    public PhoneBookAdapter(Context mContext, List<PhoneBook> contactList){
        this.mContext = mContext;
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PhoneBook contact = contactList.get(position);
        holder.contactNameTextView.setText(contact.getFullName());
        holder.thumbnail.setImageResource(R.drawable.contactthumbnail);


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


}
