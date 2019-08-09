package com.example.lenovo.timsntsaapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.ayz4sci.androidfactory.permissionhelper.PermissionHelper;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

private static final int CONTACT_PICKER_REQUEST = 1;
public static final String PREFS_NAME = "AOP_PREFS";
ArrayList<DataModel> dataModels;
ListView listView;
SharedPreferences settings;
SharedPreferences.Editor editor;
private static CustomAdapter adapter;
DatabaseHandler db = new DatabaseHandler(this);
PermissionHelper permissionHelper ;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    FloatingActionButton fab = findViewById(R.id.fab);

    permissionHelper  = PermissionHelper.getInstance(this);
    settings = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
    editor = settings.edit(); //2
    display();
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new MultiContactPicker.Builder(MainActivity.this) //Activity/fragment context
                    //.theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                    .hideScrollbar(false) //Optional - default: false
                    .showTrack(true) //Optional - default: true
                    .searchIconColor(Color.WHITE) //Option - default: White
                    .handleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                    .bubbleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                    .bubbleTextColor(Color.WHITE) //Optional - default: White
                    .showPickerForResult(CONTACT_PICKER_REQUEST);
        }
    });
}
public void display(){
    final List<com.example.lenovo.timsntsaapp.Contact> contacts = db.getAllContacts();
    //load contacts at start
    listView=findViewById(R.id.list);
    dataModels= new ArrayList<>();
    for (com.example.lenovo.timsntsaapp.Contact cn : contacts) {
        // process the contacts...
        dataModels.add(new DataModel(cn.getName().toString(),cn.getPhoneNumber().toString()));
    }
            adapter= new CustomAdapter(dataModels,MainActivity.this);
            listView.setAdapter(adapter);
             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            // selected item
            final String selected = ((TextView) v.findViewById(R.id.name)).getText().toString();
            AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Delete?");
            adb.setMessage("Are you sure you want to delete " + selected);
            final int positionToRemove = position;
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteContact(selected);
                    dataModels.remove(positionToRemove);
                    adapter.notifyDataSetChanged();
                }});
            adb.show();
        }
    });

}
// get contacts
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == CONTACT_PICKER_REQUEST){
        if(resultCode == RESULT_OK) {
            List<ContactResult> results = MultiContactPicker.obtainResult(data);
            Log.d("MyTag", results.get(0).getDisplayName());
            dataModels= new ArrayList<>();
            for (ContactResult contact : results) {
                // process the contacts...
                dataModels.add(new DataModel(contact.getDisplayName().toString(),contact.getPhoneNumbers().toString()));
                db.addContact(contact.getDisplayName().toString(),contact.getPhoneNumbers().toString());
            }
            display();
        } else if(resultCode == RESULT_CANCELED){
            System.out.println("User closed the picker without selecting items.");
        }
    }
   // permissionHelper.onActivityResult(requestCode, resultCode, data);
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
        return true;
    }
    return super.onOptionsItemSelected(item);
}

public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
}

@Override
protected void onDestroy() {
    permissionHelper.finish();
    super.onDestroy();
}
}
