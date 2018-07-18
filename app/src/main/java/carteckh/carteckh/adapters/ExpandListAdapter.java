package carteckh.carteckh.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import carteckh.carteckh.R;
import carteckh.carteckh.model.Child;
import carteckh.carteckh.model.Group;

/**
 * Created by Preet Saini on 22-01-2016.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {
    private Context context;
    int arr[]=new int[]{R.drawable.ic_expand_more,R.drawable.ic_expand_less};
    private ArrayList<Group> groups;
    private int[] icons={R.drawable.home_icon,R.drawable.home_icon,R.drawable.edit_icon,R.drawable.home_icon,R.drawable.edit_icon,R.drawable.home_icon,R.drawable.home_icon,R.drawable.edit_icon,R.drawable.home_icon};


    public ExpandListAdapter(Context context, ArrayList<Group> groups)
    {
        this.context = context;
        this.groups = groups;
    }
   /* public  void addItems(Child item,Group group)
    {
        if (!groups.contains(group)) {
            groups.add(group);
        }
        int index = groups.indexOf(group);
        ArrayList<Child> ch = groups.get(index).getItems();
        ch.add(item);
        groups.get(index).setItems(ch);
    }*/
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
            return chList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return  childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        Group group = (Group) getGroup(groupPosition);

        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.nav_drawer_item_group, null);
        }
        ImageView icon= (ImageView) view.findViewById(R.id.iv_icon);
        icon.setImageResource(icons[groupPosition]);
        TextView tv = (TextView) view.findViewById(R.id.tv_groupitem);
        Typeface typeface = Typeface.createFromAsset(tv.getContext().getAssets(), "OpenSans-Regular.ttf");
        tv.setTypeface(typeface);
        tv.setText(group.getName());
        ImageView img= (ImageView) view.findViewById(R.id.icon_expand);
        if(isExpanded)
        {
            img.setImageResource(R.drawable.ic_expand_less);
        }
        else
        {
            img.setImageResource(R.drawable.ic_expand_more);
        }
        if (getChildrenCount(groupPosition)==0) {
            img.setVisibility(View.GONE);
        }
        // TODO Auto-generated method stub
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       Child child = (Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_drawer_row, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.tv_childitem);
        Typeface typeface = Typeface.createFromAsset(expandedListTextView.getContext().getAssets(), "OpenSans-Regular.ttf");
        expandedListTextView.setTypeface(typeface);
        expandedListTextView.setText(child.getName().toString());
        expandedListTextView.setTag(child.getTag());
        // TODO Auto-generated method stub
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
