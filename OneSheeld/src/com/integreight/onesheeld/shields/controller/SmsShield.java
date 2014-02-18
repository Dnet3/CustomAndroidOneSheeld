package com.integreight.onesheeld.shields.controller;

import android.app.Activity;
import android.telephony.SmsManager;

import com.integreight.firmatabluetooth.ShieldFrame;
import com.integreight.onesheeld.enums.UIShield;
import com.integreight.onesheeld.utils.ControllerParent;

public class SmsShield extends ControllerParent<SmsShield> {
	private SmsEventHandler eventHandler;
	private String lastSmsText;
	private String lastSmsNumber;
	private static final byte SEND_SMS_METHOD_ID = (byte) 0x01;

	public String getLastSmsText() {
		return lastSmsText;
	}

	public String getLastSmsNumber() {
		return lastSmsNumber;
	}

	public SmsShield() {
		super();
	}

	@Override
	public ControllerParent<SmsShield> setTag(String tag) {
		return super.setTag(tag);
	}

	public SmsShield(Activity activity, String tag) {
		super(activity, tag);
	}

	@Override
	public void onUartReceive(byte[] data) {
		// if (data.length < 2)
		// return;
		// byte command = data[0];
		// byte methodId = data[1];
		// int n = data.length - 2;
		// byte[] newArray = new byte[n];
		// System.arraycopy(data, 2, newArray, 0, n);

		super.onUartReceive(data);
	}

	protected void sendSms(String smsNumber, String smsText) {

		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(smsNumber, null, smsText, null, null);
			eventHandler.onSmsSent(smsText, smsText);
		} catch (Exception e) {
			eventHandler.onSmsFail(e.getMessage());

			e.printStackTrace();
		}
		CommitInstanceTotable();

	}

	public void setSmsEventHandler(SmsEventHandler eventHandler) {
		this.eventHandler = eventHandler;
		CommitInstanceTotable();
	}

	public interface SmsEventHandler {
		void onSmsSent(String smsNumber, String smsText);

		void onSmsFail(String error);
	}

	@Override
	public void onNewShieldFrameReceived(ShieldFrame frame) {
		// TODO Auto-generated method stub
		if (frame.getShieldId() == UIShield.SMS_SHIELD.getId()) {
			String smsNumber = frame.getArgumentAsString(0);
			String smsText = frame.getArgumentAsString(1);
			lastSmsText = smsText;
			if (frame.getFunctionId() == SEND_SMS_METHOD_ID) {
				sendSms(smsNumber, smsText);

			}

		}

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
