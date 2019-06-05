/**
 * <ul>
 * <li>HumanRecyclerViewAdapter</li>
 * <li>com.android2ee.formation.paris.septmmxvi.withgael.view.adapter</li>
 * <li>20/09/2016</li>
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
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android2ee.formation.paris.septmmxvi.withgael.R;
import com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain.Human;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 20/09/2016.
 */
public class HumanRecyclerViewAdapter extends RecyclerView.Adapter<HumanRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "HumanRecyclerViewAdapter";
    /***********************************************************
     * Attributes
     **********************************************************/
    /**
     * LayoutInflater
     */
    LayoutInflater inflater;
    /**The dataset*/
    ArrayList<Human> dataset;
    /**
     * The guy to prevent when an human is selected
     */
    HumanSelectionListener humanSelectionListener;
    /**
     * Animation for the last item added
     *
     */
    Animation lastItemAnim;
    /**
     * Temps variables of getView
     */
    Human item;
    View rowView;
    ViewHolder viewHolder;
    /***********************************************************
     *  Constructors
     **********************************************************/
    public HumanRecyclerViewAdapter(Context context,ArrayList<Human> dataset, HumanSelectionListener humanSelectionListener){
        inflater=LayoutInflater.from(context);
        this.humanSelectionListener=humanSelectionListener;
        this.dataset=dataset;
        lastItemAnim= AnimationUtils.loadAnimation(context,R.anim.last_item_added);

    }
    /***********************************************************
     *  Override Methods
     **********************************************************/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            rowView=inflater.inflate(R.layout.human_toto,parent,false);
        }else{
            rowView=inflater.inflate(R.layout.human_other,parent,false);
        }
        viewHolder=new ViewHolder(rowView,this);
        rowView.setTag(viewHolder);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item=dataset.get(position);
        holder.getTxvName().setText(item.getName());
        holder.getTxvMessage().setText(item.getMessage());
        holder.getImvPicture().setImageResource(item.getPictureId());
       // if(runAnimation){
            //launch it on this viewholder
            runAnimation=false;
            holder.getRootView().startAnimation(lastItemAnim);
        //}
    }
    @Override
    public int getItemCount() {
        return  dataset.size();
    }

    /**
     * Return the human at the position
     * @param position
     * @return
     */
    public Human getItemAt(int position){
        return dataset.get(position);
    }
    /***********************************************************
     *  Managing Dataset
     **********************************************************/
    /**
     * Add the human to the list
     * @param human
     */
    public void add(Human human){
        dataset.add(dataset.size(),human);
        notifyItemInserted(dataset.size());
        runAnimation=true;
    }
    boolean runAnimation=false;

    /**
     * Delete the human at the position
     * @param position
     */
    public void deleteItemAt(int position){
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Delete all the items from the list
     */
    public void deleteAll(){
        int size=dataset.size();
        dataset.clear();
        notifyItemRangeRemoved(0,size);
    }
    /**
     * Call the interface HumanSelectionListener that an human has been selected
     * @param position
     */
    public void humanSelected(int position){
        humanSelectionListener.humanSelected(dataset.get(position));
    }
    /***********************************************************
     * Managing multiple lines
     **********************************************************/


    @Override
    public int getItemViewType(int position) {
        item=dataset.get(position);
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
    public class ViewHolder extends RecyclerView.ViewHolder{
        /***********************************************************
         * Attributes
         **********************************************************/
        TextView txvName;
        TextView txvMessage;
        ImageView imvPicture;
        ImageView imvDelete;
        View rootView;
        HumanRecyclerViewAdapter datasetManager;
        public ViewHolder(View itemView,HumanRecyclerViewAdapter adapter) {
            super(itemView);
            rootView=itemView;
            txvName= (TextView) itemView.findViewById(R.id.txvName);
            txvMessage= (TextView) itemView.findViewById(R.id.txvMessage);
            imvPicture= (ImageView) itemView.findViewById(R.id.imvPicture);
            imvDelete= (ImageView) itemView.findViewById(R.id.imvDelete);
            imvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCurrentItem();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    humanSelected();
                }
            });
            datasetManager=adapter;
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
            datasetManager.deleteItemAt(getAdapterPosition());
        }

        private void humanSelected(){
            //tell the arrayadapter an human has been selected
            datasetManager.humanSelected(getAdapterPosition());
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

        public View getRootView() {
            return rootView;
        }
    }

}
