package databasetest;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Parametre
{
	// Attributs pour la BDD :
	private static final String	TABLE_NAME		= "Parametre";
	private static final String	KEY				= "id";
	private static final String	SON				= "son";
	private static final String	AUTEUR_DEFAUT	= "auteurDefaut";
	private static final String	LETTRINE		= "lettrine";
	private static final String	TAILLE			= "taille";

	// Attributs pour la classe :
	private long				id				= 0;
	private int					son				= 1;
	private String				auteurDefaut;
	private int					lettrine		= 1;
	private int					taille			= 12;

	// Constructeur
	public Parametre(int son)
	{
		this.son = son;
	}

	public Parametre(int son, String auteurDefaut)
	{
		this.son = son;
		this.auteurDefaut = auteurDefaut;
	}
	
	public Parametre(int son, String auteurDefaut, int lettrine, int taille)
	{
		this.son = son;
		this.auteurDefaut = auteurDefaut;
		this.lettrine = lettrine;
		this.taille = taille;
	}

	// Getters et Setters
	public long getId()
	{
		return id;
	}

	public int getSon()
	{
		return son;
	}

	public void setSon(int son)
	{
		this.son = son;
	}

	public String getAuteurDefaut()
	{
		return auteurDefaut;
	}

	public void setAuteurDefaut(String auteurDefaut)
	{
		this.auteurDefaut = auteurDefaut;
	}

	public int getLettrine()
	{
		return lettrine;
	}

	public void setLettrine(int lettrine)
	{
		this.lettrine = lettrine;
	}

	public int getTaille()
	{
		return taille;
	}

	public void setTaille(int taille)
	{
		this.taille = taille;
	}

	// METHODES POUR LA BDD

	/**
	 * Ajoute un paramètre dans la BDD
	 */
	public void create()
	{
		ContentValues value = new ContentValues();
		value.put(SON, getSon());
		value.put(AUTEUR_DEFAUT, getAuteurDefaut());
		value.put(LETTRINE, getLettrine());
		value.put(TAILLE, getTaille());
		id = new DBManager().getDB().insert(TABLE_NAME, null, value);
	}

	/**
	 * Met à jour le son dans la BDD
	 */
	public void updateSon(int son)
	{
		this.son = son;
		ContentValues value = new ContentValues();
		value.put(SON, son);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'auteur par défaut
	 * 
	 * @param auteur , le nouveau nom de l'auteur par défaut
	 */
	public void updateAuteurDefaut(String auteur)
	{
		this.auteurDefaut = auteur;
		ContentValues value = new ContentValues();
		value.put(AUTEUR_DEFAUT, auteur);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour l'activation ou la désactivation de la lettrine (0 pour non, 1
	 * pour oui)
	 * 
	 * @param l
	 */
	public void updateLettrine(int l)
	{
		this.lettrine = l;
		ContentValues value = new ContentValues();
		value.put(LETTRINE, l);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour la taille de la police
	 * 
	 * @param t
	 */
	public void updateTaille(int t)
	{
		this.taille = t;
		ContentValues value = new ContentValues();
		value.put(TAILLE, taille);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime un parametre par son identifiant
	 */
	public void deleteByID()
	{
		new DBManager().getDB().delete(TABLE_NAME, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * 
	 * @param c un cursor
	 * @return le paramètre correspondant au cursor
	 */
	public static Parametre fromCursor(Cursor c)
	{
		Log.d("fromCursor", "je suis dans cette méthode !");
		Parametre p = new Parametre(c.getInt(1), c.getString(2), c.getInt(3), c.getInt(4));
		p.id = c.getLong(0);
		Log.d("fromCursor", "J'ai réussi à construire un parametre");
		return p;
	}

	/**
	 * 
	 * @param id
	 * @return le paramètre correspondant à l'identifiant
	 */
	public static Parametre getParametreById(long id)
	{
		Log.d("getParametreByID", "je suis dans cette méthode !");
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + SON + ", " + AUTEUR_DEFAUT + ", " + LETTRINE + ", " + TAILLE + " from " + TABLE_NAME + " where " + KEY + " = ? ", new String[] { String.valueOf(id) });
		c.moveToFirst();
		return fromCursor(c);
	}

	/**
	 * Retourne la liste de tous les parametres
	 * 
	 * @return
	 */
	public static List<Parametre> getAll()
	{
		List<Parametre> parametres = new ArrayList<Parametre>();
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + SON + ", " + AUTEUR_DEFAUT + ", " + LETTRINE + ", " + TAILLE + " from " + TABLE_NAME, null);
		if (c.getCount() > 0)
		{
			c.moveToFirst();
			do
			{
				parametres.add(fromCursor(c));
			} while (c.moveToNext());
		}
		return parametres;
	}

}
