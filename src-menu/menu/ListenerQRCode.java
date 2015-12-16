package menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.histoires.R;

public class ListenerQRCode implements OnClickListener
{
	Activity ac;

	public ListenerQRCode(Activity ac)
	{
		this.ac = ac;
	}
	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ac);

		builder.setTitle(R.string.title_share);

		View view = View.inflate(ac, R.layout.alertdialog_qrcode, null);
		builder.setView(view);

		builder.setPositiveButton(R.string.alert_return, null);
		builder.create();
		builder.show();
	}

}
