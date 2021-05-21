package com.example.visitcard.ui.cards;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.visitcard.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditCardFragment extends Fragment {

    EditText name,    //name
            mail,     //surname
            num,      //number
            work,     //profession
            adress,   //adress
            web;      //website page
    FloatingActionButton save, delete;
    CheckBox telega,
            ok,
            vk;
    public static String res;       //Stroke for final data

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_card, container, false);

        name = root.findViewById(R.id.name);
        mail = root.findViewById(R.id.email);
        num = root.findViewById(R.id.phone);
        work = root.findViewById(R.id.work);
        adress = root.findViewById(R.id.adress);
        web = root.findViewById(R.id.web);

        save = root.findViewById(R.id.save);
        delete = root.findViewById(R.id.delete);

        telega = root.findViewById(R.id.telega);
        vk = root.findViewById(R.id.vk);
        ok = root.findViewById(R.id.ok);

        save.setOnClickListener(saveOnClick());
        delete.setOnClickListener(deleteOnClick());
        return root;
    }

    private View.OnClickListener saveOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !num.getText().toString().isEmpty()) {
                    res = "BEGIN:VCARD" + "\n" +
                            "VERSION:" + "3.0" + "\n" +
                            "N:" + name.getText().toString() + "\n" +
                            "FN:" + name.getText().toString() + "\n" +
                            "TEL:" + num.getText().toString() + "\n" +
                            "EMAIL:" + mail.getText().toString() + "\n" +
                            "TITLE:" + work.getText().toString() + "\n" +
                            "ADR:" + adress.getText().toString() + "\n" +
                            "URL:" + web.getText().toString() +"\n" +
                            "ACC:" +
                            "TELEGRAM/" + telega.isChecked() +
                            "OK/" + ok.isChecked()  +
                            "VK/" + vk.isChecked() + "\n" +
                            "END:VCARD";

                    //Я отправляю данные через Bundle в CardsFragment
                    System.out.println(res);
                    CardsFragment letter = new CardsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("info", res);
                    letter.setArguments(bundle);
                    //Ты же должен вместо этого хранить готовую строку в FireBase и доставать её отттуда в CardsFragment
                    //В CardsFragment я обрабатываю строку отдельно от того, как принимаю, поэтому смело доставай данные
                    //в указаном месте в CardsFragment

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, letter).commit();
                } else {
                    name.requestFocus();
                    num.requestFocus();
                    Toast.makeText(
                            getContext(),
                            "Input your name and phone number",
                            Toast.LENGTH_SHORT
                    ).show();
                }
//                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfilesFragment()).commit();
            }
        };
    }

    private View.OnClickListener deleteOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CardsFragment()).commit();
            }
        };
    }
}