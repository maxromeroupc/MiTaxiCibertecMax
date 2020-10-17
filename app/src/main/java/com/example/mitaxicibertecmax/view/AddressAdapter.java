package com.example.mitaxicibertecmax.view;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitaxicibertecmax.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<Address> addressList = new ArrayList<>();

    private OnAddressClickListener onAddressClickListener;

    public AddressAdapter() {
        this.addressList.add(null);
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.item_address, parent, false);

        return new AddressViewHolder( view, onAddressClickListener );
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
        addressList.add(null);
        notifyDataSetChanged();
    }

    public interface OnAddressClickListener{
        void onAddressClickListener(Address address);
    }

    public void setOnAddressClickListener(OnAddressClickListener pOnAddressClickListener){
        onAddressClickListener = pOnAddressClickListener;
    }

    public void clearAddressList(){
        addressList.clear();
        notifyDataSetChanged();
    }

    public class  AddressViewHolder extends RecyclerView.ViewHolder{

        private TextView txtAddress;
        private OnAddressClickListener onAddressClickListener;

        public AddressViewHolder(@NonNull View itemView, OnAddressClickListener ponOnAddressClickListener) {
            super(itemView);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            onAddressClickListener = ponOnAddressClickListener;
        }

        public void bind(final Address address ){
            if( address != null){
                txtAddress.setText(  address.getAddressLine(0).toString()  );
                txtAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_drawer_menu_24,0,0,0);
            }else{
                txtAddress.setText(  "Seleccionar posición en el mapa" );
                txtAddress.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_my_location,0,0,0);
            }

            //((TextView)itemView).setText( address.getAddressLine(0) );
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(address != null){
                        onAddressClickListener.onAddressClickListener(address);
                    }else{

                    }

                }
            });

        }

    }

}
