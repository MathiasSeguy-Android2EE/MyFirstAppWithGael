/**
 * <ul>
 * <li>HumanArrayAdapter</li>
 * <li>com.android2ee.formation.paris.septmmxvi.withgael.view.adapter</li>
 * <li>19/09/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.paris.septmmxvi.withgael.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.paris.septmmxvi.withgael.R;
import com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain.Human;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 19/09/2016.
 */
public class HumanArrayAdapter extends ArrayAdapter<Human> {
    /***********************************************************
     * Attributes
     **********************************************************/
    /**
     * LayoutInflater
     */
    LayoutInflater inflater;
    /**
     * Temps variables of getView
     */
    Human item;
    View rowView;
    ViewHolder viewHolder;
    /***********************************************************
    *  Constructors
    **********************************************************/
    public HumanArrayAdapter(Context context, ArrayList<Human> dataset) {
        super(context, R.layout.human_toto,dataset);
        inflater=LayoutInflater.from(context);

    }
    /***********************************************************
    *  Business Methods
    **********************************************************/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         item=getItem(position);
         rowView=convertView;
        if(rowView==null){
            if(getItemViewType(position)==0){
                rowView=inflater.inflate(R.layout.human_toto,parent,false);
            }else{
                rowView=inflater.inflate(R.layout.human_other,parent,false);
            }
            viewHolder=new ViewHolder(rowView,this);
            rowView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) rowView.getTag();
        viewHolder.getTxvName().setText(item.getName());
        viewHolder.getTxvMessage().setText(item.getMessage());
        viewHolder.getImvPicture().setImageResource(item.getPictureId());
        viewHolder.setCurrentPosition(position);
        return rowView;
    }

    /***********************************************************
     * Managing multiple lines
     **********************************************************/

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        item=getItem(position);
        if(item.getName().equals(Human.TOTO)){
            return 0;
        }else if(item.getName().equals(Human.TATA)){
            return 1;
        }else{
            return 1;
        }
    }
    /***********************************************************
     *  ViewHolder pattern
     **********************************************************/
    private class ViewHolder{
        /***********************************************************
         * Attributes
         **********************************************************/
        TextView txvName;
        TextView txvMessage;
        ImageView imvPicture;
        ImageView imvDelete;
        int currentPosition;
        HumanArrayAdapter datasetManager;

        /***********************************************************
        *  Constructors
        **********************************************************/
        public ViewHolder(View view,HumanArrayAdapter arrayAdapter){
            txvName= (TextView) view.findViewById(R.id.txvName);
            txvMessage= (TextView) view.findViewById(R.id.txvMessage);
            imvPicture= (ImageView) view.findViewById(R.id.imvPicture);
            imvDelete= (ImageView) view.findViewById(R.id.imvDelete);
            imvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCurrentItem();
                }
            });
            datasetManager=arrayAdapter;
        }
        /***********************************************************
        *  Business Methods
        **********************************************************/
        /**
         * Delete the item displayed by this view
         */
        private void deleteCurrentItem(){
            //find current item position
            //Tell to the guy who manage the list to delete the item
            datasetManager.remove(getItem(currentPosition));
        }

        /***********************************************************
        *  Getters/Setters
        **********************************************************/
        public ImageView getImvPicture() {
            return imvPicture;
        }

        public TextView getTxvMessage() {
            return txvMessage;
        }

        public TextView getTxvName() {
            return txvName;
        }

        public void setCurrentPosition(int currentPosition) {
            this.currentPosition = currentPosition;
        }
    }
}
