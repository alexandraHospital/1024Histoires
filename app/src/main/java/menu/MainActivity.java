package menu;

import java.util.ArrayList;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.histoires.R;
import databasetest.Choix;
import databasetest.DBManager;
import databasetest.Livre;
import databasetest.Objet;
import databasetest.Page;
import databasetest.Parametre;

public class MainActivity extends Activity
{
	public static final UUID	uuid	= UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static Parametre		param;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// Mise en place du DBManager
		DBManager.setContext(getApplicationContext());

		// Livre.deleteAll();

		insertAllItems();
		loadParam();

		insertMathieuBook();
		insertElodieBook();
		insertMaraBook();
		insertVictorEtLeChateau();

		// Affectation des button au listener correspondant
		Button b = (Button) findViewById(R.id.button_lire);
		b.setOnClickListener(new ListenerRead(this));

		Button b2 = (Button) findViewById(R.id.button_ecrire);
		b2.setOnClickListener(new ListenerWrite(this));

		Button b3 = (Button) findViewById(R.id.button_bluetooth);
		b3.setOnClickListener(new ListenerWireless(this));

		Button b4 = (Button) findViewById(R.id.button_settings);
		b4.setOnClickListener(new ListenerSetting(this));

		Button b5 = (Button) findViewById(R.id.button_tuto);
		b5.setOnClickListener(new ListenerTuto(this));

		// Cas où notre téléphone ne possederait pas de materiel bluetooth
		if (BluetoothAdapter.getDefaultAdapter() == null)
		{
			// On cache alors le bouton Bluetooth
			b3.setVisibility(View.GONE);
		}

		ImageButton b_share = (ImageButton) findViewById(R.id.share);
		b_share.setOnClickListener(new ListenerQRCode(this));

		ImageButton b_info = (ImageButton) findViewById(R.id.about);
		b_info.setOnClickListener(new ListenerAbout(this));

	}

	private void loadParam()
	{
		// on ative le son par défaut, si la configuration n'existe pas déjà

		if (Parametre.getAll().size() == 0)
		{
			Parametre param = new Parametre(1, "", 1, 20);
			param.create();

			MainActivity.param = param;
		}
		else
		{
			MainActivity.param = Parametre.getParametreById(1);
		}
	}

	public void insertAllItems()
	{
		// Initialisation des éléments de la table si ils n'existent pas

		// Enlever cette ligne en version finale
		// Objet.deleteAll();

		ArrayList<Objet> listObjet = new ArrayList<Objet>();
		/* Objet 1 */listObjet.add(new Objet(getString(R.string.item_none), "rien"));
		/* Objet 2 */listObjet.add(new Objet(getString(R.string.item_amethyste), "amethyste"));
		/* Objet 3 */listObjet.add(new Objet(getString(R.string.item_ring), "anneau"));
		/* Objet 4 */listObjet.add(new Objet(getString(R.string.item_camera), "photo"));
		/* Objet 5 */listObjet.add(new Objet(getString(R.string.item_bow), "arc"));
		/* Objet 6 */listObjet.add(new Objet(getString(R.string.item_shield), "bouclier"));
		/* Objet 7 */listObjet.add(new Objet(getString(R.string.item_map), "carte"));
		/* Objet 8 */listObjet.add(new Objet(getString(R.string.item_cd), "cd"));
		/* Objet 9 */listObjet.add(new Objet(getString(R.string.item_chocolate), "chocolat"));
		/* Objet 10 */listObjet.add(new Objet(getString(R.string.item_key), "key"));
		/* Objet 11 */listObjet.add(new Objet(getString(R.string.item_usbkey), "cle_usb"));
		/* Objet 12 */listObjet.add(new Objet(getString(R.string.item_crown), "couronne"));
		/* Objet 13 */listObjet.add(new Objet(getString(R.string.item_knife), "couteau"));
		/* Objet 14 */listObjet.add(new Objet(getString(R.string.item_pencil), "crayon"));
		/* Objet 15 */listObjet.add(new Objet(getString(R.string.item_hdd), "disque_dur"));
		/* Objet 16 */listObjet.add(new Objet(getString(R.string.item_diskette), "disquette"));
		/* Objet 17 */listObjet.add(new Objet(getString(R.string.item_documents), "documents"));
		/* Objet 18 */listObjet.add(new Objet(getString(R.string.item_bullet), "douille"));
		/* Objet 19 */listObjet.add(new Objet(getString(R.string.item_sword), "epee"));
		/* Objet 20 */listObjet.add(new Objet(getString(R.string.item_strawberry), "fraise"));
		/* Objet 21 */listObjet.add(new Objet(getString(R.string.item_cheese), "fromage"));
		/* Objet 22 */listObjet.add(new Objet(getString(R.string.item_axe), "hache"));
		/* Objet 23 */listObjet.add(new Objet(getString(R.string.item_hamburger), "hamburger"));
		/* Objet 24 */listObjet.add(new Objet(getString(R.string.item_ham), "jambon"));
		/* Objet 25 */listObjet.add(new Objet(getString(R.string.item_lamp), "lampehuile"));
		/* Objet 26 */listObjet.add(new Objet(getString(R.string.item_book), "livre"));
		/* Objet 27 */listObjet.add(new Objet(getString(R.string.item_magnifyingglass), "loupe"));
		/* Objet 28 */listObjet.add(new Objet(getString(R.string.item_glasses), "lunettes"));
		/* Objet 29 */listObjet.add(new Objet(getString(R.string.item_lyre), "lyre"));
		/* Objet 30 */listObjet.add(new Objet(getString(R.string.item_mirror), "miroir"));
		/* Objet 31 */listObjet.add(new Objet(getString(R.string.item_muffin), "muffin"));
		/* Objet 32 */listObjet.add(new Objet(getString(R.string.item_bread), "pain"));
		/* Objet 33 */listObjet.add(new Objet(getString(R.string.item_coins), "pieces"));
		/* Objet 34 */listObjet.add(new Objet(getString(R.string.item_gun), "pistolet"));
		/* Objet 35 */listObjet.add(new Objet(getString(R.string.item_pizza), "pizza"));
		/* Objet 36 */listObjet.add(new Objet(getString(R.string.item_poele), "poele"));
		/* Objet 37 */listObjet.add(new Objet(getString(R.string.item_magicpotion), "potion_mana"));
		/* Objet 38 */listObjet.add(new Objet(getString(R.string.item_healthpotion), "potion_sante"));
		/* Objet 39 */listObjet.add(new Objet(getString(R.string.item_vigorpotion), "potion_vigueur"));
		/* Objet 40 */listObjet.add(new Objet(getString(R.string.item_poison), "poison"));
		/* Objet 41 */listObjet.add(new Objet(getString(R.string.item_rose), "rose"));
		/* Objet 42 */listObjet.add(new Objet(getString(R.string.item_ruby), "rubis"));
		/* Objet 43 */listObjet.add(new Objet(getString(R.string.item_soda), "soda"));
		/* Objet 44 */listObjet.add(new Objet(getString(R.string.item_totem), "totem"));
		/* Objet 45 */listObjet.add(new Objet(getString(R.string.item_mobilephone), "portable"));
		/* Objet 46 */listObjet.add(new Objet(getString(R.string.item_vinyl), "vinyle"));
		/* Objet 47 */listObjet.add(new Objet(getString(R.string.item_violin), "violon"));

		// On ajoute uniquement les nouveaux objets
		if (Objet.getAll().size() < listObjet.size())
		{
			for (int i = Objet.getAll().size(); i < listObjet.size(); i++)
			{
				listObjet.get(i).create();
			}
		}
	}

	public void insertMaraBook()
	{
		if (!Livre.exists("Alof au pays de la magie"))
		{
			Livre livre = new Livre();

			int numero = 1;

			livre.setTitre("Alof au pays de la magie");
			livre.setAuteur("Mara");
			livre.setSynopsis("Alof, un petit bonhomme de neige, se promène à travers le pays de la magie. S'étant perdu dans ce pays, il rencontre des personnages, de bon ainsi que de mauvais caractère. A travers de nombreuses aventures, saura-t-il retrouver son chemin?");
			livre.setEditable(0);
			livre.create();

			Page mainStreet = new Page();
			mainStreet.setNumero(numero);
			numero++;
			mainStreet.setTexte("Ceci est l'histoire du petit bonhomme de neige Alof. Il se trouve dans un merveilleux endroit magique, mais il ne peut se rappeler comment il y est arrivé. En face de lui se situe un château qui lui paraît très connu. Sur une des tours du château il aperçoit une fée dorée");
			mainStreet.create(livre);

			Page mainStreet2 = new Page();
			mainStreet2.setNumero(numero);
			numero++;
			mainStreet2.setTexte("Alof voit beaucoup d'autres personnages qui ne lui sont pas inconnus, mais pour l'instant il ne peut les mettre en rapport avec l'endroit où il se situe. Il voit une  fille aux longs cheveux blonds en compagnie d'un lapin qui regarde toujours l'horloge, un homme très musclé en compagnie d'un faune qui paraît  donner des recommandations à ce dernier, un capitaine avec une main à crochet et une reine qui joue au golf en se servant d'un flamant pour envoyer les balles de golf. Soudain, un petit rat voit Alof et s'approche de lui. Il a l'air de vouloir l'aider et lui fait signe de le suivre. ");
			mainStreet2.create(livre);

			Page ratatouille = new Page();
			ratatouille.setNumero(numero);
			numero++;
			ratatouille.setTexte("Alof décide de suivre le petit rat qui semble savoir exactement ce qu'il fait. Dans la maison dans laquelle il a conduit Alof se trouve une petite table avec une nappe blanche et une bougie. Le rat disparaît au fond de la pièce. ");
			ratatouille.create(livre);

			Page ratatouille2 = new Page();
			ratatouille2.setNumero(numero);
			numero++;
			ratatouille2.setTexte("Sur la table sur laquelle se trouve un papier avec l'inscription « Prends place et laisse-toi séduire par mon art culinaire ». Alof s'assied à la table et le rat lui apporte un bon plat. Le rat s'assied lui même à la table en face d'Alof et ne cesse de l'observer pendant qu'il mange. Il est ravi de l'appétit de son hôte. Après avoir fini son repas, Alof remercie le rat et sort de la maison. A la sortie, Alof remarque une lampe à huile sur une chaise. Il est curieux. Il veut la toucher, mais il hésite.");
			ratatouille2.create(livre);

			Page alice = new Page();
			alice.setNumero(numero);
			numero++;
			alice.setTexte("Alof se promène le long du château. Il cherche un indice qui lui permette de se repérer. Soudain il voit une petite porte. Il ouvre la porte et entre dans une salle. Il aperçoit une table en verre sur laquelle se trouve un verre rempli de lait et une boîte contenant des biscuits. Au verre est apposé un papier avec l'inscription « bois-moi » et à la boîte un autre papier avec les mots « mange-moi » ; à côté se trouvent deux clés. Alof remarque derrière la table une porte beaucoup plus grande que sa propre taille et une deuxième beaucoup plus petite. La porte par laquelle il est entré se ferme brusquement derrière lui. Alof ne sait pas s'il parviendra à passer par l'une des deux portes qui se trouvent devant lui. Désespéré, il s'assoie par terre. Soudain, le lapin qu'il a vu devant le château apparaît. Alof veut lui demander de l'aide, mais le lapin montre son horloge et dit « Je serai en retard ». Alof se rend compte qu'il est livré à lui-même. Il regarde la table et hésite.");
			alice.create(livre);

			Page laBelleEtLaBete = new Page();
			laBelleEtLaBete.setNumero(numero);
			numero++;
			laBelleEtLaBete.setTexte("En longeant le château qui lui semble de plus en plus énorme, Alof s'approche d'une grande maison. Il n'entend aucun bruit. Il a l'impression que cette maison est vide et que personne n'y habite. Hésitant, il ouvre la porte de la maison, il ne voit personne. Il traverse un long couloir dans l'espoir de  trouver quelqu'un qui pourrait l'aider. A droite et à gauche du couloir se trouvent des armures anciennes. Soudain il entend des chuchotements derrière lui. Alof se retourne, les chuchotements s'arrêtent. Il continue à avancer dans le couloir. Quand les chuchotements reprennent, Alof se retourne d'un coup brusque, il a l'impression que les chuchotements provenaient d'un candélabre et d'une horloge qui se trouvent au milieu du couloir, juste derrière lui. En effet, ce même  candélabre lui adresse la parole et lui demande ce qui l'a fait entrer dans cette maison. Alof, bouleversé, lui répond  qu'il s'est  trompé et qu'il va ressortir. ");
			laBelleEtLaBete.create(livre);

			Page laBelleEtLaBete2 = new Page();
			laBelleEtLaBete2.setNumero(numero);
			numero++;
			laBelleEtLaBete2.setTexte("Le candélabre, aimablement, invite Olaf à rester pour manger avec eux. L'horloge conduit Alof dans une grande salle où se trouve une table très longue. Quand il entre, il voit le candélabre en train de parler aux assiettes, aux tasses et à la théière. Un peu perplexe de ce qu'il voit, Alof s'assied à la grande table. Aussitôt des assiettes, des verres et des couverts se mettent en place sur la  table afin d'être utilisés par Alof.  Après le dîner, Alof quitte la maison. En sortant, il voit  un petit chien blanc avec des points noirs qui est poursuivi par une femme aux cheveux gris noirs qui a l’air très cruelle.");
			laBelleEtLaBete2.create(livre);

			Page aladdin = new Page();
			aladdin.setNumero(numero);
			numero++;
			aladdin.setTexte("Alof, hésitant, touche la lampe à huile. La lampe commence à changer de couleur, elle devient toute rouge et sa taille devient de plus en plus grande. Une fumée en sort, et avec elle, un grand génie bleu. Alof a peur, mais, curieux, il essaie de toucher le génie. Le génie s'échappe, mais revient de suite en rigolant. Il explique à Alof que la personne qui le fait sortir de la lampe a droit à un vœu parmi les vœux qu'il lui propose :");
			aladdin.create(livre);

			Page peterPan = new Page();
			peterPan.setNumero(numero);
			numero++;
			peterPan.setTexte("A côté du restaurant se trouve un grand lac. Alof s'approche de ce lac. Il a envie de se reposer. Il s'assied au bord du lac. Tout à coup il entend du bruit. Un homme avec une main à crochet poursuit un jeune garçon qui vole. Derrière l'homme rampe un crocodile. On entend le tic tac régulier d'un réveil. L'homme demande de l'aide au garçon, mais l'insulte en même temps. Alof qui est amusé par ce qu’il voit, commence à rire. Malheureusement, il attire l'attention de l'homme avec la main à crochet sur lui. Furieux de ce que quelqu'un ose rire de lui, il se précipite sur Alof. Le jeune garçon reconnaît le danger pour Alof, il siffle et une petite fée s'approche en volant. Le garçon la prend dans sa main et la ballotte au-dessus de la tête d'Alof. De la poussière de fée tombe sur Alof qui décolle et vole au dessus de l’homme avec la main à crochet. ");
			peterPan.create(livre);

			Page raiponce = new Page();
			raiponce.setNumero(numero);
			numero++;
			raiponce.setTexte("La taille d'Alof  a diminué. Il peut maintenant passé par la petite porte. Il entre dans une chambre sur les murs de laquelle sont peints de lampions sur les murs. Il longe les murs pour examiner ces peintures. Il se penche par la fenêtre et s'aperçoit qu'il se trouve dans une tour.  A ce moment, il entend la voix d'une jeune fille. Il se retourne, elle parle à un caméléon. Jamais auparavant il n'a vu des cheveux aussi longs que ceux de cette fille. Il s’approche d'elle. Quand la fille l'aperçoit, elle prend une  poêle qui se trouve à côté d'elle pour se défendre. Elle demande à Alof d'où il vient et ce qu'il cherche dans cette tour. Alof lui expose sa situation. La fille lui explique qu'elle est prisonnière dans cette tour et qu'il n'y a pas de porte pour en sortir. Elle lui propose de le faire sortir de la tour en se glissant sur ses cheveux longs. Alof accepte sa proposition. La fille lui donne la poêle pour qu'il puisse se défendre en cas d'agression.");
			raiponce.create(livre);

			Page toyStory = new Page();
			toyStory.setNumero(numero);
			numero++;
			toyStory.setTexte("Alof entre dans une chambre dans laquelle se trouve des tas de cartons remplis de jouets. Il est fatigué et s'assied par terre. Tout est calme. Soudain, un jouet soldat vert sort d'un carton. Il fait un signe de main et d'autres soldats le suivent. D'innombrables jouets commencent à sortir des cartons, un chien,  un homme pomme de terre, un dinosaure, une bergère, une boîte à musique... Les soldats verts semblent se sentir en danger par la présence d’Alof et vouloir défendre le reste des jouets. Au moment où ils arrivent près d'Alof, un soldat de l'espace apparaît et se présente comme le chef de la chambre. Dans l'autre coin de la chambre, un cowboy s'écrie que c'est lui le premier jouet qui a habité cette chambre. Le soldat de l'espace et le cowboy semblent être en désaccord. ");
			toyStory.create(livre);

			Page toyStory2 = new Page();
			toyStory2.setNumero(numero);
			numero++;
			toyStory2.setTexte("Mais Alof, bonhomme de neige, qui se trouve désormais aussi parmi eux, commence à attirer leur attention et les deux se rapprochent de lui. A ce moment, la porte de la chambre s'ouvre et un petit garçon entre. Tous les jouets restent immobiles. Alof essaie de faire pareil, mais le garçon a déjà remarqué qu'il y a un  nouveau jouet dans la chambre. Il tend la main pour le prendre. Alof a peur, il ne sait pas comment échapper à l'emprise du petit garçon.");
			toyStory2.create(livre);

			Page dalmatiens = new Page();
			dalmatiens.setNumero(numero);
			numero++;
			dalmatiens
					.setTexte("Alof cache le chien derrière lui pour empêcher que la femme au cheveux gris noirs ne réussisse à s’emparer de lui. Deux hommes la suivent, munis d'épuisettes. Ils cherchent le chien eux aussi. Heureusement ils n'ont pas vu Alof avec le chien. Lorsque les poursuiveurs sont hors de vue, le petit dalmatien sort rapidement de derrière son sauveur et pointe une direction précise. Il se retourne pour voir si Alof le suit. Arrivés tous les deux devant une grande maison, le petit chien aboie pour se faire remarquer. Deux grands dalmatiens sortent par une trappe. Le petit chien aboie pour faire signe à Alof d'entrer avec eux dans la maison. Alof se retrouve parmi une centaine de petits chiots dalmatiens. Ils se précipitent sur lui pour jouer avec lui. Soudain, Alof entend la voix d'une personne qui appelle les chiens. Ceux-ci se précipitent vers une autre pièce de la maison. Alof se redresse et parcourt le couloir de la maison. Il voit deux portes, une porte rose entrouverte et une porte rouge fermée.");
			dalmatiens.create(livre);

			Page roiLion = new Page();
			roiLion.setNumero(numero);
			numero++;
			roiLion.setTexte("Alof se trouve dans la savane. Il a l’air perdu et désespéré. A quelques mètres de lui, il voit un phacochère et un suricate qui sont en train de chercher des insectes pour manger. Quand ils voient Alof, ils le saluent cordialement. Ces deux nouveaux compagnons essaient de le réconforter en lui disant qu'il ne faut pas trop penser à ses soucis et que la vie est beaucoup plus facile si l’on ne se tracasse pas trop la tête. Un petit lion saute de derrière un buisson. Il tourne autour d’Alof pour lui montrer qu’il aimerait jouer avec lui. Il lui propose de s’asseoir sur son dos. Alof est ravi et monte.  Le petit lion fait un bond et fonce de toutes ses forces, Alof cependant n'arrive pas à se tenir.");
			roiLion.create(livre);

			Page cendrillon = new Page();
			cendrillon.setNumero(numero);
			numero++;
			cendrillon.setTexte("Alof est sur un balcon et regarde dans une grande salle. Un jeune couple danse. La jeune fille porte une robe très élégante et des chaussures en verre. Le couple se laisse entraîner par la musique. Soudain les cloches d’une église sonnent. La fille, effrayée, s'enfuit en courant. Le jeune homme la suit. Alof essaie de les rattraper, mais en vain. Il s’arrête et aperçoit une chaussure en verre que la jeune fille a perdu dans l'escalier.");
			cendrillon.create(livre);

			Page monstreAcademy = new Page();
			monstreAcademy.setNumero(numero);
			numero++;
			monstreAcademy.setTexte("Alof se retrouve dans une chambre d'enfant. Une petite fille en pyjama, assise par terre sur un tapis,  joue avec ses poupées. Quand elle voit Alof, elle s'approche de lui et lui fait un câlin. Alof apprécie ce geste, parce qu'il aime sentir l'affection de quelqu'un après le voyage fatiguant qu'il a fait depuis. Il remarque sur le bureau de la petite fille des dessins qu’elle a faits. Ceux-ci montrent un grand monstre poilu de couleur bleue ainsi qu'un monstre caméléon de couleur mauve. La petite fille tient par la main le monstre bleu. Quand Alof se retourne, il voit que la petite fille est en train  d'observer la lune à travers sa fenêtre ouverte. Alof s’appuie sur le banc de la fenêtre pour regarder lui aussi, mais il perd l'équilibre.");
			monstreAcademy.create(livre);

			Page dumbo = new Page();
			dumbo.setNumero(numero);
			numero++;
			dumbo.setTexte("Lorsqu’il se retourne, il voit un éléphant qui le suit en volant derrière lui. Alof suit l'éléphant qui tient une plume dans sa trompe. Il aperçoit une souris assise entre les grandes oreilles de l'éléphant. La souris dirige l'éléphant. Mais malheureusement la densité de la poussière de fée diminue.  Alof commence à atterrir.");
			dumbo.create(livre);

			Page blancheNeige = new Page();
			blancheNeige.setNumero(numero);
			numero++;
			blancheNeige.setTexte("Alof se retrouve dans une forêt dense. Il découvre un petit chalet avec un moulin et une rivière à côté. Une jeune fille chante en lavant des vêtements dans la rivière. En s’approchant, il voit qu’un petit raton laveur est assis à côté de la jeune fille et l'aide à laver son linge. Alof traverse un petit pont et se dirige vers la jeune fille. Celle-ci l'invite à entrer dans sa maison pour lui servir à boire. A l’intérieur du chalet, des animaux sont en train d'aider la jeune fille à faire le ménage. Les oiseaux nettoient les lampes fixées au mur, un chevreuil nettoie les escaliers, des écureuils roux nettoient des assiettes et les sèchent avec leur queue poilue.");
			blancheNeige.create(livre);

			Page blancheNeige2 = new Page();
			blancheNeige2.setNumero(numero);
			numero++;
			blancheNeige2.setTexte("Alof s'approche d’une petite table et compte sept chaises très petites. Il voit que sur chaque chaise est gravé un nom. Lorsqu’Alof est sur le point de partir, il voit sept nains qui se dirigent vers le chalet. Après avoir été présenté aux nains, il reçoit de leur chef un diamant, en guise d’amitié.");
			blancheNeige2.create(livre);

			Page pinocchio = new Page();
			pinocchio.setNumero(numero);
			numero++;
			pinocchio.setTexte("Alof rencontre une marionnette en bois, représentant un petit garçon, qui lui raconte qu'il est en route pour l'école. Un renard les rejoint et leur propose de l'accompagner dans un parc à jeux où les enfants peuvent s'amuser à leur guise, loin du contrôle de leurs parents. Le petit garçon hésite, mais puisqu’Alof est tout feu tout flamme, le garçon les suit.  Dans le parc, les deux s'amusent à fond. Ils montenet sur la grande roue et le carrousel et ils achètent du pop-corn et de la barbe à papa. Après avoir mangé,  Alof remarque que les oreilles du petit garçon se transforment lentement en  oreilles d'âne. Et le nez d'Alof commence à ressembler à un nez d'âne aussi. Alof se rend compte qu’ils sont tombés dans un piège. Alof essaie de fuir cet endroit. Des gardes le retiennent.");
			pinocchio.create(livre);

			Page liloStitch = new Page();
			liloStitch.setNumero(numero);
			numero++;
			liloStitch.setTexte("Alof fait la rencontre d'une petite fille qui lui présente son chien. Alof  a l'impression qu'il ne s'agit pas d'un chien, mais plutôt d'un extra terrestre. Le chien, de couleur bleue, a six pattes et au lieu d'aboyer, il fait des bruits bizarres. Alof s'assoie dans l'ombre à côté d'un palmier et observe les gens auxquels la petite fille présente son chien. Nombreux sont ceux qui sont effrayés et s’enfuient");
			liloStitch.create(livre);

			Page cars = new Page();
			cars.setNumero(numero);
			numero++;
			cars.setTexte("Alof voit une voiture rouge qui s’approche de lui à grande vitesse. Ses fenêtres avant ont la forme de grands yeux et de grands éclairs sont peints sur les côtés. Elle s'arrête brusquement à côté d'Alof et lui propose de l’emmener.");
			cars.create(livre);

			Page nemo = new Page();
			nemo.setNumero(numero);
			numero++;
			nemo.setTexte("Un poisson chirurgien s'approche d'Alof qui profite de l’occasion pour lui demander de l’aide. Le poisson est ravi de pouvoir lui offrir ses services de guide et lui propose de le suivre. Mais après peu de temps, le poisson se retourne vers Alof et d’un air énervé, lui demande pourquoi il nage derrière lui. Alof, étonné, lui rappelle qu'il lui avait proposé de l’aider à retrouver son chemin. Le poisson rigole et lui avoue qu'il avait complètement oublié ce qu'Alof lui avait demandé. Avec un nouvel élan, ils recommencent à nager tous les deux. Mais après avoir parcouru une courte distance, même scénario. Alof, impatient, décide de ne plus le suivre.");
			nemo.create(livre);

			Page passage = new Page();
			passage.setNumero(numero);
			numero++;
			passage.setTexte("Alof monte dans la voiture et lui parle. Il raconte qu'il aimerait bien retourner au grand château. La voiture lui propose de le déposer devant le château.");
			passage.create(livre);

			Page arielle2 = new Page();
			arielle2.setNumero(numero);
			numero++;
			arielle2.setTexte("Alof nage. Soudain il entend de la musique. Il se retourne et voit un crabe en train de chanter entourée de toutes sortes de poissons. Le crabe chante que la vie dans la mer est bien meilleure que celle sur terre. Alof les rejoint. Mais bientôt il se rend compte qu’il est préférable de poursuivre son chemin.");
			arielle2.create(livre);

			Page aladdin2 = new Page();
			aladdin2.setNumero(numero);
			numero++;
			aladdin2.setTexte("Alof se promène le long du bord de mer. D'un coup, il s’aperçoit que ce n'est plus du sable qu'il ressent en dessous de ses pieds. Il remarque qu'il a marché sur un tapis. Le tapis commence à bouger. Il décolle de la terre. Alof s’accroche au bord du tapis. Le tapis monte très haut. Il s'arrête devant le balcon d'une cathédrale.");
			aladdin2.create(livre);

			Page notreDamme = new Page();
			notreDamme.setNumero(numero);
			numero++;
			notreDamme.setTexte("Alof entre dans une des deux tours de la cathédrale. Il voit un homme assis devant une maquette de la cathédrale. Quand il se rapproche, il remarque que l'homme a une bosse sur le dos. Alof s'approche de lui. Le bossu est étonné de l'arrivée d'Alof, mais lui propose de se reposer.");
			notreDamme.create(livre);

			Page frozen = new Page();
			frozen.setNumero(numero);
			numero++;
			frozen.setTexte("Alof s'endort. Il rêve d'une reine qui peut faire de la magie. Elle fait de la neige. La reine s'éloigne d'un village en courant. Arrivée toute seule sur une montagne enneigée, elle utilise sa magie pour se construire un palais. Quand elle entre dans son palais, elle voit Alof. Elle l'a créé en même temps que le palais. Elle lui fait un câlin, car elle sait qu'il aime être embrassé chaleureusement.");
			frozen.create(livre);

			Page mulan = new Page();
			mulan.setNumero(numero);
			numero++;
			mulan.setTexte("Olaf sort de la cathédrale. Devant la grande porte, il rencontre un petit dragon rouge furieux, parce que tout le monde pense qu'il est un lézard. Mais en réalité, il n'est qu'un dragon en format de poche.");
			mulan.create(livre);

			Page aristochats = new Page();
			aristochats.setNumero(numero);
			numero++;
			aristochats.setTexte("Alof commence est fatigué. Le tapis le pose par terre. Il voit de loin une litière avec des chats. Puisque la chatte et ses chattons dorment, Alof se met à côté d'eux et s'endort. Il se réveille parce que la litière bouge brusquement. Il entend le bruit  très fort d'une moto. Il voit que quelqu'un est en train de bouger la litière sur la moto.");
			aristochats.create(livre);

			Page fin = new Page();
			fin.setNumero(numero);
			numero++;
			fin.setTexte("Alof se retrouve de nouveau en face du grand château. Une petite souris s'approche de lui. Elle  porte un pantalon rouge et une queue de pie. Derrière elle s'approche une souris vêtue d’une  très jolie robe à gros points blancs, de grandes chaussures jaunes ; elle porte un grand nœud sur la tête.. Elle rigole. La souris au pantalon rouge fait signe à Alof de les suivre. Ils montent tous les trois sur la  tour la plus haute du château. De là-haut, ils ont une vue splendide sur tous les univers à travers  lesquels  Alof a voyagé : la savane, la mer, la forêt, les maisons, les chàteaux... La souris explique à Alof qu'il se trouve au pays de l'imagination où tous les rêves deviennent réalité et que s'il le souhaite très fortement, Alof peut  rentrer chez lui.");
			fin.create(livre);

			Page arielle = new Page();
			arielle.setNumero(numero);
			numero++;
			arielle.setTexte("Alof s'approche du bord de mer. Il voit une sirène aux longs cheveux roux qui s'amuse avec un poisson jaune et un crabe. La sirène aperçoit Alof et s'approche de lui en nageant. Elle lui avoue qu'elle l'envie parce qu'il a des jambes qui lui permettent de marcher sur terre et qu’elle aimerait sortir de l'eau pour pouvoir marcher elle aussi.");
			arielle.create(livre);

			Page passage3 = new Page();
			passage3.setNumero(numero);
			numero++;
			passage3.setTexte("Alof monte à la surface de l’eau et cherche son souffle. Il se trouve en dessous d'un pont. Il sort de l'eau et s'approche du pont.");
			passage3.create(livre);

			Page indestructibles = new Page();
			indestructibles.setNumero(numero);
			numero++;
			indestructibles.setTexte("Alof essaie de toutes ses forces de se cramponner à un arbre, mais n’y parvient pas. C'est le moment où le grand homme très musclé vêtu d’un costume de super héro s'approche en courant. Alof commence à prendre son envol. L'homme tient Alof par son pied. Malheureusement la force de cet homme ne suffit pas. Sa femme s'approche et attrape Alof par ses bras élastiques. Elle porte le même costume, leurs enfants aussi.");
			indestructibles.create(livre);

			Page winny = new Page();
			winny.setNumero(numero);
			numero++;
			winny.setTexte("Alof rencontre un petit ourson, qui est occupé à vider des verres remplis de miel. Son compagnon, un goret, lui apporte un nouveau verre de miel.");
			winny.create(livre);

			Page winny2 = new Page();
			winny2.setNumero(numero);
			numero++;
			winny2.setTexte("Alof s'assied à côté de Pooh et commence à consommer du miel. Après avoir mangé, il se dirige vers un petit chalet. ");
			winny2.create(livre);

			Page chipChap = new Page();
			chipChap.setNumero(numero);
			numero++;
			chipChap.setTexte("Il rencontre deux tamias qui lui offrent des noix.");
			chipChap.create(livre);

			Page passage4 = new Page();
			passage4.setNumero(numero);
			numero++;
			passage4.setTexte("Alof retourne d'où il venait. ");
			passage4.create(livre);

			Page passage5 = new Page();
			passage5.setNumero(numero);
			numero++;
			passage5.setTexte("Alof se promène le long du bord de mer.");
			passage5.create(livre);

			Page passage6 = new Page();
			passage6.setNumero(numero);
			numero++;
			passage6.setTexte("Alof entre dans la forêt et s'approche d'un chalet.");
			passage6.create(livre);

			Choix ch1 = new Choix();
			ch1.setIdNextPage(mainStreet2.getId());
			ch1.setTexte("Alof se retourne");
			ch1.create(mainStreet);

			Choix ch2 = new Choix();
			ch2.setIdNextPage(ratatouille.getId());
			ch2.setTexte("Alof le suit");
			ch2.create(mainStreet2);

			Choix ch3 = new Choix();
			ch3.setIdNextPage(laBelleEtLaBete.getId());
			ch3.setTexte("Alof s'approche du château");
			ch3.create(mainStreet2);

			Choix ch4 = new Choix();
			ch4.setIdNextPage(ratatouille2.getId());
			ch4.setTexte("Alof s'approche de la table");
			ch4.create(ratatouille);

			Choix ch5 = new Choix();
			ch5.setIdNextPage(aladdin.getId());
			ch5.setTexte("Alof la touche");
			ch5.create(ratatouille2);

			Choix ch6 = new Choix();
			ch6.setIdNextPage(peterPan.getId());
			ch6.setTexte("Alof sort du restaurant");
			ch6.create(ratatouille2);

			Choix ch7 = new Choix();
			ch7.setIdNextPage(raiponce.getId());
			ch7.setTexte("Alof mange un biscuit");
			ch7.create(alice);

			Choix ch8 = new Choix();
			ch8.setIdNextPage(toyStory.getId());
			ch8.setTexte("Alof boit du lait");
			ch8.create(alice);

			Choix ch9 = new Choix();
			ch9.setIdNextPage(laBelleEtLaBete2.getId());
			ch9.setTexte("Alof essaie de sortir");
			ch9.create(laBelleEtLaBete);

			Choix ch10 = new Choix();
			ch10.setIdNextPage(dalmatiens.getId());
			ch10.setTexte("Alof attrape le chien");
			ch10.create(laBelleEtLaBete2);

			Choix ch11 = new Choix();
			ch11.setIdNextPage(dumbo.getId());
			ch11.setTexte("de voler");
			ch11.create(aladdin);

			Choix ch12 = new Choix();
			ch12.setIdNextPage(nemo.getId());
			ch12.setTexte("de nager");
			ch12.create(aladdin);

			Choix ch13 = new Choix();
			ch13.setIdNextPage(roiLion.getId());
			ch13.setTexte("d'aller à la savanne");
			ch13.create(aladdin);

			Choix ch14 = new Choix();
			ch14.setIdNextPage(dumbo.getId());
			ch14.setTexte("Alof aperçoit un éléphant volant ");
			ch14.create(peterPan);

			Choix ch15 = new Choix();
			ch15.setIdNextPage(indestructibles.getId());
			ch15.setTexte("Alof essaie d’atterrir");
			ch15.create(peterPan);

			Choix ch16 = new Choix();
			ch16.setIdNextPage(blancheNeige.getId());
			ch16.setTexte("Alof entre dans la forêt");
			ch16.create(raiponce);

			Choix ch17 = new Choix();
			ch17.setIdNextPage(pinocchio.getId());
			ch17.setTexte("Alof traverse le pont");
			ch17.create(raiponce);

			Choix ch18 = new Choix();
			ch18.setIdNextPage(toyStory2.getId());
			ch18.setTexte("Alof se tait");
			ch18.create(toyStory);

			Choix ch19 = new Choix();
			ch19.setIdNextPage(liloStitch.getId());
			ch19.setTexte("Alof attrape un soldat vert");
			ch19.create(toyStory2);

			Choix ch20 = new Choix();
			ch20.setIdNextPage(monstreAcademy.getId());
			ch20.setTexte("Alof ouvre la porte rose");
			ch20.create(dalmatiens);

			Choix ch21 = new Choix();
			ch21.setIdNextPage(cendrillon.getId());
			ch21.setTexte("Alof ouvre par la porte rouge");
			ch21.create(dalmatiens);

			Choix ch22 = new Choix();
			ch22.setIdNextPage(cars.getId());
			ch22.setTexte("Alof tombe sur une rue");
			ch22.create(roiLion);

			Choix ch23 = new Choix();
			ch23.setIdNextPage(alice.getId());
			ch23.setTexte("Alof sort du château ");
			ch23.create(cendrillon);

			Choix ch24 = new Choix();
			ch24.setIdNextPage(winny.getId());
			ch24.setTexte("Alof sort du château ");
			ch24.create(monstreAcademy);

			Choix ch25 = new Choix();
			ch25.setIdNextPage(laBelleEtLaBete.getId());
			ch25.setTexte("Il aperçoit une maison");
			ch25.create(dumbo);

			Choix ch26 = new Choix();
			ch26.setIdNextPage(alice.getId());
			ch26.setTexte("Il cherche à retrouver le château");
			ch26.create(dumbo);

			Choix ch27 = new Choix();
			ch27.setIdNextPage(blancheNeige2.getId());
			ch27.setTexte("Alof s'approche d'une petite table");
			ch27.create(blancheNeige);

			Choix ch28 = new Choix();
			ch28.setIdNextPage(arielle.getId());
			ch28.setTexte("Alof prend le diamant");
			ch28.create(blancheNeige2);

			Choix ch29 = new Choix();
			ch29.setIdNextPage(passage5.getId());
			ch29.setTexte("Alof se défend");
			ch29.create(pinocchio);

			Choix ch30 = new Choix();
			ch30.setIdNextPage(aladdin2.getId());
			ch30.setTexte("Alof se promène le long de la plage");
			ch30.create(liloStitch);

			Choix ch31 = new Choix();
			ch31.setIdNextPage(passage.getId());
			ch31.setTexte("Alof monte ");
			ch31.create(cars);

			Choix ch32 = new Choix();
			ch32.setIdNextPage(liloStitch.getId());
			ch32.setTexte("Alof ne monte pas");
			ch32.create(cars);

			Choix ch33 = new Choix();
			ch33.setIdNextPage(arielle2.getId());
			ch33.setTexte("Alof continue tout seul");
			ch33.create(nemo);

			Choix ch34 = new Choix();
			ch34.setIdNextPage(passage3.getId());
			ch34.setTexte("Il remonte à la surface de l'eau");
			ch34.create(nemo);

			Choix ch35 = new Choix();
			ch35.setIdNextPage(laBelleEtLaBete.getId());
			ch35.setTexte("Voiture démarre");
			ch35.create(passage);

			Choix ch36 = new Choix();
			ch36.setIdNextPage(aladdin2.getId());
			ch36.setTexte("Alof sort de l'eau");
			ch36.create(arielle2);

			Choix ch37 = new Choix();
			ch37.setIdNextPage(notreDamme.getId());
			ch37.setTexte("Alof descend");
			ch37.create(aladdin2);

			Choix ch38 = new Choix();
			ch38.setIdNextPage(aristochats.getId());
			ch38.setTexte("Alof continue son vol");
			ch38.create(aladdin2);

			Choix ch39 = new Choix();
			ch39.setIdNextPage(frozen.getId());
			ch39.setTexte("Alof est fatigué");
			ch39.create(notreDamme);

			Choix ch40 = new Choix();
			ch40.setIdNextPage(mulan.getId());
			ch40.setTexte("Alof continue son chemin");
			ch40.create(notreDamme);

			Choix ch41 = new Choix();
			ch41.setIdNextPage(mulan.getId());
			ch41.setTexte("Alof se réveille");
			ch41.create(frozen);

			Choix ch42 = new Choix();
			ch42.setIdNextPage(fin.getId());
			ch42.setTexte("Alof avance");
			ch42.create(mulan);

			Choix ch43 = new Choix();
			ch43.setIdNextPage(fin.getId());
			ch43.setTexte("Alof saute hors de la litière");
			ch43.create(aristochats);

			Choix ch44 = new Choix();
			ch44.setIdNextPage(arielle2.getId());
			ch44.setTexte("Alof la rejoint dans l'eau");
			ch44.create(arielle);

			Choix ch45 = new Choix();
			ch45.setIdNextPage(pinocchio.getId());
			ch45.setTexte("Alof la rejoint dans l'eau");
			ch45.create(passage3);

			Choix ch46 = new Choix();
			ch46.setIdNextPage(passage4.getId());
			ch46.setTexte("Alof les remercie");
			ch46.create(indestructibles);

			Choix ch47 = new Choix();
			ch47.setIdNextPage(winny.getId());
			ch47.setTexte("Alof s'éloigne");
			ch47.create(indestructibles);

			Choix ch48 = new Choix();
			ch48.setIdNextPage(winny2.getId());
			ch48.setTexte("Alof aime le miel");
			ch48.create(winny);

			Choix ch49 = new Choix();
			ch49.setIdNextPage(chipChap.getId());
			ch49.setTexte("Alof n'aime pas le miel");
			ch49.create(winny);

			Choix ch50 = new Choix();
			ch50.setIdNextPage(blancheNeige.getId());
			ch50.setTexte("Alof avance");
			ch50.create(winny2);

			Choix ch51 = new Choix();
			ch51.setIdNextPage(aladdin2.getId());
			ch51.setTexte("Alof se promène");
			ch51.create(passage5);

			Choix ch52 = new Choix();
			ch52.setIdNextPage(passage6.getId());
			ch52.setTexte("Il accepte");
			ch52.create(chipChap);

			Choix ch53 = new Choix();
			ch53.setIdNextPage(blancheNeige.getId());
			ch53.setTexte("avancer");
			ch53.create(passage6);

			Choix ch54 = new Choix();
			ch54.setIdNextPage(alice.getId());
			ch54.setTexte("Il s'approche du château");
			ch54.create(passage4);
		}
	}

	public void insertMathieuBook()
	{

		if (!Livre.exists("Enquête de Sherlock"))
		{
			final long CLE = 10;
			final long DOCUMENTS = 17;
			final long PIECES = 33;
			final long COUTEAU = 13;
			final long LOUPE = 27;
			final long DOUILLE = 18;

			int numero = 1;

			Livre livre = new Livre();

			livre.setTitre("Enquête de Sherlock");
			livre.setAuteur("Mathieu");
			livre.setSynopsis("Alors que Sherlock Holmes, le célèbre détective de Scoland Yard se retrouve tout seul car son associé le docteur Watson est malade, il est appelé sur une histoire des plus morbides. Son sens de la déduction et de l'observaton légendaire viendront-ils à bout de cette affaire ?");
			livre.setEditable(0);
			livre.create();

			// OK
			Page porche = new Page();
			porche.setNumero(numero);
			numero++;
			porche.setObjetDonne(LOUPE);
			porche.setTexte("250 Backer street, je n'ai jamais encore eu à enquêter si près de chez moi. Je me tiens sur le porche et alors que l'odeur de putréfaction du cadavre arrive à peine à mes narines, l’inspecteur James Whiteman en charge de l'affaire semble-t-il arriver à ma rencontre.\nSon badge usé et sa confiance en lui sur une scène de crime me laissent penser qu'il s'agit d'un policier en fin de carrière.\n- En trente ans de carrière, jamais vu ça !\nJ'ai vu juste sur ce point. Ses pupilles dilatées et ses mains tremblotantes m'indiquent qu'il s'agit d'un alcoolique notoire, peut-être même commence-t-il à ressentir le manque. Enfin bon, il ne vaut mieux pas se le mettre à dos, son aide peut m'être utile par la suite, ne serait-ce que pour en savoir plus sur l'affaire.\n- Bonjour, pouvez-vous me mener à la scène de crime ?\n- Hummm et vous-êtes ? \n- Je suis le détective Sherlock Holmes, Scotland Yard m'a proposé cette affaire, et je dois dire que les circonstances de la mort ont piqué ma curiosité.\n- Ah vous êtes ce détective qui fait des miracles à ce qu'on dit.\n- Je ne suis pas plus faiseur de miracles que vous, mes seules armes pour résoudre cette affaire sont la science et la logique.");
			porche.create(livre);

			// OK
			Page scene = new Page();
			scene.setNumero(numero);
			numero++;
			scene.setTexte("La putréfaction est à son apogée, alors que tous les enquêteurs fuient le cadavre, au contraire je m'en approche aussi près que possible. Rien ne doit m’échapper. Le légiste sera là d'une minute à l'autre pour emmener le corps. \nPoint numéro un : le corps gît par terre, dos contre terre, le corps a été éventré, de la vessie à l'estomac, vraisemblablement de bas en haut, la coupure est nette et appliquée. Le geste est technique, un professionnel de toute évidence.\nPoint numéro deux : en manipulant un peu le corps on se rend compte que la victime possède un impact de balle à l'arrière de la tête. La présence de poudre sur le crâne laisse penser à un coup à bout portant.\nPoint numéro trois : la victime est sur un tapis totalement entaché de sang, mais le planché est totalement immaculé.\nMes conclusions : de toute évidence nous ne sommes pas sur les lieux du crime. La victime à sûrement été exécutée dans un endroit bien moins fréquenté. Quant à son entaille au ventre, je suppose que son ou ses ravisseurs étaient à la recherche de quelque chose. Ce quelque chose est sûrement le mobile du crime. L’exécution est professionnelle, et la coupure est propre, à moins d'avoir à faire à un tueur à gage chirurgien, je miserais sur deux complices. Quant à leurs identités, c'est sur les membres de sa famille que se porteront mes premiers soupçons. \nAïe,  à peine une minute que j'examine le corps que le légiste le réclame déjà. Je n'ai que quelques secondes pour un dernier examen.");
			scene.create(livre);

			// OK
			Page exam_bouche = new Page();
			exam_bouche.setNumero(numero);
			numero++;
			exam_bouche.setTexte("La bouche est totalement sèche et la rigidité cadavérique m'empêche de la manipuler à mes souhaits. Cependant en tâtonnant je sens une masse solide au fond.  Après quelques essais infructueux j'arrive à en extraire une vieille clé métallique. D'une longueur d'environ 4 centimètres et malgré son âge apparent, je sens que cette clé est une pièce particulièrement bien travaillée. Probablement une clé de coffre fort à laquelle on a donné une apparence banale.\nEn plein milieu des mes déductions l'inspecteur WhiteMan revient à ma rencontre.\n- Alors le jeune prodige a-t-il fait des miracles ?\n- Je partirais sur la piste d'un tueur à gage et d'un médecin, peut-être dans la famille ou l'entourage.\n- Très bien monsieur le génie, autre chose ?");
			exam_bouche.create(livre);

			// OK
			Page exam_pied = new Page();
			exam_pied.setNumero(numero);
			numero++;
			exam_pied.setTexte("Un rapide coup d'oeil sur les pieds de la victime me permet d’apercevoir des traces d'herbe toutes fraîches. Il est clair que la victime a couru dans l'herbe, peut être pour fuir ses assaillants.  Transporter un corps n'est pas chose facile, peut-être que la scène de crime n'est pas loin.");
			exam_pied.create(livre);

			// OK
			Page jardin = new Page();
			jardin.setNumero(numero);
			numero++;
			jardin.setTexte("En revenant sur mes pas, j'arrive devant la porte fenêtre menant au jardin, quelques policiers y fument des cigarettes en discutant. Le jardin est rectangulaire, tout en longueur, et entouré de haies. Un endroit parfait pour y tuer quelqu'un me dis-je. Et tandis que tout ce beau monde fumait, je remarquai le petit détail qui changeait tout. Dans ce jardin d'apparence négligé où les arbres poussaient anarchiquement et les épaves de vélos côtoyaient ronces et lierre, le gazon était impeccablement tondu. Qui se soucierait de son gazon mais pas du reste ? En émettant l'hypothèse que je me trouvais sur la scène du crime et que le gazon avait été tondu pour effacer toutes traces du crime je me mets alors en quête d'éventuels indices. Très rapidement mes soupçons se concrétisent, à l'aide de ma loupe dont je ne me sépare jamais, je repère un carré de pelouse de quelques centimètres ou l'herbe est brûlée. Sûrement l’endroit où la douille est tombée. Peut-être est-elle encore dans le coin ?");
			jardin.create(livre);

			// OK
			Page cherche_arbre = new Page();
			cherche_arbre.setNumero(numero);
			numero++;
			cherche_arbre.setObjetDonne(DOUILLE);
			cherche_arbre.setTexte("Au bout de quelques minutes je finis par mettre la main sur une douille de calibre 9 mm. Il ne s'agit définitivement plus d'un professionnel, une telle personne aurait emporté la douille pour qu'on ne puisse jamais identifier les empruntes sur celle-ci. Cependant vu l'averse de cette nuit, probablement aucune emprunte ne sera exploitable. Elle peut cependant être utile et je la garde au cas où.");
			cherche_arbre.create(livre);

			// OK
			Page cacher_cle = new Page();
			cacher_cle.setNumero(numero);
			numero++;
			cacher_cle.setObjetDonne(CLE);
			cacher_cle.setTexte("L'inspecteur ne m'inspire pas confiance, je décide de lui cacher l'existence de la clé. \n- Non rien du tout, je vous tiendrais au courant si je découvre quoi que ce soit.\n- Je dois dire que je suis un peut déçu je m'attendais à mieux de votre part.\n- Je vous avais prévenu, je suis enquêteur pas magicien.\nSur ce je laisse l'inspecteur à la rédaction de son rapport pour continuer à explorer la maison à la recherche d'indices.");
			cacher_cle.create(livre);

			// OK
			Page partager_cle = new Page();
			partager_cle.setNumero(numero);
			numero++;
			partager_cle.setTexte("Je m'empresse d'ajouter :\n- Oui j'ai trouvé une clé dans l’œsophage de cette femme.\n- Ah oui faites voir ça.\n- Je préférerais la garder.\n- Désolé mais vous devez remettre tout indice aux autorités compétentes, nous allons l'envoyer au labo pour analyse.\nDevant ce flic borné je n'ai d'autre choix que de lui laisser la clé. Bien que je perde un indice je suis sûr que la maison en regorge d'autres.");
			partager_cle.create(livre);

			// OK
			Page chambre = new Page();
			chambre.setNumero(numero);
			chambre.setTexte("En empruntant le couloir principal, j'aperçois un escalier en colimaçon qui mène au premier étage. Une fois à l'étage je trouve facilement la chambre à coucher. La pièce est assez sommaire, un double lit et une commode composent le gros des meubles. Un tableau vient ajouter un soupçon décoratif au tout. Peut être y a-t-il quelque chose à trouver en rapport avec l'affaire.");
			chambre.create(livre);

			// OK
			Page cuisine = new Page();
			cuisine.setNumero(numero);
			numero++;
			cuisine.setTexte("La cuisine est une pièce exiguë. Elle ne dépasse sans doute pas les dix mètres carré. Bien qu'une dizaine d’assiettes et de couverts attendent dans l'évier que l'on s'occupe d'eux, un signal lumineux ne cesse de clignoter sur le lave-vaisselle.");
			cuisine.create(livre);

			// OK
			Page fouiller_lave_vaisselle = new Page();
			fouiller_lave_vaisselle.setNumero(numero);
			numero++;
			fouiller_lave_vaisselle.setObjetDonne(COUTEAU);
			fouiller_lave_vaisselle.setObjetSupprime(LOUPE);
			fouiller_lave_vaisselle.setTexte("A l'ouverture du lave-vaisselle, un nuage de vapeur s'en échappe. Il a vraisemblablement été lancé il y a peu longtemps. A première vue, la plupart des couverts ont été mis là à la va-vite. Quelque chose cloche dans ce lave vaisselle, pas assez de profondeur, j'arrive à distinguer qu'une planche de bois a été grossièrement disposée là, destinée à faire office de double fond. Après quelques essais infructueux je m'engouffre littéralement dans le lave-vaisselle afin de lever ce mystère. Dans l'opération ma loupe tombe de ma poche et le verre se brise. Une fois le double fond mis en lumière, mon attention se pose rapidement sur un couteau à poisson d'un quarantaine de centimètres de longueur. Parfait pour éventrer une femme. Mon intuition me dit de le garder, c'est sûrement une des armes du crime.");
			fouiller_lave_vaisselle.create(livre);

			// OK
			Page fouiller_chambre = new Page();
			fouiller_chambre.setNumero(numero);
			numero++;
			fouiller_chambre.setTexte("Cette chambre, bien qu'assez pauvre en décoration, aborde une magnifique copie d'un Van Gogh. Bien que pas évident aux premiers coups d'oeil ce tableau à tout de suspect. En l'examinant de plus près ses fixations sont latérales, ce qui est curieux pour un tableau. En le faisant pivoté il dévoile un coffre fort à serrure. J'y insère la clé précédemment trouvée dans la bouche de la victime. Et le coffre fort me livre ses secrets. A l'intérieur je n'y trouve qu'un ensemble de documents administratifs sans intérêt et un peu d'argent liquide, et une dizaine de livres sterling en pièces de monnaie. Un de ces trois objets est le mobile du meurtre. Je prends les documents, ils contiennent peut être des informations pas évidentes aux premiers coups d'oeil.");
			fouiller_chambre.setObjetSupprime(CLE);
			fouiller_chambre.setObjetDonne(DOCUMENTS);
			fouiller_chambre.create(livre);

			// OK
			Page prendre_piece = new Page();
			prendre_piece.setNumero(numero);
			numero++;
			prendre_piece.setTexte("Je dénombre un total de 12 livres et 42 cents. Pourquoi les garder dans ce coffre fort ? Peut-être ont-ils une forte valeur sentimentale.");
			prendre_piece.setObjetSupprime(CLE);
			prendre_piece.setObjetDonne(PIECES);
			prendre_piece.create(livre);

			// OK
			Page interrogatoire = new Page();
			interrogatoire.setNumero(numero);
			numero++;
			interrogatoire
					.setTexte("Une fois de retour dans le salon, le corps a disparu, le légiste l'a emporté pour examens. A proximité de son ancien emplacement se tient la famille de la victime. L'inspecteur Whiteman les interroge, il recueille alibis et témoignages. Restant à l'écart je les observe afin de me faire une idée sur ces nouveaux personnages.\nTout d’abord le père, un vieil homme d'environ soixante-dix ans. Il est en fauteuil roulant, son teint blanc laisse présager un état de santé alarmant, peut-être même est-il mourant. Il répond étonnamment dynamiquement aux questions de l'inspecteur.\nVient ensuite le mari, un homme mature, brun, cheveux impeccablement coiffés. Son visage affiche  l’émotion de la perte de sa femme, il ne cesse de toucher son alliance. \nEnfin le frère de la victime, un blondinet d'une vingtaine d'années sûrement le fruit du remariage du père, il ne cache pas son ras-le-bol d'être là.\nL'inspecteur semble avoir fini et une fois son calepin refermé il vient vers moi.\n- Rien à en tirer. Dit-il d'un air franchement déçu.\n- Ai-je votre permission pour les interroger ?\n- Oui cependant soyez bref, votre présence ici ne doit pas s'ébruiter.");
			interrogatoire.create(livre);

			Page listSuspect = new Page();
			listSuspect.setNumero(numero);
			numero++;
			listSuspect.setTexte("Qui interroger maintenant ?");
			listSuspect.create(livre);

			Page intPere = new Page();
			intPere.setNumero(numero);
			numero++;
			intPere.setTexte("Je m'isole avec le père dans une pièce voisine. Il semble à bout de force, la journée a dû être éprouvante pour lui. Malgré cela je discerne dans ses yeux une féroce envie de vivre. ");
			intPere.create(livre);

			Page pereCle = new Page();
			pereCle.setNumero(numero);
			numero++;
			pereCle.setTexte("En glissant discrètement ma main dans ma poche de pantalon, j'en sors la clé trouvée précédemment sur le cadavre. Je la garde cachée dans ma main. Encore quelques questions sans intérêt puis je bondis à sa rencontre, je m'approche aussi près de lui que possible, nos visages sont à peine à 10 centimètres l'un de l'autre. Je peux à présent distinguer chaque ride de son visage. J’amène ma main entre nous, la clé entre le pouce et l'index. L'effet de surprise est total, pourtant je ne discerne qu'une faible surprise dans ses expressions faciales, son âge y est sans doute pour beaucoup.\n- Que pouvez-vous me dire sur cette clé ?\n- Il s'agit de la …\n*Respiration profonde* \n-...Clé du coffre...\n*Respiration profonde* \n-... de ma fille.\n- Et savez-vous ce qu'il contient ?\n- Les affaires...\n*Respiration profonde* \n-... de ma fille\n*Respiration profonde* \n-... ne sont pas les miennes.");
			pereCle.create(livre);

			Page pereDouille = new Page();
			pereDouille.setNumero(numero);
			numero++;
			pereDouille.setTexte("Je présente la douille au suspect.\n-Calibre 9 mm, possédez-vous une arme ?\n-Non");
			pereDouille.create(livre);

			Page pereCouteau = new Page();
			pereCouteau.setNumero(numero);
			numero++;
			pereCouteau.setTexte("Sans le ménager au préalable je lui expose le couteau que j'ai trouvé dans la cuisine. Il nie en bloc avoir déjà vu ce couteau.");
			pereCouteau.create(livre);

			Page pereDocuments = new Page();
			pereDocuments.setNumero(numero);
			numero++;
			pereDocuments.setTexte("Je me mets à feuilleter les documents trouvés dans le coffre. Au bout de quelques secondes, je finis par lever les yeux vers mon interlocuteur, celui-ci ne montre aucun signe d'impatience.\n- Saviez-vous que votre fille divorçait ?\n*Respiration profonde* \n- Non.\n*Respiration profonde* \nBien qu'il nie, il s'est pincé les lèvres. Je ne sais pas encore si ce détail est significatif.");
			pereDocuments.create(livre);

			Page perePieces = new Page();
			perePieces.setNumero(numero);
			numero++;
			perePieces.setTexte("Dans la paume de ma main, une poignée de pièces. Vieilles, rouillées, apparemment sans valeur. Je présente ma découverte à mon interlocuteur.\n- Les avez-vous déjà vu auparavant ?\n- Non *Respiration profonde* jamais.");
			perePieces.create(livre);

			Page intMari = new Page();
			intMari.setNumero(numero);
			numero++;
			intMari.setTexte("Je prends le mari à part dans le jardin. Il m'a reconnu, c'est sûr, il ne cesse de m'observer depuis que nos regards se sont croisés. Il est temps de passer à l'interrogatoire, voyons ce qu'il a dans le ventre. ");
			intMari.create(livre);

			Page mariCle = new Page();
			mariCle.setNumero(numero);
			numero++;
			mariCle.setTexte("Je sors la clé trouvée sur le corps de la victime et la lance au mari, surpris, il arrive quand même par l'attraper au vol.\n- Connaissez-vous cette clé ?\n- Oui, il s'agit de la clé de notre coffre fort.\n- Votre femme avait-elle une raison de vous la cacher ?\n- Non pas du tout, j'en ai d'ailleurs un exemplaire autour de mon cou.\nComme pour se justifier il enlève de sa nuque un pendentif ressemblant en tout point à la première clé. Il les met côte à côte pour les comparer.\n- Vous voyez : les mêmes.\nLe mari de la victime possédait donc un double de la clé du coffre, pourquoi donc aurait-il ouvert sa femme à la recherche de ce qu'il possédait déjà ? Tout ça n'est pas très clair, je garde ces éléments en mémoire pour la suite de l'enquête.");
			mariCle.create(livre);

			Page mariDouille = new Page();
			mariDouille.setNumero(numero);
			numero++;
			mariDouille.setTexte("Je mets ma main dans la poche gauche de ma veste et j'y trouve la douille précédemment trouvée dans le jardin.\n- Avez-vous une arme ?\n- Moi ? Non jamais, je déteste les armes, c'est ma femme qui en est une grande amatrice, ce fût d’ailleurs la source de pas mal de disputes avec elle.\nJ'amène alors ma main au niveau de son visage, la douille entre le pouce et l'index.\n- Votre femme tirait-elle au 9 mm ?\n- Oui bien sûr, où avez-vous trouvé cette douille ?\n- Qu'importe cela regarde la police maintenant.");
			mariDouille.create(livre);

			Page mariCouteau = new Page();
			mariCouteau.setNumero(numero);
			numero++;
			mariCouteau.setTexte("Je sors de mon sac le couteau trouvé dans le lave-vaisselle et lui tend par la lame.\n- Avez-vous déjà vu ce couteau auparavant ?\n- Non jamais, pourquoi où l'avez vous trouvé ?\n- Il semblerait que ce soit le couteau qui ait servi aux meurtriers de votre femme, et je l'ai trouvé dans votre cuisine.\n- Impossible, nous ne possédons pas de couteau de ce type, Nous avons hérité il y a quelque temps de l'argenterie de ma famille, quelques breloques sans valeur, mais aucun couteau ne ressemble à celui que vous me présenter.\nAinsi il nie avoir déjà vu ce couteau auparavant. Soit, je garde cette information dans un coin de ma tête.");
			mariCouteau.create(livre);

			Page mariDocuments = new Page();
			mariDocuments.setNumero(numero);
			numero++;
			mariDocuments.setTexte("Je sors alors les documents trouvés dans le coffre fort de la victime.\n- Ces documents vous sont-ils familiers ?\nÀ la vue de ces documents, son visage s'assombrit, de tout évidence il ne souhaitait pas que l'on mette la main sur ces documents.\n- Oui, ce sont les papiers de notre divorce.\n- Ainsi vous divorciez de la victime et vous n'avez pas juger bon de mettre la police au courant ?\n- Il faut me comprendre j'étais sûr que je deviendrais le suspect numéro un si je rendais cette information publique. Je croyais ces documents à l'abri dans notre coffre fort.\nJe ne sais pas s'il ment ou dit la vérité, le divorce est un très bon mobile, ici le suspect était peut être en désaccord avec la victime. Je dois en tout cas garder cette information à l’esprit pour la suite de l'enquête.");
			mariDocuments.create(livre);

			Page mariPieces = new Page();
			mariPieces.setNumero(numero);
			numero++;
			mariPieces.setTexte("Je recueille toutes les pièces que j'ai pu trouver dans le coffre fort, je les tiens à présent dans la paume de ma main.\n- Savez-vous pourquoi votre femme gardait-elle de vulgaires pièces de monnaie dans son coffre fort ?\n- Ce ne sont pas de vulgaires pièces de monnaie. Vous tenez dans votre main toute notre fortune. Ces quelques pièces furent les premières émises lors de la décimalisation de la Livre Sterling. On devait les faire estimer prochainement, mains on nous promettait déjà quelques 20 millions de dollars pour le tout. C'est la mère de ma femme qui les lui avait léguées avant de mourir.\nCela représente un bon paquet d'argent, je tiens là un des mobiles possibles du meurtre. Cette information est précieuse et je dois méditer dessus.");
			mariPieces.create(livre);

			Page intFrere = new Page();
			intFrere.setNumero(numero);
			numero++;
			intFrere.setTexte("C'est au frère que je vais m'attaquer. Après l'avoir invité à me suivre dans une pièce voisine nous sommes enfin seuls. Il s'agit du plus jeune des trois suspects. Bien qu'il fasse beaucoup d'efforts pour que cela ne se voit pas il semble un peu nerveux à l'idée de répondre à mes questions. Voyons comment je peux le faire réagir.");
			intFrere.create(livre);

			Page frereCle = new Page();
			frereCle.setNumero(numero);
			numero++;
			frereCle.setTexte("Entre mon pouce et mon index la clé trouvée sur la scène de crime. \n- Voici la clé du coffre fort de votre défunte sœur. Savez-vous pourquoi je l'ai trouvée dans sa trachée ?\n- Ce que vous me dites là est horrible, pourquoi aurait-elle fait une chose pareille ?\n- C'est à vous que je le demande.\n- Je n'en ai aucune idée, elle devait y garder quelque chose d'important.");
			frereCle.create(livre);

			Page frereDouille = new Page();
			frereDouille.setNumero(numero);
			numero++;
			frereDouille.setTexte("Je présente la douille trouvée dans le jardin au suspect.- Est-ce la douille de la balle qui a tué ma sœur ?- J'ai toutes les raisons de le croire, une expertise balistique nous le confirmera prochainement.- Maniez-vous les armes ?- Oh non surtout pas je suis chirurgien, mes mains sont assurées pour plusieurs milliers de Livres, je ne peux pas prendre le risque de les abîmer, sans elles je me retrouverais au chômage.");
			frereDouille.create(livre);

			Page frereCouteau = new Page();
			frereCouteau.setNumero(numero);
			numero++;
			frereCouteau.setTexte("Je sors le couteau devant lui.\n- J'ai trouvé ce couteau dans le lave-vaisselle.\n- C'est chez ma sœur ici, je ne sais pas ce qu'elle faisait avec.\n- Vous deviez venir ici souvent quand même, l'avez-vous déjà vu auparavant ?\n- C'est la première fois que je vois un tel couteau.");
			frereCouteau.create(livre);

			Page frereDocuments = new Page();
			frereDocuments.setNumero(numero);
			numero++;
			frereDocuments.setTexte("Je présente les documents, trouvés un peu plus tôt dans le coffre fort, au frère.\n- Saviez-vous que votre sœur et son mari divorçaient ?\n- Je l’ignorais, ma sœur n'avait pas pour habitude d'étaler sa vie privée, en fait je dirais même qu'elle était assez réservée.\nJ'en apprends un peu plus sur la personnalité de la victime enfin si je me fie à son frère mais puis-je raisonnablement faire confiance à un suspect ?");
			frereDocuments.create(livre);

			Page frerePieces = new Page();
			frerePieces.setNumero(numero);
			numero++;
			frerePieces.setTexte("Je sors les pièces trouvées précédemment de ma poche et les présente à mon interlocuteur.\n- Savez-vous où j'ai trouvé ces pièces.\n- Non cela a-t-il un rapport avec l'enquête ?\n- Je les ai trouvées dans le coffre fort de votre sœur donc oui cela a un rapport. Avez-vous une idée de leur valeur.\n- Non, mais ce ne sont pas les vôtres, vous devriez me les remettre, je les transmettrais à son mari.\n- Je peux tout à fait lui remettre moi-même, merci de cette délicate attention.\n\nQuoi qu'il dise, je pense qu'il en sait plus qu'il veut bien le dire sur ces pièces. Gardons cela en tête pour la suite de l'enquête.");
			frerePieces.create(livre);

			Page accusation = new Page();
			accusation.setNumero(numero);
			numero++;
			accusation.setTexte("Je réunis alors pour la seconde fois l'assemblée. L'inspecteur a l'air plus qu'anxieux à l'idée que j'expose ma théorie. La famille bien que dubitative semble impatiente. Et les autres policiers eux sont là à attendre comme des enfants à qui on aurait dit d'être sage. Tous sont en face de moi et me regardent guettant chacun de mes gestes. On aurait pu se croire à un spectacle de magicien si les circonstances avaient étés différentes. Et comme ils voulait que je sois le magicien, je me devais de faire le show, j'élance un doigt inquisiteur vers le public ainsi qu'un retentissant :");
			accusation.create(livre);

			Page compliceDeFrere = new Page();
			compliceDeFrere.setNumero(numero);
			numero++;
			compliceDeFrere.setTexte("avec l'aide de :");
			compliceDeFrere.create(livre);

			Page compliceDeMari = new Page();
			compliceDeMari.setNumero(numero);
			numero++;
			compliceDeMari.setTexte("avec l'aide de :");
			compliceDeMari.create(livre);

			Page compliceDePere = new Page();
			compliceDePere.setNumero(numero);
			numero++;
			compliceDePere.setTexte("avec l'aide de :");
			compliceDePere.create(livre);

			Page mobileOK = new Page();
			mobileOK.setNumero(numero);
			numero++;
			mobileOK.setTexte("Mobile :");
			mobileOK.create(livre);

			Page mobileNOK = new Page();
			mobileNOK.setNumero(numero);
			numero++;
			mobileNOK.setTexte("Mobile :");
			mobileNOK.create(livre);

			Page finOK = new Page();
			finOK.setNumero(numero);
			numero++;
			finOK.setTexte("Le frère semble particulièrement réceptif à ma petite théorie. Le père lui ne se contente que d'un grognement. Malgré cela je remarque qu'ils partagent tous les deux une manie bien particulière, un pincement de lèvres trahit leur anxiété. J'ai mis le doigt sur quelque chose j'en suis sûr. Peut être qu'en les poussant un peu j'arriverais à en tirer quelque chose. Je commence par lancer au frère :\n- Ma petite théorie ne vous plaît pas ?\n- Non je n'ai sûrement pas tué ma sœur pour quelques pièces.\nJe m'adresse ensuite au père.\n- Et vous ? Ne me dites pas que votre fils a fait ça tout seul.\n- Arrêtez ! Ne mêlez pas mon père à ça.\n- Pourquoi pas ?\nA ce moment-là, je me suis rapproché aussi près que possible du frère afin d'accentuer un sentiment d'enfermement en lui, je dois lui donner l'impression que je contrôle la situation, il ne doit plus voir d’échappatoire. Face contre face, je lui lance alors :\n- Je suis presque sûr que votre vieillard de père a très bien pu éventrer votre sœur cette nuit !\nLes larmes au yeux et dans une rage folle le frère se mis à rire tout seul. Le petit gloussement se transforma vite en un rire de folie. \n- Vous n'avez décidément rien compris. Vous n'êtes pas aussi intelligent qu'on le dit finalement, comment aurait-il pu dans sa condition faire cela ? Lui ? Il ne s'est contenté que de l’abattre dans le jardin pendant que je la tenais.\nBingo. \nToute la salle fût soudain comme tétanisée par ces révélations. Personne ne pouvait croire à ce qu'il venait de dire. C’était bien lui et le père qui avaient fait le coup. Et comme je le pensais, dans le but de pouvoir payer le traitement expérimental pour le père que sa fille lui refusait. Mon inspecteur préféré (pour aujourd'hui du moins) s'empressa de passer les menottes aux coupables et me remercia abondamment. Et voilà une enquête de plus résolue, une enquête de moins à résoudre, une enquête à ajouter au palmarès de Sherlock Holmes.");
			finOK.create(livre);

			Page finNOK = new Page();
			finNOK.setNumero(numero);
			numero++;
			finNOK.setTexte("Malheureusement, votre déduction est fausse, peut-être que vous y arriverez mieux la prochaine fois.");
			finNOK.create(livre);

			// -------------------------------

			Choix ch1 = new Choix();
			ch1.setIdNextPage(scene.getId());
			ch1.setTexte("Se rendre sur la scène de crime");
			ch1.create(porche);

			// --------- scene --------
			Choix ch2 = new Choix();
			ch2.setIdNextPage(exam_bouche.getId());
			ch2.setTexte("Examiner la bouche");
			ch2.create(scene);

			Choix ch3 = new Choix();
			ch3.setIdNextPage(exam_pied.getId());
			ch3.setTexte("Examiner les pieds");
			ch3.create(scene);
			// ---------------------------

			Choix ch4 = new Choix();
			ch4.setIdNextPage(jardin.getId());
			ch4.setTexte("Aller dans le jardin");
			ch4.create(exam_pied);

			Choix ch5 = new Choix();
			ch5.setIdNextPage(cherche_arbre.getId());
			ch5.setTexte("Chercher dans les arbres");
			ch5.create(jardin);

			// ------ Exam bouche --------
			Choix ch6 = new Choix();
			ch6.setIdNextPage(cacher_cle.getId());
			ch6.setTexte("Lui cacher la clé");
			ch6.create(exam_bouche);

			Choix ch7 = new Choix();
			ch7.setIdNextPage(partager_cle.getId());
			ch7.setTexte("Lui parler de la clé");
			ch7.create(exam_bouche);
			// ---------------------------

			Choix ch8 = new Choix();
			ch8.setIdNextPage(chambre.getId());
			ch8.setTexte("Aller dans la chambre");
			ch8.create(partager_cle);
			ch8.create(cacher_cle);
			ch8.create(cherche_arbre);
			ch8.create(cuisine);
			ch8.create(fouiller_lave_vaisselle);

			Choix ch9 = new Choix();
			ch9.setIdNextPage(cuisine.getId());
			ch9.setTexte("Aller dans la cuisine");
			ch9.create(partager_cle);
			ch9.create(perePieces);
			ch9.create(prendre_piece);
			ch9.create(cacher_cle);
			ch9.create(cherche_arbre);
			ch9.create(chambre);

			Choix ch10 = new Choix();
			ch10.setIdNextPage(fouiller_lave_vaisselle.getId());
			ch10.setTexte("Fouiller le lave vaiselle");
			ch10.setObjetRequis(LOUPE);
			ch10.create(cuisine);

			Choix ch11 = new Choix();
			ch11.setIdNextPage(fouiller_chambre.getId());
			ch11.setObjetRequis(CLE);
			ch11.setTexte("Fouiller la chambre");
			ch11.create(chambre);

			Choix ch12 = new Choix();
			ch12.setIdNextPage(prendre_piece.getId());
			ch12.setTexte("Prendre les pièces");
			ch12.create(fouiller_chambre);

			Choix ch13 = new Choix();
			ch13.setIdNextPage(interrogatoire.getId());
			ch13.setTexte("Aller interoger les suspects");
			ch13.create(cuisine);
			ch13.create(chambre);

			Choix ch14 = new Choix();
			ch14.setIdNextPage(listSuspect.getId());
			ch14.setTexte("Aller voir les suspects");
			ch14.create(interrogatoire);

			Choix ch15 = new Choix();
			ch15.setIdNextPage(intPere.getId());
			ch15.setTexte("Interroger le père");
			ch15.create(listSuspect);

			Choix ch16 = new Choix();
			ch16.setIdNextPage(intMari.getId());
			ch16.setTexte("Interroger le mari");
			ch16.create(listSuspect);

			Choix ch17 = new Choix();
			ch17.setIdNextPage(intFrere.getId());
			ch17.setTexte("Interroger le frère");
			ch17.create(listSuspect);

			Choix ch18 = new Choix();
			ch18.setIdNextPage(listSuspect.getId());
			ch18.setTexte("Interroger quelqu'un d'autre");
			ch18.create(intFrere);
			ch18.create(intMari);
			ch18.create(intPere);

			// ---------------------------------

			Choix ch19 = new Choix();
			ch19.setIdNextPage(pereCle.getId());
			ch19.setTexte("montrer clé");
			ch19.setObjetRequis(CLE);
			ch19.create(intPere);

			Choix ch20 = new Choix();
			ch20.setIdNextPage(frereCle.getId());
			ch20.setTexte("montrer clé");
			ch20.setObjetRequis(CLE);
			ch20.create(intFrere);

			Choix ch21 = new Choix();
			ch21.setIdNextPage(mariCle.getId());
			ch21.setTexte("montrer clé");
			ch21.setObjetRequis(CLE);
			ch21.create(intMari);

			// ---------------------------------

			Choix ch22 = new Choix();
			ch22.setIdNextPage(pereDouille.getId());
			ch22.setTexte("montrer douille");
			ch22.setObjetRequis(DOUILLE);
			ch22.create(intPere);

			Choix ch23 = new Choix();
			ch23.setIdNextPage(frereDouille.getId());
			ch23.setTexte("montrer douille");
			ch23.setObjetRequis(DOUILLE);
			ch23.create(intFrere);

			Choix ch24 = new Choix();
			ch24.setIdNextPage(mariDouille.getId());
			ch24.setTexte("montrer douille");
			ch24.setObjetRequis(DOUILLE);
			ch24.create(intMari);

			// ---------------------------------

			Choix ch25 = new Choix();
			ch25.setIdNextPage(pereCouteau.getId());
			ch25.setTexte("montrer couteau");
			ch25.setObjetRequis(COUTEAU);
			ch25.create(intPere);

			Choix ch26 = new Choix();
			ch26.setIdNextPage(frereCouteau.getId());
			ch26.setTexte("montrer couteau");
			ch26.setObjetRequis(COUTEAU);
			ch26.create(intFrere);

			Choix ch27 = new Choix();
			ch27.setIdNextPage(mariCouteau.getId());
			ch27.setTexte("montrer couteau");
			ch27.setObjetRequis(COUTEAU);
			ch27.create(intMari);

			// ---------------------------------

			Choix ch28 = new Choix();
			ch28.setIdNextPage(pereDocuments.getId());
			ch28.setTexte("montrer documents");
			ch28.setObjetRequis(DOCUMENTS);
			ch28.create(intPere);

			Choix ch29 = new Choix();
			ch29.setIdNextPage(frereDocuments.getId());
			ch29.setTexte("montrer documents");
			ch29.setObjetRequis(DOCUMENTS);
			ch29.create(intFrere);

			Choix ch30 = new Choix();
			ch30.setIdNextPage(mariDocuments.getId());
			ch30.setTexte("montrer documents");
			ch30.setObjetRequis(DOCUMENTS);
			ch30.create(intMari);

			// ---------------------------------

			Choix ch31 = new Choix();
			ch31.setIdNextPage(perePieces.getId());
			ch31.setTexte("montrer pieces");
			ch31.setObjetRequis(PIECES);
			ch31.create(intPere);

			Choix ch32 = new Choix();
			ch32.setIdNextPage(frerePieces.getId());
			ch32.setTexte("montrer pieces");
			ch32.setObjetRequis(PIECES);
			ch32.create(intFrere);

			Choix ch33 = new Choix();
			ch33.setIdNextPage(mariPieces.getId());
			ch33.setTexte("montrer pieces");
			ch33.setObjetRequis(PIECES);
			ch33.create(intMari);

			// --------------------------

			Choix ch34 = new Choix();
			ch34.setIdNextPage(intMari.getId());
			ch34.setTexte("Continuer l'interrogatoire");
			ch34.create(mariCle);
			ch34.create(mariDouille);
			ch34.create(mariDocuments);
			ch34.create(mariCouteau);
			ch34.create(mariPieces);

			Choix ch35 = new Choix();
			ch35.setIdNextPage(intFrere.getId());
			ch35.setTexte("Continuer l'interrogatoire");
			ch34.create(frereCle);
			ch34.create(frereDouille);
			ch34.create(frereDocuments);
			ch34.create(frereCouteau);
			ch34.create(frerePieces);

			Choix ch36 = new Choix();
			ch36.setIdNextPage(intPere.getId());
			ch36.setTexte("Continuer l'interrogatoire");
			ch36.create(pereCle);
			ch36.create(pereDouille);
			ch36.create(pereDocuments);
			ch36.create(pereCouteau);
			ch36.create(perePieces);

			// --------------------------------------

			Choix ch37 = new Choix();
			ch37.setIdNextPage(accusation.getId());
			ch37.setTexte("Passer à l'accusation");
			ch37.create(listSuspect);

			// -----------------------------------

			Choix ch38 = new Choix();
			ch38.setIdNextPage(compliceDeFrere.getId());
			ch38.setTexte("Le frère");
			ch38.create(accusation);

			Choix ch39 = new Choix();
			ch39.setIdNextPage(compliceDePere.getId());
			ch39.setTexte("Le père");
			ch39.create(accusation);

			Choix ch40 = new Choix();
			ch40.setIdNextPage(compliceDeMari.getId());
			ch40.setTexte("Le Mari");
			ch40.create(accusation);

			// -------------------------------

			Choix ch41 = new Choix();
			ch41.setIdNextPage(mobileNOK.getId());
			ch41.setTexte("Le frère");
			ch41.create(compliceDeMari);

			Choix ch42 = new Choix();
			ch42.setIdNextPage(mobileNOK.getId());
			ch42.setTexte("Le père");
			ch42.create(compliceDeMari);

			// -------------------------------

			Choix ch43 = new Choix();
			ch43.setIdNextPage(mobileOK.getId());
			ch43.setTexte("Le père");
			ch43.create(compliceDeFrere);

			Choix ch44 = new Choix();
			ch44.setIdNextPage(mobileNOK.getId());
			ch44.setTexte("Le mari");
			ch44.create(compliceDeFrere);

			// -------------------------------

			Choix ch45 = new Choix();
			ch45.setIdNextPage(mobileOK.getId());
			ch45.setTexte("Le frère");
			ch45.create(compliceDePere);

			Choix ch46 = new Choix();
			ch46.setIdNextPage(mobileNOK.getId());
			ch46.setTexte("Le mari");
			ch46.create(compliceDePere);

			// ---------------------------------

			Choix ch47 = new Choix();
			ch47.setIdNextPage(finNOK.getId());
			ch47.setTexte("La maladie du père");
			ch47.create(mobileNOK);

			Choix ch48 = new Choix();
			ch48.setIdNextPage(finNOK.getId());
			ch48.setTexte("Le divorce du mari");
			ch48.create(mobileNOK);

			Choix ch49 = new Choix();
			ch49.setIdNextPage(finNOK.getId());
			ch49.setTexte("Dispute avec son frère");
			ch49.create(mobileNOK);

			// ------------------------------

			Choix ch50 = new Choix();
			ch50.setIdNextPage(finOK.getId());
			ch50.setTexte("La maladie du père");
			ch50.create(mobileOK);

			Choix ch51 = new Choix();
			ch51.setIdNextPage(finNOK.getId());
			ch51.setTexte("Le divorce du mari");
			ch51.create(mobileOK);

			Choix ch52 = new Choix();
			ch52.setIdNextPage(finNOK.getId());
			ch52.setTexte("Dispute avec son frère");
			ch52.create(mobileOK);

			livre.updateFirstPage(porche.getId());
		}
	}

	public void insertVictorEtLeChateau()
	{
		// Livre.deleteAll();
		if (!Livre.exists("Victor et le château"))
		{
			Livre victor = new Livre("Victor et le château", "Alexandra", "Victor est perdu dans une forêt. Craignant l'orage qui arrive, il se réfugie dans un château. Mais ce château est sous l'emprise d'un maléfice...", 0);
			victor.create();

			final long MAGIE = 37;
			final long SANTE = 38;
			final long VIGUEUR = 39;
			final long AMETHYSTE = 2;
			final long EPEE = 19;
			final long BOUCLIER = 6;
			final long CLE = 10;
			final long OR = 33;
			final long CARTE = 7;
			final long JAMBON = 24;
			final long ANNEAU = 3;

			// ------------PAGES------------
			Page page1 = new Page();
			page1.setTexte("Victor errait dans cette épaisse forêt qui l'entourait. Plus il marchait, et plus il avait l'impression de s'éloigner de son but. Chaque arbre qu'il croisait lui donnait le sentiment d'être déjà passé par ici. Cette fois c'était sûr, il était inévitablement perdu. \n" + "Le ciel menaçait d'éclater en un terrible orage. Les nuages étaient si noirs qu'il faisait presque nuit. A travers le feuillage des arbres, par chance, Victor aperçut un château perché sur une colline. Il s'y dirigea aussitôt, de peur de devoir passer la nuit dans cette forêt qui commençait à l'effrayer, et sous l'orage. ");
			page1.setNumero(1);
			page1.create(victor);

			Page page2 = new Page();
			page2.setTexte("Arrivé au château, Victor se sentit ridiculement petit devant les immenses portes qui se tenaient devant lui. Il frappa. Personne ne répondit. Il entendait au loin la foudre se rapprocher. Il frappa une seconde fois, plus insistant. Rien.");
			page2.setNumero(2);
			page2.create(victor);

			Page page3 = new Page();
			page3.setTexte("Victor poussa l'impressionnante porte, un grincement sinistre s'échappa, puis il rentra dans le château. Aussitôt, un sentiment d'angoisse le saisit. L'ambiance était si glaciale qu'un frisson le parcourut de la tête aux pieds. " + "\nIl avança timidement dans l'entrée. Sur un mur, un parchemin était accroché.");
			page3.setNumero(3);
			page3.create(victor);

			Page page4 = new Page();
			page4.setTexte("Victor s'approcha du parchemin pour le lire.\n" + "« <i>Méfie-toi, étranger ! Tu es ici dans la demeure du prince Philippe. Et tu ne pourras en sortir que si tu libères ce château du sortilège qui l'habite ! Seras-tu assez téméraire pour t'aventurer dans tous ses couloirs ? Seras-tu assez malin pour résoudre les énigmes qui s'y cachent ? </i>»" + "\nVictor prit peur un instant. De toute façon, il n'avait plus tellement le choix…");
			page4.setNumero(4);
			page4.create(victor);

			Page page5 = new Page();
			page5.setTexte("Victor entra dans la pièce principale du château, dans laquelle il pouvait voir 3 portes face à lui : une à gauche, une au milieu, une à droite. La porte du milieu semblait verrouillée. Dans un coin de la pièce se tenait un sorcier pas très avenant, et une table sur laquelle se dressait un sac contenant des objets brillants attirait l'attention de notre jeune homme.");
			page5.setNumero(5);
			page5.create(victor);

			Page page6 = new Page();
			page6.setTexte("Victor se dirigea vers le sorcier. \n" + "Le sac sur la table contenait des centaines d'améthystes.\n" + "Le sorcier marmonna : « Mmmh… que veux-tu ? Si tu n'as pas d'argent, tu ne m'intéresses pas !»");
			page6.setNumero(6);
			page6.create(victor);

			Page page7 = new Page();
			page7.setTexte("Le sorcier déploya son manteau noir. \n « Quelle potion t'intéresse ? »");
			page7.setNumero(7);
			page7.create(victor);

			Page page8 = new Page();
			page8.setTexte("Le sorcier donna la potion à Victor, pris les pièces sèchement, puis retourna s'asseoir. ");
			page8.setNumero(8);
			page8.setObjetDonne(VIGUEUR);
			page8.setObjetSupprime(OR);
			page8.create(victor);

			Page page9 = new Page();
			page9.setTexte("Le sorcier donna la potion à Victor, pris les pièces sèchement, puis retourna s'asseoir. ");
			page9.setNumero(9);
			page9.setObjetDonne(MAGIE);
			page9.setObjetSupprime(OR);
			page9.create(victor);

			Page page10 = new Page();
			page10.setTexte("Le sorcier donna la potion à Victor, pris les pièces sèchement, puis retourna s'asseoir. ");
			page10.setNumero(10);
			page10.setObjetDonne(SANTE);
			page10.setObjetSupprime(OR);
			page10.create(victor);

			Page page12 = new Page();
			page12.setTexte("Victor mis une améthyste dans sa poche.");
			page12.setNumero(12);
			page12.setObjetDonne(AMETHYSTE);
			page12.create(victor);

			Page page13 = new Page();
			page13.setTexte("Victor ouvrit la porte de gauche. Il marchait dans le couloir bâti de pierres froides. Soudain, la route se coupa sous ses pieds. Il y avait un obstacle en plein milieu du couloir dans lequel il s'aventurait.  En s'approchant, ce que vit Victor lui glaça le sang : le sol était jonché d'épines, et des lianes tapissaient le plafond. Victor allait devoir faire un choix pour passer…");
			page13.setNumero(13);
			page13.create(victor);

			Page page14 = new Page();
			page14.setTexte("Victor marchait avec prudence pour éviter de se blesser sur les épines. Chaque pas qu'il faisait était réfléchi et calculé. Au bout de quelques mètres, il atteignit l'autre bout et poursuivit son chemin.\n" + "Le couloir le dirigeait vers une pièce.");
			page14.setNumero(14);
			page14.create(victor);

			Page page15 = new Page();
			page15.setTexte("Victor s'accrocha comme il le pouvait aux lianes suspendues par dessus les épines aux dards menaçants. Il essayait d'avancer en s'agrippant à l'aide d'un bras, puis l'autre. Pensant avoir une prise solide, il continua sa course, mais la liane qu'il venait d'attraper semblait fragile et se rompit sous son poids. Victor tomba et sa chute sur les épines lui fut fatale.");
			page15.setNumero(15);
			page15.create(victor);

			Page page16 = new Page();
			page16.setTexte("Victor s'avança dans la pièce, dans laquelle il pouvait voir une carte, gisant sur le sol.");
			page16.setNumero(16);
			page16.create(victor);

			Page page17 = new Page();
			page17.setTexte("Victor saisit la carte au sol.");
			page17.setObjetDonne(CARTE);
			page17.setNumero(17);
			page17.create(victor);

			Page page18 = new Page();
			page18.setTexte("Victor continua de marcher dans ce couloir qui lui semblait interminable. Il tomba dans une pièce qui semblait être le garde-manger. Des pièces de viandes suspendues au plafond, des bouteilles de vin ainsi que des vieux fromages remplissaient la pièce et une odeur appétissante s'en dégageait.");
			page18.setNumero(18);
			page18.create(victor);

			Page page19 = new Page();
			page19.setTexte("Après avoir mis le jambon dans son sac, Victor sortit du garde-manger, puis arriva à une intersection. Deux chemins s'offraient à lui : un qui semblait être un raccourci, s'il en croit la lumière qu'il peut apercevoir au bout ; et l'autre, qui avait l'air beaucoup plus long.");
			page19.setNumero(19);
			page19.setObjetDonne(JAMBON);
			page19.create(victor);

			Page page20 = new Page();
			page20.setTexte("Sur son chemin, Victor trouva une petite clé qu'il mit dans sa poche. Au bout du couloir, il se retrouva de nouveau dans la pièce principale du château.");
			page20.setNumero(20);
			page20.setObjetDonne(CLE);
			page20.create(victor);

			Page page21 = new Page();
			page21.setTexte("Sur son chemin, Victor trouva des pièces d'or qu'il mit dans sa poche. Au bout du long couloir, il se retrouva de nouveau dans la pièce principale du château.");
			page21.setNumero(21);
			page21.setObjetDonne(OR);
			page21.create(victor);

			Page page22 = new Page();
			page22.setTexte("Victor se coupa un morceau de fromage qu'il engloutit aussitôt. Ce fromage était très goûteux. Un sentiment étrange envahit Victor, comme s'il faisait une chose interdite.\n" + "Là, le sol se mit à trembler, et une voix effrayante résonnait dans les couloirs du château « <i>Qui ose manger la nourriture de mon château ?</i> »\n" + "Le sol tremblait de plus en plus fort, puis les murs à leur tour. La pièce s'écroulait ! Une pierre se détacha du plafond et cogna la tête de notre héros, qui ne se releva pas.");
			page22.setNumero(22);
			page22.create(victor);

			Page page23 = new Page();
			page23.setTexte("La carte était très abîmée, mais Victor pouvait tout de même distinguer certains éléments. Dans un texte à côté du plan du château, il pouvait lire « La statue qui retrouvera l’œil manquant reprendra vie. »\n" + "Qu'est-ce que cela pouvait signifier ?\n" + "A peine eut-il le temps de lire cette phrase que la carte se désintégra dans ses mains.");
			page23.setObjetSupprime(CARTE);
			page23.setNumero(23);
			page23.create(victor);

			Page page24 = new Page();
			page24.setTexte("Victor poussa la porte de droite. Cette fois encore, il se retrouva dans un couloir sombre. Il pouvait entendre un bruit sourd au loin… Plus il avançait, plus le bruit se rapprochait. Peu à peu, il pouvait distinguer une silhouette disgracieuse devant lui, là d'où venait le bruit, probablement. Lorsqu'il était assez proche, Victor eut un frisson : il était nez à nez avec une créature des plus effrayantes. Elle était verte, poilue, gigantesque, et d'après ses grognements, de mauvaise humeur. Ses petits yeux jaunes le regardaient.");
			page24.setNumero(24);
			page24.create(victor);

			Page page25 = new Page();
			page25.setTexte("Victor se rappela qu'il avait une pièce de jambon dans son sac. Il la sortit, et l'odeur intéressa la créature. Il la renifla, et l'engloutit. La créature avait l'air de se sentir mieux, et dégagea le chemin pour laisser notre héros continuer sa route.");
			page25.setNumero(25);
			page25.setObjetSupprime(JAMBON);
			page25.create(victor);

			Page page26 = new Page();
			page26.setTexte("Victor s'engouffra dans le long couloir qui s'élançait devant lui. Il aperçut un grimoire ouvert, posé sur une table.");
			page26.setNumero(26);
			page26.create(victor);

			Page page27 = new Page();
			page27.setTexte("Victor s'approcha du grimoire pour le lire. Un brouillard dense s'en échappa Victor fut aspiré dans le livre… Pour en sortir, il devait répondre à une énigme dont les lettres flottaient dans les airs face à lui. \n" + "<i>Un bourreau laisse à un condamné le choix de sa mort :\n" + "- écrasé sous une boule de cinq tonnes\n" + "- se faire dévorer par des lions qui n'ont pas mangé depuis cinq mois\n" + "- être décapité une nuit de pleine lune\n" + "- être mangé vivant par une tribu de cannibales\n" + "Quel est le meilleur choix à faire ?</i>");
			page27.setNumero(27);
			page27.create(victor);

			Page page28 = new Page();
			page28.setTexte("Le livre recracha Victor qui tomba sur le sol. Il se releva et se rendit compte qu'un bouclier était tombé à côté de lui.");
			page28.setNumero(28);
			page28.create(victor);

			Page page29 = new Page();
			page29.setTexte("Victor pris le bouclier, puis poursuivit son chemin. Il arriva de nouveau à une intersection.");
			page29.setNumero(29);
			page29.setObjetDonne(BOUCLIER);
			page29.create(victor);

			Page page30 = new Page();
			page30.setTexte("Victor marchait dans le long couloir, lorsqu'un objet brillant attira son attention. Par terre, il y avait un anneau en or. Victor le saisit et le mit dans sa poche, pensant qu'il pourrait lui être utile.\n" + "Il se retrouva dans la pièce principale du château.");
			page30.setNumero(30);
			page30.setObjetDonne(ANNEAU);
			page30.create(victor);

			Page page31 = new Page();
			page31.setTexte("Victor essaya de passer sous la créature qui, d'un coup de griffe, infligea à notre héros de graves blessures.");
			page31.setNumero(31);
			page31.create(victor);

			Page page32 = new Page();
			page32.setTexte("Victor se retrouva dans la pièce principale du château.");
			page32.setNumero(32);
			page32.create(victor);

			Page page33 = new Page();
			page33.setTexte("Le livre recracha Victor, qui avait mal répondu à l'énigme. Il se releva, un peu secoué, puis poursuivit son chemin.Il arriva de nouveau à une intersection.");
			page33.setNumero(33);
			page33.create(victor);

			Page page34 = new Page();
			page34.setTexte("Grâce à la clé qu'il avait trouvée, Victor put ouvrir la porte du milieu. Il arriva dans une pièce dans laquelle un fantôme, une silhouette de femme, était assise et pleurait. Elle était vêtue d'une robe de mariée déchirée et abîmée. Victor pouvait apercevoir un coffre à travers le fantôme. ");
			page34.setNumero(34);
			page34.create(victor);

			Page page35 = new Page();
			page35.setTexte("Voyant que Victor essayait de passer, la mariée se mit dans une colère noire et cria tellement fort que Victor fut projeté dans la salle principale du château.");
			page35.setNumero(35);
			page35.create(victor);

			Page page36 = new Page();
			page36.setTexte("La défunte mariée pris l'anneau que lui tendait Victor. Là, elle se mit à rire et danser allègrement à travers la pièce, puis elle disparut.\n" + "Victor s'avança alors vers le coffre pour l'ouvrir, et découvrit une épée, qu'il saisit aussitôt.");
			page36.setNumero(36);
			page36.setObjetSupprime(ANNEAU);
			page36.setObjetDonne(EPEE);
			page36.create(victor);

			Page page37 = new Page();
			page37.setTexte("Victor retourna dans la pièce principale du château. En plein milieu, un escalier avait  fait son apparition.");
			page37.setNumero(37);
			page37.create(victor);

			Page page38 = new Page();
			page38.setTexte("Victor se dirigea vers le sorcier. \n" + "Le sac sur la table contenait des centaines d'améthystes.\n" + "Le sorcier marmonna : « Mmmh… que veux-tu ? Si tu n'as pas d'argent, tu ne m'intéresses pas !»");
			page38.setNumero(38);
			page38.create(victor);

			Page page39 = new Page();
			page39.setTexte("Victor mis une améthyste dans sa poche.");
			page39.setNumero(39);
			page39.setObjetDonne(AMETHYSTE);
			page39.create(victor);

			Page page40 = new Page();
			page40.setTexte("Le sorcier ouvrit son manteau noir. \n" + "« Quelle potion t'intéresse ? »");
			page40.setNumero(40);
			page40.create(victor);

			Page page41 = new Page();
			page41.setTexte("Le sorcier donna la potion à Victor, pris les pièces sèchement, puis retourna s'asseoir. ");
			page41.setNumero(41);
			page41.setObjetSupprime(OR);
			page41.setObjetDonne(VIGUEUR);
			page41.create(victor);

			Page page42 = new Page();
			page42.setTexte("Le sorcier donna la potion à Victor, pris les pièces sèchement, puis retourna s'asseoir. ");
			page42.setNumero(42);
			page42.setObjetSupprime(OR);
			page42.setObjetDonne(SANTE);
			page42.create(victor);

			Page page43 = new Page();
			page43.setTexte("Le sorcier donna la potion à Victor, pris les pièces sèchement, puis retourna s'asseoir. ");
			page43.setNumero(43);
			page43.setObjetSupprime(OR);
			page43.setObjetDonne(MAGIE);
			page43.create(victor);

			Page page44 = new Page();
			page44.setTexte("Victor gravit une à une les marches de cet étrange escalier qui venait d'apparaître. Tout en haut, il atteignit une grande salle vide, qui ne contenait qu'une statue représentant un très grand homme en armure, assis sur un trône, au centre de la pièce. Victor s'approcha pour la regarder de plus près… il lui manquait un œil.");
			page44.setNumero(44);
			page44.create(victor);

			Page page45 = new Page();
			page45.setObjetSupprime(AMETHYSTE);
			page45.setTexte("Victor pris l'améthyste qu'il avait mis dans sa poche, et la plaça sur l’œil manquant de la statue. Là, la statue prit vie : l'homme s'anima, se leva de son trône et sortit son épée, dans le but d'attaquer le jeune homme.");
			page45.setNumero(45);
			page45.create(victor);

			Page page46 = new Page();
			page46.setTexte("Victor se protégea avec son épée, mais le coup du chevalier était si fort que la lame se brisa. ");
			page46.setObjetSupprime(EPEE);
			page46.setNumero(46);
			page46.create(victor);

			Page page47 = new Page();
			page47.setTexte("Victor eut alors une idée : il ouvrit la potion de magie et la versa sur le chevalier. La colère de ce dernier s'arrêta net, et il se transforma peu à peu : il rétrécissait jusqu'à regagner la taille d'un homme.\n" + "« Merci, brave garçon ! Tu m'as délivré du sortilège que des sorcières ont lancé sur mon château et sur moi ! Comment te remercier ? »");
			page47.setObjetSupprime(MAGIE);
			page47.setNumero(47);
			page47.create(victor);

			Page page48 = new Page();
			page48.setTexte("Victor se souvint qu'il avait acheté une potion de vigueur au sorcier dans la pièce principale. Il la sortit, et l'avala d'une traite. Il sentit alors une force le gagner, et frappa l'immense chevalier de toutes ses forces ! Sous le coup du jeune homme, le chevalier tomba, sa colère s'arrêta net, et il se transforma peu à peu : il rétrécissait jusqu'à regagner la taille d'un homme.\n" + "« Merci, brave garçon ! Tu m'as délivré du sortilège que des sorcières ont lancé sur mon château et sur moi ! Comment te remercier ? »");
			page48.setNumero(48);
			page48.setObjetSupprime(VIGUEUR);
			page48.create(victor);

			Page page49 = new Page();
			page49.setTexte("Victor leva son bouclier, mais le coup du chevalier géant était si fort que le jeune homme s'écroula au sol.");
			page49.setNumero(49);
			page49.create(victor);

			Page page50 = new Page();
			page50.setTexte("Le chevalier, pensant avoir vaincu son ennemi, se retourna et marcha lentement vers son trône.  Pendant ce temps, à bout de force, Victor saisit la potion de santé qu'il gardait dans son sac. Il la but, et sentit immédiatement ses effets agir.\n" + "L'armure du colosse laissait entrevoir une partie de son dos qui n'était pas protégée. Victor se releva et frappa de toutes ses forces le chevalier qui lui tournait le dos. Sous le coup du jeune homme, le chevalier tomba, et il se transforma peu à peu : il rétrécissait jusqu'à regagner la taille d'un homme.\n" + "« Merci, brave garçon ! Tu m'as délivré du sortilège que des sorcières ont lancé sur mon château et sur moi ! Comment te remercier ? »");
			page50.setNumero(50);
			page50.setObjetSupprime(SANTE);
			page50.create(victor);

			// ------------CHOIX------------
			Choix marcher = new Choix();
			marcher.setTexte("Marcher jusqu'au château");
			marcher.setIdNextPage(page2.getId());
			marcher.create(page1);

			Choix pousser = new Choix();
			pousser.setTexte("Pousser la porte");
			pousser.setIdNextPage(page3.getId());
			pousser.create(page2);

			Choix lire = new Choix();
			lire.setTexte("Lire le parchemin");
			lire.setIdNextPage(page4.getId());
			lire.create(page3);

			Choix avancer = new Choix();
			avancer.setTexte("Avancer dans la pièce principale ");
			avancer.setIdNextPage(page5.getId());
			avancer.create(page3);

			Choix avancer2 = new Choix();
			avancer2.setTexte("Avancer dans la pièce principale ");
			avancer2.setIdNextPage(page5.getId());
			avancer2.create(page4);

			Choix gauche = new Choix();
			gauche.setTexte("Ouvrir la porte de gauche");
			gauche.setIdNextPage(page13.getId());
			gauche.create(page5);
			gauche.create(page20);
			gauche.create(page21);
			gauche.create(page30);
			gauche.create(page32);
			gauche.create(page35);

			Choix milieu = new Choix();
			milieu.setTexte("Déverrouiller la porte du milieu");
			milieu.setIdNextPage(page34.getId());
			milieu.setObjetRequis(CLE);
			milieu.create(page5);
			milieu.create(page20);
			milieu.create(page21);
			milieu.create(page30);
			milieu.create(page32);
			milieu.create(page35);

			Choix droite = new Choix();
			droite.setTexte("Ouvrir la porte de droite");
			droite.setIdNextPage(page24.getId());
			droite.create(page5);
			droite.create(page20);
			droite.create(page21);
			droite.create(page30);
			droite.create(page32);
			droite.create(page35);

			Choix sorcier = new Choix();
			sorcier.setTexte("Aller vers le sorcier");
			sorcier.setIdNextPage(page6.getId());
			sorcier.create(page5);
			sorcier.create(page20);
			sorcier.create(page21);
			sorcier.create(page30);
			sorcier.create(page32);
			sorcier.create(page35);

			Choix amethyste = new Choix();
			amethyste.setTexte("Prendre une améthyste");
			amethyste.setIdNextPage(page12.getId());
			amethyste.create(page6);

			Choix retourner = new Choix();
			retourner.setTexte("Retourner au centre de la pièce");
			retourner.setIdNextPage(page5.getId());
			retourner.create(page6);

			Choix acheter = new Choix();
			acheter.setTexte("Acheter une potion");
			acheter.setObjetRequis(OR);
			acheter.setIdNextPage(page7.getId());
			acheter.create(page6);
			acheter.create(page20);

			Choix vigueur = new Choix();
			vigueur.setTexte("Potion de vigueur");
			vigueur.setIdNextPage(page8.getId());
			vigueur.create(page7);

			Choix sante = new Choix();
			sante.setTexte("Potion de santé");
			sante.setIdNextPage(page10.getId());
			sante.create(page7);

			Choix magie = new Choix();
			magie.setTexte("Potion de magie");
			magie.setIdNextPage(page9.getId());
			magie.create(page7);

			Choix centre = new Choix();
			centre.setIdNextPage(page5.getId());
			centre.setTexte("Retourner au centre de la pièce");

			centre.create(page8);
			centre.create(page9);
			centre.create(page10);
			centre.create(page12);

			Choix epines = new Choix();
			epines.setTexte("Essayer de marcher entre les épines");
			epines.setIdNextPage(page14.getId());
			epines.create(page13);

			Choix lianes = new Choix();
			lianes.setTexte("Essayer de traverser en s'accrochant aux lianes");
			lianes.setIdNextPage(page15.getId());
			lianes.create(page13);

			Choix entrer = new Choix();
			entrer.setTexte("Entrer dans la pièce");
			entrer.setIdNextPage(page16.getId());
			entrer.create(page14);

			Choix continuer = new Choix();
			continuer.setTexte("Continuer son chemin");
			continuer.setIdNextPage(page18.getId());
			continuer.create(page14);
			continuer.create(page16);
			continuer.create(page23);

			Choix prendreCarte = new Choix();
			prendreCarte.setTexte("Prendre la carte");
			prendreCarte.setIdNextPage(page17.getId());
			prendreCarte.create(page16);

			Choix lireCarte = new Choix();
			lireCarte.setTexte("Lire la carte");
			lireCarte.setIdNextPage(page23.getId());
			lireCarte.create(page17);

			Choix prendreJambon = new Choix();
			prendreJambon.setTexte("Prendre un jambon");
			prendreJambon.setIdNextPage(page19.getId());
			prendreJambon.create(page18);

			Choix fromage = new Choix();
			fromage.setTexte("Manger un morceau de fromage");
			fromage.setIdNextPage(page22.getId());
			fromage.create(page18);

			Choix raccourci = new Choix();
			raccourci.setTexte("Prendre le raccourci");
			raccourci.setIdNextPage(page20.getId());
			raccourci.create(page19);

			Choix chemin = new Choix();
			chemin.setTexte("Prendre le long chemin");
			chemin.setIdNextPage(page21.getId());
			chemin.create(page19);

			Choix ouvrirGauche = new Choix();
			ouvrirGauche.setTexte("Ouvrir la porte de gauche");
			ouvrirGauche.setIdNextPage(page13.getId());
			ouvrirGauche.create(page20);

			Choix passer = new Choix();
			passer.setTexte("Passer en dessous de la créature");
			passer.setIdNextPage(page31.getId());
			passer.create(page24);

			Choix demitour = new Choix();
			demitour.setTexte("Faire demi-tour");
			demitour.setIdNextPage(page32.getId());
			demitour.create(page24);

			Choix donnerJambon = new Choix();
			donnerJambon.setTexte("Donner le jambon à la créature");
			donnerJambon.setIdNextPage(page25.getId());
			donnerJambon.setObjetRequis(JAMBON);
			donnerJambon.create(page24);

			Choix continuer2 = new Choix();
			continuer2.setTexte("Continuer son chemin");
			continuer2.setIdNextPage(page26.getId());
			continuer2.create(page25);

			Choix grimoire = new Choix();
			grimoire.setTexte("S'approcher du grimoire");
			grimoire.setIdNextPage(page27.getId());
			grimoire.create(page26);

			Choix continuer3 = new Choix();
			continuer3.setTexte("Continuer son chemin");
			continuer3.setIdNextPage(page30.getId());
			continuer3.create(page26);

			Choix boule = new Choix();
			boule.setTexte("Écrasé sous la boule");
			boule.setIdNextPage(page33.getId());
			boule.create(page27);

			Choix lion = new Choix();
			lion.setTexte("Dévoré par les lions");
			lion.setIdNextPage(page28.getId());
			lion.create(page27);

			Choix decapite = new Choix();
			decapite.setTexte("Décapité");
			decapite.setIdNextPage(page33.getId());
			decapite.create(page27);

			Choix mange = new Choix();
			mange.setTexte("Mangé vivant");
			mange.setIdNextPage(page33.getId());
			mange.create(page27);

			Choix ramasser = new Choix();
			ramasser.setTexte("Ramasser le bouclier");
			ramasser.setIdNextPage(page29.getId());
			ramasser.create(page28);

			Choix continuer4 = new Choix();
			continuer4.setTexte("Continuer son chemin");
			continuer4.setIdNextPage(page30.getId());
			continuer4.create(page28);

			Choix raccourci2 = new Choix();
			raccourci2.setTexte("Prendre le raccourci");
			raccourci2.setIdNextPage(page32.getId());
			raccourci2.create(page29);
			raccourci2.create(page33);

			Choix longchemin = new Choix();
			longchemin.setTexte("Prendre le plus long chemin");
			longchemin.setIdNextPage(page30.getId());
			longchemin.create(page29);
			longchemin.create(page33);

			Choix atteindre = new Choix();
			atteindre.setTexte("Passer pour atteindre le coffre");
			atteindre.setIdNextPage(page35.getId());
			atteindre.create(page34);

			Choix donnerAnneau = new Choix();
			donnerAnneau.setTexte("Donner l'anneau");
			donnerAnneau.setObjetRequis(ANNEAU);
			donnerAnneau.setIdNextPage(page36.getId());
			donnerAnneau.create(page34);

			Choix retourner3 = new Choix();
			retourner3.setTexte("Retourner dans la pièce principale");
			retourner3.setIdNextPage(page37.getId());
			retourner3.create(page36);

			Choix monter = new Choix();
			monter.setTexte("Monter l'escalier");
			monter.setIdNextPage(page44.getId());
			monter.create(page37);

			Choix sorcier2 = new Choix();
			sorcier2.setTexte("Aller vers le sorcier");
			sorcier2.setIdNextPage(page38.getId());
			sorcier2.create(page37);

			Choix amethyste2 = new Choix();
			amethyste2.setTexte("Prendre une améthyste");
			amethyste2.setIdNextPage(page39.getId());
			amethyste2.create(page38);

			Choix retourner5 = new Choix();
			retourner5.setTexte("Retourner au centre de la pièce");
			retourner5.setIdNextPage(page37.getId());
			retourner5.create(page38);
			retourner5.create(page39);
			retourner5.create(page41);
			retourner5.create(page42);
			retourner5.create(page43);
			retourner5.create(page44);

			Choix potion = new Choix();
			potion.setTexte("Acheter une potion");
			potion.setIdNextPage(page40.getId());
			potion.setObjetRequis(OR);
			potion.create(page38);

			Choix vigueur2 = new Choix();
			vigueur2.setTexte("Potion de vigueur");
			vigueur2.setIdNextPage(page41.getId());
			vigueur2.create(page40);

			Choix sante2 = new Choix();
			sante2.setTexte("Potion de santé");
			sante2.setIdNextPage(page42.getId());
			sante2.create(page40);

			Choix magie2 = new Choix();
			magie2.setTexte("Potion de magie");
			magie2.setIdNextPage(page43.getId());
			magie2.create(page40);

			Choix oeil = new Choix();
			oeil.setTexte("Mettre l'améthyste sur l'oeil manquant");
			oeil.setObjetRequis(AMETHYSTE);
			oeil.setIdNextPage(page45.getId());
			oeil.create(page44);

			Choix proteger = new Choix();
			proteger.setTexte("Se protéger avec son bouclier");
			proteger.setIdNextPage(page49.getId());
			proteger.setObjetRequis(BOUCLIER);
			proteger.create(page45);

			Choix sedefendre = new Choix();
			sedefendre.setTexte("Se défendre avec son épée");
			sedefendre.setIdNextPage(page46.getId());
			sedefendre.setObjetRequis(EPEE);
			sedefendre.create(page45);

			Choix lancerMagie = new Choix();
			lancerMagie.setTexte("Lancer la potion de magie");
			lancerMagie.setObjetRequis(MAGIE);
			lancerMagie.setIdNextPage(page47.getId());
			lancerMagie.create(page46);

			Choix boireVigueur = new Choix();
			boireVigueur.setTexte("Boire la potion de vigueur");
			boireVigueur.setObjetRequis(VIGUEUR);
			boireVigueur.setIdNextPage(page48.getId());
			boireVigueur.create(page46);

			Choix boireSante = new Choix();
			boireSante.setTexte("Boire la potion de santé");
			boireSante.setObjetRequis(SANTE);
			boireSante.setIdNextPage(page49.getId());
			boireSante.create(page48);

			victor.updateFirstPage(page1.getId());

		}
	}

	public void insertElodieBook()
	{

		final long AMETHYSTE = 2;
		final long EPEE = 19;
		final long BOUCLIER = 6;
		final long CLE = 10;
		final long OR = 33;
		final long CARTE = 7;
		final long JAMBON = 24;
		final long FROMAGE = 21;
		final long LIVRE = 26;
		final long SANTE = 38;

		if (!Livre.exists("Une forêt plutôt hostile"))
		{
			Livre livre = new Livre();

			int numero = 1;

			livre.setTitre("Une forêt plutôt hostile");
			livre.setAuteur("Elodie");
			livre.setSynopsis("Une quête épique vous attend. Des malandrins vous ont dérobé vos précieuses affaires, et se sont réfugiés au plus profond d'une forêt terrifiante. Saurez vous traquer tous les malfrats et ainsi récupérer l'intégralité de vos trésors ? ");
			livre.setEditable(0);
			livre.create();

			// ----------Pages------------

			Page intro = new Page();
			intro.setNumero(numero);
			numero++;
			intro.setTexte("Vous vous réveillez étendu par terre de manière fort peu grâcieuse. Votre tête vous fait souffrir atrocement mais vos vagues souvenirs de la veille vous indiquent que l'origine de ce mal de tête n'est pas un coup vicieusement porté derrière la nuque. " + "En effet, la dernière chose dont vous vous rappelez est d'avoir accepté un concours de boisson lancé par un inconnu provocateur, dans le but de montrer à cet inconnu ce dont un vrai nordique est capable.Vous décidez de tenter de vous lever.");
			intro.create(livre);

			Page seLever = new Page();
			seLever.setNumero(numero);
			numero++;
			seLever.setTexte("Malgré votre crâne qui vous semble peser une bonne trentaine de kilos, vous vous levez. Le monde se met à tourner autour de vous. " + "Il est grand temps de prendre une potion de santé pour remettre vos idées en place.");
			seLever.create(livre);

			Page prendPotion = new Page();
			prendPotion.setNumero(numero);
			numero++;
			prendPotion.setTexte("En portant votre main à votre sacoche, vous vous apercevez qu'elle ne contient plus une seule potion. D'ailleurs, elle ne contient rien du tout. " + "Furieux, vous en déduisez que l'on vous a lâchement détroussé pendant que vous reposiez îvre mort sur le sol de cette forêt. La colère chassant momentanément votre mal de crâne, vous décidez de retrouver les voleurs afin de leur donner une bonne leçon. " + "Deux chemins s'offrent à vous. L'un part vers votre droite et paraît s'enfoncer au plus profond de la forêt. L'autre semble plus sûr, plus lumineux, et traverse la clairière dans laquelle vous vous trouvez.");
			prendPotion.create(livre);

			Page cheminObs = new Page();
			cheminObs.setNumero(numero);
			numero++;
			cheminObs.setTexte("Vous marchez quelques dizaines de mètres avant d'arriver devant une petite cabane qui vous semble abandonnée. Vous pouvez tenter de vous aventurer à l'intérieur de la cabane, ou bien passer votre chemin.");
			cheminObs.create(livre);

			Page cheminNord = new Page();
			cheminNord.setNumero(numero);
			numero++;
			cheminNord.setTexte("Vous arrivez à un embranchement. Le chemin au nord semble mener vers une autre clairière, plus petite que la première, le chemin à l'est vers une rivière, et le chemin au sud vous ramène près de votre position de départ.");
			cheminNord.create(livre);

			Page cheminSud = new Page();
			cheminSud.setNumero(numero);
			numero++;
			cheminSud.setTexte("Vous arrivez dans ce qui paraît être un cul-de-sac.");
			cheminSud.create(livre);

			Page entreCabane = new Page();
			entreCabane.setNumero(numero);
			numero++;
			entreCabane.setTexte("Vous commencez par frapper à la porte, puis, n'entendant pas de réponse, vous poussez la porte. Elle s'ouvre sans résistance. À l'intérieur de la cabane se trouvent un lit, un chaudron et un coffre.");
			entreCabane.create(livre);

			Page poursuitChemin = new Page();
			poursuitChemin.setNumero(numero);
			numero++;
			poursuitChemin.setTexte("Vous continuez votre chemin, chemin qui par ailleurs est de plus en plus sombre, si bien que vous n'arrivez bientôt plus à discerner l'endroit ou vous posez vos pieds. Soudain le sol se dérobe sous vos pieds. " + "Après avoir glissé sur le chemin boueux pendant une dizaine de mètres, vous atterrissez brutalement. Vous vous relevez en essayant de garder une once de dignité, puis regardez les alentours. La pente abrupte ne vous autorise pas à faire demi-tour. Vous entendez le bruit d'une rivière non loin.");
			poursuitChemin.create(livre);

			Page dortLit = new Page();
			dortLit.setNumero(numero);
			numero++;
			dortLit.setTexte("Vous vous endormez paisiblement dans ce lit si confortable. C'est moins paisiblement que vous vous réveillez face à un vieux sorcier menacant, l'air très mécontent, de toute évidence le propriétaire de la cabane. À peine avez vous ouvert la bouche qu'il murmure un sort vous plongeant instantanément dans un sommeil dont cette fois vous ne vous réveillerez pas...");
			dortLit.create(livre);

			Page regardeChaudron = new Page();
			regardeChaudron.setNumero(numero);
			numero++;
			regardeChaudron.setTexte("Vous jetez un coup d'oeil à l'intérieur du chaudron. Au fond se trouve un vieux morceau de jambon.");
			regardeChaudron.create(livre);

			Page fouilleCoffre = new Page();
			fouilleCoffre.setNumero(numero);
			numero++;
			fouilleCoffre.setTexte("À peine avez vous entrouvert le coffre qu'un gros rat affamé à l'évidence enfermé dans ce coffre depuis longtemps se jette sur votre main. Vous lachez le coffre avec dégoût et il se referme avec fracas, enfermant la bête enragée.");
			fouilleCoffre.create(livre);

			Page sortCabane = new Page();
			sortCabane.setNumero(numero);
			numero++;
			sortCabane.setTexte("Vous sortez de la cabane.");
			sortCabane.create(livre);

			Page prendJambon = new Page();
			prendJambon.setNumero(numero);
			prendJambon.setObjetDonne(JAMBON);
			numero++;
			prendJambon.setTexte("En dessous du jambon se trouvait une améthyste !");
			prendJambon.create(livre);

			Page prendAmethyste = new Page();
			prendAmethyste.setNumero(numero);
			numero++;
			prendAmethyste.setTexte("Vous prenez l'améthyste.");
			prendAmethyste.setObjetDonne(AMETHYSTE);
			prendAmethyste.create(livre);

			Page donneFromage = new Page();
			donneFromage.setNumero(numero);
			donneFromage.setObjetSupprime(FROMAGE);
			numero++;
			donneFromage.setTexte("Vous ouvrez à nouveau le coffre et lancez le fromage à l'intérieur. Le rat se jette dessus en se désintéressant totalement de vous. Un coup d'oeil à l'intérieur du coffre vous permet d'apercevoir un livre intitulé : <i>Le guide du parfait malfrat, volume II</i>");
			donneFromage.create(livre);

			Page prendLivre = new Page();
			prendLivre.setNumero(numero);
			prendLivre.setObjetDonne(LIVRE);
			numero++;
			prendLivre.setTexte("Vous ramassez le livre, le feuilletez et ne comprenez strictement rien à ce que vous lisez. Cependant, vous pourrez le vendre pour un bon prix, vous décidez donc de le mettre dans votre sacoche.");
			prendLivre.create(livre);

			Page retourClairiere = new Page();
			retourClairiere.setNumero(numero);
			numero++;
			retourClairiere.setTexte("Vous vous retrouvez à nouveau dans la clairière dans laquelle vous dormiez.");
			retourClairiere.create(livre);

			Page riviere = new Page();
			riviere.setNumero(numero);
			numero++;
			riviere.setTexte("Vous arrivez au bord de la rivière. Au pied d'un arbre vous apercevez un homme qui semble dormir paisiblement. Le courant est trop fort pour vous donner l'envie de traverser la rivière mais vous pouvez longer la rivière vers l'amont ou l'aval.");
			riviere.create(livre);

			Page fouillerHomme = new Page();
			fouillerHomme.setNumero(numero);
			numero++;
			fouillerHomme.setTexte("Vous vous avancez silencieusement vers l'inconnu endormi. N'écoutant que votre cupidité, vous fouillez discrètement la poche exposée de l'homme. Malheureusement le seul butin que vous trouvez est un morceau de fromage malodorant.");
			fouillerHomme.create(livre);

			Page riviereGauche = new Page();
			riviereGauche.setNumero(numero);
			numero++;
			riviereGauche.setTexte("Vous arrivez à la source de la rivière. Soudain, un arbre déjà fragilisé cède suite à une bourrasque soudaine, et tombe dans votre direction.");
			riviereGauche.create(livre);

			Page riviereDroite = new Page();
			riviereDroite.setNumero(numero);
			numero++;
			riviereDroite.setTexte("Vous empruntez le chemin de droite. Vous vous enfoncez de plus en plus profondément dans la forêt, jusqu'à croiser le chemin de deux Affreuses. Vous jugeant séduisant et à leur goût, les deux créatures vous ensorcellent et font de vous leur galant serviteur, pour l'éternité...");
			riviereDroite.create(livre);

			Page prendFromage = new Page();
			prendFromage.setNumero(numero);
			numero++;
			prendFromage.setTexte("Non sans dégoût, vous vous emparez du morceau de fromage. L'homme remue légèrement mais reste assoupi.");
			prendFromage.setObjetDonne(FROMAGE);
			prendFromage.create(livre);

			Page decaleGauche = new Page();
			decaleGauche.setNumero(numero);
			numero++;
			decaleGauche.setTexte("Vous vous jetez sur votre gauche. L'arbre tombe avec fracas à quelques centimètres de vous. Vous pouvez maintenant emprunter le chemin de la rivière, vers la droite, ou bien vous diriger vers les bois.");
			decaleGauche.create(livre);

			Page court = new Page();
			court.setNumero(numero);
			numero++;
			court.setTexte("Vous courez pour esquiver l'arbre, trébuchez sur une pierre, et vous étalez de tout votre long. L'arbre s'écroule sur vous, vous précipitant vers une mort rapide, mais douloureuse...");
			court.create(livre);

			Page clairiere2 = new Page();
			clairiere2.setNumero(numero);
			numero++;
			clairiere2.setTexte("Vous arrivez dans une petite clairière très lumineuse et fleurie. Un Sprigeon se trouve au centre de la clairière. Il s'adresse à vous d'un ton amical : \n \"Un service contre un autre. Donne moi une améthyste et je t'aiderai à obtenir ce que tu cherches.\"");
			clairiere2.create(livre);

			Page attaquer = new Page();
			attaquer.setNumero(numero);
			numero++;
			attaquer.setTexte("Vous attaquez sauvagement le Sprigeon. Celui-ci vous regarde d'un air triste et disparait. Vous êtes désormais l'ennemi de la forêt, et condamné à errer dedans sans jamais trouver votre chemin...");
			attaquer.create(livre);

			Page donneAmethyste = new Page();
			donneAmethyste.setNumero(numero);
			numero++;
			donneAmethyste.setTexte("Vous tendez l'améthyste au Sprigeon. Celui-ci la prend d'un mouvement grâcieux et fait apparaître une carte qu'il vous tend. \"Là où tout semblait s'arrêter, désormais, le chemin vous est montré\". Vous remerciez votre mystérieux interlocuteur et mettez la carte dans votre sacoche.");
			donneAmethyste.setObjetSupprime(AMETHYSTE);
			donneAmethyste.setObjetDonne(CARTE);
			donneAmethyste.create(livre);

			Page sortCarte = new Page();
			sortCarte.setNumero(numero);
			numero++;
			sortCarte.setTexte("La carte vous indique qu'un chemin est bel et bien présent en face de vous.");
			sortCarte.create(livre);

			Page cheminSecret = new Page();
			cheminSecret.setNumero(numero);
			numero++;
			cheminSecret.setTexte("Vous avancez et découvrez que le chemin apparaît devant vous au fur et à mesure. Quelques mètres plus loin, vous tombez nez à nez avec une bande de bandits se partageant votre potion de santé ainsi que votre or. " + "Vous brandissez vos poings, prêt à vous battre pour récupérer vos biens, mais celui qui semble être le chef s'adresse à vous d'un ton calme. Il porte votre précieuse épée ! \"Holà l'ami, vous voyez bien que nous sommes trop nombreux. Et si nous passions plutôt un accord ? Donnant donnant, comme on le dit chez nous.\"");
			cheminSecret.create(livre);

			Page attaquerB = new Page();
			attaquerB.setNumero(numero);
			numero++;
			attaquerB.setTexte("Rendu inconscient par la fureur, vous vous jetez sur ces malotrus en hurlant votre slogan : \n\"Bourgeois !\n Saint Denus ! \n Que trépasse si je...\"\n Bien entendu, vous vous faites tailler en pièces avant même d'avoir terminé votre phrase.");
			attaquerB.create(livre);

			Page ecouterB = new Page();
			ecouterB.setNumero(numero);
			numero++;
			ecouterB.setTexte("\"J'ai entendu dire qu'un vieux sorcier possédait un livre qui m'intéresse beaucoup, caché dans sa cabane : <i>Le guide du parfait malfrat, volume II</i>. \nDonnez le moi et vous récupèrerez vos affaires.\"");
			ecouterB.create(livre);

			Page fin = new Page();
			fin.setNumero(numero);
			numero++;
			fin.setTexte("Vous donnez le livre au brigand. Celui-ci sourit, le prend, et tente de vous attaquer. Cependant, le livre en main, il n'est pas assez prompt à dégainer et vous vous emparez de votre épée. Il ne vous faut pas plus d'une minute pour venir à bout de la bande. Victorieux, vous récupérez enfin vos trésors. Ce fut somme toute, une journée bien remplie.");
			donneAmethyste.setObjetSupprime(CARTE);
			donneAmethyste.setObjetDonne(EPEE);
			donneAmethyste.setObjetDonne(SANTE);
			fin.create(livre);

			// ----------Choix-----------

			Choix ch1 = new Choix();
			ch1.setIdNextPage(seLever.getId());
			ch1.setTexte("Se lever");
			ch1.create(intro);

			Choix ch2 = new Choix();
			ch2.setIdNextPage(prendPotion.getId());
			ch2.setTexte("Boire une potion");
			ch2.create(seLever);

			Choix ch3 = new Choix();
			ch3.setIdNextPage(cheminObs.getId());
			ch3.setTexte("Prendre le chemin obscur");
			ch3.create(prendPotion);
			ch3.create(retourClairiere);

			Choix ch4 = new Choix();
			ch4.setIdNextPage(cheminNord.getId());
			ch4.setTexte("Prendre le grand chemin vers le nord");
			ch4.create(prendPotion);
			ch4.create(retourClairiere);

			Choix ch5 = new Choix();
			ch5.setIdNextPage(cheminSud.getId());
			ch5.setTexte("Prendre le grand chemin vers le sud");
			ch5.create(prendPotion);
			ch5.create(retourClairiere);

			Choix ch6 = new Choix();
			ch6.setIdNextPage(entreCabane.getId());
			ch6.setTexte("Entrer dans la cabane");
			ch6.create(cheminObs);

			Choix ch7 = new Choix();
			ch7.setIdNextPage(poursuitChemin.getId());
			ch7.setTexte("Poursuivre votre chemin");
			ch7.create(cheminObs);
			ch7.create(sortCabane);

			Choix ch8 = new Choix();
			ch8.setIdNextPage(dortLit.getId());
			ch8.setTexte("Se reposer dans le lit");
			ch8.create(entreCabane);
			ch8.create(regardeChaudron);
			ch8.create(prendJambon);
			ch8.create(prendAmethyste);
			ch8.create(fouilleCoffre);
			ch8.create(donneFromage);
			ch8.create(prendLivre);

			Choix ch9 = new Choix();
			ch9.setIdNextPage(regardeChaudron.getId());
			ch9.setTexte("Regarder à l'intérieur du Chaudron");
			ch9.create(entreCabane);
			ch9.create(fouilleCoffre);
			ch9.create(donneFromage);
			ch9.create(prendLivre);

			Choix ch10 = new Choix();
			ch10.setIdNextPage(fouilleCoffre.getId());
			ch10.setTexte("Fouiller le coffre");
			ch10.create(entreCabane);
			ch10.create(regardeChaudron);
			ch10.create(prendJambon);
			ch10.create(prendAmethyste);
			ch10.create(prendLivre);

			Choix ch11 = new Choix();
			ch11.setIdNextPage(sortCabane.getId());
			ch11.setTexte("Sortir de la cabane");
			ch11.create(entreCabane);
			ch11.create(regardeChaudron);
			ch11.create(prendJambon);
			ch11.create(prendAmethyste);
			ch11.create(fouilleCoffre);
			ch11.create(donneFromage);
			ch11.create(prendLivre);

			Choix ch12 = new Choix();
			ch12.setIdNextPage(prendJambon.getId());
			ch12.setTexte("Prendre le jambon");
			ch12.create(regardeChaudron);

			Choix ch13 = new Choix();
			ch13.setIdNextPage(prendAmethyste.getId());
			ch13.setTexte("Prendre l'améthyste");
			ch13.create(prendJambon);

			Choix ch14 = new Choix();
			ch14.setIdNextPage(donneFromage.getId());
			ch14.setTexte("Donner le fromage au rat");
			ch14.setObjetRequis(FROMAGE);
			ch14.create(fouilleCoffre);

			Choix ch15 = new Choix();
			ch15.setIdNextPage(prendLivre.getId());
			ch15.setTexte("Prendre le livre");
			ch15.create(donneFromage);

			Choix ch16 = new Choix();
			ch16.setIdNextPage(retourClairiere.getId());
			ch16.setTexte("Retourner dans la clairière");
			ch16.create(sortCabane);

			Choix ch17 = new Choix();
			ch17.setIdNextPage(riviere.getId());
			ch17.setTexte("Se diriger vers la rivière");
			ch17.create(poursuitChemin);
			ch17.create(decaleGauche);

			Choix ch18 = new Choix();
			ch18.setIdNextPage(fouillerHomme.getId());
			ch18.setTexte("Fouiller l'homme à terre");
			ch18.create(riviere);

			Choix ch19 = new Choix();
			ch19.setIdNextPage(riviereGauche.getId());
			ch19.setTexte("Longer la rivière vers la gauche");
			ch19.create(riviere);
			ch19.create(fouillerHomme);
			ch19.create(prendFromage);

			Choix ch20 = new Choix();
			ch20.setIdNextPage(riviereDroite.getId());
			ch20.setTexte("Longer la rivière vers la droide");
			ch20.create(riviere);
			ch20.create(fouillerHomme);
			ch20.create(prendFromage);

			Choix ch21 = new Choix();
			ch21.setIdNextPage(prendFromage.getId());
			ch21.setTexte("Prendre le morceau de fromage");
			ch21.create(fouillerHomme);

			Choix ch22 = new Choix();
			ch22.setIdNextPage(decaleGauche.getId());
			ch22.setTexte("Se décaler vers la gauche");
			ch22.create(riviereGauche);

			Choix ch23 = new Choix();
			ch23.setIdNextPage(court.getId());
			ch23.setTexte("Courir pour esquiver l'arbre");
			ch23.create(riviereGauche);

			Choix ch24 = new Choix();
			ch24.setIdNextPage(cheminNord.getId());
			ch24.setTexte("Se diriger vers les bois");
			ch24.create(decaleGauche);

			Choix ch25 = new Choix();
			ch25.setIdNextPage(riviereGauche.getId());
			ch25.setTexte("Se diriger vers la rivière");
			ch25.create(cheminNord);

			Choix ch26 = new Choix();
			ch26.setIdNextPage(clairiere2.getId());
			ch26.setTexte("Se diriger vers la petite clairière");
			ch26.create(cheminNord);

			Choix ch27 = new Choix();
			ch27.setIdNextPage(retourClairiere.getId());
			ch27.setTexte("Retourner vers la position de départ");
			ch27.create(cheminNord);

			Choix ch28 = new Choix();
			ch28.setIdNextPage(attaquer.getId());
			ch28.setTexte("L'attaquer");
			ch28.create(clairiere2);

			Choix ch29 = new Choix();
			ch29.setIdNextPage(donneAmethyste.getId());
			ch29.setTexte("Lui donner une amethyste");
			ch29.setObjetRequis(AMETHYSTE);
			ch29.create(clairiere2);

			Choix ch30 = new Choix();
			ch30.setIdNextPage(cheminNord.getId());
			ch30.setTexte("Faire demi-tour");
			ch30.create(clairiere2);
			ch30.create(donneAmethyste);
			ch30.create(cheminSecret);
			ch30.create(donneAmethyste);
			ch30.create(ecouterB);

			Choix ch31 = new Choix();
			ch31.setIdNextPage(retourClairiere.getId());
			ch31.setTexte("Faire demi-tour");
			ch31.create(cheminSud);
			ch31.create(sortCarte);

			Choix ch32 = new Choix();
			ch32.setIdNextPage(sortCarte.getId());
			ch32.setTexte("Sortir la carte");
			ch32.setObjetRequis(CARTE);
			ch32.create(cheminSud);

			Choix ch33 = new Choix();
			ch33.setIdNextPage(cheminSecret.getId());
			ch33.setTexte("Emprunter le chemin secret");
			ch33.setObjetRequis(CARTE);
			ch33.create(sortCarte);

			Choix ch34 = new Choix();
			ch34.setIdNextPage(ecouterB.getId());
			ch34.setTexte("L'écouter");
			ch34.create(cheminSecret);

			Choix ch35 = new Choix();
			ch35.setIdNextPage(attaquerB.getId());
			ch35.setTexte("L'attaquer");
			ch35.create(cheminSecret);

			Choix ch36 = new Choix();
			ch36.setIdNextPage(fin.getId());
			ch36.setTexte("Donner le livre");
			ch36.setObjetRequis(LIVRE);
			ch36.create(ecouterB);

		}
	}

	public static void playSound(Activity ac, int resId)
	{
		MediaPlayer mPlayer = new MediaPlayer();

		if (MainActivity.param != null && MainActivity.param.getSon() == 1)
		{
			if (mPlayer != null)
			{
				mPlayer.stop();
				mPlayer.release();
			}

			mPlayer = MediaPlayer.create(ac, resId);
			mPlayer.start();
		}
	}
}
