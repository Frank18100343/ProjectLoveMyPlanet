package dev.frankperez.projectlovemyplanet;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;





public class GlobalClass extends Application {


    public static String idUsuario;
     public static String nombres ;
    public static String ruc ;
    public static String razonSocial;
    public static String apellidos;
    public static String email ;
    public static String telefono;
    public static int saldoPuntos;
    public static  String Tipo ;


    public static String getFecha(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String Fecha = dateFormat.format(new Date());
        return Fecha;
    }
    public static void showFecha(final Activity activity, final EditText editText, final String titulo) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String mday = day <=9? "0"+day:day+"";
                        String mmes = (month+1) <=9? "0"+(month+1):(month+1)+"";
                        final String selectedDate = mday + "/" + mmes + "/" + year;
                        editText.setText(selectedDate);
                    }
                }, editText.getText().toString());
                newFragment.show(activity.getFragmentManager(), titulo);
            }
        });
    }
    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener listener;
        public String fecha ="";
        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener, String mfecha) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.fecha = mfecha;
            fragment.setListener(listener);
            return fragment;
        }
        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            String[] fms = fecha.split("/");
            if(fms.length == 3) {
                year = Integer.parseInt(fms[2]);
                month = Integer.parseInt(fms[1])-1;
                day = Integer.parseInt(fms[0]);
            }
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }

}
