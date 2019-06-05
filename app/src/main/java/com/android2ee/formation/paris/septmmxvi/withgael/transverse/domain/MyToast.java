/**
 * <ul>
 * <li>MyToast</li>
 * <li>com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain</li>
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

package com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android2ee.formation.paris.septmmxvi.withgael.R;

/**
 * Created by Mathias Seguy - Android2EE on 20/09/2016.
 */
public class MyToast {

    public static Toast makeText(Context ctx,String message,int duration){
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view= inflater.inflate(R.layout.toast_layout,null);
        ((TextView)view.findViewById(R.id.txvToastMessage)).setText(message);
        Toast toast=new Toast(ctx);
        toast.setView(view);
        toast.setDuration(duration);
        return toast;
    }
}
