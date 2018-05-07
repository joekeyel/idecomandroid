package my.com.tm.idecomm;

/**
 * Created by user on 15/12/2017.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ListRemainingAdapter extends ArrayAdapter implements Filterable {

    private List<ListRemainingModel> Remaining;
    private List<ListRemainingModel> orig;
    private int resource;
    private LayoutInflater inflator;

    public ListRemainingAdapter(Context context, int resource, List<ListRemainingModel> objects) {
        super(context, resource, objects);

        Remaining = objects;
        this.resource = resource;
        inflator = (LayoutInflater)getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }



    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<ListRemainingModel> results = new ArrayList<ListRemainingModel>();
                if (orig == null)
                    orig = Remaining;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ListRemainingModel g : orig) {
                            if (g.getsite().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
//                            if (g.getServiceNo().toLowerCase()
//                                    .contains(constraint.toString()))
//                                results.add(g);
//                            if (g.getReferencenumber().toLowerCase()
//                                    .contains(constraint.toString()))
//                                results.add(g);

                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          Filter.FilterResults results) {
                Remaining = (ArrayList<ListRemainingModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Remaining.size();
    }

    @Override
    public Object getItem(int position) {
        return Remaining.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            convertView = inflator.inflate(resource,null);
        }

        TextView site,buyer;
        TextView exchange,siteown,status;
        TextView ar,loa,f92,inv,pay,wp,stage;

        if(!Remaining.get(position).getar().equals("100%") ){

            site = (TextView) convertView.findViewById(R.id.satu);
            exchange = (TextView) convertView.findViewById(R.id.dua);
            siteown = (TextView) convertView.findViewById(R.id.tiga);
            buyer = (TextView) convertView.findViewById(R.id.empat);
            stage = (TextView) convertView.findViewById(R.id.lima);
            status = (TextView) convertView.findViewById(R.id.enam);

            site.setText(Remaining.get(position).getsite());
            exchange.setText(Remaining.get(position).getexchange());
            siteown.setText(Remaining.get(position).getsiteown());
            buyer.setText(Remaining.get(position).getbuyer());
            status.setText(Remaining.get(position).getar());
            stage.setText("AR Pending");

        }else if(!Remaining.get(position).getf92().equals("100%") ){

            site = (TextView) convertView.findViewById(R.id.satu);
            exchange = (TextView) convertView.findViewById(R.id.dua);
            siteown = (TextView) convertView.findViewById(R.id.tiga);
            buyer = (TextView) convertView.findViewById(R.id.empat);
            stage = (TextView) convertView.findViewById(R.id.lima);
            status = (TextView) convertView.findViewById(R.id.enam);

            site.setText(Remaining.get(position).getsite());
            exchange.setText(Remaining.get(position).getexchange());
            siteown.setText(Remaining.get(position).getsiteown());
            buyer.setText(Remaining.get(position).getbuyer());
            status.setText(Remaining.get(position).getf92());
            stage.setText("F92 Pending");

        }else if(!Remaining.get(position).getinv().equals("100%") ){

            site = (TextView) convertView.findViewById(R.id.satu);
            exchange = (TextView) convertView.findViewById(R.id.dua);
            siteown = (TextView) convertView.findViewById(R.id.tiga);
            buyer = (TextView) convertView.findViewById(R.id.empat);
            stage = (TextView) convertView.findViewById(R.id.lima);
            status = (TextView) convertView.findViewById(R.id.enam);

            site.setText(Remaining.get(position).getsite());
            exchange.setText(Remaining.get(position).getexchange());
            siteown.setText(Remaining.get(position).getsiteown());
            buyer.setText(Remaining.get(position).getbuyer());
            status.setText(Remaining.get(position).getinv());
            stage.setText("Invoice Pending");

        }else if(!Remaining.get(position).getpay().equals("100%") ){

            site = (TextView) convertView.findViewById(R.id.satu);
            exchange = (TextView) convertView.findViewById(R.id.dua);
            siteown = (TextView) convertView.findViewById(R.id.tiga);
            buyer = (TextView) convertView.findViewById(R.id.empat);
            stage = (TextView) convertView.findViewById(R.id.lima);
            status = (TextView) convertView.findViewById(R.id.enam);

            site.setText(Remaining.get(position).getsite());
            exchange.setText(Remaining.get(position).getexchange());
            siteown.setText(Remaining.get(position).getsiteown());
            buyer.setText(Remaining.get(position).getbuyer());
            status.setText(Remaining.get(position).getpay());
            stage.setText("Payment Pending");

        }else if(!Remaining.get(position).getwp().equals("100%") ){

            site = (TextView) convertView.findViewById(R.id.satu);
            exchange = (TextView) convertView.findViewById(R.id.dua);
            siteown = (TextView) convertView.findViewById(R.id.tiga);
            buyer = (TextView) convertView.findViewById(R.id.empat);
            stage = (TextView) convertView.findViewById(R.id.lima);
            status = (TextView) convertView.findViewById(R.id.enam);

            site.setText(Remaining.get(position).getsite());
            exchange.setText(Remaining.get(position).getexchange());
            siteown.setText(Remaining.get(position).getsiteown());
            buyer.setText(Remaining.get(position).getbuyer());
            status.setText(Remaining.get(position).getwp());
            stage.setText("Work Permit Pending");
        }

        return convertView;
    }
}

