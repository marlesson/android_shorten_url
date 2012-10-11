package com.shortener.app;

import java.io.IOException;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
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
	private RadioGroup 	rg_server;
	private EditText 	txt_url_result;
	private Dialog 		mDialog;
    private MyAsyncTask mAsyncTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_shortener 	= (Button) 	 findViewById(R.id.btn_shortener);
        txt_url 		= (EditText) findViewById(R.id.txt_url);
        btn_copy        = (Button)   findViewById(R.id.btn_copy);
        rg_server 		= (RadioGroup)   findViewById(R.id.rg_server);
        txt_url_result  = (EditText)   findViewById(R.id.txt_url_result);
        
        txt_url.setText("www.google.com.br");
        
        btn_shortener.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				txt_url_result.setText("");
				new MyAsyncTask().execute();
			}

		});
        
        btn_copy.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Uri uri = Uri.parse(txt_url_result.getText().toString());
        		Intent it = new Intent(Intent.ACTION_VIEW, uri);
        		MainActivity.this.startActivity(it);        		
//        		AlertDialog.Builder mensagem = new
//				AlertDialog.Builder(MainActivity.this);
//				mensagem.setTitle("Message");
//				mensagem.setMessage("Ctrl+C");
//				mensagem.setNeutralButton("OK", null);
//				mensagem.show();
			}
        });          
    }

    
    private String requestServer(int server) throws IOException{
    	String result = null;
    	
		switch (server) {
	    case R.id.r_migreme:
	    	result = Migreme.urlShort(txt_url.getText().toString());
	        break;
	    case R.id.r_tiny:
	    	result = "tiny";
	        break;
	    case R.id.r_bitly:
	    	result = "bitly";
	        break;
	    case R.id.r_googl:
	    	result = "Google";
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
