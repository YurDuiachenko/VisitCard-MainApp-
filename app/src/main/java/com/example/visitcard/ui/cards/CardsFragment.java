package com.example.visitcard.ui.cards;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.visitcard.MainActivity;
import com.example.visitcard.R;
import com.example.visitcard.vCard;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import net.glxn.qrgen.android.QRCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardsFragment extends Fragment {

    FloatingActionButton add_new_card;
    String info;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cards, container, false);

        recyclerView = root.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        add_new_card = root.findViewById(R.id.add_card);
        add_new_card.setOnClickListener(onAddNewCardClick());

        ////////////////////////////////////////
        Bundle bundle = getArguments();
        if (bundle != null) {
            info = bundle.getString("info");

            HashMap<String, String> data = vCard.readRes(info);
            Bitmap bitmap = QRCode.from(info).withCharset("UTF-8").bitmap();

            ArrayList<String> soc = new ArrayList<>();
            soc.add(0, data.get("tl"));
            soc.add(1, data.get("ok"));
            soc.add(2, data.get("vk"));

            MainActivity.cardList.add(
                    new Card(data.get("name"),
                            data.get("phone"),
                            data.get("mail"),
                            data.get("work"),
                            bitmap,
                            soc)
            );
        }
        ///////////////////////////////////////

        CardAdapter adapter = new CardAdapter(getContext(), MainActivity.cardList);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private View.OnClickListener onAddNewCardClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction flip = getFragmentManager().beginTransaction();
                flip.replace(R.id.nav_host_fragment, new AddCardFragment());
                flip.commit();
            }
        };
    }
}