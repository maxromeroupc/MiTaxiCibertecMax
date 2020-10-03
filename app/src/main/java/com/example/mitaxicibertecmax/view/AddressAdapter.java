package com.example.mitaxicibertecmax.view;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList = new ArrayList<>();

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        return new AddressViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bind( addressList.get(position));
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public void setAddressList( List<Address> addressList ){
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    public class  AddressViewHolder extends RecyclerView.ViewHolder{

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind( Address address ){
            ((TextView)itemView).setText( address.getAddressLine(0) );
        }

    }

}
