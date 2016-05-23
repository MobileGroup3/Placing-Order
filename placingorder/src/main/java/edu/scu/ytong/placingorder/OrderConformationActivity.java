package edu.scu.ytong.placingorder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;

import java.util.ArrayList;
import java.util.List;

import edu.scu.ytong.placingorder.entities.DishItem;
import edu.scu.ytong.placingorder.entities.OrderItemSimple;
import edu.scu.ytong.placingorder.entities.SimpleCartItem;

public class OrderConformationActivity extends AppCompatActivity{
    public static final String CART_LIST_EXTRA_KEY = "cart_list_extra_key";
    public static final String TOTAL_AMOUNT_EXTRA_KEY = "total_amount_extra_key";
    public static final String ADDRESS_EXTRA_KEY = "address_extra_key";
    ArrayList<SimpleCartItem> simpleCartItemList;
    List<OrderItemSimple> orderList;
    ListView orderListView;
    OrderAdapter orderAdapter;
    TextView totalAmountTextView;
    TextView addressTextView;
    String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_conformation);

        final String APPLICATION_ID = "6B06D541-69FC-AA24-FF52-EB6421144100";
        final String ANDROID_SECRET_KEY = "F2C00252-B60B-8048-FF2B-F2893504BD00";
        final String VERSION = "v1";

        Intent intent = getIntent();

        double totalAmount = intent.getDoubleExtra(TOTAL_AMOUNT_EXTRA_KEY,0);
        totalAmountTextView = (TextView) findViewById(R.id.text_view_order_amount);
        totalAmountTextView.setText("$" + String.valueOf(totalAmount));

        address = intent.getStringExtra(ADDRESS_EXTRA_KEY);
        addressTextView = (TextView) findViewById(R.id.text_view_order_address);
        addressTextView.setText(address);


        simpleCartItemList = intent.getParcelableArrayListExtra(CART_LIST_EXTRA_KEY);
        final int cartItemNumber = simpleCartItemList.size();


        orderListView = (ListView) findViewById(R.id.list_view_order_dish);
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this,R.layout.shopping_cart_item_view, orderList);
        orderListView.setAdapter(orderAdapter);

        new AsyncTask<Void,Void,List<OrderItemSimple>>(){
            @Override
            protected List<OrderItemSimple> doInBackground(Void... params) {
                for(int i = 0; i < cartItemNumber; i++) {

                    String menuObjectId = simpleCartItemList.get(i).getDishObjectId();
                    DishItem dish  = Backendless.Persistence.of(DishItem.class).findById(menuObjectId);

                    int orderQuantity = simpleCartItemList.get(i).getOrderQuantity();
                    OrderItemSimple orderItemSimple = new OrderItemSimple(dish,orderQuantity);
                    orderList.add(orderItemSimple);


                }
                return orderList;

            }

            @Override
            protected void onPostExecute(List<OrderItemSimple> orderList) {

                Log.e("orderList",String.valueOf(orderList.size()));


                orderAdapter.setData(orderList);
            }

        }.execute();










//        TextView test = (TextView) findViewById(R.id.test);
//        test.setText(String.valueOf(cartItemList.get(0).getOrderQuantity()));

    }
}
