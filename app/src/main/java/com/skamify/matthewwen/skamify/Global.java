package com.skamify.matthewwen.skamify;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class Global {

    public static void addFragment(FragmentManager manager, Fragment fragment, int id){
        manager.beginTransaction().add(id, fragment).commit();
    }

    public static void addFragmentBack(FragmentManager manager, Fragment fragment, int id){
        manager.beginTransaction().add(id, fragment).addToBackStack(null).commit();
    }

    public static void removeFragment(FragmentManager manager, Fragment fragment){
        manager.beginTransaction().remove(fragment).commit();
    }
}
