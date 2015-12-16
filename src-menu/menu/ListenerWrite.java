package menu;

import liste_livres.ListLivresActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ListenerWrite implements OnClickListener
{
	Activity ac;
	
	public ListenerWrite(Activity ac)
	{
		this.ac = ac;
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(this.ac, ListLivresActivity.class);
		intent.putExtra("requete", "");
		ac.startActivity(intent);
	}

}
