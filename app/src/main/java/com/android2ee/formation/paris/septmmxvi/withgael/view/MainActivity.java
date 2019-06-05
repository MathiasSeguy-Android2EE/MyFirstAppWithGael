package com.android2ee.formation.paris.septmmxvi.withgael.view;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android2ee.formation.paris.septmmxvi.withgael.R;
import com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain.Human;
import com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain.MyToast;
import com.android2ee.formation.paris.septmmxvi.withgael.view.adapter.HumanRecyclerViewAdapter;
import com.android2ee.formation.paris.septmmxvi.withgael.view.adapter.HumanSelectionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HumanSelectionListener{
    private static final String TAG = "MainActivity";
    public static final String EDITTEXT_VALUE = "EDITTEXT_VALUE";
    public static final String RESULT_LIST = "RESULT_LIST";
    /***********************************************************
     * Attributes
     **********************************************************/
    /**
     * Edittext Message
     */
    EditText edtMessagae;
    /**
     * The Button Add
     */
    Button btnAdd;
    /**
     * The result area
     */
    RecyclerView rcvResult;
    /**
     * dataset idsplayed by the list
     */
    ArrayList<Human> humen;
    /**
     * ArrayAdapter of the list view
     */
    HumanRecyclerViewAdapter recyclerViewAdapter;
    /**
     * The message of the selected item waiting to be paste
     */
    String messageToCopy;
    /***********************************************************
     * Animations
     **********************************************************/
    /**
     * Post Honey comb animation for the button add
     */
    AnimatorSet btnAddAnimation;
    /**
     * Previous Honey comb animation for the button add
     */
    Animation btnAddAnimationGinger;
    /***********************************************************
     *  Temp variables
     **********************************************************/
    /**
     * Temp string
     */
    String messageTemp;
    /***********************************************************
     * Managing Life Cycle
     **********************************************************/
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        //inflate the layout
        setContentView(R.layout.activity_main);
        //initialiaze the graphical components
        edtMessagae= (EditText) findViewById(R.id.edtMessage);
        btnAdd= (Button) findViewById(R.id.btnAdd);
        rcvResult = (RecyclerView) findViewById(R.id.rcvResult);
        rcvResult.setLayoutManager(getLayoutManager(LIN));
        if(savedInstanceState!=null&&savedInstanceState.containsKey(RESULT_LIST)){
            humen =savedInstanceState.getParcelableArrayList(RESULT_LIST);
        }else{
            humen =new ArrayList<>();
            for(Human hum:Human.findAll(Human.class)){
                    humen.add(hum);
            }
        }
        recyclerViewAdapter =new HumanRecyclerViewAdapter(this, humen,this);
        rcvResult.setAdapter(recyclerViewAdapter);
        //Manage your animations
        if(getResources().getBoolean(R.bool.postICS)){
            btnAddAnimation= (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.rotate_y);
            btnAddAnimation.setTarget(btnAdd);
        }else{
            btnAddAnimationGinger= AnimationUtils.loadAnimation(this,R.anim.bump);
        }
        //add your listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMessage();
            }
        });
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        super.onRestoreInstanceState(savedInstanceState);
        edtMessagae.setText(savedInstanceState.getString(EDITTEXT_VALUE));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState() called with: " + "outState = [" + outState + "]");
        super.onSaveInstanceState(outState);
        //save your elements
        outState.putString(EDITTEXT_VALUE,edtMessagae.getText().toString());
        outState.putParcelableArrayList(RESULT_LIST, humen);
    }

    /***********************************************************
     *  Playing with layout Managers
     **********************************************************/
    /**
     * The request type for layoutManager
     */
    private final static int GRID=0,STAG=1,LIN=2;

    /**
     * Return the LayoutManager of the specified type
     * @param type constants above
     * @return
     */
    private RecyclerView.LayoutManager getLayoutManager(int type){
        switch (type){
            case GRID:
                return getLayoutManagerGrid();
            case LIN:
                return getLayoutManagerLinear();
            case STAG:
                return getLayoutManagerStaggered();
            default:
                return getLayoutManagerLinear();
        }
    }
    /**
     * LayoutManager of Grid type
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManagerGrid() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        //define specific span of specific cells according to a rule
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int arg0) {
                return (arg0 % 3) == 0 ? 2 : 1;
            }
        });
        return gridLayoutManager;
    }
    /**
     * LayoutManager of Grid type
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManagerStaggered() {
        StaggeredGridLayoutManager stagLayoutManager=new StaggeredGridLayoutManager(2,GridLayoutManager.VERTICAL);

        stagLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        return stagLayoutManager;
    }
    /**
     * LayoutManager of Grid type
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManagerLinear() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        return linearLayoutManager;
    }


    /***********************************************************
    *  Business Methods
    **********************************************************/
    /**
     * Add the content of the edittext in the result area
     */
    private void addMessage(){
        messageTemp = edtMessagae.getText().toString();
        recyclerViewAdapter.add(new Human(messageTemp, recyclerViewAdapter.getItemCount()));
        edtMessagae.setText("");
        //Launch your animations
        if(getResources().getBoolean(R.bool.postICS)){
            btnAddAnimation.start();
        }else{
            btnAdd.startAnimation(btnAddAnimationGinger);
        }
    }


    /**
     * When an human is selected in the recyclerview this method is called
     *
     * @param human
     */
    @Override
    public void humanSelected(Human human) {
        messageToCopy=human.getMessage();
        showDialog(ALERT_DIALOG_ID);
    }
    /***********************************************************
     *  Managing Dialog
     **********************************************************/
    /**
     * DialogAlert Id
     */
    private static final int ALERT_DIALOG_ID=8112008;
    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==ALERT_DIALOG_ID){
            return buildMyAlertDialog();
        }
        return super.onCreateDialog(id);
    }

    /**
     * Create the AlertDialog
     * @return
     */
    private Dialog buildMyAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.ad_message,messageToCopy));
        builder.setPositiveButton(getString(R.string.ad_yes),new Dialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                copyToEditTextTheMessage();
            }
        });
        builder.setNegativeButton(getString(R.string.ad_no),new Dialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abordTheCopy();
            }
        });
        return builder.create();
    }

    /**
     * Copy the message to copy in the edit text
     */
    private void copyToEditTextTheMessage(){
        edtMessagae.setText(messageToCopy);
    }

    /**
     * Show a polite message to confirm the abort of the copy
     */
    private void abordTheCopy(){
        MyToast.makeText(this,"Go fuck so!",Toast.LENGTH_LONG).show();
    }

    /***********************************************************
     *  Managing Menu
     **********************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.id_item_deleteall){
            recyclerViewAdapter.deleteAll();
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }
}
