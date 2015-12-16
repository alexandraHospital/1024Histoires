package menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.histoires.R;

public class MenuTutoActivity extends Activity implements OnClickListener
{
	private Button	tutoRead;
	private Button	tutoWrite;
	private Button	tutoSendWifi;
	private Button	tutoSendBluetooth;
	private Button	tutoReceiveWifi;
	private Button	tutoReceiveBluetooth;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_tuto);

		this.tutoRead 			= (Button) findViewById(R.id.button_tuto_lire);
		this.tutoWrite 			= (Button) findViewById(R.id.button_tuto_ecrire);
		this.tutoSendWifi 		= (Button) findViewById(R.id.button_tuto_sendwifi);
		this.tutoSendBluetooth 	= (Button) findViewById(R.id.button_tuto_sendbluetooth);
		this.tutoReceiveWifi 		= (Button) findViewById(R.id.button_tuto_receivebwifi);
		this.tutoReceiveBluetooth 	= (Button) findViewById(R.id.button_tuto_receivebluetooth);
		
		tutoRead.setOnClickListener(this);
		tutoWrite.setOnClickListener(this);
		tutoSendWifi.setOnClickListener(this);
		tutoSendBluetooth.setOnClickListener(this);
		tutoReceiveWifi.setOnClickListener(this);
		tutoReceiveBluetooth.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(this, TutoActivity.class);

		int number = 0;
		
		if (v == this.tutoRead)
		{
			number = 0;
		}
		else if (v == this.tutoWrite)
		{
			number = 250;
		}
		else if (v == this.tutoSendWifi)
		{
			number = 50;
		}
		else if (v == this.tutoSendBluetooth)
		{
			number = 100;
		}
		else if (v == this.tutoReceiveWifi)
		{
			number = 150;
		}
		else if (v == this.tutoReceiveBluetooth)
		{
			number = 200;
		}

		intent.putExtra("number", number);

		this.startActivity(intent);
	}

}
