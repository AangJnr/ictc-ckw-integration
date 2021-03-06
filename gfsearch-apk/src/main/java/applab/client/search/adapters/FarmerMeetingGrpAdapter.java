package applab.client.search.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import applab.client.search.R;
import applab.client.search.model.Farmer;
import applab.client.search.model.Meeting;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by skwakwa on 9/29/15.
 */
public class FarmerMeetingGrpAdapter extends BaseExpandableListAdapter {


    //    private final String[] groupItem;
    private Context mContext;
    public LayoutInflater minflater;
    public ExpandableListView list;
    public int[] groupIcons;
    List<String> listTitles = null;
    String [] colors ;

    Map<String, List<Meeting>> clusterData = null;

    //    private final String[] name;
//    private final String[] location;
//    private final String[] mainCrop;
//    private final String[] groups;
    public int lastExpandedGroupPosition;

    public FarmerMeetingGrpAdapter(Context mContext,
                          List<String> titles,
                          Map<String, List<Meeting>> clusterData,

                          String [] colors,
                          ExpandableListView list) {
//        groupItem = groupItems;
        this.mContext = mContext;
        listTitles = titles;
        this.clusterData = clusterData;
        this.groupIcons = groupIcons;

        minflater = LayoutInflater.from(mContext);
        this.list = list;
        this.colors = colors;

    }

    public int getGroupCount() {
        return listTitles.size();
    }

    public int getChildrenCount(int groupPosition) {

        int count = 0;
        try {
            System.out.println("Chile Position " + groupPosition);
            System.out.println("Group name : " + listTitles.get(groupPosition));
            count = clusterData.get(listTitles.get(groupPosition)).size();
        } catch (Exception e) {

        }

        return count;
    }

    public Object getGroup(int i) {
        return null;
    }

    public Object getChild(int i, int i1) {
        return null;
    }

    public long getGroupId(int i) {
        return 0;
    }

    public long getChildId(int i, int i1) {
        return 0;
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.list_simple_text_text_, viewGroup, false);
        }

        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.simp_rel_lay);

       // l.setBackgroundColor(Color.parseColor("#87A03B"));
//#87A03B

        TextView names = (TextView) view.findViewById(R.id.lst_text_item_titles);
        TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon);
        String [] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        String cluster = listTitles.get(groupPosition);
        names.setText(Html.fromHtml(cluster));
        names.setTextColor(Color.parseColor("#000000"));
        Meeting  m= clusterData.get(listTitles.get(groupPosition)).get(0);

        Calendar cal  = Calendar.getInstance();
        cal.setTime(m.getScheduledDate());
        icon.setBackgroundColor(Color.parseColor(getColor()));
        icon.setText(months[(cal.get(Calendar.MONTH))%12]);


        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = minflater.inflate(R.layout.list_simple_child_text, viewGroup, false);
        }
        List<Meeting> farmers = clusterData.get(listTitles.get(groupPosition));
        Meeting farmer = farmers.get(childPosition);
        TextView names = (TextView) view.findViewById(R.id.lst_text_item_titles1);
        TextView icon = (TextView) view.findViewById(R.id.lst_text_item_icon1);


        RelativeLayout l = (RelativeLayout) view.findViewById(R.id.simp_rel_lay);
        if(farmer.getFarmerDetails()== null)
            l.setVisibility(View.GONE);
        else {
            //To check it out
            if (farmer.getAttended() == 1 && childPosition > 0) {

                names.setTextColor(Color.parseColor("#cccccc"));
                icon.setBackgroundColor(Color.parseColor("#cccccc"));
            } else {
                l.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            names.setText(farmer.getFarmerDetails().getFullname());

            icon.setText(String.valueOf(farmer.getFarmerDetails().getLastName().charAt(0)));
        }return view;
    }

    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void onGroupExpanded(int groupPosition) {

        if (groupPosition != lastExpandedGroupPosition) {
            list.collapseGroup(lastExpandedGroupPosition);

        }

        super.onGroupExpanded(groupPosition);

        lastExpandedGroupPosition = groupPosition;

    }

    public String getColor(){
        return colors[(new Double(Math.floor(Math.random()*colors.length)).intValue())];
    }
}

