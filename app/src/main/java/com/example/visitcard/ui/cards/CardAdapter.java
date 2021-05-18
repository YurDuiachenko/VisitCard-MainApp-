package com.example.visitcard.ui.cards;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.visitcard.R;
import com.example.visitcard.vCard;

import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.PersonViewHolder> {

    private Context context;
    private List<Card> cardList;

    public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_card, null);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        //getting the product of the specified position
        Card card = cardList.get(position);
        holder.name.setText(card.getName());
        holder.phone.setText(card.getPhone());
        holder.email.setText(String.valueOf(card.getMail()));
        holder.work.setText(String.valueOf(card.getWork()));
        holder.qr.setImageBitmap(card.getImage());
//        if(vCard.data.get("tl").equals("true")) {
//            holder.tl.setVisibility(View.VISIBLE);
//        } else{
//            holder.tl.setVisibility(View.INVISIBLE);
//        }
//
//        if(vCard.data.get("vk").equals("true")) {
//            holder.vk.setVisibility(View.VISIBLE);
//        } else{
//            holder.vk.setVisibility(View.INVISIBLE);
//        }
//
//        if(vCard.data.get("ok").equals("true")) {
//            holder.ok.setVisibility(View.VISIBLE);
//        } else{
//            holder.ok.setVisibility(View.INVISIBLE);
//        }
    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }


    class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone, email, work;
        ImageView qr, tl, vk, ok;

        public PersonViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            work = itemView.findViewById(R.id.work);
            qr = itemView.findViewById(R.id.qr);
            tl = itemView.findViewById(R.id.telega);
            vk = itemView.findViewById(R.id.vk);
            ok = itemView.findViewById(R.id.ok);
        }
    }
}
