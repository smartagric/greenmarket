package com.integra.dealcaller;



import static com.integra.dealcaller.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.integra.dealcaller.CommonUtilities.EXTRA_MESSAGE;
import static com.integra.dealcaller.CommonUtilities.SENDER_ID;

import java.io.PrintWriter;
import java.io.StringWriter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
 
import com.google.android.gcm.GCMRegistrar;
import com.integra.dealcaller.R;
 
public class MyExceptionHandler implements
java.lang.Thread.UncaughtExceptionHandler {
private final Context myContext;
private final Class<?> myActivityClass;

public MyExceptionHandler(Context context, Class<?> c) {

myContext = context;
myActivityClass = c;
}

public void uncaughtException(Thread thread, Throwable exception) {

StringWriter stackTrace = new StringWriter();
//exception.printStackTrace(new PrintWriter(stackTrace));
//System.err.println(stackTrace);// You can use LogCat too
//Intent intent = new Intent(myContext, myActivityClass);
String s = stackTrace.toString();
Toast.makeText(myContext, s, Toast.LENGTH_LONG).show();
//you can use this String to know what caused the exception and in which Activity
//intent.putExtra("uncaughtException",
//        "Exception is: " + stackTrace.toString());
//intent.putExtra("stacktrace", s);
//myContext.startActivity(intent);
//for restarting the Activity
Process.killProcess(Process.myPid());
System.exit(0);
}
}