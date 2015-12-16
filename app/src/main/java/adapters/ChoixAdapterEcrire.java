package adapters;

import java.util.List;

import databasetest.Choix;
import databasetest.Objet;
import databasetest.Page;

import com.example.histoires.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ChoixAdapterEcrire extends BaseAdapter{
	// Attributs
	private List<Choix> choix;
	private LayoutInflater inflater;
	Context contexte;
	Activity ac;

	public ChoixAdapterEcrire(Context context, List<Choix> choix, Activity ac) {
		inflater = LayoutInflater.from(context);
		this.choix = choix;
		this.contexte = context;
		this.ac = ac;
	}

	@Override
	public int getCount() {

		return choix.size();
	}

	@Override
	public Object getItem(int position) {
		return choix.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder; 	// qui gardera en memoire les elements titre, auteur
		// et synopsis

		if (convertView == null) { 	// si la view n'existe pas, on la crée

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.choix_adapter_ecrire, null);	// on
			// charge
			// l'XML
			// en
			// memoire
			// pour
			// l'attibuer
			// a
			// notre
			// objet

			holder.supprimer = (Button) convertView.findViewById(R.id.supprimerChoix);
			holder.tvTexte = (TextView) convertView.findViewById(R.id.tvTexte);
			holder.nomChoix = (TextView) convertView.findViewById(R.id.tvBoutonChoix);
			holder.objetRequis = (TextView) convertView.findViewById(R.id.tvObjetRequis);

			convertView.setTag(holder);
		} 
		else 
			holder = (ViewHolder) convertView.getTag();

		//Le numero de la page cible
		holder.nomChoix.setText("Page "+Page.getPageByID((choix.get(position).getIdNextPage())).getNumero()+" : ");
		
		//Le texte du choix
		if(choix.get(position).getTexte().length()>13)
			holder.tvTexte.setText(choix.get(position).getTexte().subSequence(0, 10)+"...");
		else
			holder.tvTexte.setText(choix.get(position).getTexte());
		
		//L'objet requis par le choix
		//Si le choix ne requiert aucun objet
		if(choix.get(position).getObjetRequis()==0) 
			holder.objetRequis.setText(ac.getString(R.string.requiert)+" /");
		//Si le choix requiert un objet
		else {
			if(Objet.getObjetByID(choix.get(position).getObjetRequis()).getNom().length()>8)
				holder.objetRequis.setText(ac.getString(R.string.requiert)+Objet.getObjetByID(choix.get(position).getObjetRequis()).getNom().subSequence(0,5)+"...");
			else
				holder.objetRequis.setText(ac.getString(R.string.requiert)+Objet.getObjetByID(choix.get(position).getObjetRequis()).getNom());
		}

		//Listener pour supprimer un choix
		holder.supprimer.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				//Une boite de dialogue s'affiche lorsque l'on clique sur "supprimer"
				AlertDialog.Builder ad = new AlertDialog.Builder(contexte);
				ad.setMessage(R.string.supprimerChoix);

				//Si on clique sur 'oui', la boite de dialogue se ferme 
				//Et suppression est effectuee
				ad.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						//On supprime le choix de la base de donnees et de la liste
						choix.get(position).delete();
						choix.remove(choix.get(position));
						notifyDataSetChanged();

					}
				}

						);

				//Si on selectionne 'non' la boite de dialogue se ferme sans cons�quence
				ad.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				}
						);
				ad.show();
			}
		});

		return convertView;

	}

	private class ViewHolder{
		Button supprimer;
		TextView nomChoix;
		TextView tvTexte;
		TextView objetRequis;

	}
}

