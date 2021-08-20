package codepath.demos.helloworlddemo;

import android.app.Activity;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HelloWorldActivity extends Activity {

	List<String> items;
	Button btnAdd;
	EditText etItem;
	RecyclerView rvItems;
	ItemsAdapter itemsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);

		btnAdd = findViewById(R.id.btnAdd);
		etItem = findViewById(R.id.etitem);
		rvItems = findViewById(R.id.rvItems);



		loadItems();

		ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
			@Override
			public void onItemLongClicked(int position) {
				items.remove(position);
				itemsAdapter.notifyItemRemoved(position);
				Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
				saveItems();
			}
		};

		final ItemsAdapter itemsAdapter = new ItemsAdapter(items, onLongClickListener);
		rvItems.setAdapter(itemsAdapter);
		rvItems.setLayoutManager(new LinearLayoutManager(this ));

		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String todoItem = etItem.getText().toString();
				items.add(todoItem);
				itemsAdapter.notifyItemInserted(items.size()-1);
				etItem.setText("");
				Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
				saveItems();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_hello_world, menu);
		return true;
	}

	private File getDataFile(){
		return new File(getFilesDir(), "data.txt");
	}

	private  void loadItems(){
		try{
		items = new ArrayList<>(FileUtils.readLines(getDataFile(), String.valueOf(Charset.defaultCharset())));
		} catch (IOException e){
			Log.e("MainActivity", "Error reading items", e);
			items = new ArrayList<>();
		}
	}

	private void saveItems(){
		try{
			FileUtils.writeLines(getDataFile(), items);
		} catch (IOException e){
			Log.e("MainActivity", "Error writing items", e);
			items = new ArrayList<>();
		}


	}



}
