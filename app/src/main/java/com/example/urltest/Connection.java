package com.example.urltest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.net.InetAddress;


public class Connection {
    Context context;
    boolean internet = false;
    public Connection(Context context) {
        this.context = context;
    }
    public void connectivity() throws IOException {
        InetAddress ip = null;
        boolean connected = false;
        try {
            ip = InetAddress.getByName("www.libgen.is");
            Log.v("CONN", "Connected to " + ip);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("CONN", "HOST_RES_ERR : " + e.getMessage());
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setMessage("Some error occurred while connecting to  our servers. ").setTitle("Connectivity");
//            builder.setCancelable(false).setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    context.startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                }
//            });
//            builder.show();
        }
        if (ip==null) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Ooops...Make sure that you have an active Internet connection ").setTitle("Connectivity");
                builder.setCancelable(false).setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).setNegativeButton("Downloads", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            Toast.makeText(context, "Failed to connect to servers", Toast.LENGTH_LONG).show();
        } else {
           // Toast.makeText(context, "Connection Success" + ip, Toast.LENGTH_LONG).show();
            Log.v("CONN", "Connected to " + ip);
        }
    }
}