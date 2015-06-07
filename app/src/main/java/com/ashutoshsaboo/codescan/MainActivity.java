package com.ashutoshsaboo.codescan;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.ashutoshsaboo.codescan.R;


public class MainActivity extends ActionBarActivity {
    private String codeFormat,codeContent;
    private TextView formatTxt, contentTxt;
    //private EditText editTextInput;
    private String editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        //editTextInput = (EditText) findViewById(R.id.editTextInput);
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

    /**
     * event handler for scan button
     * @param view view of the activity
     */

    public void scanNow(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(String.valueOf(R.string.scan_bar_code));
        integrator.setResultDisplayDuration(0);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
        //onSearchClick(codeContent);
    }

    public void scanQR(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(String.valueOf(R.string.scan_bar_code));
        integrator.setResultDisplayDuration(0);
        //integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
        //onSearchClick(codeContent);
    }


    /**
     * function handle scan result
     * @param requestCode scanned code
     * @param resultCode  result of scanned code
     * @param intent intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            codeContent = scanningResult.getContents();
            codeFormat = scanningResult.getFormatName();
            editTextInput=codeContent;
            onSearchClick(editTextInput);

            // display it on screen
            formatTxt.setText("FORMAT: " + codeFormat);
            contentTxt.setText("CONTENT: " + codeContent);

            //editTextInput=null;



        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public void onSearchClick(String query)
    {
        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            //String term = editTextInput.getText().toString();
            intent.putExtra(SearchManager.QUERY, query);
            startActivity(intent);
        } catch (Exception e) {
// TODO: handle exception
        }

        /*String url="https://www.google.com/search?q=";
        String query = editTextInput.getText().toString(); //search query
        url=url+query;
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url);*/

    }
}
