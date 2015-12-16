package lire_livre;
import com.example.histoires.R;
import databasetest.Livre;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import menu.MainActivity;

public class LeaveClickListener implements OnClickListener
{
	Activity ac;
	Livre lv;
	
	public LeaveClickListener(Activity ac, Livre lv)
	{
		this.ac = ac;
		this.lv = lv;
	}

	
	@Override
	public void onClick(View v)
	{
		MainActivity.playSound(ac, R.raw.fermerlivre);
		lv.updateMarquePage(0);
		ac.finish();
	}
	
	
}
