package haipv.fbintegration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import java.io.File;
import java.net.URI;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.sromku.simple.fb.SimpleFacebook;
//import com.sromku.simple.fb.entities.Feed;
//import com.sromku.simple.fb.listeners.OnPublishListener;
//import com.sromku.simple.fb.SimpleFacebook;
//import com.sromku.simple.fb.entities.Feed;
//import com.sromku.simple.fb.listeners.OnPublishListener;

public class ShareActivity extends AppCompatActivity {

    private static final int PICK_CONTACT = 1;
    @BindView(R.id.fbShare)
    ShareButton fbShare;
    @BindView(R.id.fbSend)
    SendButton fbSend;

    @BindView(R.id.SMS)
    SendButton button;
    public static final String TAG = "ShareActivity";
    private String phoneNumber = "5556";
    private String smsBody = "SMS BODY";
//    private SimpleFacebook mSimpleFacebook = SimpleFacebook.getInstance();
//    private Feed feed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
//        else {
//            // Android version is lesser than 6.0 or the permission is already granted.
//            List<String> contacts = getContactNames();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
//            lstNames.setAdapter(adapter);
//        }

        /**
         * Share with link completed
         */
//        ShareLinkContent content = new ShareLinkContent.Builder()
//                .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                .build();
//        fbShare.setShareContent(content);
//        fbSend.setShareContent(content);

//        SimpleFacebook s = SimpleFacebook.getInstance();
//        OnPublishListener onPublishListener = new OnPublishListener() {
//
//            @Override
//            public void onComplete(String postId) {
//                Log.i(TAG, "Published successfully. The new post id = " + postId);
//            }
//
//        };
//        String imageUri = "drawable://" + R.drawable.gift;
//        feed = new Feed.Builder()
//                .setMessage("Clone it out...")
//                .setName("Simple Facebook for Android")
//                .setCaption("Code less, do the same.")
//                .setDescription(getString(R.string.text_share))
//                .setPicture(imageUri)
////                    .setLink("https://github.com/sromku/android-simple-facebook")
//                .build();
//        mSimpleFacebook.publish(feed,onPublishListener);
    }


    @OnClick(R.id.SMS)
    public void sendSmSMMS(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setClassName("haipv.fbintegration", "haipv.fbintegration.ComposeMessageActivity");
        sendIntent.putExtra("address", "1213123123");
        sendIntent.putExtra("sms_body", "if you are sending text");
        final File file1 = new File("drawable://" + R.drawable.gift);
        if (file1.exists()) {
            System.out.println("file is exist");
        }
        Uri uri = Uri.fromFile(file1);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("drawable://" + R.drawable.gift);
        startActivity(sendIntent);
    }

    @OnClick(R.id.btnSmsManager)
    public void submitBtn(View vew) {
        try {

// Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,
                    null,
                    smsBody,
                    null,
                    null);
            Toast.makeText(getApplicationContext(), "Your sms has successfully sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @OnClick(R.id.btnSIntent)
    public void sendSmsBySIntent() {
        try {
            // add the phone number in the data
            Uri uri = Uri.parse("smsto:" + phoneNumber);

            Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
            // add the message at the sms_body extra field
            smsSIntent.putExtra("sms_body", "sendSmsBySIntent");

            startActivity(smsSIntent);
        } catch (Exception ex) {
            Toast.makeText(this, "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @OnClick(R.id.btnVIntent)
    public void sendSmsByVIntent() {
        Uri uri = Uri.parse("sms:" + phoneNumber);
        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        // prompts only sms-mms clients
//        smsVIntent.setType("vnd.android-dir/mms-sms");
        smsVIntent.setData(uri);
        // extra fields for number and message respectively
        smsVIntent.putExtra("address", phoneNumber);
        smsVIntent.putExtra("sms_body", "btnSIntent");
        smsVIntent.putExtra("address", phoneNumber);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            smsVIntent.setAction(Intent.ACTION_SENDTO);
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getApplicationContext());
            if(defaultSmsPackageName != null) {
                smsVIntent.setPackage(defaultSmsPackageName);
            }
        } else {
            smsVIntent.setAction(Intent.ACTION_VIEW);
            smsVIntent.setType("vnd.android-dir/mms-sms");
        }
        try {
            startActivity(smsVIntent);
        } catch (Exception ex) {
            Toast.makeText(this, "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }

    }
    @OnClick(R.id.btnMMS)
    public void sendMMS(View view){
        try {
            Bitmap image = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            view.draw(new Canvas(image));
            String url = MediaStore.Images.Media.insertImage(getContentResolver(), image, "title", null);

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra("sms", phoneNumber);
            sendIntent.putExtra("sms_body", "some text");
            sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
            sendIntent.setType("image/png");
        } catch (Exception e){
            Toast.makeText(this, "Your sms has failed...",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @OnClick(R.id.openContact)
    public void sendWithOpenContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

    }

    private File createImageFile() {
        File file = new File("D:\\03.android\\01.androidProj\\fbIntegration1\\app\\src\\main\\res\\drawable-v24\\gift.png");
        return file;
    }

    public String getURLForResource(int resourceId) {
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + resourceId).toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (PICK_CONTACT): {
                if (resultCode == RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        String contactId =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //
                        //  Get all phone numbers.
                        //
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext()) {
                            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            switch (type) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    Toast.makeText(this, "NUMBER TYPE_HOME: " + number, Toast.LENGTH_LONG).show();
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    // do something with the Mobile number here...
                                    Toast.makeText(this, "NUMBER TYPE_MOBILE: " + number, Toast.LENGTH_LONG).show();
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    Toast.makeText(this, "NUMBER TYPE_WORK: " + number, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                        phones.close();
                    }
                    cursor.close();
                }

            }
        }
    }
}
