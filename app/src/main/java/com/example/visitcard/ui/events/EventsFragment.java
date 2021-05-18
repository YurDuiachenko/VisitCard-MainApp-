package com.example.visitcard.ui.events;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.visitcard.R;
import com.google.android.material.card.MaterialCardView;
import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.scheme.VCard;

import static com.example.visitcard.R.id.qr;

public class EventsFragment extends Fragment {

    ImageView qr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_events, container, false);

        qr = root.findViewById(R.id.qr);

        VCard Yu_Dio = new VCard("John Doe")
                .setEmail("john.doe@example.org")
                .setAddress("John Doe Street 1, 5678 Doestown")
                .setTitle("Mister")
                .setCompany("John Doe Inc.")
                .setPhoneNumber("1234")
                .setWebsite("www.example.org");
        Bitmap bitmap = QRCode.from(Yu_Dio).withCharset("UTF-8").bitmap();
        qr.setImageBitmap(bitmap);

        return root;
    }
}