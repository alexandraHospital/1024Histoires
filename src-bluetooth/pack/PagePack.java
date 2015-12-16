package pack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import databasetest.Page;

@SuppressWarnings("serial")
public class PagePack implements Serializable
{
	private String 	texte;
	private long 	idLivre;
	private long	objetDonne;
	private long	objetPris;
	private long 	numero;
	
	private List<ChoixPack> choixPacks;
	
	public PagePack(Page page)
	{
		this.texte		= page.getTexte();
		this.idLivre	= page.getIdLivre();
		this.numero		= page.getNumero();
		
		this.objetDonne = page.getObjetDonne();
		this.objetPris	= page.getObjetSupprime();
		
		choixPacks 		= new ArrayList<ChoixPack>();
	}
	
	
	public Page getPage()
	{
		Page pg = new Page();
		
		pg.setTexte(texte);
		pg.setIdLivre(idLivre);
		pg.setNumero(numero);
		pg.setObjetDonne(objetDonne);
		pg.setObjetSupprime(objetPris);
		
		return pg;
	}
	
	
	
	public void addChoix(ChoixPack cpk)
	{
		this.choixPacks.add(cpk);
	}
	
	
	public List<ChoixPack> getChoixPacks()
	{
		return this.choixPacks;
	}
}
