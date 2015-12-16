package adapters;

import java.util.List;

import databasetest.Choix;
import databasetest.Page;

import com.example.histoires.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PageNonSauvegardeesAdapter extends BaseAdapter{
	private List <Page> pages;
	private LayoutInflater inflater;
	public long numeroDernierePage=0;
	Context contexte;

	public PageNonSauvegardeesAdapter(Context context, List<Page> pages) {
		inflater = LayoutInflater.from(context);
		this.pages = pages;
		this.contexte = context;
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public Object getItem(int position) {
		return pages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void adapterResultats(List<Page> resultat){
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder2 holder; 

		if (convertView == null) { // si la view n'existe pas, on la crée

			holder = new ViewHolder2();
			convertView = inflater.inflate(R.layout.page_non_sauvegardee_adapter, null); // on
			// charge
			// l'XML
			// en
			// m�moire
			// pour
			// l'attibuer
			// �
			// notre
			// objet

			//On recupere le texte, le numero et le bouton supprimer de chaque choix
			holder.tvTexte2 = (TextView) convertView.findViewById(R.id.tvTexte2);
			holder.tvNumero2 = (TextView) convertView.findViewById(R.id.tvNumero2);
			holder.supprimer = (Button) convertView.findViewById(R.id.supprimerPage);

			convertView.setTag(holder);
		} 
		else 
			holder = (ViewHolder2) convertView.getTag();

		//On regle le numero de la derniere page pointee par un choix
		holder.numero=pages.get(position).getNumero();
		numeroDernierePage=(holder.numero);

		//On ajuste la premiere partie du texte affiche pour chaque choix
		if(pages.get(position).getTexte().length()>20)
			holder.tvTexte2.setText(pages.get(position).getTexte().subSequence(0, 17)+"...");
		else
			holder.tvTexte2.setText(pages.get(position).getTexte());
		
		holder.tvNumero2.setText("Page "+String.valueOf(holder.numero)+" : ");

		//Listener pour supprimer une page
		holder.supprimer.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				if(pages.size()>1){
				//Une boite de dialogue s'ouvre lorsqu'on appuie sur "supprimer"
				AlertDialog.Builder ad = new AlertDialog.Builder(contexte);
				ad.setMessage(R.string.supprimerPage);

				//Si on clique sur 'oui', la boite de dialogue se ferme 
				//Et suppression est effectuee
				ad.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						//On supprime tous les choix de la page
						List<Choix> listeDesChoix =Choix.getAll(pages.get(position));
						for(int i=0;i<listeDesChoix.size();i++) listeDesChoix.get(i).delete();

						//On supprime les choix qui pointent vers la page 
						Choix.deleteByPageCible(pages.get(position).getId());

						//On supprime la page
						pages.get(position).delete();
						pages.remove(pages.get(position));

						//On change le numero de la derniere page creee et on actualise la liste de pages
						long nouveauNumero=1; 
						for(int i=(position);i<pages.size();i++){
							nouveauNumero= pages.get(i).getNumero();
							pages.get(i).setNumero(nouveauNumero-1);
							pages.get(i).updateNumero(nouveauNumero-1);
						}
						numeroDernierePage=(nouveauNumero-1);
						notifyDataSetChanged();
					}
				}

						);

				//Si on selectionne 'non' la boite de dialogue se ferme sans consequence
				ad.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				}
						);
				ad.show();
				}
				else Toast.makeText(contexte, R.string.indiceChoix, Toast.LENGTH_SHORT).show();
			}});


		return convertView;
	}

	public long numeroDernierePage(){
		return numeroDernierePage;
	}

	//Pour memoriser les informations
	private class ViewHolder2 {
		long numero=0;
		TextView tvNumero2;
		TextView tvTexte2;
		Button supprimer;
	}

}

