package com.example.visitcard.telegram;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.visitcard.MainActivity;
import com.github.badoualy.telegram.api.Kotlogram;
import com.github.badoualy.telegram.tl.exception.RpcErrorException;

import java.io.IOException;

public class Verification extends Worker {
    public final String TAG = "MY_TAG";

    public Verification(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Result doWork() {
        try {
            System.out.println("START");
            MainActivity.client = Kotlogram.getDefaultClient(MainActivity.application, new ApiStorage());
            MainActivity.sentCode = MainActivity.client.authSendCode(false, MainActivity.PHONE_NUMBER, true);
        } catch (RpcErrorException | IOException e) {
            System.out.println("THERE'S AN EXCEPTION");
            e.printStackTrace();
        }
        return Result.success();
    }

}