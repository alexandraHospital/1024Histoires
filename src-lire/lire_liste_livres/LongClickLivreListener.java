package lire_liste_livres;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import com.example.histoires.R;

public class LongClickLivreListener implements OnItemLongClickListener
{
	private Activity		ac;

	public LongClickLivreListener(Activity ac)
	{
		this.ac 		= ac;
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> av, View v, int position, long arg3)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(av.getContext());

		builder.setMessage(ac.getString(R.string.delete)+" ?");
		builder.setPositiveButton(R.string.yes, new YesDelListener(ac, av, position));
		builder.setNegativeButton(R.string.no, null);
		
		builder.create();
		builder.show();
		return false;
	}
}
