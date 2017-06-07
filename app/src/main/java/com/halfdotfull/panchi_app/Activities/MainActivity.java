package com.halfdotfull.panchi_app.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.halfdotfull.panchi_app.FakeCallReceiver;
import com.halfdotfull.panchi_app.Permissions;
import com.halfdotfull.panchi_app.R;
import com.halfdotfull.panchi_app.Services.MessageService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 2804;
    CardView message, contacts, fakecall, helpline, police, isSafe;
    SharedPreferences mSharedPreferences;
    String number, serial;

    //SmsManager smsManager = SmsManager.getDefault();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (CardView) findViewById(R.id.cardView2);
        isSafe = (CardView) findViewById(R.id.cardView7);
        contacts = (CardView) findViewById(R.id.cardView1);

        fakecall = (CardView) findViewById(R.id.cardView3);
        police = (CardView) findViewById(R.id.cardView5);
        helpline = (CardView) findViewById(R.id.cardView4);

           /* Permissions.askforPermission(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS,Manifest.permission.CALL_PHONE},
                    new Permissions.OnpermissionResultListner() {
                        @Override
                        public void OnGranted(String fperman) {
                            startService();
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnDenied(String fperman) {
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }
                    });*/
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            Permissions.askforPermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    new Permissions.OnpermissionResultListner() {
                        @Override
                        public void OnGranted(String fperman) {
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnDenied(String fperman) {
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            Permissions.askforPermission(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    new Permissions.OnpermissionResultListner() {
                        @Override
                        public void OnGranted(String fperman) {
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnDenied(String fperman) {
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS)!=PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED)
        {
            Permissions.askforPermission(this, new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS},
                    new Permissions.OnpermissionResultListner() {
                        @Override
                        public void OnGranted(String fperman) {
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnDenied(String fperman) {
                            Toast.makeText(MainActivity.this, fperman, Toast.LENGTH_SHORT).show();
                        }
                    });
        }


        isSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, isSafe.class);
                startActivity(intent);
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Police.class);
                startActivity(intent);
            }
        });
        fakecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 10);
                long currentFakeTime = calendar.getTimeInMillis();

                String fakeNameEntered = "Papa";
                String fakeNumberEntered = "9412168792";
                setUpAlarm(currentFakeTime, fakeNameEntered, fakeNumberEntered);
            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Contact.class);
                startActivity(i);
            }
        });
        helpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1091"));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this)
                        .title(R.string.input)
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("Enter Here", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                mSharedPreferences=getSharedPreferences("panchi", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=mSharedPreferences.edit();
                                editor.putString("Message",input.toString());
                                editor.apply();
                            }
                        });
                MaterialDialog dialog = builder.build();
                dialog.show();
            }
        });
    }

    public void setUpAlarm(long selectedTimeInMilliseconds, String fakeName, String fakeNumber){

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, FakeCallReceiver.class);

        intent.putExtra("FAKENAME", fakeName);
        intent.putExtra("FAKENUMBER", fakeNumber);

        PendingIntent fakePendingIntent = PendingIntent.getBroadcast(this, 0,  intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, selectedTimeInMilliseconds, fakePendingIntent);
        Toast.makeText(getApplicationContext(), "Your fake call time has been set", Toast.LENGTH_SHORT).show();
    }
    private void startService() {
        Intent intent=new Intent(MainActivity.this,MessageService.class);
        intent.putExtra("number",number);
        intent.putExtra("serial",serial);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        int count=0,i;
        int size=permissions.length;
        for(i=0;i<size;i++)
        {
            if(grantResults[i]== PackageManager.PERMISSION_GRANTED)
                count++;
        }
        if(count==size)
            startService();

        Permissions.OnPermResult(requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}