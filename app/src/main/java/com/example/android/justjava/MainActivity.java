package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        EditText text = (EditText) findViewById(R.id.name_person);
        String namePerson = text.getText().toString();

        String order = createOrderSummary(namePerson, calculatePrice(hasWhippedCream, hasChocolate), hasWhippedCream, hasChocolate);
        String mailSubject = getString(R.string.email_subject, namePerson);

        // make intent to send the order bij email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    /**
     * Creates an order summary
     *
     * @param price           of the order
     * @param hasWhippedCream of the customer has whipped cream or not
     * @return order summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String summary = getString(R.string.name_on_order, name);
        summary += "\n" + getString(R.string.quantity_on_order, quantity);
        summary += "\nHas whipped cream: " + hasWhippedCream;
        summary += "\n" + getString(R.string.has_chocolate, String.valueOf(hasChocolate)) ;
        summary += "\nTotal: $" + price;
        summary += "\nThank you";
        return summary;
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasCream, boolean hasChocolate) {
        int price = 5;
        if (hasCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 2;
        }
        return quantity * price;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this,"CAN'T ORDER MORE THAN 100 COFFEES",Toast.LENGTH_SHORT).show();

            return;
        }

        quantity = quantity + 1;

        display(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this,"CAN'T ORDER LESS THAN 1 COFFEE",Toast.LENGTH_SHORT).show();

            return;
        }

        quantity = quantity - 1;

        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int amount) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + amount);
    }



}