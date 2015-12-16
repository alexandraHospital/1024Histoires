package pack;

import java.io.Serializable;
import databasetest.Choix;
import databasetest.Page;
import databasetest.Livre;

@SuppressWarnings("serial")
public class ChoixPack implements Serializable
{
	private String 	texte;
	private long	numeroPage;
	private long 	numeroNextPage;
	private long	objetRequis;
	
	public ChoixPack(Page pg, Choix choix)
	{
		Page pgCible = Page.getPageByID(choix.getIdNextPage());
		
		this.texte			= choix.getTexte();
		
		this.objetRequis	= choix.getObjetRequis();
		this.numeroPage		= pg.getNumero();
		this.numeroNextPage	= pgCible.getNumero();
	}
	
	
	public Choix getChoix(long idLivre)
	{
		Choix ch = new Choix();
		
		Page pgNext = Page.getPageByNumero(idLivre, numeroNextPage);
		Page pgCurrent = Page.getPageByNumero(idLivre, numeroPage);
		
		ch.setObjetRequis(objetRequis);
		ch.setTexte(texte);
		ch.setIdPage(pgCurrent.getId());
		ch.setIdNextPage(pgNext.getId());
		
		return ch;
	}
}
