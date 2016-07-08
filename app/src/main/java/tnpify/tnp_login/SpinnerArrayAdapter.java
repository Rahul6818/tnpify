package tnpify.tnp_login;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Tushar on 7/7/2016.
 */
public class SpinnerArrayAdapter<T> extends ArrayAdapter {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public SpinnerArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     */
    public SpinnerArrayAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public SpinnerArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public SpinnerArrayAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public SpinnerArrayAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public SpinnerArrayAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public boolean isEnabled(int position) {
        if(position == 0)
            return false;
        return true;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(position == 0) {
            view = super.getDropDownView(position, convertView, parent);
            view.setActivated(false);
            view.setBackgroundColor(Color.GRAY);
        } else {
            view = super.getDropDownView(position, null, parent);
        }
        return view;
    }
}
