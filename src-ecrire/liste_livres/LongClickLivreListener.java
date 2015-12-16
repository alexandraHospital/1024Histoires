package liste_livres;

import com.example.histoires.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

public class LongClickLivreListener implements OnItemLongClickListener
{
	private Activity	ac;

	public LongClickLivreListener(Activity ac)
	{
		this.ac = ac;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> av, View v, int position, long arg3)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(av.getContext());

		builder.setMessage(ac.getString(R.string.delete) + " ?");
		builder.setPositiveButton(R.string.yes, new YesDelListener(ac, av, position));
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id)
			{
				// User cancelled the dialog
			}
		});
		// Create the AlertDialog object and return it
		builder.create();
		builder.show();
		return false;
	}
}
