package databasetest;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;

public class Choix
{

	// Attributs pour la BDD :
	public static final String	TABLE_NAME		= "Choix";
	public static final String	KEY				= "id";
	public static final String	NUMERO			= "numero";
	public static final String	ID_PAGE			= "idPage";
	public static final String	ID_NEXT_PAGE	= "idNextPage";
	public static final String	TEXTE			= "texte";
	public static final String	OBJET_REQUIS	= "objetRequis";

	// Attributs pour la classe :
	private long				id				= 0;
	private long				numero			= 1;
	private long				idPage;
	private long				idNextPage;
	private String				texte;
	private long				objetRequis		= 0;

	// Constructeur par défaut :
	public Choix()
	{

	}

	// Constructeurs :
	// public Choix(long idPage, long idNextPage, String texte) {
	// this.idPage = idPage;
	// this.idNextPage = idNextPage;
	// this.texte = texte;
	// }

	public Choix(long idPage, String texte)
	{
		this.idPage = idPage;
		this.texte = texte;
	}


	public Choix(long idPage, long idNextPage, String texte)
	{
		this.idPage = idPage;
		this.idNextPage = idNextPage;
		this.texte = texte;
	}

	public Choix(long idPage, long numero, long idNextPage, String texte, long objetRequis)
	{
		this.idPage = idPage;
		this.numero = numero;
		this.idNextPage = idNextPage;
		this.texte = texte;
		this.objetRequis = objetRequis;
	}
	
	public Choix(long idPage, long idNextPage, String texte, long objetRequis)
	{
		this.idPage = idPage;
		this.idNextPage = idNextPage;
		this.texte = texte;
		this.objetRequis = objetRequis;
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

	public long getIdPage()
	{
		return idPage;
	}

	public void setIdPage(long idPage)
	{
		this.idPage = idPage;
	}

	public String getTexte()
	{
		return texte;
	}

	public void setTexte(String texte)
	{
		this.texte = texte;
	}

	public long getIdNextPage()
	{
		return idNextPage;
	}

	public void setIdNextPage(long idNextPage)
	{
		this.idNextPage = idNextPage;
	}

	public long getObjetRequis()
	{
		return objetRequis;
	}

	public void setObjetRequis(long objetRequis)
	{
		this.objetRequis = objetRequis;
	}

	public long getNumero()
	{
		return numero;
	}

	public void setNumero(long numero)
	{
		this.numero = numero;
	}

	// Méthodes pour la BDD :

	/**
	 * Convertit un cursor d'une requête en un objet de type Choix
	 * 
	 * @param c
	 *            le cursor de la requête
	 * @return choix, le cursor converti en choix
	 */
	public static Choix fromCursor(Cursor c)
	{
		Choix choix = new Choix(c.getLong(1), c.getLong(2), c.getLong(3), c.getString(4), c.getLong(5));
		choix.id = c.getLong(0);
		return choix;
	}

	/**
	 * Retourne l'objet Choix demandé par son id
	 * 
	 * @param id
	 *            , l'identifiant du choix
	 * @return choix, objet Choix correspondant à l'id demandé
	 */
	public static Choix getChoixById(long id)
	{
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + ID_PAGE + ", " + NUMERO + ", " + ID_NEXT_PAGE + ", " + TEXTE + ", " + OBJET_REQUIS + " from " + TABLE_NAME + " where " + KEY + " = ?", new String[] { String.valueOf(id) });
		c.moveToFirst();
		return fromCursor(c);
	}
	

	/**
	 * Retourne tous les choix de la page
	 * 
	 * @param page
	 *            , la page dont on veut tous les choix
	 * @return choix, la liste de tous les choix de la page
	 */

	public static List<Choix> getAll(Page page)
	{
		List<Choix> choix = new ArrayList<Choix>();
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + ID_PAGE + ", " + NUMERO + ", " + ID_NEXT_PAGE + ", " + TEXTE + ", " + OBJET_REQUIS + " from " + TABLE_NAME + " where " + ID_PAGE + " = ?", new String[] { String.valueOf(page.getId()) });

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			do
			{
				choix.add(fromCursor(c));
			} while (c.moveToNext());
		}
		return choix;
	}

	/**
	 * Rajoute un choix dans la page
	 * 
	 * @param page
	 *            , la page dans laquelle ajouter le choix
	 */

	public void create(Page page)
	{
		ContentValues value = new ContentValues();
		value.put(ID_PAGE, page.getId());
		value.put(NUMERO, getNumero());
		value.put(ID_NEXT_PAGE, getIdNextPage());
		value.put(TEXTE, getTexte());
		value.put(OBJET_REQUIS, getObjetRequis());
		id = new DBManager().getDB().insert(TABLE_NAME, null, value);
	}

	/**
	 * Met à jour le texte du choix
	 * 
	 * @param nouveauTexte
	 */

	public void updateChoix(String nouveauTexte)
	{
		this.texte = nouveauTexte;
		ContentValues value = new ContentValues();
		value.put(TEXTE, nouveauTexte);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour le numéro du choix
	 * 
	 * @param numero
	 */

	public void updateNumero(long numero)
	{
		this.numero = numero;
		ContentValues value = new ContentValues();
		value.put(NUMERO, numero);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour la page destination pour un choix
	 * 
	 * @param idNextPage
	 *            , le nouvel identifiant de la page destination
	 */

	public void updateNextPage(long idNextPage)
	{
		this.idNextPage = idNextPage;
		ContentValues value = new ContentValues();
		value.put(ID_NEXT_PAGE, idNextPage);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'identifiant de la page à laquelle appartient le choix
	 * 
	 * @param idPage
	 *            , le nouvel identifiant de la page à laquelle appartient le
	 *            choix
	 */
	public void updateIdPage(long idPage)
	{
		this.idPage = idPage;
		ContentValues value = new ContentValues();
		value.put(ID_PAGE, idPage);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'objet requis dans le choix
	 * 
	 * @param objet
	 *            l'identifiant de l'objet
	 */
	public void updateObjetRequis(long objet)
	{
		this.objetRequis = objet;
		ContentValues value = new ContentValues();
		value.put(OBJET_REQUIS, objet);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime un choix
	 */
	public void delete()
	{
		new DBManager().getDB().delete(TABLE_NAME, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime tous les choix ayant pour page cible la page d'identifiant id
	 * 
	 * @param id
	 *            identifiant de la page
	 */
	public static void deleteByPageCible(long id)
	{
		new DBManager().getDB().delete(TABLE_NAME, ID_NEXT_PAGE + " = ?", new String[] { String.valueOf(id) });
	}

	/**
	 * Supprime tous les choix pour une page
	 * 
	 * @param page
	 *            la page dont on veut supprimer tous les choix
	 */
	public static void deleteAll(Page page)
	{
		new DBManager().getDB().delete(TABLE_NAME, ID_PAGE + " = ?", new String[] { String.valueOf(page.getId()) });
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("id choix : " + getId());
		sb.append("\n");
		sb.append("id page : " + getIdPage());
		sb.append("\n");
		sb.append("id next page : " + getIdNextPage());
		sb.append("\n");
		sb.append("texte : " + getTexte());
		return sb.toString();
	}

}
