package lire_livre;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import com.example.histoires.R;
import databasetest.Objet;

public class InventoryListener implements OnClickListener
{
	LireActivity 		ac;
	ArrayList<Objet> 	inventaireList;


	public InventoryListener(LireActivity ac, ArrayList<Objet> inventaireList)
	{
		this.ac 				= ac;
		this.inventaireList 	= inventaireList;
	}

	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ac);
		builder.setTitle(R.string.list_objet_titre);

		View view = View.inflate(ac, R.layout.alertdialog_my_items, null);
		builder.setView(view);

		ListView listObjet 			= (ListView) view.findViewById(R.id.my_items_list);		
		ObjectAdapter adapter 		= new ObjectAdapter(ac, R.layout.alertdialog_my_items, this.inventaireList);
		listObjet.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		builder.setPositiveButton(R.string.alert_return, null);

		listObjet.setOnItemClickListener(new RemoveItemClickListener(adapter, ac));

		builder.setCancelable(true);

		builder.create();
		builder.show();


	}

}
