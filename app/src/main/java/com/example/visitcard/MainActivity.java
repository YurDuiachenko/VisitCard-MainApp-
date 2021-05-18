package com.example.visitcard;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.visitcard.ui.cards.Card;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_transfer, R.id.navigation_cards, R.id.navigation_events)
//                .build();


        ///////////////////////////////
        try {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_CONTACTS,
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.NFC,
                            Manifest.permission.NFC_TRANSACTION_EVENT,
                            Manifest.permission.READ_PHONE_NUMBERS},
                    PackageManager.PERMISSION_GRANTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /////////////////////////////


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        cardList = new ArrayList<>();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.i("Scan", "Scanner didn't has an information");
            } else {
                Log.i("Scan", "Scanned");
                String all = result.getContents();
                Log.i("!!!", all);
                HashMap<String, String> info = vCard.readRes(all);
                ArrayList<ContentProviderOperation> now = vCard.addNewStandardContact(
                        info.get("name"),
                        info.get("phone"),
                        info.get("mail"),
                        info.get("work"),
                        info.get("adress"),
                        info.get("website"));
                try {
                    getBaseContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, now);
                } catch (Exception e) {
                    Log.e("Exception: ", e.getMessage());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
