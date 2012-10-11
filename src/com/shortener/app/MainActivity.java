package com.shortener.app;

import java.io.IOException;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.shortener.url.*;

public class MainActivity extends Activity {

	private Button 		btn_shortener;
	private EditText 	txt_url;
	private Button 		btn_copy;
	private Button 		btn_openbrowser;
	private RadioGroup 	rg_server;
	private EditText 	txt_url_result;
	private Dialog 		mDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_shortener 	= (Button) 	 findViewById(R.id.btn_shortener);
        txt_url 		= (EditText) findViewById(R.id.txt_url);
        btn_copy        = (Button)   findViewById(R.id.btn_copy);
        btn_openbrowser = (Button)   findViewById(R.id.btn_openbrowser);
        rg_server 		= (RadioGroup)   findViewById(R.id.rg_server);
        txt_url_result  = (EditText)   findViewById(R.id.txt_url_result);
        
        txt_url.setText("www.google.com.br");
        
        btn_shortener.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				txt_url_result.setText("");
				new MyAsyncTask().execute();
			}
		});
        
        btn_openbrowser.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Uri uri = Uri.parse(txt_url_result.getText().toString());
        		Intent it = new Intent(Intent.ACTION_VIEW, uri);
        		MainActivity.this.startActivity(it);        		
			}
        });          
        
        btn_copy.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        			
			}
        });        
    }

    
    @TargetApi(9)
	private String requestServer(int server) throws IOException{
    	String result = null;
    	String url    = txt_url.getText().toString();
    	
		if(url.isEmpty()){
			return "";
		}
    	
		switch (server) {
	    case R.id.r_migreme:
	    	result = Migreme.urlShort(url);
	        break;
	    case R.id.r_tiny:
	    	result = TinyUrl.urlShort(url);
	        break;
	    case R.id.r_bitly:
	    	result = Bitly.urlShort(url);
	        break;
		}		
		
		return result;
    }
    
    private void showErrorMessage(String errorMessage){
		AlertDialog.Builder mensagem = new
		AlertDialog.Builder(MainActivity.this);
		mensagem.setTitle("Erro");
		mensagem.setMessage(errorMessage);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();    	
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
    	 
        protected void onPreExecute() {
            mDialog = ProgressDialog.show(MainActivity.this, "", "Processando...", false, false);
        }
        
        protected String doInBackground(String... arg) {
        	String result = null;
			try {
				result = requestServer(rg_server.getCheckedRadioButtonId());
			} catch (IOException e) {
				showErrorMessage("Erro: "+e.getMessage());
			}
			return result;
         }
        
        protected void onPostExecute(String result) {
            mDialog.dismiss();
            txt_url_result.setText(result);
        }
    }  
}
