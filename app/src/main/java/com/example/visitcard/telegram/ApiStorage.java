package com.example.visitcard.telegram;

import android.os.Environment;
import androidx.annotation.Nullable;
import com.github.badoualy.telegram.api.TelegramApiStorage;
import com.github.badoualy.telegram.mtproto.auth.AuthKey;
import com.github.badoualy.telegram.mtproto.model.DataCenter;
import com.github.badoualy.telegram.mtproto.model.MTSession;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ApiStorage implements TelegramApiStorage {

    //Create File variable for auth.key and dc.save
    FileOutputStream outputStream = null;
    File sdCard = Environment.getExternalStorageDirectory();
    File visigram = new File(sdCard.getAbsoluteFile() + "/Visigram");
    public final File AUTH_KEY_FILE = new File(visigram, "/auth.key");
    public final File NEAREST_DC_FILE = new File(visigram,"/dc.save");

    @Override
    public void saveAuthKey(@NotNull AuthKey authKey) {
        try {
            visigram.mkdir();
            FileUtils.writeByteArrayToFile(AUTH_KEY_FILE, authKey.getKey());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public AuthKey loadAuthKey() {
        try {
            visigram.mkdir();
            return new AuthKey(FileUtils.readFileToByteArray(AUTH_KEY_FILE));
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException))
                e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveDc(@NotNull DataCenter dataCenter) {
        try {
            visigram.mkdir();
            FileUtils.write(NEAREST_DC_FILE, dataCenter.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public DataCenter loadDc() {
        try {
            visigram.mkdir();
            String[] infos = FileUtils.readFileToString(NEAREST_DC_FILE).split(":");
            return new DataCenter(infos[0], Integer.parseInt(infos[1]));
        } catch (IOException e) {
            if (!(e instanceof FileNotFoundException))
                e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteAuthKey() {
        try {
            visigram.mkdir();
            FileUtils.forceDelete(AUTH_KEY_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDc() {
        try {
            FileUtils.forceDelete(NEAREST_DC_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSession(@Nullable MTSession session) {

    }

    @Nullable
    @Override
    public MTSession loadSession() {
        return null;
    }
}