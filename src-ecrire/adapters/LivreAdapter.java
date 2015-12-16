package adapters;

import java.util.ArrayList;
import java.util.List;
import com.example.histoires.R;
import databasetest.Livre;
import databasetest.Page;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LivreAdapter extends BaseAdapter
{
	// Attributs
	private List<Livre> bibliotheque;
	private LayoutInflater inflater;

	public LivreAdapter(Context context, List<Livre> bibliotheque, String requete, boolean checktitre, boolean checkauthor, boolean checksynopsis, boolean checkpage) 
	{
		inflater 			= LayoutInflater.from(context);
		this.bibliotheque 	= new ArrayList<Livre>();

		for(Livre lv : bibliotheque)
		{
			// Cas o� l'on � un filtre appliqu� � l'ensemble des livres

			/*
			Log.d("debug", ""+requete);
			Log.d("debug", ""+checktitre);
			Log.d("debug", ""+checkauthor);
			Log.d("debug", ""+checksynopsis);
			 */
			if(!requete.equals("") && (checktitre || checkauthor || checksynopsis || checkpage))
			{
				if(checktitre)
				{
					// On ajoute en haut de liste les Livre contenant la requete dans le titre
					if(lv.getTitre().toLowerCase().contains(requete.toLowerCase()))
					{
						this.bibliotheque.add(0, lv);
					}
				}

				if(checkauthor)
				{
					if(lv.getAuteur().toLowerCase().contains(requete.toLowerCase()) && !this.bibliotheque.contains(lv))
					{
						this.bibliotheque.add(0, lv);
					}
				}

				if(checksynopsis)
				{
					if(lv.getSynopsis().toLowerCase().contains(requete.toLowerCase()) && !this.bibliotheque.contains(lv))
					{
						this.bibliotheque.add(0, lv);
					}
				}

				if(checkpage)
				{
					List<Page> pages = Page.getAll(lv);
					for(Page page : pages)
					{
						if(page.getTexte().toLowerCase().contains(requete.toLowerCase()) && !this.bibliotheque.contains(lv))
						{
							this.bibliotheque.add(0, lv);
							break;
						}
					}
				}

			}
			// Cas o� l'on � pas de filtre appliqu� � l'ensemble des livres
			else
			{
				this.bibliotheque.add(lv);
			}
		}

	}

	@Override
	public int getCount() 
	{
		return bibliotheque.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return bibliotheque.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder; // qui gardera en m�moire les �l�ments titre, auteur
		// et synopsis

		if (convertView == null)
		{ // si la view n'existe pas, on la cr�e

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.livre_adapter, null); // on
			// charge
			// l'XML
			// en
			// m�moire
			// pour
			// l'attibuer
			// �
			// notre
			// objet

			holder.tvTitre = (TextView) convertView.findViewById(R.id.tvTitre);
			holder.tvAuteur = (TextView) convertView
					.findViewById(R.id.tvAuteur);

			convertView.setTag(holder);
		} 
		else 
			holder = (ViewHolder) convertView.getTag();

		holder.tvTitre.setText(bibliotheque.get(position).getTitre());
		holder.tvAuteur.setText(bibliotheque.get(position).getAuteur());
		return convertView;

	}

	private class ViewHolder
	{
		TextView tvTitre;
		TextView tvAuteur;
	}

}
