package activity;

import menu.MainActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import com.example.histoires.R;

public class ParametreActivity extends Activity implements OnCheckedChangeListener, OnClickListener
{
	private Switch		swSon;
	private Switch		swLettrine;
	private EditText	defautNameAuthor;
	private RadioButton	radioPetit;
	private RadioButton	radioMoyen;
	private RadioButton	radioGrand;
	private Button		save;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_parametre);

		initSon();
		initLettrine();
		initTaille();

		defautNameAuthor = (EditText) findViewById(R.id.input_defaut_author_name);
		defautNameAuthor.setText(MainActivity.param.getAuteurDefaut());

		save = (Button) findViewById(R.id.button_save_param);
		save.setOnClickListener(this);
	}

	private void initTaille()
	{
		this.radioPetit = (RadioButton) findViewById(R.id.radio_petit);
		this.radioMoyen = (RadioButton) findViewById(R.id.radio_moyen);
		this.radioGrand = (RadioButton) findViewById(R.id.radio_grand);

		this.radioPetit.setChecked(false);
		this.radioMoyen.setChecked(false);
		this.radioGrand.setChecked(false);

		switch (MainActivity.param.getTaille())
		{
			case 15:
				this.radioPetit.setChecked(true);
			break;
			case 20:
				this.radioMoyen.setChecked(true);
			break;
			case 25:
				this.radioGrand.setChecked(true);
			break;
		}

		this.radioPetit.setOnClickListener(this);
		this.radioMoyen.setOnClickListener(this);
		this.radioGrand.setOnClickListener(this);
	}

	private void initLettrine()
	{
		swLettrine = (Switch) findViewById(R.id.switch_lettrine);
		swLettrine.setOnCheckedChangeListener(this);
		swLettrine.setTextOn(getString(R.string.yes));
		swLettrine.setTextOff(getString(R.string.no));

		if (MainActivity.param.getLettrine() == 1)
		{
			swLettrine.setChecked(true);
		}
		else
		{
			swLettrine.setChecked(false);
		}
	}

	private void initSon()
	{
		swSon = (Switch) findViewById(R.id.son_switch);
		swSon.setOnCheckedChangeListener(this);
		swSon.setTextOn(getString(R.string.yes));
		swSon.setTextOff(getString(R.string.no));

		if (MainActivity.param.getSon() == 1)
		{
			swSon.setChecked(true);

		}
		else
		{
			swSon.setChecked(false);

		}
	}

	@Override
	public void onBackPressed()
	{
		MainActivity.param.updateAuteurDefaut(this.defautNameAuthor.getText().toString());
		this.finish();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		if (buttonView == this.swSon)
		{
			if (isChecked)
			{
				MainActivity.param.updateSon(1);
			}
			else
			{
				MainActivity.param.updateSon(0);
			}
		}
		else if (buttonView == this.swLettrine)
		{
			if (isChecked)
			{
				MainActivity.param.updateLettrine(1);
			}
			else
			{
				MainActivity.param.updateLettrine(0);
			}
		}

		// this.soundState = !isChecked;
	}

	@Override
	public void onClick(View v)
	{
		if (v == this.radioPetit || v == this.radioMoyen || v == this.radioGrand)
		{
			this.radioPetit.setChecked(false);
			this.radioMoyen.setChecked(false);
			this.radioGrand.setChecked(false);

			if (v == this.radioPetit)
			{
				MainActivity.param.updateTaille(15);
				this.radioPetit.setChecked(true);
			}
			else if (v == this.radioMoyen)
			{
				MainActivity.param.updateTaille(20);
				this.radioMoyen.setChecked(true);
			}
			else if (v == this.radioGrand)
			{
				MainActivity.param.updateTaille(25);
				this.radioGrand.setChecked(true);
			}
		}
		else if (v == save)
		{
			MainActivity.param.updateAuteurDefaut(this.defautNameAuthor.getText().toString());
			this.finish();
		}

	}

}
