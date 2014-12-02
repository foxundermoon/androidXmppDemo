package com.swgis.android.xmpp.service;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import java.util.HashSet;
import java.util.List;
import com.swgis.android.xmpp.util.*;

public class XmppService extends Service {
	public static final int CONNECTED = 0;
	public static final int DISCONNECTED = -1;
	public static final int CONNECTING = 1;
	public static final String PONG_TIMEOUT = "pong timeout";// 连接超时
	public static final String NETWORK_ERROR = "network error";// 网络错误
	public static final String LOGOUT = "logout";// 手动退出
	public static final String LOGIN_FAILED = "login failed";// 登录失败
	public static final String DISCONNECTED_WITHOUT_WARNING = "disconnected without warning";// 没有警告的断开连接

//	private IBinder mBinder = new XXBinder();
	private Handler mMainHandler = new Handler();

	private boolean mIsFirstLoginAction;
	// 自动重连 start
	private static final int RECONNECT_AFTER = 5;
	private static final int RECONNECT_MAXIMUM = 10 * 60;// 最大重连时间间隔
	private static final String RECONNECT_ALARM = "com.way.xx.RECONNECT_ALARM";
	// private boolean mIsNeedReConnection = false; // 是否需要重连
	private int mConnectedState = DISCONNECTED; // 是否已经连接
	private int mReconnectTimeout = RECONNECT_AFTER;
	private Intent mAlarmIntent = new Intent(RECONNECT_ALARM);
	private PendingIntent mPAlarmIntent;
	// 自动重连 end
	private ActivityManager mActivityManager;



	@Override
	public void onCreate() {
		super.onCreate();
////		XXBroadcastReceiver.mListeners.add(this);
////		BaseActivity.mListeners.add(this);
////		mActivityManager = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
////		mPAlarmIntent = PendingIntent.getBroadcast(this, 0, mAlarmIntent,
////				PendingIntent.FLAG_UPDATE_CURRENT);
////		registerReceiver(mAlarmReceiver, new IntentFilter(RECONNECT_ALARM));
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		if (intent != null
//				&& intent.getAction() != null
//				&& TextUtils.equals(intent.getAction(),
//						XXBroadcastReceiver.BOOT_COMPLETED_ACTION)) {
//			String account = PreferenceUtils.getPrefString(XXService.this,
//					PreferenceConstants.ACCOUNT, "");
//			String password = PreferenceUtils.getPrefString(XXService.this,
//					PreferenceConstants.PASSWORD, "");
//			if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password))
//				Login(account, password);
//		}
//		mMainHandler.removeCallbacks(monitorStatus);
//		mMainHandler.postDelayed(monitorStatus, 1000L);// 检查应用是否在后台运行线程
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
//		XXBroadcastReceiver.mListeners.remove(this);
//		BaseActivity.mListeners.remove(this);
//		((AlarmManager) getSystemService(Context.ALARM_SERVICE))
//				.cancel(mPAlarmIntent);// 取消重连闹钟
//		unregisterReceiver(mAlarmReceiver);// 注销广播监听
//		logout();
	}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 登录
	public void Login(final String account, final String password) {
//		if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
//			connectionFailed(NETWORK_ERROR);
//			return;
//		}
//		if (mConnectingThread != null) {
//			L.i("a connection is still goign on!");
//			return;
//		}
//		mConnectingThread = new Thread() {
//			@Override
//			public void run() {
//				try {
//					postConnecting();
//					mSmackable = new SmackImpl(XXService.this);
//					if (mSmackable.login(account, password)) {
//						// 登陆成功
//						postConnectionScuessed();
//					} else {
//						// 登陆失败
//						postConnectionFailed(LOGIN_FAILED);
//					}
//				} catch (XXException e) {
//					String message = e.getLocalizedMessage();
//					// 登陆失败
//					if (e.getCause() != null)
//						message += "\n" + e.getCause().getLocalizedMessage();
//					postConnectionFailed(message);
//					L.i(XXService.class, "YaximXMPPException in doConnect():");
//					e.printStackTrace();
//				} finally {
//					if (mConnectingThread != null)
//						synchronized (mConnectingThread) {
//							mConnectingThread = null;
//						}
//				}
//			}
//
//		};
//		mConnectingThread.start();
	}

	// 退出
	public boolean logout() {
		// mIsNeedReConnection = false;// 手动退出就不需要重连闹钟了
//		boolean isLogout = false;
//		if (mConnectingThread != null) {
//			synchronized (mConnectingThread) {
//				try {
//					mConnectingThread.interrupt();
//					mConnectingThread.join(50);
//				} catch (InterruptedException e) {
//					L.e("doDisconnect: failed catching connecting thread");
//				} finally {
//					mConnectingThread = null;
//				}
//			}
//		}
//		if (mSmackable != null) {
//			isLogout = mSmackable.logout();
//			mSmackable = null;
//		}
//		connectionFailed(LOGOUT);// 手动退出


//		return isLogout;
        return  true;
	}



	/**
	 * 非UI线程连接失败反馈
	 * 
	 * @param reason
	 */
	public void postConnectionFailed(final String reason) {
		mMainHandler.post(new Runnable() {
            public void run() {
                connectionFailed(reason);
            }
        });
	}









	private void connectionFailed(String reason) {
//		L.i(XXService.class, "connectionFailed: " + reason);
//		mConnectedState = DISCONNECTED;// 更新当前连接状态
////		if (mSmackable != null)
////			mSmackable.setStatusOffline();// 将所有联系人标记为离线
//		if (TextUtils.equals(reason, LOGOUT)) {// 如果是手动退出
//			((AlarmManager) getSystemService(Context.ALARM_SERVICE))
//					.cancel(mPAlarmIntent);
//			return;
//		}
//		// 回调
//		if (mConnectionStatusCallback != null) {
//			mConnectionStatusCallback.connectionStatusChanged(mConnectedState,
//					reason);
//			if (mIsFirstLoginAction)// 如果是第一次登录,就算登录失败也不需要继续
//				return;
//		}
//
//		// 无网络连接时,直接返回
//		if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
//			((AlarmManager) getSystemService(Context.ALARM_SERVICE))
//					.cancel(mPAlarmIntent);
//			return;
//		}
//
//		String account = PreferenceUtils.getPrefString(XXService.this,
//				PreferenceConstants.ACCOUNT, "");
//		String password = PreferenceUtils.getPrefString(XXService.this,
//				PreferenceConstants.PASSWORD, "");
//		// 无保存的帐号密码时，也直接返回
//		if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
//			L.d("account = null || password = null");
//			return;
		}




}
