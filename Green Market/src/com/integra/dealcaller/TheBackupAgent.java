package com.integra.dealcaller;




import static com.integra.dealcaller.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.integra.dealcaller.CommonUtilities.EXTRA_MESSAGE;
import static com.integra.dealcaller.CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
 
import com.google.android.gcm.GCMRegistrar;
import com.integra.dealcaller.R;
 



public class TheBackupAgent extends BackupAgentHelper {
    // The names of the SharedPreferences groups that the application maintains.  These
    // are the same strings that are passed to getSharedPreferences(String, int).
    static final String PREF_TO_SAVE = "chat";
    

    // An arbitrary string used within the BackupAgentHelper implementation to
    // identify the SharedPreferencesBackupHelper's data.
    static final String MY_PREFS_BACKUP_KEY = "pref_backup_key";

    // Simply allocate a helper and install it
    public void onCreate() {
        SharedPreferencesBackupHelper helper =new SharedPreferencesBackupHelper(this, PREF_TO_SAVE);
        addHelper(MY_PREFS_BACKUP_KEY, helper);
    }

}
