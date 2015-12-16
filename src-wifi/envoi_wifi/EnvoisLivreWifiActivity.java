package envoi_wifi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.example.histoires.R;


public class EnvoisLivreWifiActivity extends Activity implements OnClickListener
{
	private long	livreID;
	private EditText inputIP; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_envois_livre_wifi);

		livreID = getIntent().getExtras().getLong("LivreID");

		inputIP = (EditText) findViewById(R.id.ip_input);
		
		Button submitButton = (Button) findViewById(R.id.submit_ip);
		submitButton.setOnClickListener(this);
	}



	@Override
	public void onClick(View v)
	{
		if(!this.inputIP.getText().toString().equals(""))
		{
			EnvoisWifiAsyncTask sendThread = new EnvoisWifiAsyncTask(this, this.livreID, this.inputIP.getText().toString());
			sendThread.execute();
		}
		
	}
}
