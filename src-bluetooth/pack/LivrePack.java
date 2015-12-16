package pack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import databasetest.Choix;
import databasetest.Livre;
import databasetest.Page;

@SuppressWarnings("serial")
public class LivrePack implements Serializable
{
	private String 	titre;
	private String 	auteur;
	private String 	synopsis;
	private long	firstPage;
	
	private List<PagePack> pagePacks;
	
	public LivrePack(long id)
	{
		Livre livre 	= Livre.getLivreByID(id);
		
		this.titre 		= livre.getTitre();
		this.auteur		= livre.getAuteur();
		this.synopsis	= livre.getSynopsis();
		this.firstPage	= livre.getIdFirstPage();
		
		pagePacks = new ArrayList<PagePack>();
		
		//------------------
		
		
		List<Page> pages = Page.getAll(livre);
		
		for(Page pg : pages)
		{
			PagePack ppk = new PagePack(pg);
			pagePacks.add(ppk);
			
			List<Choix> choix = Choix.getAll(pg);
			
			for(Choix ch : choix)
			{
				ChoixPack cpk = new ChoixPack(pg, ch);
				ppk.addChoix(cpk);
			}
		}
	}
	
	public void insertThisIntoDatabase()
	{
		Livre lv = this.getLivre();
		lv.create();
		
		for(PagePack ppk : pagePacks)
		{
			Page pg = ppk.getPage();
			pg.create(lv);
		}
		
		for(PagePack ppk : pagePacks)
		{
			for(ChoixPack cck : ppk.getChoixPacks())
			{
				Choix ch = cck.getChoix(lv.getId());
				ch.create(Page.getPageByID(ch.getIdPage()));
			}
		}
	}

	
	public Livre getLivre()
	{
		Livre lv = new Livre();
		
		lv.setTitre(titre);
		lv.setAuteur(auteur);
		lv.setSynopsis(synopsis);
		lv.setIdFirstPage(firstPage);
		lv.setMarquePage(0);
		lv.setEditable(0);
		lv.setImage("");
		
		return lv;
	}
	
	
	
	public void setTitre(String titre)
	{
		this.titre = titre;
	}
	
	public String getTitre()
	{
		return this.titre;
	}
}
