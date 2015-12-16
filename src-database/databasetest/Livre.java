package databasetest;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Livre
{
	// Attributs pour la BDD :

	public static final String	TABLE_NAME		= "Livre";
	public static final String	KEY				= "id";
	public static final String	TITRE			= "titre";
	public static final String	AUTEUR			= "auteur";
	public static final String	SYNOPSIS		= "synopsis";
	public static final String	ID_FIRST_PAGE	= "idFirstPage";
	public static final String	MARQUE_PAGE		= "marquePage";
	public static final String	IMAGE			= "image";
	public static final String	PAGE_TABLE		= "Page";
	public static final String	ID_LIVRE		= "idLivre";
	public static final String	EDITABLE		= "editable";
	public static final String	INVENTAIRE		= "inventaire";

	// Attributs pour la classe :
	private long				id				= 0;
	private String				titre;
	private String				auteur;
	private String				synopsis;
	private long				idFirstPage		= 1;
	private long				marquePage		= 0;
	private String				image			= null;
	private int					editable		= 1;
	private List<Objet>			inventaire		= new ArrayList<Objet>();

	// Constructeurs
	public Livre(String titre, String auteur, String synopsis, int editable, List<Objet> inventaire)
	{
		this.titre = titre;
		this.auteur = auteur;
		this.synopsis = synopsis;
		this.editable = editable;
		this.inventaire = inventaire;
	}
	
	public Livre(String titre, String auteur, String synopsis, int editable)
	{
		this.titre = titre;
		this.auteur = auteur;
		this.synopsis = synopsis;
		this.editable = editable;
	}

	public Livre(String titre, String auteur, String synopsis, long idFirstPage, long marquePage, String image, int editable, List<Objet> inventaire)
	{
		this.titre = titre;
		this.auteur = auteur;
		this.synopsis = synopsis;
		this.idFirstPage = idFirstPage;
		this.marquePage = marquePage;
		this.image = image;
		this.editable = editable;
		this.inventaire = inventaire;
	}

	// Constructeur par défaut
	public Livre()
	{

	}

	// Getters et Setters
	public long getId()
	{
		return id;
	}

	public String getTitre()
	{
		return titre;
	}

	public void setTitre(String titre)
	{
		this.titre = titre;
	}

	public String getSynopsis()
	{
		return synopsis;
	}

	public void setSynopsis(String synopsis)
	{
		this.synopsis = synopsis;
	}

	public String getAuteur()
	{
		return auteur;
	}

	public void setAuteur(String auteur)
	{
		this.auteur = auteur;
	}

	public long getIdFirstPage()
	{
		return idFirstPage;
	}

	public void setIdFirstPage(long idFirstPage)
	{
		this.idFirstPage = idFirstPage;
	}

	public long getMarquePage()
	{
		return marquePage;
	}

	public void setMarquePage(long marquePage)
	{
		this.marquePage = marquePage;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public int getEditable()
	{
		return editable;
	}

	public void setEditable(int editable)
	{
		this.editable = editable;
	}

	public List<Objet> getInventaire()
	{
		return inventaire;
	}

	public void setInventaire(List<Objet> inventaire)
	{
		this.inventaire = inventaire;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("ID : " + id);
		sb.append("\n");
		sb.append("Auteur : " + auteur);
		sb.append("\n");
		sb.append("Synopsis : " + synopsis);
		sb.append("\n");
		return sb.toString();
	}

	// Méthodes pour la BDD------------------------------

	/**
	 * Convertit un cursor d'une requête en un objet de type Livre
	 * 
	 * @param c
	 *            le cursor de la requête
	 * @return l, le cursor converti en Livre
	 */
	public static Livre fromCursor(Cursor c)
	{
		Livre l = new Livre(c.getString(1), c.getString(2), c.getString(3), c.getLong(4), c.getLong(5), c.getString(6), c.getInt(7), stringToInventaire(c.getString(8)));
		l.id = c.getLong(0);
		return l;
	}

	/**
	 * Retourne le livre correspondant au titre
	 * 
	 * @param titre
	 *            le titre du livre demandé
	 * @return livre objet de type Livre correspondant au titre
	 */
	public static Livre getLivreByTitre(String titre)
	{
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + TITRE + ", " + AUTEUR + ", " + SYNOPSIS + ", " + ID_FIRST_PAGE + ", " + MARQUE_PAGE + ", " + IMAGE + ", " + EDITABLE + ", " + INVENTAIRE + " from " + TABLE_NAME + " where " + TITRE + " = ?", new String[] { titre });
		c.moveToFirst();
		return fromCursor(c);
	}

	/**
	 * Retourne le livre correspondant à l'identifiant
	 * 
	 * @param id
	 *            l'identifiant du livre demndé
	 * @return livre objet de type Livre correspondant à l'identifiant
	 */
	public static Livre getLivreByID(long id)
	{
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + TITRE + ", " + AUTEUR + ", " + SYNOPSIS + ", " + ID_FIRST_PAGE + ", " + MARQUE_PAGE + ", " + IMAGE + ", " + EDITABLE + ", " + INVENTAIRE + " from " + TABLE_NAME + " where " + KEY + " = ?",
				new String[] { String.valueOf(id) });
		c.moveToFirst();
		return fromCursor(c);
	}

	/**
	 * Retourne une liste de tous les livres de la BDD
	 * 
	 * @return List une liste de livres
	 */
	public static List<Livre> getAll()
	{
		List<Livre> bibliotheque = new ArrayList<Livre>();
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + TITRE + ", " + AUTEUR + ", " + SYNOPSIS + ", " + ID_FIRST_PAGE + ", " + MARQUE_PAGE + ", " + IMAGE + ", " + EDITABLE + ", " + INVENTAIRE + " from " + TABLE_NAME, null);

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			do
			{
				bibliotheque.add(fromCursor(c));
			} while (c.moveToNext());
		}
		return bibliotheque;
	}

	/**
	 * Ajoute un livre dans la BDD
	 */
	public void create()
	{
		ContentValues value = new ContentValues();
		value.put(TITRE, getTitre());
		value.put(AUTEUR, getAuteur());
		value.put(SYNOPSIS, getSynopsis());
		value.put(ID_FIRST_PAGE, getIdFirstPage());
		value.put(MARQUE_PAGE, getMarquePage());
		value.put(IMAGE, getImage());
		value.put(EDITABLE, getEditable());
		value.put(INVENTAIRE, inventaireToString(getInventaire()));
		id = new DBManager().getDB().insert(TABLE_NAME, null, value);
	}

	/**
	 * Retourne si un livre existe dans la base de données ou non
	 * 
	 * @param titre
	 * @param auteur
	 * @return true si le livre existe, false sinon
	 */
	public static boolean exists(String titre, String auteur){
		boolean b = false;
		Cursor c = new DBManager().getDB().rawQuery(
				"select " + KEY + ", " + TITRE + ", " + AUTEUR + ", "
						+ SYNOPSIS + ", " + ID_FIRST_PAGE + ", " + MARQUE_PAGE + ", " + IMAGE + " from "
						+ TABLE_NAME + " where " + TITRE + " = ? AND " + AUTEUR +" = ?",
				new String[] { titre, auteur });
		c.moveToFirst();
		if(c.getCount()>0)
			b = true;
		return b;
	}
	
	public static boolean exists(String titre){
		boolean b = false;
		Cursor c = new DBManager().getDB().rawQuery(
				"select " + KEY + ", " + TITRE + ", " + AUTEUR + ", "
						+ SYNOPSIS + ", " + ID_FIRST_PAGE + ", " + MARQUE_PAGE + ", " + IMAGE + " from "
						+ TABLE_NAME + " where " + TITRE + " = ? ",
				new String[] { titre });
		c.moveToFirst();
		if(c.getCount()>0)
			b = true;
		return b;
	}

	/**
	 * Met à jour le titre du livre
	 * 
	 * @param nouveauTitre
	 *            le nouveau titre du livre
	 */
	public void updateTitre(String nouveauTitre)
	{
		this.titre = nouveauTitre;
		ContentValues value = new ContentValues();
		value.put(TITRE, nouveauTitre);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour le synopsis du livre
	 * 
	 * @param nouveauSynopsis
	 *            le nouveau synopsis
	 */
	public void updateSynopsis(String nouveauSynopsis)
	{
		this.synopsis = nouveauSynopsis;
		ContentValues value = new ContentValues();
		value.put(SYNOPSIS, nouveauSynopsis);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour la première page du livre
	 * 
	 * @param idFirstPage
	 *            la nouvelle première page du livre
	 */
	public void updateFirstPage(long idFirstPage)
	{
		this.idFirstPage = idFirstPage;
		ContentValues value = new ContentValues();
		value.put(ID_FIRST_PAGE, idFirstPage);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour le marque page du livre
	 * 
	 * @param idPage
	 *            l'identifiant de la page à marquer
	 */
	public void updateMarquePage(long idPage)
	{
		this.marquePage = idPage;
		ContentValues value = new ContentValues();
		value.put(MARQUE_PAGE, idPage);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'image de la page de couverture
	 * 
	 * @param url
	 *            , le chemin de l'image
	 */
	public void updateImage(String url)
	{
		this.image = url;
		ContentValues value = new ContentValues();
		value.put(IMAGE, url);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour la possibilité d'éditer un livre
	 * 
	 * @param editable
	 *            0 si pas éditable, 1 si oui
	 */

	public void updateEditable(int editable)
	{
		this.editable = editable;
		ContentValues value = new ContentValues();
		value.put(EDITABLE, editable);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'inventaire du livre
	 * 
	 * @param nouvelInventaire
	 */
	public void updateInventaire(List<Objet> nouvelInventaire)
	{
		this.inventaire = nouvelInventaire;
		ContentValues value = new ContentValues();
		String s = inventaireToString(nouvelInventaire);
		Log.d("updateInventaire", "La valeur de s : " + s);
		value.put(INVENTAIRE, inventaireToString(nouvelInventaire));
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime un livre par son titre ainsi que toutes les pages associées à ce
	 * livre
	 */
	public void deleteByTitre()
	{
		Page.deleteAll(this);
		new DBManager().getDB().delete(TABLE_NAME, TITRE + " = ?", new String[] { getTitre() });
	}

	/**
	 * Supprime un livre par son id ainsi que toutes les pages associées à ce
	 * livre
	 */
	public void deleteByID()
	{
		Page.deleteAll(this);
		new DBManager().getDB().delete(TABLE_NAME, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime tous les livres
	 */
	public static void deleteAll()
	{
		Page.deleteAll();
		new DBManager().getDB().delete(TABLE_NAME, null, null);
	}

	/**
	 * Convertit un inventaire en chaine de caractères
	 * 
	 * @param inventaireLivre
	 * @return la chaine de caractères comprenant les objets de l'inventaire
	 */
	public String inventaireToString(List<Objet> inventaireLivre)
	{
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < inventaireLivre.size(); i++)
		{
			sb.append(inventaireLivre.get(i).getId());
			sb.append("*");
		}

		return sb.toString();
	}

	/**
	 * Convertit une chaine de caractères en inventaire
	 * 
	 * @param inventaireBDD
	 * @return un inventaire
	 */
	public static List<Objet> stringToInventaire(String inventaireBDD)
	{
		List<Objet> inventaire = new ArrayList<Objet>();
		StringTokenizer st = new StringTokenizer(inventaireBDD, "*");
		while (st.hasMoreTokens())
		{
			inventaire.add(Objet.getObjetByID(Long.parseLong(st.nextToken())));
		}
		return inventaire;
	}

	/**
	 * 
	 * @return
	 */
	public String toStringForInventaire()
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inventaire.size(); i++)
		{
			sb.append(inventaire.get(i).getNom() + " ");
		}

		return sb.toString();
	}

}
