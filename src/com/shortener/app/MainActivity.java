package com.shortener.app;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn_shortener 	= (Button) 	 findViewById(R.id.btn_shortener);
        txt_url 		= (EditText) findViewById(R.id.txt_url);
        btn_copy        = (Button)   findViewById(R.id.btn_copy);
        rg_server 		= (RadioGroup)   findViewById(R.id.rg_server);
        txt_url_result  = (EditText)   findViewById(R.id.txt_url_result);
        
        
        txt_url.setText("http://www.google.com.br");
        
        btn_shortener.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				switch (rg_server.getCheckedRadioButtonId()) {
			    case R.id.r_migreme:
					try {
						txt_url_result.setText(Migreme.urlShort(txt_url.getText().toString()));
					} catch (IOException e) {
						showErrorMessage("Error na comunicação: ".concat(e.getMessage()));
						e.printStackTrace();
					}			       
			        break;
			    case R.id.r_tiny:
			    	txt_url_result.setText("tiny");
			        break;
			    case R.id.r_bitly:
			    	txt_url_result.setText("bitly");
			        break;
			    case R.id.r_googl:
			    	txt_url_result.setText("Google");
			        break;
			}				

			}

		});
        
        btn_copy.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		AlertDialog.Builder mensagem = new
				AlertDialog.Builder(MainActivity.this);
				mensagem.setTitle("Message");
				mensagem.setMessage("Ctrl+C");
				mensagem.setNeutralButton("OK", null);
				mensagem.show();
			}
        });          
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    public void showErrorMessage(String errorMessage){
		AlertDialog.Builder mensagem = new
		AlertDialog.Builder(MainActivity.this);
		mensagem.setTitle("Erro");
		mensagem.setMessage("errorMessage");
		mensagem.setNeutralButton("OK", null);
		mensagem.show();    	
    }
}
