package databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper
{
	private final static int	VERSION					= 8;
	private final static String	NOM						= "1024__Histoires.db";

	// ----------------------------------La table
	// LIVRE---------------------------------
	private static final String	LIVRE_KEY				= "id";
	private static final String	LIVRE_TITRE				= "titre";
	private static final String	LIVRE_AUTEUR			= "auteur";
	private static final String	LIVRE_SYNOPSIS			= "synopsis";
	private static final String	LIVRE_MARQUE_PAGE		= "marquePage";
	private static final String	LIVRE_ID_FIRST_PAGE		= "idFirstPage";
	private static final String	LIVRE_IMAGE				= "image";
	private static final String	LIVRE_EDITABLE			= "editable";
	private static final String	LIVRE_INVENTAIRE		= "inventaire";

	// ----------------------------------La table
	// PAGE----------------------------------
	private static final String	PAGE_KEY				= "id";
	private static final String	PAGE_ID_LIVRE			= "idLivre";
	private static final String	PAGE_NUMERO				= "numero";
	private static final String	PAGE_TEXTE				= "texte";
	private static final String	PAGE_OBJET_DONNE		= "objetDonne";
	private static final String	PAGE_OBJET_SUPPRIME		= "objetSupprime";
	private static final String	PAGE_TABLE_NAME			= "Page";

	// ----------------------------------La table
	// CHOIX----------------------------------
	private static final String	CHOIX_KEY				= "id";
	private static final String	CHOIX_NUMERO			= "numero";
	private static final String	CHOIX_ID_PAGE			= "idPage";
	private static final String	CHOIX_ID_NEXT_PAGE		= "idNextPage";
	private static final String	CHOIX_TEXTE				= "texte";
	private static final String	CHOIX_OBJET_REQUIS		= "objetRequis";
	private static final String	CHOIX_TABLE_NAME		= "Choix";

	// -------------------------------- La table PARAMETRE
	// ------------------------------
	private static final String	PARAMETRE_KEY			= "id";
	private static final String	PARAMETRE_SON			= "son";
	private static final String	PARAMETRE_TABLE_NAME	= "Parametre";
	private static final String	PARAMETRE_AUTEUR_DEFAUT	= "auteurDefaut";
	private static final String	PARAMETRE_LETTRINE		= "lettrine";
	private static final String	PARAMETRE_TAILLE		= "taille";

	// -------------------------------- La table OBJET
	// -----------------------------------
	private static final String	OBJET_KEY				= "id";
	private static final String	OBJET_NOM				= "nom";
	private static final String	OBJET_IMAGE				= "image";
	private static final String	OBJET_TABLE_NAME		= "Objet";

	// -------------------------Pour la création de la table
	// LIVRE------------------------
	private static final String	LIVRE_TABLE_NAME		= "Livre";
	private static final String	LIVRE_TABLE_CREATE		= "CREATE TABLE " + LIVRE_TABLE_NAME + " ( " + LIVRE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LIVRE_TITRE + " TEXT NOT NULL UNIQUE, " + LIVRE_AUTEUR + " TEXT NOT NULL, " + LIVRE_SYNOPSIS + " TEXT NOT NULL, " + LIVRE_ID_FIRST_PAGE + " INTEGER NOT NULL, " + LIVRE_MARQUE_PAGE + " INTEGER, " + LIVRE_IMAGE + " TEXT, " + LIVRE_EDITABLE + " INTEGER NOT NULL, " + LIVRE_INVENTAIRE + " TEXT, " + "FOREIGN KEY (" + LIVRE_MARQUE_PAGE + ") REFERENCES " + PAGE_TABLE_NAME + " (" + PAGE_KEY + " ), FOREIGN KEY (" + LIVRE_ID_FIRST_PAGE + ") REFERENCES " + PAGE_TABLE_NAME + " (" + PAGE_KEY + " ), " + "UNIQUE(" + LIVRE_KEY + ", " + LIVRE_ID_FIRST_PAGE + "), UNIQUE(" + LIVRE_KEY + ", " + LIVRE_MARQUE_PAGE + "));";
	private static final String	LIVRE_TABLE_DROP		= "DROP TABLE IF EXISTS " + LIVRE_TABLE_NAME + ";";

	// ------------------------Pour la création de la table
	// PAGE------------------------
	private static final String	PAGE_TABLE_CREATE		= "CREATE TABLE " + PAGE_TABLE_NAME + " (" + PAGE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PAGE_NUMERO + " INTEGER NOT NULL, " + PAGE_ID_LIVRE + " INTEGER NOT NULL, " + PAGE_TEXTE + " TEXT NOT NULL, " + PAGE_OBJET_DONNE + " INTEGER, " + PAGE_OBJET_SUPPRIME + " INTEGER, " + "FOREIGN KEY (" + PAGE_ID_LIVRE + ") REFERENCES " + LIVRE_TABLE_NAME + " (" + LIVRE_KEY + "), FOREIGN KEY (" + PAGE_OBJET_DONNE + ") REFERENCES " + OBJET_TABLE_NAME + " (" + OBJET_KEY + "), FOREIGN KEY (" + PAGE_OBJET_SUPPRIME + ") REFERENCES " + OBJET_TABLE_NAME + " (" + OBJET_KEY + "));";

	private static final String	PAGE_TABLE_DROP			= "DROP TABLE IF EXISTS " + PAGE_TABLE_NAME + ";";

	// ------------------------Pour la création de la table
	// CHOIX------------------------
	private static final String	CHOIX_TABLE_CREATE		= "CREATE TABLE " + CHOIX_TABLE_NAME + " (" + CHOIX_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CHOIX_ID_PAGE + " INTEGER NOT NULL, " + CHOIX_NUMERO + " INTEGER, " + CHOIX_ID_NEXT_PAGE + " INTEGER NOT NULL, " + CHOIX_TEXTE + " TEXT NOT NULL, " + CHOIX_OBJET_REQUIS + " INTEGER, " + "FOREIGN KEY (" + CHOIX_ID_NEXT_PAGE + ") REFERENCES " + PAGE_ID_LIVRE + " (" + PAGE_KEY + ") " + "FOREIGN KEY (" + CHOIX_ID_PAGE + ") REFERENCES " + PAGE_ID_LIVRE + " (" + PAGE_KEY + "), " + " FOREIGN KEY (" + CHOIX_OBJET_REQUIS + ") REFERENCES " + OBJET_TABLE_NAME + " (" + OBJET_KEY + "), " + " UNIQUE(" + CHOIX_ID_PAGE + ", " + CHOIX_TEXTE + ")); ";

	private static final String	CHOIX_TABLE_DROP		= "DROP TABLE IF EXISTS " + CHOIX_TABLE_NAME + ";";

	// --------------------- Pour la création de la table PARAMETRE
	// ---------------------
	private static final String	PARAMETRE_TABLE_CREATE	= "CREATE TABLE " + PARAMETRE_TABLE_NAME + " ( " + PARAMETRE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PARAMETRE_SON + " INTEGER NOT NULL, " + PARAMETRE_AUTEUR_DEFAUT + " TEXT, " + PARAMETRE_LETTRINE + " INTEGER, " + PARAMETRE_TAILLE + " INTEGER);";

	private static final String	PARAMETRE_TABLE_DROP	= "DROP TABLE IF EXISTS " + PARAMETRE_TABLE_NAME + ";";

	// --------------------Pour la création de la table OBJET
	// ---------------------------
	private static final String	OBJET_TABLE_CREATE		= "CREATE TABLE " + OBJET_TABLE_NAME + " (" + OBJET_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + OBJET_NOM + " TEXT NOT NULL, " + OBJET_IMAGE + " TEXT NOT NULL, UNIQUE (" + OBJET_KEY + "," + OBJET_NOM + "));";
	// , UNIQUE ("+ OBJET_NOM + ")
	private static final String	OBJET_TABLE_DROP		= "DROP TABLE IF EXISTS " + OBJET_TABLE_NAME + ";";

	// Constructeur
	public DatabaseHandler(Context context)
	{
		super(context, NOM, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CHOIX_TABLE_CREATE);
		db.execSQL(PAGE_TABLE_CREATE);
		db.execSQL(LIVRE_TABLE_CREATE);
		db.execSQL(PARAMETRE_TABLE_CREATE);
		db.execSQL(OBJET_TABLE_CREATE);
		Log.d("Parametre", PARAMETRE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(LIVRE_TABLE_DROP);
		db.execSQL(PAGE_TABLE_DROP);
		db.execSQL(CHOIX_TABLE_DROP);
		db.execSQL(PARAMETRE_TABLE_DROP);
		db.execSQL(OBJET_TABLE_DROP);
		onCreate(db);
	}
}