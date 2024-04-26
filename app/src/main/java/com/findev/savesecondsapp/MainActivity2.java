package com.findev.savesecondsapp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2 extends Activity {

    /* Fields */
    private static final int CONTACT_PICKER_RESULT = 1001;
    private String usingCode;

    /* Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TypedArray list = getResources().obtainTypedArray(R.array.ussdcodes);

        LayoutInflater inflater = this.getLayoutInflater();
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Set width to match parent
                (int) getResources().getDimension(R.dimen.image_height) // Set height to 70dp
        );
        params.setMargins(0, 16, 0, 0); // Set margin programmatically (left, top, right, bottom)
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.loyalty_img); // Set your image resource here

        ll.addView(imageView);


        /* Initialize each JSON object of "ussd_info.xml". */
        for (int i = 0; i < list.length(); i++) {
            JSONObject j = null;
            try {
                j = new JSONObject(list.getString(i));
                //add imageview with image here
                View v = inflater.inflate(R.layout.list_item, null);

                LinearLayout l = (LinearLayout) v.findViewById(R.id.linear);

                final String code = j.getString("code");
                Button bar = (Button) l.findViewById(R.id.bar);
                bar.setText(j.getString("title"));
                bar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (code.contains("contact")) {
                            usingCode = code;
                            startActivityForResult(new Intent(
                                            Intent.ACTION_PICK, Contacts.CONTENT_URI),
                                    CONTACT_PICKER_RESULT);
                        } else {
                            startActivity(new Intent(
                                    "android.intent.action.CALL", Uri
                                    .parse("tel:"
                                            + code.replaceAll("#",
                                            Uri.encode("#")))));
                        }
                    }
                });

                final String description = j.getString("description");
                ((Button) l.findViewById(R.id.ussdinfobutton))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(
                                        MainActivity2.this);
                                View view = LayoutInflater.from(
                                        MainActivity2.this).inflate(
                                        R.layout.info_layout, null);
                                ((TextView) view.findViewById(R.id.ussdcode))
                                        .setText(code);
                                ((TextView) view
                                        .findViewById(R.id.ussddescription))
                                        .setText(description);
                                builder.setView(view);
                                builder.setCancelable(true).setNeutralButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(
                                                    final DialogInterface dialog,
                                                    @SuppressWarnings("unused") final int id) {
                                                dialog.cancel();
                                            }
                                        });
                                builder.create().show();
                            }
                        });

                ll.addView(v);
            } catch (JSONException e) {
                Log.e("log", "XML information failed");
            }
        }
        setContentView(ll);
    }

    /* Threat the number: "-" and prefixes */
    String threatPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("-", "");
        String prefix = getResources().getString(R.string.prefix);
        if (phoneNumber.startsWith(prefix))
            phoneNumber = phoneNumber.substring(prefix.length());
        else if (phoneNumber.startsWith("00" + prefix.substring(1)))
            phoneNumber = phoneNumber.substring(prefix.length() + 1);
        return phoneNumber;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Uri result = data.getData();
                    String id = result.getLastPathSegment();
                    Cursor pCur = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);

                    if (pCur != null) {
                        if (pCur.moveToFirst()) {
                            int phoneNumberColumnIndex = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            if (phoneNumberColumnIndex != -1) {
                                String phoneNumber = pCur.getString(phoneNumberColumnIndex);
                                pCur.close();

                                // Perform further operations with the phone number
                                usingCode = usingCode.replaceAll("contact", threatPhoneNumber(phoneNumber));
                                usingCode = usingCode.replaceAll("#", Uri.encode("#"));

                                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + usingCode)));
                            } else {
                                Log.e("Error", "Phone number column not found");
                            }
                        } else {
                            Log.e("Error", "No data found for contact ID: " + id);
                        }
                        pCur.close(); // Close the cursor after use
                    } else {
                        Log.e("Error", "Cursor is null");
                    }
                    break;
            }
        }
    }

    /* Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.generalmenu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* switch (item.getItemId()) {
            case R.id.iwebsite:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/diogobernardino"));
                startActivity(browserIntent);
                break;
        }*/
        return true;
    }

}