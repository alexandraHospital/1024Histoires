package Listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ParcourirListener implements OnClickListener
{
	Activity ac;
	
	public ParcourirListener(Activity ac)
	{
		this.ac = ac;
	}
	
	
	@Override
	public void onClick(View v)
	{
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				 
		ac.startActivityForResult(i, 0);
	}

}
