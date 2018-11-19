package com.victor.pruebas.demopedidosya.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.victor.pruebas.demopedidosya.R;

public class UtilGenericDialog {

    public static void showDialog(Context context, String title, String description){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setPositiveButton("Aceptar", null);
        builder.show();
    }

    public static void showGenericErrordialog (Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.update_position));
        builder.setMessage(context.getString(R.string.search_restaurants_question));
        builder.setPositiveButton("Aceptar", null);
        builder.show();
    }
}
