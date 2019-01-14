package epfl.handdrone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;

/**
 * Un {@link ArrayAdapter} qui permet d'afficher les informations concernant les drones
 * détectés par l'appareil dans une {@link android.widget.ListView}.
 * Cette implémentation affiche le nom du drone ainsi que son identifiant.
 */
public class DeviceListAdapter extends ArrayAdapter<ARDiscoveryDeviceService> {

    private final LayoutInflater mInflater;

    public DeviceListAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.device_list_item, parent, false);
            ViewHolder holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        ARDiscoveryDeviceService item = getItem(position);

        if (item != null) {
            holder.deviceName.setText(item.getName());
            holder.deviceID.setText(String.valueOf(item.getProductID()));
        }

        return convertView;
    }

    /**
     * Pattern ViewHolder permettant d'améliorer les performances de défilement des ListView.
     */
    private static class ViewHolder {

        final TextView deviceName;
        final TextView deviceID;

        ViewHolder(View rootView) {
            deviceName = (TextView) rootView.findViewById(R.id.deviceName);
            deviceID = (TextView) rootView.findViewById(R.id.deviceId);
        }
    }
}