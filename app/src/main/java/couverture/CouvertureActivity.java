package couverture;

import TextTools.TextViewEx;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.histoires.R;
import databasetest.DBManager;
import databasetest.Livre;
import databasetest.Page;

public class CouvertureActivity extends Activity implements OnClickListener
{
	long	livreID;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_couverture);

		// Crée la base de données et ses tables :
		DBManager.setContext(getApplicationContext());

		// Récupération du livre courant
		livreID = getIntent().getExtras().getLong("LivreID");
		Livre livre = Livre.getLivreByID(livreID);

		// Mise en relation avec le XML
		TextView titre = (TextView) findViewById(R.id.title);
		titre.setText(livre.getTitre());

		TextView auteur = (TextView) findViewById(R.id.auteur);
		auteur.setText(livre.getAuteur());

		TextView nbPages = (TextView) findViewById(R.id.nb_pages);
		nbPages.setText(getString(R.string.page_count) + " : " + Page.getAll(livre).size());

		TextViewEx synopsis = (TextViewEx) findViewById(R.id.synopsis);
		synopsis.setText(livre.getSynopsis(), true);

		// page de couverture : image
		ImageView couverture = (ImageView) findViewById(R.id.imange_couverture);

		if (livre.getImage() != null && !livre.getImage().equals(""))
		{

			Bitmap image = BitmapFactory.decodeFile(livre.getImage());

			if (image != null)
			{
				couverture.setImageBitmap(image);
			}
		}
		else
		{
			couverture.setVisibility(View.GONE);
		}

		// Création d'un bouton pour acceder à la première page
		ImageButton boutonEnvoyer = (ImageButton) findViewById(R.id.send_button);
		boutonEnvoyer.setOnClickListener(this);

		// Cas où notre téléphone ne possederais pas de materiel bluetooth
		if (BluetoothAdapter.getDefaultAdapter() == null)
		{
			boutonEnvoyer.setVisibility(View.GONE);
		}

		// Création d'un bouton pour acceder à la première page
		Button boutonLire = (Button) findViewById(R.id.lire);

		if (Page.getAll(livre).size() > 0)
		{
			boutonLire.setOnClickListener(new LireListener(this, 1, livreID));
		}

		// On affiche le boutton reprendre suelement si le marque page est
		// different de zero
		Button boutonReprendre = (Button) findViewById(R.id.reprendre);
		if (livre.getMarquePage() != 0)
		{
			boutonReprendre.setOnClickListener(new LireListener(this, livre.getMarquePage(), livreID));
		}
		else
		{
			boutonReprendre.setVisibility(View.INVISIBLE);
			boutonReprendre.setEnabled(false);
		}

	}

	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.sendby);

		View view = View.inflate(this, R.layout.alertdialog_wireless_send, null);

		LinearLayout layout = (LinearLayout) view.findViewById(R.id.alertdialog_wireless);
		layout.getChildAt(0).setOnClickListener(new BluetoothSendListener(this, this.livreID));
		layout.getChildAt(1).setOnClickListener(new WifiSendListener(this, this.livreID));

		builder.setView(view);
		builder.create();
		builder.show();

		/*
		 * Intent intent = new Intent(this, EnvoisLivreActivity.class);
		 * intent.putExtra("LivreID", this.livreID); this.startActivity(intent);
		 */
	}

}
