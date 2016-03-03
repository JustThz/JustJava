package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int numberOfCoffees = 0;

    //public void getName(View view) {
    //    EditText getName = (EditText) findViewById(R.id.name_edit_text);
    //}

    public void submitOrder(View view) {
        EditText getName = (EditText) findViewById(R.id.name_edit_text);
        String name = getName.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("MainActivity", "Has Whipped Cream:" + hasWhippedCream); //Linea para enviar información al Log de la PC

        int price = CalculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        displayMessage(priceMessage);                                 //Se deshabilito displayMessaye ya que se envia ahora a Intent

        // Esto es un Array y se inicia con las {}
        String[] hi = {"JustThz@hotmail.com"};                          //Array de direcciones que se puede agregar al Intent
        composeEmail(hi, "Hola", priceMessage);                         //llamada a Intent
    }

    public void composeEmail(String[] addresses, String subject, String message ) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));                           // only email apps should handle thisX
        intent.putExtra(Intent.EXTRA_TEXT,message);                     //Esta linea en donde se agrega el texto que se quiere enviar
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);                 //Esta linea Agrega la dirección, esto puede ser un array
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);                 //Se agrega el asunto
        if (intent.resolveActivity(getPackageManager()) != null) {      //Condicional de seguridad
            startActivity(intent);
        }
    }

    public String createOrderSummary(int price, boolean WhippedCream, boolean Chocolate, String name) {
        String message = "\n"+ getString(R.string.order, name);
        message += "\n\n"+ getString(R.string.Name, name )+ ": "+ name;
        message += "\n" + getString(R.string.quantity, name ) + ": " + numberOfCoffees;
        message += "\n" + getString(R.string.Add, name ) + " " + getString(R.string.Whipped_Cream, name ) + "? " + WhippedCream;
        message += "\n" + getString(R.string.Add, name ) + " "  + getString(R.string.Chocolate, name ) + "? " + Chocolate;
        message += "\nTotal: $ " + price + "\n\n" + getString(R.string.Thank_you, name ) + "!";
        return (message);
    }

    public int CalculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int total = 5;
        Log.v("MainActivity", "Price: " + total);
        if (hasWhippedCream) {
            total = total + 1;
        }
        if (hasChocolate) {
            total = total + 2;
        }
        return total * numberOfCoffees;
    }

    public void increment(View view) {
        int plus = numberOfCoffees + 1;
        if (plus < 20) {
            numberOfCoffees = numberOfCoffees + 1;
            display(plus);
        } else {
            // Para el toast puede ser de esta forma o ver method decrement
            Context context = getApplicationContext();
            CharSequence text = "Too much Coffee STOP!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            numberOfCoffees = 20;
            display(numberOfCoffees);
        }
    }

    public void decrement(View view) {
        int menos = numberOfCoffees - 1;
        if (menos > 0) {
            numberOfCoffees = numberOfCoffees - 1;
            display(menos);
        } else {
            // De esta manera tambien se podria poner el Toast
            Toast.makeText(this, "Imaginary Coffees not allowed", Toast.LENGTH_SHORT).show();
            numberOfCoffees = 0;
            display(numberOfCoffees);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * // This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
        //composeEmail(message);

    }

}