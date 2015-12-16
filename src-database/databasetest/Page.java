package databasetest;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;

public class Page
{
	// Attributs pour la BDD:

	public static final String	TABLE_NAME		= "Page";
	public static final String	KEY				= "id";
	public static final String	ID_LIVRE		= "idLivre";
	public static final String	NUMERO			= "numero";
	public static final String	TEXTE			= "texte";
	public static final String	OBJET_DONNE		= "objetDonne";
	public static final String	OBJET_SUPPRIME	= "objetSupprime";

	public static final String	TABLE_CHOIX		= "Choix";
	public static final String	ID_NEXT_PAGE	= "idNextPage";

	// Attributs pour la classe :
	private long				id;

	// identifiant du livre auquel appartient la page
	private long				idLivre;

	// numéro de page
	private long				numero;

	// texte de la page
	private String				texte;

	// Id de l'objet donné sur cette page
	private long				objetDonne;

	private long				objetSupprime;

	// Constructeur par défaut :
	public Page()
	{

	}

	// Constructeurs :
	public Page(long idLivre, String texte)
	{
		this.idLivre = idLivre;
		this.texte = texte;
	}

	public Page(long idLivre, String texte, long numero)
	{
		this.idLivre = idLivre;
		this.texte = texte;
		this.numero = numero;
	}

	public Page(long idLivre, long numero, String texte, long objetDonne, long objetSupprime)
	{
		this.idLivre = idLivre;
		this.texte = texte;
		this.numero = numero;
		this.objetDonne = objetDonne;
		this.objetSupprime = objetSupprime;
	}

	public Page(long idLivre, long numero, String texte, long objetDonne)
	{
		this.idLivre = idLivre;
		this.texte = texte;
		this.numero = numero;
		this.objetDonne = objetDonne;
	}

	// Getters et Setters
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getIdLivre()
	{
		return idLivre;
	}

	public void setIdLivre(long idLivre)
	{
		this.idLivre = idLivre;
	}

	public long getNumero()
	{
		return numero;
	}

	public void setNumero(long numero)
	{
		this.numero = numero;
	}

	public String getTexte()
	{
		return texte;
	}

	public void setTexte(String texte)
	{
		this.texte = texte;
	}

	public long getObjetDonne()
	{
		return objetDonne;
	}

	public void setObjetDonne(long objetDonne)
	{
		this.objetDonne = objetDonne;
	}

	public long getObjetSupprime()
	{
		return objetSupprime;
	}

	public void setObjetSupprime(long objetSupprime)
	{
		this.objetSupprime = objetSupprime;
	}

	// Méthodes pour la BDD

	/**
	 * Convertit un cursor en un objet Page
	 * 
	 * @param c
	 * @return
	 */
	public static Page fromCursor(Cursor c)
	{
		Page p = new Page(c.getLong(1), c.getLong(2), c.getString(3), c.getLong(4), c.getLong(5));
		p.id = c.getLong(0);
		return p;
	}

	/**
	 * Retourne une page par son numero de page
	 * 
	 * @param id
	 *            l'identifiant du livre de la page
	 * @param numero
	 *            le numero de page
	 * @return un objet Page
	 */
	public static Page getPageByNumero(long idLivre, long numero)
	{
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + ID_LIVRE + ", " + NUMERO + ", " + TEXTE + ", " + OBJET_DONNE + ", " + OBJET_SUPPRIME + " from " + TABLE_NAME + " where " + ID_LIVRE + " = ? AND " + NUMERO + " = ? ",
				new String[] { String.valueOf(idLivre), String.valueOf(numero) });
		c.moveToFirst();
		if(c.getCount() == 0){
			return null;
		}
		return fromCursor(c);
	}

	/**
	 * Retourne la liste de toutes les pages d'un livre
	 * 
	 * @param livre
	 * @return une liste d'objets Page
	 */
	public static List<Page> getAll(Livre livre)
	{
		List<Page> pages = new ArrayList<Page>();
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + ID_LIVRE + ", " + NUMERO + ", " + TEXTE + ", " + OBJET_DONNE + ", " + OBJET_SUPPRIME + " from " + TABLE_NAME + " where " + ID_LIVRE + " = ? ", new String[] { String.valueOf(livre.getId()) });

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			do
			{
				pages.add(fromCursor(c));
			} while (c.moveToNext());
		}
		return pages;
	}

	/**
	 * Retourne une page par son identifiant
	 * 
	 * @param id
	 *            l'identifiant de la page
	 * @return un objet Page
	 */
	public static Page getPageByID(long id)
	{
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + ID_LIVRE + ", " + NUMERO + ", " + TEXTE + ", " + OBJET_DONNE + ", " + OBJET_SUPPRIME + " from " + TABLE_NAME + " where " + KEY + " = ?", new String[] { String.valueOf(id) });
		c.moveToFirst();
		return fromCursor(c);
	}

	/**
	 * Ajoute une page à un livre
	 * 
	 * @param livre
	 *            le livre dans lequel ajouter la page
	 */
	public void create(Livre livre)
	{
		ContentValues value = new ContentValues();
		value.put(ID_LIVRE, livre.getId());
		value.put(TEXTE, getTexte());
		value.put(NUMERO, getNumero());
		value.put(OBJET_DONNE, getObjetDonne());
		value.put(OBJET_SUPPRIME, getObjetSupprime());
		id = new DBManager().getDB().insert(TABLE_NAME, null, value);
	}

	/**
	 * Met à jour le texte de la page
	 * 
	 * @param nouveauTexte
	 *            le nouveau texte de la page
	 */
	public void updatePage(String nouveauTexte)
	{
		this.texte = nouveauTexte;
		ContentValues value = new ContentValues();
		value.put(TEXTE, nouveauTexte);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour le numéro de la page
	 * 
	 * @param numero
	 *            le nouveau numéro de la page
	 */
	public void updateNumero(long numero)
	{
		this.numero = numero;
		ContentValues value = new ContentValues();
		value.put(NUMERO, numero);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'objet donné sur la page
	 * 
	 * @param objet
	 *            l'identifiant de l'objet
	 */
	public void updateObjetDonne(long objet)
	{
		objetDonne = objet;
		ContentValues value = new ContentValues();
		value.put(OBJET_DONNE, objet);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'objet supprimé sur la page
	 * 
	 * @param objet
	 *            l'identifiant de l'objet
	 */
	public void updateObjetSupprime(long objet)
	{
		objetSupprime = objet;
		ContentValues value = new ContentValues();
		value.put(OBJET_SUPPRIME, objet);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime la page ainsi que tous les choix qui ont pour page destination
	 * cette page, et les choix de cette page
	 */
	public void delete()
	{
		Choix.deleteAll(this);
		new DBManager().getDB().delete(TABLE_CHOIX, ID_NEXT_PAGE + " = ?", new String[] { String.valueOf(getId()) });
		new DBManager().getDB().delete(TABLE_NAME, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime toutes les pages associées à un livre
	 * 
	 * @param livre
	 *            le livre dont on supprime toutes les pages
	 */
	public static void deleteAll(Livre livre)
	{
		new DBManager().getDB().delete(TABLE_NAME, ID_LIVRE + " = ?", new String[] { String.valueOf(livre.getId()) });
	}

	/**
	 * Supprime toutes les pages existantes
	 */
	public static void deleteAll()
	{
		new DBManager().getDB().delete(TABLE_NAME, null, null);
	}

}
