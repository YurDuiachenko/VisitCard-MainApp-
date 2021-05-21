package com.example.visitcard.telegram;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.visitcard.MainActivity;
import com.github.badoualy.telegram.api.TelegramClient;
import com.github.badoualy.telegram.tl.api.TLImportedContact;
import com.github.badoualy.telegram.tl.api.TLInputPeerUser;
import com.github.badoualy.telegram.tl.api.TLInputPhoneContact;
import com.github.badoualy.telegram.tl.api.contacts.TLImportedContacts;
import com.github.badoualy.telegram.tl.core.TLVector;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;

import java.io.IOException;
import java.util.Random;

public class Connection extends Worker {
    public final String TAG = "MY_TAG";
    static String name = "", surname = "", username = "";


    public Connection(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Result doWork() {

        try {
            MainActivity.authorization = MainActivity.client.authSignIn(MainActivity.PHONE_NUMBER, MainActivity.sentCode.getPhoneCodeHash(), MainActivity.pass);
            MainActivity.self = MainActivity.authorization.getUser().getAsUser();

            System.out.println("You are now signed in as " +
                    MainActivity.self.getFirstName() + " " +
                    MainActivity.self.getLastName() + " @" +
                    MainActivity.self.getUsername());

            name = MainActivity.self.getFirstName();
            surname = MainActivity.self.getLastName();
            username = MainActivity.self.getUsername();

            if(name == null && username == null && surname==null){
                name = MainActivity.PHONE_NUMBER;
                username = "";
                surname = "";
            }

            System.out.println(MainActivity.authorization.getUser());
            System.out.println(MainActivity.self.getId());
            System.out.println(MainActivity.self.getAccessHash());

            sendMessage(MainActivity.client, MainActivity.PEER_PHONE_NUMBER, "", "I scanned you business card.");

        } catch (RpcErrorException | IOException e) {
            System.out.println("THERE'S AN EXCEPTION");
            e.printStackTrace();
        }
        finally {
            MainActivity.client.close();
        }
        return Result.success();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void sendMessage(TelegramClient client, String phone, String firstName, String text) {

        try {
            Random random = new Random();

            TLVector<TLInputPhoneContact> vector = new TLVector<>();
            TLInputPhoneContact contact = new TLInputPhoneContact(Math.abs(random.nextLong()), phone,
                    firstName, "");
            vector.add(contact);
            TLImportedContacts importContacts = client.contactsImportContacts(vector, false);

            TLImportedContact importedContact = importContacts.getImported().stream().findFirst().orElse(null);

            TLInputPeerUser inputPeerUser = new TLInputPeerUser();
            inputPeerUser.setUserId(importedContact.getUserId());

            MainActivity.client.messagesSendMessage(inputPeerUser, text, Math.abs(new Random().nextLong()));

        } catch (RpcErrorException | IOException e) {
            e.printStackTrace();
        }
    }
}