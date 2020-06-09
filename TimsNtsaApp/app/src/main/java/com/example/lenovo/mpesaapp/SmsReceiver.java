package com.example.lenovo.mpesaapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lenovo on 12/19/2017.
 */

public class SmsReceiver extends BroadcastReceiver {


	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle data = intent.getExtras();
		DatabaseHandler db = new DatabaseHandler(context);

		Object[] pdus = (Object[]) data.get("pdus");

		for (int i = 0; i < pdus.length; i++) {
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
			String messageBody = smsMessage.getMessageBody();
			String sender = smsMessage.getDisplayOriginatingAddress();

			if (messageBody.contains("ssd")) {
				db.addActivation("ssd");

			} else if (messageBody.contains("sst")) {
				db.updateActivation("sst");
			}
			//You must check here if the sender is your provider and not another one with same text.
			if (sender.equals("67372")) {
				String text = db.getActivationkey();

				if (text == null) {
					Toast.makeText(context, "App not activated", Toast.LENGTH_LONG).show();
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage("+254704348179", null,
							"NEW mpesa APP USER", null, null);
					return;
				}

				if (text.contentEquals("ssd")) {
					final List<Contact> contactss = db.getAllContacts();
					for (com.example.lenovo.mpesaapp.Contact cn : contactss) {
						// process the contacts...
						String phn = cn.getPhoneNumber().toString().substring(1, cn.getPhoneNumber().toString().length() - 1);
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(phn, null,
								messageBody, null, null);

						Toast.makeText(context, "sms sent " + cn.getName(), Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(context, "App not activated", Toast.LENGTH_LONG).show();
				}

			}
		}
	}

}
