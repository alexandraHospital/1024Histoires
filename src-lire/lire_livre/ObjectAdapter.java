package lire_livre;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.histoires.R;
import databasetest.Objet;

public class ObjectAdapter extends ArrayAdapter<Objet>
{
	// declaring our ArrayList of items
	private ArrayList<Objet>	objects;
	private Context				context;

	public ObjectAdapter(Context context, int resource, ArrayList<Objet> objects)
	{
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.objet_adapter, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this
		 * method. The variable simply refers to the position of the current
		 * object in the list. (The ArrayAdapter iterates through the list we
		 * sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		Objet i = objects.get(position);

		if (i != null)
		{

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.

			ImageView icon = (ImageView) v.findViewById(R.id.item_picture);
			TextView nom = (TextView) v.findViewById(R.id.item_name);

			// check to see if each individual textview is null.
			// if not, assign some text!
			if (icon != null)
			{
				icon.setImageResource(context.getResources().getIdentifier(
						i.getImage(), "raw", R.class.getPackage().getName()));
			}
			if (nom != null)
			{
				nom.setText(i.getNom());
			}
		}

		// the view must be returned to our activity
		return v;

	}

	public ArrayList<Objet> getObjet()
	{
		return this.objects;
	}
}
