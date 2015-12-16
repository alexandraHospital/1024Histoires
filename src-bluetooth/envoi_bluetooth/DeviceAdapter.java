package envoi_bluetooth;

import java.util.ArrayList;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.histoires.R;

public class DeviceAdapter  extends ArrayAdapter<BluetoothDevice>
{
	// declaring our ArrayList of items
	private ArrayList<BluetoothDevice> objects;

	public DeviceAdapter(Context context, int resource,  ArrayList<BluetoothDevice> objects)
	{
		super(context, resource, objects);
		this.objects = objects;
	}


	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.devices_adapter, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		BluetoothDevice i = objects.get(position);

		if (i != null) {

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.

			TextView nom = (TextView) v.findViewById(R.id.tv_nom);
			TextView mac = (TextView) v.findViewById(R.id.tv_mac);

			// check to see if each individual textview is null.
			// if not, assign some text!
			if (nom != null){
				nom.setText(i.getName());
			}
			if (mac != null){
				mac.setText(i.getAddress());
			}
		}

		// the view must be returned to our activity
		return v;

	}


	public ArrayList<BluetoothDevice> getDevices()
	{
		return this.objects;
	}
}
