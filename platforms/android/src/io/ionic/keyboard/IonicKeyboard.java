package io.ionic.keyboard;

        import org.apache.cordova.CallbackContext;
        import org.apache.cordova.CordovaInterface;
        import org.apache.cordova.CordovaPlugin;
        import org.apache.cordova.CordovaWebView;
        import org.apache.cordova.LOG;
        import org.apache.cordova.PluginResult;
        import org.apache.cordova.PluginResult.Status;
        import org.json.JSONArray;
        import org.json.JSONException;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.graphics.Rect;
        import android.util.DisplayMetrics;
        import android.view.View;
        import android.view.ViewTreeObserver.OnGlobalLayoutListener;
        import android.view.inputmethod.InputMethodManager;

// import additionally required classes for calculating screen height
        import android.view.Display;
        import android.graphics.Point;
        import android.os.Build;
        import android.widget.Toast;

public class IonicKeyboard extends CordovaPlugin {

    private BroadcastReceiver receiver;
    private BroadcastReceiver receiver2;
    private BroadcastReceiver receiver3;
    private BroadcastReceiver receiver4;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        final CordovaWebView appView = webView;

        IntentFilter filter = new IntentFilter("android.intent.action.SCANRESULT");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action
                final String scanResult = intent.getStringExtra("value");
                LOG.e("DEBUG", "android.intent.action.SCANRESULT: " + scanResult);
                appView.sendJavascript("cordova.fireWindowEvent('native.onscanbarcode', { 'scanResult':'" + scanResult + "'});");
            }
        };
        cordova.getActivity().registerReceiver(receiver, filter);

        IntentFilter filter2 = new IntentFilter("scan.rcv.message");
        receiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String scanResult;
                byte[] barocode = intent.getByteArrayExtra("barocode");
                int barocodelen = intent.getIntExtra("length", 0);
                byte temp = intent.getByteExtra("barcodeType", (byte) 0);
                android.util.Log.i("debug", "----codetype--" + temp);
                scanResult = new String(barocode, 0, barocodelen);
//              final String scanResult = intent.getStringExtra("value");
                LOG.e("DEBUG", "scan.rcv.message: " + scanResult);
                appView.sendJavascript("cordova.fireWindowEvent('native.onscanbarcode', { 'scanResult':'" + scanResult + "'});");
            }
        };
        cordova.getActivity().registerReceiver(receiver2, filter2);

        IntentFilter filter3 = new IntentFilter("com.zkc.scancode");
        receiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action
                final String scanResult = intent.getStringExtra("code");
                LOG.e("DEBUG", "com.zkc.scancode: " + scanResult);
                appView.sendJavascript("cordova.fireWindowEvent('native.onscanbarcode', { 'scanResult':'" + scanResult + "'});");
            }
        };
        cordova.getActivity().registerReceiver(receiver3, filter3);

        IntentFilter filter4 = new IntentFilter("com.jb.action.GET_SCANDATA");
        receiver4 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action
                final String scanResult = intent.getStringExtra("data");
                LOG.e("DEBUG", "com.jb.action.GET_SCANDATA: " + scanResult);
                appView.sendJavascript("cordova.fireWindowEvent('native.onscanbarcode', { 'scanResult':'" + scanResult + "'});");
            }
        };
        cordova.getActivity().registerReceiver(receiver4, filter4);

        Toast.makeText(cordova.getActivity().getApplicationContext(), "Ready to Scan", Toast.LENGTH_LONG).show();
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if ("close".equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    //http://stackoverflow.com/a/7696791/1091751
                    InputMethodManager inputManager = (InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    View v = cordova.getActivity().getCurrentFocus();

                    if (v == null) {
                        callbackContext.error("No current focus");
                    } else {
                        inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        callbackContext.success(); // Thread-safe.
                    }
                }
            });
            return true;
        }
        if ("show".equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    ((InputMethodManager) cordova.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    callbackContext.success(); // Thread-safe.
                }
            });
            return true;
        }
        if ("init".equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    //calculate density-independent pixels (dp)
                    //http://developer.android.com/guide/practices/screens_support.html
                    DisplayMetrics dm = new DisplayMetrics();
                    cordova.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                    final float density = dm.density;

                    //http://stackoverflow.com/a/4737265/1091751 detect if keyboard is showing
                    final View rootView = cordova.getActivity().getWindow().getDecorView().findViewById(android.R.id.content).getRootView();
                    OnGlobalLayoutListener list = new OnGlobalLayoutListener() {
                        int previousHeightDiff = 0;
                        @Override
                        public void onGlobalLayout() {
                            Rect r = new Rect();
                            //r will be populated with the coordinates of your view that area still visible.
                            rootView.getWindowVisibleDisplayFrame(r);

                            PluginResult result;

                            // cache properties for later use
                            int rootViewHeight = rootView.getRootView().getHeight();
                            int resultBottom = r.bottom;

                            // calculate screen height differently for android versions >= 21: Lollipop 5.x, Marshmallow 6.x
                            //http://stackoverflow.com/a/29257533/3642890 beware of nexus 5
                            int screenHeight;

                            if (Build.VERSION.SDK_INT >= 21) {
                                Display display = cordova.getActivity().getWindowManager().getDefaultDisplay();
                                Point size = new Point();
                                display.getSize(size);
                                screenHeight = size.y;
                            } else {
                                screenHeight = rootViewHeight;
                            }

                            int heightDiff = screenHeight - resultBottom;

                            int pixelHeightDiff = (int)(heightDiff / density);
                            if (pixelHeightDiff > 100 && pixelHeightDiff != previousHeightDiff) { // if more than 100 pixels, its probably a keyboard...
                                String msg = "S" + Integer.toString(pixelHeightDiff);
                                result = new PluginResult(PluginResult.Status.OK, msg);
                                result.setKeepCallback(true);
                                callbackContext.sendPluginResult(result);
                            }
                            else if ( pixelHeightDiff != previousHeightDiff && ( previousHeightDiff - pixelHeightDiff ) > 100 ){
                                String msg = "H";
                                result = new PluginResult(PluginResult.Status.OK, msg);
                                result.setKeepCallback(true);
                                callbackContext.sendPluginResult(result);
                            }
                            previousHeightDiff = pixelHeightDiff;
                        }
                    };

                    rootView.getViewTreeObserver().addOnGlobalLayoutListener(list);


                    PluginResult dataResult = new PluginResult(PluginResult.Status.OK);
                    dataResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(dataResult);
                }
            });
            return true;
        }
        return false;  // Returning false results in a "MethodNotFound" error.
    }


}
