package com.example.visitcard;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.visitcard.ui.cards.Card;
import com.github.badoualy.telegram.api.TelegramApp;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLUser;
import com.github.badoualy.telegram.tl.api.auth.TLAuthorization;
import com.github.badoualy.telegram.tl.api.auth.TLSentCode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final int API_ID = 3657825;
    public static final String API_HASH = "2084a3c7a7e4963fea5ac912c9cba87d";

    public static final String APP_VERSION = "AppVersion";
    public static final String MODEL = "Model";
    public static final String SYSTEM_VERSION = "SysVer";
    public static final String LANG_CODE = "en";

    public static TelegramApp application = new TelegramApp(API_ID, API_HASH, MODEL, SYSTEM_VERSION, APP_VERSION, LANG_CODE);


    public static final String PHONE_NUMBER = "+79102588486" ; // International format
    public static final String PEER_PHONE_NUMBER = "+79205267168" ; // International format

    EditText code;
    Switch telegram_switch;
    static String pass;
    Button verify;

    static TelegramClient client;
    static TLSentCode sentCode;
    static TLAuthorization authorization;
    static TLUser self;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
