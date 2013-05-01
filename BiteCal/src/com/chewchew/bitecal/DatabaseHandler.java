package com.chewchew.bitecal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.chewchew.bitecal/databases/";
	private static String DB_VERSION = "01";
	private static String DB_NAME = "bitecal" + DB_VERSION +  ".db";
    private SQLiteDatabase db; 
    private final Context local_context;
	
	public DatabaseHandler(Context context) {
		super(context, DB_NAME, null, 1);
		this.local_context = context;
		//DB_PATH = local_context.getFilesDir().getPath();
		System.out.println(DB_PATH);
		
		
		File f = new File(DB_PATH);
		if (!f.exists()) {
			f.mkdir();
		}
	}
	
	public void createDataBase() throws IOException {
		System.out.println("Checking for the DB");
		boolean db_exists = checkDataBase();
    	if(!db_exists){
    		System.out.println("Creating the DB");
    	   	this.getReadableDatabase();
        	try {
        		System.out.println("Attemping to copy the DB");
     			copyDataBase();
     		} catch (IOException e) {
         		System.out.println("Error copying database");
         		throw new Error("Error copying database");
         	}
    	}
    }
	
	  /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		System.out.println("The DB did exist");
 
    	}catch(Exception e){
 
    		//database does't exist yet.
    		System.out.println("The DB didn't exist");
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
    
    public void copyAnyway() throws IOException {
    	copyDataBase();
    }
    
    public void insertRow (String name, float cal) {
    	name = name.toUpperCase();
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues cv = new ContentValues();
    	cv.put("name", name);
    	cv.put("cal", cal);
    	
    	db.insert("bitecal", null, cv);
    	db.close();
    }
    
    public ArrayList<Food> sendQuery (String search_words) {
    	search_words = search_words.toUpperCase();
    	String[] split_search = search_words.split(" ");
    	
    	String query = "SELECT name,cal FROM bitecal WHERE";
    	for (String str : split_search) {
    		query += " name LIKE ";
    		
    		str = "%" + str + "%";
    		//System.out.println(str);
    		query += "'" + str + "' AND";
    	}
    	query = query.substring(0,query.length()-4);
    	System.out.println(query);
    	
    	//System.out.println("Searching for " + search_words);
    	
    	ArrayList<Food> result_array = new ArrayList<Food>();
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(query, new String[] {});
    	
    	if (c.moveToFirst()) {
    		
    		System.out.println(c.getString(0) + ">>" + c.getString(1));
    		result_array.add(new Food(c.getString(0), 1.0, Double.parseDouble(c.getString(1))));
		    while (c.moveToNext()) {
		    	result_array.add(new Food(c.getString(0), 1.0, Double.parseDouble(c.getString(1))));
		        //c.moveToNext();
		    }
		}
    	db.close();
    	return result_array;
    }

	  /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream input_str = local_context.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String output_file = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream output_str = new FileOutputStream(output_file);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[400000];
    	int length;
    	while ((length = input_str.read(buffer))>0){
    		output_str.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	output_str.flush();
    	output_str.close();
    	input_str.close();
 
    }
 
    public void openDataBase() throws SQLException {
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(db != null)
    		    db.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
