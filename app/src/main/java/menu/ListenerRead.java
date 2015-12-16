package menu;

import lire_liste_livres.ListLivresActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class ListenerRead implements OnClickListener
{
	Activity ac;
	
	public ListenerRead(Activity ac)
	{
		this.ac = ac;
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(this.ac, ListLivresActivity.class);
		intent.putExtra("motscle", "");
		intent.putExtra("checktitre", false);
		intent.putExtra("checkauthor", false);
		intent.putExtra("checksynopsis", false);
		intent.putExtra("checkpage", false);
		ac.startActivity(intent);
	}

}
