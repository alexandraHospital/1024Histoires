package menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.example.histoires.R;

public class TutoActivity extends Activity implements OnClickListener
{
	private ImageView	screenShot;
	private Button		precedent;
	private Button		suivant;
	private int			id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tuto);

		id = getIntent().getExtras().getInt("number");

		screenShot = (ImageView) findViewById(R.id.screen_tuto2);
		suivant = (Button) findViewById(R.id.suivant_button);
		precedent = (Button) findViewById(R.id.precedent_button);
		precedent.setVisibility(View.INVISIBLE);

		suivant.setOnClickListener(this);
		precedent.setOnClickListener(this);

		maj();
	}

	private void maj()
	{
		screenShot.setImageResource(this.getResources().getIdentifier("screen_" + id, "raw", R.class.getPackage().getName()));
	}

	@Override
	public void onClick(View v)
	{
		
		if (v == this.suivant)
		{
			id++;
			if (id == 15 || id == 56 || id == 103 || id == 151 || id == 202 || id == 276)
			{
				this.suivant.setText(getString(R.string.tuto_quit));
				this.suivant.setOnClickListener(new QuitListener(this));
				maj();
			}
			else
			{
				this.precedent.setVisibility(View.VISIBLE);
				maj();
			}
		}
		else if (v == this.precedent)
		{
			id--;
			if (id == 0 || id == 50 || id == 100 || id == 150 || id == 200 || id == 250)
			{
				this.precedent.setVisibility(View.INVISIBLE);
				maj();
			}
			else
			{
				this.suivant.setText(getString(R.string.tuto_suivant));
				this.suivant.setOnClickListener(this);
				
				maj();
			}
		}

	}

}
