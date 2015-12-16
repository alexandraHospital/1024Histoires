package activites;

import menu.MainActivity;
import Listener.ParcourirListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.histoires.R;
import databasetest.Livre;
import databasetest.Page;

public class PageCouverture extends Activity implements OnClickListener
{

	Livre		livre			= null;
	EditText	auteur			= null;
	EditText	titre			= null;
	EditText	synopsis		= null;

	Button		commencer		= null;
	Button		parcourir		= null;

	boolean		modifie			= false;

	ImageView	apercu			= null;
	String		pathPic			= "";

	String		titreDuLivre	= "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_couverture);

		commencer = (Button) findViewById(R.id.allerMenuCreerPages);
		parcourir = (Button) findViewById(R.id.parcourir);

		auteur = (EditText) findViewById(R.id.nomAuteur);
		auteur.setText(MainActivity.param.getAuteurDefaut());
		titre = (EditText) findViewById(R.id.titre);
		synopsis = (EditText) findViewById(R.id.synopsis);

		apercu = (ImageView) findViewById(R.id.apercu);

		Intent activiteAppelante = getIntent();

		if (activiteAppelante.hasExtra("titreDuLivreModifie"))
		{
			titreDuLivre = activiteAppelante.getStringExtra("titreDuLivreModifie");
			livre = Livre.getLivreByTitre(titreDuLivre);

			Bitmap image = BitmapFactory.decodeFile(livre.getImage());
			if (image != null)
			{
				apercu.setImageBitmap(image);
			}

			auteur.setText(livre.getAuteur());
			auteur.setEnabled(false);
			titre.setText(livre.getTitre());
			synopsis.setText(livre.getSynopsis());
			modifie = true;
			commencer.setText("Modifier");
		}

		commencer.setOnClickListener(this);
		parcourir.setOnClickListener(new ParcourirListener(this));

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0 && resultCode == RESULT_OK && null != data)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Bitmap image = null;

			image = BitmapFactory.decodeFile(picturePath);

			if ((image.getWidth() * image.getHeight() * 24) / 8 / 1024 / 1024 > 1)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Attention");
				builder.setMessage("Votre image est trop grande !");
				builder.create();
				builder.show();
			}
			else
			{
				if (image != null)
				{
					apercu.setImageBitmap(image);
				}
				apercu.setImageBitmap(image);
				this.pathPic = picturePath;
			}
		}
	}

	@Override
	public void onBackPressed()
	{

		if (modifie)
		{

			Intent intent = new Intent(this, MenuCreationPages.class);
			intent.putExtra("titreDuLivreAModifier", titre.getText().toString());
			startActivity(intent);
			finish();
		}

		else
		{
			Intent intent5 = new Intent(this, MainActivity.class);
			startActivity(intent5);
			finish();
		}

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub

		String livreTitre = titre.getText().toString();
		String livreAuteur = auteur.getText().toString();
		String livreSynopsis = synopsis.getText().toString();

		if (livreAuteur.equals("") || livreTitre.equals(""))
		{
			Toast.makeText(this, R.string.avertissementCreation, Toast.LENGTH_SHORT).show();
		}
		else if (Livre.exists(livreTitre, livreAuteur) && !modifie)
		{
			Toast.makeText(this, "Ce titre est déjà pris", Toast.LENGTH_SHORT).show();
		}
		else if (modifie)
		{
			livre.updateTitre(livreTitre);
			livre.setSynopsis(livreSynopsis);
			livre.updateSynopsis(livreSynopsis);
			livre.setImage(this.pathPic);
			livre.updateImage(this.pathPic);
			Intent intent = new Intent(this, MenuCreationPages.class);
			intent.putExtra("titreDuLivreAModifier", livreTitre);
			startActivity(intent);
			finish();
		}
		else
		{
			Livre livre = new Livre(livreTitre, livreAuteur, livreSynopsis, 1);
			livre.create();

			Page page1 = new Page(livre.getId(), "", 1);
			page1.create(livre);
			livre.setIdFirstPage(page1.getId());
			livre.updateFirstPage(page1.getId());

			livre.setImage(this.pathPic);
			livre.updateImage(this.pathPic);
			Intent intent = new Intent(this, MenuCreationPages.class);
			intent.putExtra("titreDuLivre", livreTitre);
			startActivity(intent);
			finish();
		}

	}

}
