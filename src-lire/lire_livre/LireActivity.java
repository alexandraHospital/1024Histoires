package lire_livre;

import java.util.ArrayList;
import java.util.List;
import menu.MainActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.histoires.R;
import databasetest.Choix;
import databasetest.Livre;
import databasetest.Objet;
import databasetest.Page;

public class LireActivity extends Activity implements OnClickListener
{
	private Livre				livre;
	private Page				page;
	private ImageButton			marquePage;
	private ImageButton			inventaireButton;
	private boolean				marquePageEnable	= false;
	private ArrayList<Objet>	inventaireList;
	private boolean				onResume;

	private final int			maxBag				= 6;

	@Override
	/**
	 * A la création de cette Activité on recupère l'ID du livre que l'on veux feuilleter, on effectue alors une maj() avec la première page de ce livre
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_lire);

		// Recuperation de l'ID de la première page
		// long pageID = getIntent().getExtras().getLong("PageNumero");

		// Recuperation du livre id
		long livreID = getIntent().getExtras().getLong("LivreID");
		long pageNum = getIntent().getExtras().getLong("pageID");

		if(livreID == 0 || pageNum == 0)
		{
			this.finish();
		}
		
		// Détermination de la page
		page = Page.getPageByNumero(livreID, pageNum);
		livre = Livre.getLivreByID(livreID);

		// Raz du MP
		livre.updateMarquePage(0);
		
		this.inventaireList = new ArrayList<Objet>();

		// Test duméro de la page = numéro de la première age du livre reviens à
		// tester si on à clicker sur "lire" ou "reprendre"
		if (livre.getIdFirstPage() != page.getNumero())
		{
			// Initialisation de l'inventaire
			this.inventaireList = (ArrayList<Objet>) livre.getInventaire();
		}

		if (page.getNumero() == livre.getMarquePage())
		{
			this.onResume = true;
			this.livre.updateMarquePage(0);
		}
		else
		{
			this.onResume = false;
		}

		// Code relative au marque page
		marquePage = (ImageButton) findViewById(R.id.marquePage);
		marquePage.setOnClickListener(this);

		maj(page);
	}

	/**
	 * Cette méthode permet de mettre à jours la page affiché par un page passée
	 * en paramètre
	 * 
	 * @param pg
	 */
	public void maj(Page pg)
	{
		MainActivity.playSound(this, R.raw.tournerpage);
		this.page = pg;

		// /!\ attention ordre des maj important !
		ajoutObjet(pg);
		supprimerObjet(pg);

		popupFullInventory();

		// -----------------------------------------------

		majInventaireIcon();

		majMarquePageIcon();

		// -----------------------------------------------

		majText(pg);

		majChoix(pg);

		// -----------------------------------------------

	}

	/**
	 * Permet de faire jaillir un pop up si l'inventaire est plein afin de
	 * notifier l'utilisateur sur ce fait
	 */
	private void popupFullInventory()
	{
		if (this.inventaireList.size() >= maxBag)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.full_inventory);
			builder.setPositiveButton("OK", null);

			builder.create();
			builder.show();
		}
	}

	/**
	 * Permet d'ajouter un objet à l'inventaire si la page en contient un
	 * 
	 * @param pg
	 */
	private void ajoutObjet(Page pg)
	{
		// On ajoute l'objet si la page en comporte un deplus on n'ajoute pas
		// d'objet si l'on reprend l'histoire, cependant ce n'est pas le cas si
		// nous sommes sur la première page.
		if (pg.getObjetDonne() > 0 && (!onResume || pg.getNumero() == livre.getIdFirstPage()))
		{
			this.inventaireList.add(Objet.getObjetByID(pg.getObjetDonne()));
		}
		// Reuinit de cette variable
		onResume = false;
	}

	/**
	 * Permet de supprimer un objet de l'inventaire, cet objet est spécifié par
	 * la page sur laquelle nous sommes.
	 * 
	 * @param pg
	 */
	private void supprimerObjet(Page pg)
	{
		// On ajoute l'objet si la page en comporte un deplus on n'ajoute pas
		// d'objet si l'on reprend l'histoire
		if (pg.getObjetSupprime() > 0 && !onResume)
		{
			this.inventaireList.remove(Objet.getObjetByID(pg.getObjetSupprime()));
		}
		// Reinit de cette variable
		onResume = false;
	}

	/**
	 * Met la partie texte de la page à jours
	 * 
	 * @param pg
	 */
	private void majText(Page pg)
	{
		// Code relatif au texte
		// TextViewEx text = (TextViewEx) findViewById(R.id.texte);
		// text.setText(pg.getTexte() + "\n", true);
		ScrollView scroll = (ScrollView) findViewById(R.id.scrollView1);
		scroll.scrollTo(0, 0);

		LinearLayout layout = (LinearLayout) findViewById(R.id.linear_lire);
		layout.removeViewAt(0);

		WebView newWebViewText = new WebView(this);	
		newWebViewText.getSettings().setDefaultTextEncodingName("utf-8");
		newWebViewText.loadDataWithBaseURL(null, toHTML(pg.getTexte()), "text/html", "utf-8", null);
		newWebViewText.setBackgroundColor(Color.TRANSPARENT);
		
		layout.addView(newWebViewText, 0);

	}

	/**
	 * Permet de mettre enforme de le texte passé en entrée via du css/html
	 * 
	 * @param texte source
	 * @return page html correspondante
	 */
	public String toHTML(String src)
	{
		StringBuffer buff = new StringBuffer();

		buff.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		buff.append("<html>");
		buff.append("<head>");
		buff.append("<style type='text/css'>");
		buff.append("html, body {background: transparent}");
		buff.append("p { text-align: justify; font-size: "+MainActivity.param.getTaille() +"px;}");
		// buff.append("p:first-letter {font-size: 35px; float: left; margin-right: 5px; color: darkred; font-family:'Times New Roman',Georgia,Serif; border: 1px solid darkred; padding: 3px; text-shadow: black 1px 1px, black -1px 1px, black -1px -1px, black 1px -1px; }");
		if(MainActivity.param.getLettrine() == 1)
		{
			buff.append("p:first-letter {font-size: "+MainActivity.param.getTaille()*2 +"px; float: left; margin-right: 5px; color: darkred; font-family:'Times New Roman',Georgia,Serif; border: 1px solid darkred; padding: 3px; }");
		}
		buff.append("</style>");
		buff.append("</head>");
		buff.append("<body>");
		buff.append("<p>");
		buff.append(src.replace("\n", "<br>"));
		buff.append("<p>");
		buff.append("</body>");
		buff.append("</html>");
		return buff.toString();
	}

	/**
	 * Met la partie bouton de la page à jours
	 * 
	 * @param pg
	 */
	protected void majChoix(Page pg)
	{
		// Code relatif aux choix
		List<Choix> chs = Choix.getAll(pg);

		RelativeLayout Rlay = (RelativeLayout) findViewById(R.id.Rlay);
		Rlay.removeAllViews();
		LinearLayout Llay = new LinearLayout(this);
		Llay.setOrientation(LinearLayout.VERTICAL);

		int nbButton = 0;

		for (Choix ch : chs)
		{
			Log.d(ch.getTexte(), ch.getObjetRequis() + "");

			if (ch.getObjetRequis() <= 0 || this.inventaireList.contains(Objet.getObjetByID(ch.getObjetRequis())))
			{
				Button b = new Button(this);
				b.setText(ch.getTexte());
				// Les choix ne sont actifs que si l'inventaire n'est pas saturé
				if (this.inventaireList.size() >= maxBag)
				{
					b.setEnabled(false);
				}

				b.setOnClickListener(new LireChangePageListener(this, ch.getIdNextPage()));

				Llay.addView(b);

				nbButton++;
			}

		}

		if (nbButton <= 0)
		{
			Button b = new Button(this);
			b.setText(getString(R.string.button_end));
			b.setOnClickListener(new LeaveClickListener(this, this.livre));
			Llay.addView(b);
		}

		Rlay.addView(Llay);
	}

	/**
	 * Met l'icone inventaire à jours
	 */
	protected void majInventaireIcon()
	{
		inventaireButton = (ImageButton) findViewById(R.id.inventory_icon);
		TextView count = (TextView) findViewById(R.id.inventory_count);

		// Code relative à l'inventaire
		if (this.inventaireList.size() > 0)
		{
			inventaireButton.setOnClickListener(new InventoryListener(this, this.inventaireList));
		}
		else
		{
			inventaireButton.setOnClickListener(null);
		}

		// -------------------------------------

		if (this.inventaireList.size() >= maxBag)
		{
			inventaireButton.setImageResource(R.drawable.inventory_full);
		}
		else if (this.inventaireList.size() <= 0)
		{
			inventaireButton.setImageResource(R.drawable.inventory_empty);
		}
		else
		{
			inventaireButton.setImageResource(R.drawable.inventory);
		}

		count.setText("[" + this.inventaireList.size() + "/" + (maxBag-1) + "]");
	}

	/**
	 * Met l'icone marque-page à jours
	 * 
	 * @param livre
	 */
	private void majMarquePageIcon()
	{

		if (livre.getMarquePage() == page.getNumero())
		{
			marquePage.setImageResource(R.drawable.ruban_enable);
			this.marquePageEnable = true;
		}
		else
		{
			marquePage.setImageResource(R.drawable.ruban_disable);
			this.marquePageEnable = false;
		}
	}

	@Override
	/**
	 * Lorsque l'on click sur le marque page l'on définie le marque page de ce livre comme étant cette page. on retire le marque page du livre si c'était déjà le cas.
	 */
	public void onClick(View v)
	{
		// if (livre.getIdFirstPage() != page.getNumero())
		if (true)
		{
			if (this.marquePageEnable)
			{
				this.marquePage.setImageResource(R.drawable.ruban_disable);

				livre.updateInventaire(new ArrayList<Objet>());
				livre.updateMarquePage(0);
			}
			else
			{
				this.marquePage.setImageResource(R.drawable.ruban_enable);

				livre.updateInventaire(this.inventaireList);
				livre.updateMarquePage(page.getNumero());
			}

			// Changement du boolean
			this.marquePageEnable = !this.marquePageEnable;
		}
	}

	@Override
	public void onBackPressed()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Confirmation");
		builder.setMessage(R.string.confirm_quit);
		
		builder.setPositiveButton(R.string.yes, new ConfirmQuitListener(this));
		builder.setNegativeButton(R.string.no, null);
		
		builder.create();
		builder.show();
	}

	
	
	public Page getPage()
	{
		return this.page;
	}
}
