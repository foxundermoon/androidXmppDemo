package com.swgis.android.xmpp.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.swgis.android.xmpp.MyApplication;
import com.swgis.android.xmpp.R;
import com.swgis.android.xmpp.client.Constants;
import com.swgis.android.xmpp.client.ServiceManager;
import com.swgis.android.xmpp.client.receiver.XmppStatusReceiver;

public class LoginActivity extends ActionBarActivity implements XmppStatusReceiver.StatusHandler {

    private XmppStatusReceiver xmppStatusReceiver;
    private ServiceManager serviceManager;
    Handler handler;

    @Override
    protected void onStop() {
        unregisterXmppReceiver();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerXmppReceiver();
    }

    private void registerXmppReceiver() {
        this.xmppStatusReceiver = new XmppStatusReceiver(this);
        XmppStatusReceiver xmppStatusReceiver = this.xmppStatusReceiver;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_XMPP_STATUS);
        registerReceiver(xmppStatusReceiver, intentFilter);
    }

    private void unregisterXmppReceiver() {
        unregisterReceiver(xmppStatusReceiver);
    }

    private LoginHolder holder;
    private View.OnClickListener loginListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        holder = new LoginHolder();
        loginListener = new LoginListener();
        holder.loginBtn = (Button) findViewById(R.id.loginSubmitBtn);
        holder.cancelBtn = (Button) findViewById(R.id.loginCancelBtn);
        holder.regTextView = (TextView) findViewById(R.id.textViewRegLink);
        holder.username = (EditText) findViewById(R.id.editUsername);
        holder.passwd = (EditText) findViewById(R.id.editPasswd);
        holder.registeBtnListener();
        handler = new Handler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    @Override
    public void handle(Intent intent) {
        tips(intent.getIntExtra(Constants.XMPP_STATUS, 0) + "");
        holder.username.setText("error code:" + intent.getIntExtra(Constants.XMPP_STATUS, 0));
        holder.loginBtn.setEnabled(true);
    }

    @Override
    public void onLogin(boolean success) {
        if (success) {
            MyApplication.getInstance().isLogin = true;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tips("login success");
                    redirectToMainActivity();
                }
            });
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tips("login failed");
                    holder.loginBtn.setEnabled(true);
                }
            });
        }
    }

    private void redirectToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onXmppError(Intent intent) {

    }

    class LoginHolder {
        public Button loginBtn;
        public Button cancelBtn;
        public EditText username;
        public EditText passwd;
        public TextView regTextView;

        public void registeBtnListener() {
            loginBtn.setOnClickListener(loginListener);
            cancelBtn.setOnClickListener(loginListener);
            regTextView.setOnClickListener(loginListener);

        }
    }

    class LoginListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == holder.loginBtn) login();
            if (v == holder.cancelBtn) loginCancel();
            if (v == holder.regTextView) redirectRegister();
        }

        private void redirectRegister() {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }

        private void loginCancel() {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        private void login() {
            String name = holder.username.getText().toString();
            String pasd = holder.passwd.getText().toString();
            if (name == null || name == "") {
                tips("name can't empty!");
            } else if (pasd == null || pasd == "") {
                tips("password can't empty!");
            } else {
                try {
                    Integer nuser = Integer.getInteger(name);
                    LoginActivity.this.login(name + "", pasd);
                } catch (Exception fe) {
                    tips("user name format error,number please!");
                }
            }
        }
    }

    private void login(String s, String pasd) {
        holder.loginBtn.setEnabled(false);

        if (serviceManager == null) {
            serviceManager = ServiceManager.getInstance(this);
            if (getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, MODE_PRIVATE)
                    .edit()
                    .putString(Constants.XMPP_USERNAME, s)
                    .putString(Constants.XMPP_PASSWORD, pasd)
                    .commit()) {
                tips("loging.....");
                serviceManager.startService();
            } else {
                tips("sharedPreferences error");
            }
        }
    }

    private void tips(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
