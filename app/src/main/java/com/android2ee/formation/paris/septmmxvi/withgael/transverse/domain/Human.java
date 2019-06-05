/**
 * <ul>
 * <li>Human</li>
 * <li>com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain</li>
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

package com.android2ee.formation.paris.septmmxvi.withgael.transverse.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.android2ee.formation.paris.septmmxvi.withgael.R;
import com.orm.SugarRecord;

/**
 * Created by Mathias Seguy - Android2EE on 19/09/2016.
 */
public class Human extends SugarRecord implements Parcelable {
    public static final String TOTO = "Toto";
    public static final String TATA = "Tata";
    private String name;
    private String message;
    private int pictureId;

    public Human(String message, int position){
        if(position%2==0){
            name= TOTO;
        }else{
            name= TATA;
        }
        this.message=message;
        pictureId= R.mipmap.ic_human_even;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Human{");
        sb.append("message='").append(message).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    protected Human(Parcel in) {
        name = in.readString();
        message = in.readString();
        pictureId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(message);
        dest.writeInt(pictureId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Human> CREATOR = new Parcelable.Creator<Human>() {
        @Override
        public Human createFromParcel(Parcel in) {
            return new Human(in);
        }

        @Override
        public Human[] newArray(int size) {
            return new Human[size];
        }
    };
}
