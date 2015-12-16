package menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.example.histoires.R;

public class ListenerWireless implements OnClickListener
{
	Activity	ac;

	public ListenerWireless(Activity ac)
	{
		this.ac = ac;
	}

	@Override
	public void onClick(View v)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ac);
		builder.setTitle(R.string.receiveby);

		View view = View.inflate(ac, R.layout.alertdialog_wireless_send, null);

		LinearLayout layout = (LinearLayout) view.findViewById(R.id.alertdialog_wireless);
		layout.getChildAt(0).setOnClickListener(new ListenerBluetooth(ac));
		layout.getChildAt(1).setOnClickListener(new ListenerWifi(ac));

		builder.setView(view);
		builder.create();
		builder.show();
	}

}
