package com.example.visitcard.ui.cards;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.example.visitcard.R;
import com.example.visitcard.vCard;

import java.util.List;

import static com.example.visitcard.ui.cards.CardsFragment.flip;


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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip.replace(R.id.nav_host_fragment, new EditCardFragment());
                flip.commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }


    class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone, email, work;
        ImageView qr, tl, vk, ok, delete, edit;

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
