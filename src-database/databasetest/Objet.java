package databasetest;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.Cursor;

public class Objet
{
	// Attributs pour la base de données
	public static final String	TABLE_NAME	= "Objet";
	public static final String	KEY			= "id";
	public static final String	NOM			= "nom";
	public static final String	IMAGE		= "image";

	// Attributs pour la classe
	private long				id			= 0;
	private String				nom;
	private String				image;

	// Constructeurs :
	public Objet(String nom)
	{
		this.nom = nom;
	}

	public Objet(String nom, String image)
	{
		this.nom = nom;
		this.image = image;
	}

	// Getters et Setters :
	public long getId()
	{
		return id;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom(String nom)
	{
		this.nom = nom;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	// Méthodes pour la manipulation dans la BDD :
	/**
	 * Convertit un cursor en objet Objet
	 * 
	 * @param c
	 * @return un objet Objet
	 */

	public static Objet fromCursor(Cursor c)
	{
		Objet o = new Objet(c.getString(1), c.getString(2));
		o.id = c.getLong(0);
		return o;
	}

	/**
	 * Retourne l'objet qui a pour identifiant id
	 * 
	 * @param id
	 * @return un objet Objet
	 */
	public static Objet getObjetByID(long id)
	{
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + NOM + ", " + IMAGE + " from " + TABLE_NAME + " where " + KEY + " = ?", new String[] { String.valueOf(id) });
		c.moveToFirst();
		return fromCursor(c);
	}

	/**
	 * Retourne l'objet qui a pour nom "nom"
	 * 
	 * @param nom
	 * @return un objet Objet
	 */
	public static Objet getObjetByNom(String nom)
	{
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + NOM + ", " + IMAGE + " from " + TABLE_NAME + " where " + NOM + " = ?", new String[] { nom });
		c.moveToFirst();
		return fromCursor(c);
	}

	/**
	 * Ajoute un objet dans la base de données
	 */
	public void create()
	{
		ContentValues value = new ContentValues();
		value.put(NOM, getNom());
		value.put(IMAGE, getImage());
		id = new DBManager().getDB().insert(TABLE_NAME, null, value);
	}

	/**
	 * Retourne tous les objets de la base de données
	 * 
	 * @return
	 */
	public static List<Objet> getAll()
	{
		List<Objet> objets = new ArrayList<Objet>();
		Cursor c = new DBManager().getDB().rawQuery("select " + KEY + ", " + NOM + ", " + IMAGE + " from " + TABLE_NAME, null);

		if (c.getCount() > 0)
		{
			c.moveToFirst();
			do
			{
				objets.add(fromCursor(c));
			} while (c.moveToNext());
		}
		return objets;
	}

	/**
	 * Met à jour le nom de l'objet
	 * 
	 * @param nom
	 */
	public void updateNom(String nom)
	{
		ContentValues value = new ContentValues();
		value.put(NOM, nom);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Met à jour le chemin de l'image de l'objet
	 * 
	 * @param image
	 */
	public void updateImage(String image)
	{
		ContentValues value = new ContentValues();
		value.put(IMAGE, image);
		new DBManager().getDB().update(TABLE_NAME, value, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime un objet
	 */
	public void delete()
	{
		new DBManager().getDB().delete(TABLE_NAME, KEY + " = ?", new String[] { String.valueOf(getId()) });
	}

	/**
	 * Supprime tous les objets
	 */
	public static void deleteAll()
	{
		new DBManager().getDB().delete(TABLE_NAME, null, null);
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Objet)
		{
			Objet toCompare = (Objet) o;
			return this.nom.equals(toCompare.getNom());
		}
		return false;
	}
}
