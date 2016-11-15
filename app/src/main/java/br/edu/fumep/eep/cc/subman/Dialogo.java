package br.edu.fumep.eep.cc.subman;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by arabasso on 15/11/2016.
 *
 */

public class Dialogo {
    public static void mostrar(final Context context, int titulo, int mensagem, DialogInterface.OnClickListener clickListener){
        new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(mensagem)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, clickListener)
                .setNegativeButton(android.R.string.no, null).show();
    }
}
