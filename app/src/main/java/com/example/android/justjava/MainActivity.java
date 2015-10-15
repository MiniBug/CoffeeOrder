package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameEditText = (EditText) findViewById(R.id.edit_text_name);
        String nameOrder = nameEditText.getText().toString();
        totalPrice = calculatePrice(hasWhippedCream, hasChocolate);
        createOrderSummary(totalPrice, hasWhippedCream, hasChocolate, nameOrder);
    }

    /**
     * This method creates order summary
     *
     * @param totalPrice      for the order
     * @param hasWhippedCream tells did user order whipped cream or not for the coffee
     * @param hasChocolate    tells did user order chocolate or not for the coffee
     * @return summary of the order
     */

    private void createOrderSummary(int totalPrice, boolean hasWhippedCream, boolean hasChocolate, String nameOrder) {

        String priceMessage = getString(R.string.edit_text_name, nameOrder);
        priceMessage += "\n" + getString(R.string.whipped_cream, hasChocolate);
        priceMessage += "\n" + getString(R.string.chocolate, hasChocolate);
        priceMessage += "\n" + getString(R.string.quantity,
                NumberFormat.getCurrencyInstance().format(quantity));
        priceMessage += "\n" + getString(R.string.thank_you);


//        String summary = "Name: " + nameOrder + "\n";
//        summary += "Add whipped cream?: " + hasWhippedCream + "\n";
//        summary += "Add chocolate?: " + hasChocolate + "\n";
//        summary += "Quantity: " + quantity + "\n";
//        summary += "Total: $" + totalPrice + "\n";
//        summary += getString(R.string.thank_you);


        String recepientEmail = "filip.fabijanic@gmail.com"; //i.fabijanic5@gmail.com

        composeEmail(recepientEmail, "Coffee order for " + nameOrder, priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void composeEmail(String recepientEmail, String subject, String summary){

        Intent emailOrderIntent = new Intent(Intent.ACTION_SENDTO);
        emailOrderIntent.setData(Uri.parse("mailto:"));
        emailOrderIntent.putExtra(Intent.EXTRA_EMAIL, recepientEmail);
        emailOrderIntent.putExtra(Intent.EXTRA_SUBJECT, subject);


        emailOrderIntent.putExtra(android.content.Intent.EXTRA_TEXT, summary);

        if (emailOrderIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailOrderIntent, "Choose an Email client :"));
            displayMessage();
        }
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage() {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText("Your order has been created");
    }

    /**
     * Calculates the price for number of cupps ordered.
     *
     * @return total price.
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {

        int basePrice = 5;

        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    /*This method increment the given quantity value of cups of coffee.*/
    public void increment(View view) {

        if (quantity == 100) {
            Context context = getApplicationContext();
            CharSequence text = "You can not have more than 100 coffies";
            int duration = Toast.LENGTH_SHORT;
            // Show an error message as a toast
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity += 1;
        displayQuantity(quantity);

    }

    /*This method decrement the given quantity value of cups of coffee.*/
    public void decrement(View view) {
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "You can not have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;
            // Show an error message as a toast
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }
}