package com.example.visitcard;

import android.content.ContentProviderOperation;
import android.provider.ContactsContract;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class vCard {
    public static HashMap<String, String> data = new HashMap<>();

    public static ArrayList<ContentProviderOperation> addNewStandardContact(
            String name,
            String phone,
            String email,
            String work,
            String adr,
            String url) {
        ArrayList<ContentProviderOperation> contact = new ArrayList<ContentProviderOperation>();
        //Добавляем пустой контакт
        contact.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        //Добавляем данные имени
        contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());
        //Добавляем данные телефона
        contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());
        //Добавляем данные почты
        contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                .build());
        //Добавляем данные работы
        contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, work)
                .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                .build());
//        //Добавляем данные сайта
//        contact.add(ContentProviderOperation.newAssertQuery(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.Website.URL, url)
//                .withValue(ContactsContract.CommonDataKinds.Website.TYPE, ContactsContract.CommonDataKinds.Website.TYPE_PROFILE)
//                .withValue(ContactsContract.CommonDataKinds.Website.URL, url)
//                .build());
//        //Добавляем данные адреса
//        contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.SipAddress.MIMETYPE)
//                .withValue(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS, adr)
//                .withValue(ContactsContract.CommonDataKinds.SipAddress.TYPE, ContactsContract.CommonDataKinds.SipAddress.TYPE_HOME)
//                .withValue(ContactsContract.CommonDataKinds.SipAddress.SIP_ADDRESS, adr)
//                .build());
        return contact;
    }

    public static HashMap<String, String> readRes(String res) {
        data.put(
                "name",
                res.substring(
                        res.indexOf("\nN:") + 3,
                        res.indexOf("\n", res.indexOf("\nN:") + 2)
                )
        );

        data.put(
                "phone",
                res.substring(
                        res.indexOf("TEL:") + 4,
                        res.indexOf("\n", res.indexOf("TEL:") + 4)
                )
        );

        data.put(
                "mail",
                res.substring(
                        res.indexOf("EMAIL:") + 6,
                        res.indexOf("\n", res.indexOf("EMAIL:") + 6)
                )
        );

        data.put(
                "work",
                res.substring(
                        res.indexOf("TITLE:") + 6,
                        res.indexOf("\n", res.indexOf("TITLE:") + 6)
                )
        );

        data.put(
                "tl",
                res.substring(
                        res.indexOf("TELEGRAM") + 9,
                        res.indexOf("OK") - 2
                )
        );

        data.put(
                "ok",
                res.substring(
                        res.indexOf("OK") + 3,
                        res.indexOf("VK") - 2
                )
        );

        data.put(
                "vk",
                res.substring(
                        res.indexOf("VK") + 3,
                        res.indexOf("END") - 2
                )
        );

        data.put(
                "adress",
                res.substring(
                        res.indexOf("ADR") + 3,
                        res.indexOf("URL") - 2
                )
        );

        data.put(
                "website",
                res.substring(
                        res.indexOf("URL") + 3,
                        res.indexOf("ACC") - 2
                )
        );
        return data;
    }



}
