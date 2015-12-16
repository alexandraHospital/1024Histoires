package lire_livre;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.example.histoires.R;
import databasetest.Objet;

public class RemoveItemClickListener implements OnItemClickListener, DialogInterface.OnClickListener
{
	private	LireActivity 		ac;
	private Objet				ob;
	private ObjectAdapter 		adapter;

	public RemoveItemClickListener(ObjectAdapter adapter, LireActivity ac)
	{
		this.ac				= ac;
		this.adapter		= adapter;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		this.adapter.remove(this.ob);
		ac.majInventaireIcon();
		ac.majChoix(ac.getPage());
	}


	@Override
	public void onItemClick(AdapterView<?> av, View v, int position, long arg3)
	{
		this.ob 		= ((Objet) av.getItemAtPosition(position));

		AlertDialog.Builder builder = new AlertDialog.Builder(ac);
		builder.setTitle(R.string.confirm_title);
		builder.setMessage(R.string.confirm_message);
		builder.setPositiveButton(R.string.yes, this);
		builder.setNegativeButton(R.string.no, null);

		builder.create();
		builder.show();
	}

}
