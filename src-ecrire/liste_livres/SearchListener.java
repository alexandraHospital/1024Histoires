package liste_livres;

import android.app.Activity;
import android.content.Intent;
import android.widget.SearchView.OnQueryTextListener;

public class SearchListener implements OnQueryTextListener
{
	Activity ac;
	
	public SearchListener(Activity ac)
	{
		this.ac = ac;
	}
	
	@Override
	public boolean onQueryTextChange(String newText)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		Intent intent = new Intent(ac, ListLivresActivity.class);
		intent.putExtra("requete", query);
		ac.startActivity(intent);
		ac.finish();
		return false;
	}

}
