package lire_liste_livres;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.EditText;

public class YesSearchListener implements DialogInterface.OnClickListener
{
	Activity ac;
	
	EditText keyword;
	CheckBox checktitre;
	CheckBox checkauthor;
	CheckBox checksynopsis; 
	CheckBox checkpage;
	
	public YesSearchListener(Activity ac, EditText keyword, CheckBox checktitre, CheckBox checkauthor, CheckBox checksynopsis, CheckBox checkpage)
	{
		this.ac 			= ac;
		this.keyword 		= keyword;
		this.checktitre 	= checktitre;
		this.checkauthor 	= checkauthor;
		this.checksynopsis 	= checksynopsis;
		this.checkpage 		= checkpage;
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{

		Intent intent = new Intent(ac, ListLivresActivity.class);
		intent.putExtra("motscle", this.keyword.getText().toString());
		intent.putExtra("checktitre", this.checktitre.isChecked());
		intent.putExtra("checkauthor", this.checkauthor.isChecked());
		intent.putExtra("checksynopsis", this.checksynopsis.isChecked());
		intent.putExtra("checkpage", this.checkpage.isChecked());
		
		ac.startActivity(intent);
		ac.finish();
	}
}
