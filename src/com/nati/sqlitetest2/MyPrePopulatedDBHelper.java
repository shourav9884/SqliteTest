package com.nati.sqlitetest2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyPrePopulatedDBHelper extends SQLiteOpenHelper{
	public static String DB_PATH;

    //Database file name
   public static String DB_NAME;
   public SQLiteDatabase database;
   public final Context cntext;

    public SQLiteDatabase getDb() {
       return database;
   }
	public MyPrePopulatedDBHelper(Context context,String name) {
		super(context, name, null, 1);
		this.cntext=context;
		DB_NAME=name;
		String packageName = context.getPackageName();
	    DB_PATH = String.format("/data/data/%s/databases/", packageName);
	     
	    openDataBase();
	}
	private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = cntext.getAssets().open(DB_NAME+".sqlite");

         //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;
        System.out.println(outFileName);
         //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

         //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }
	public SQLiteDatabase openDataBase() throws SQLException {
	        String path = DB_PATH + DB_NAME;
	        if (database == null) {
	        	
	            createDataBase();
	            database = SQLiteDatabase.openDatabase(path, null,
	                SQLiteDatabase.OPEN_READWRITE);
	        }
	        return database;
	    }
	 public void createDataBase() {
	        boolean dbExist = checkDataBase();
	        System.out.println("Is Exists "+dbExist);
	        if (!dbExist) {
//	            this.getReadableDatabase();
	            try {
	            	SQLiteDatabase db=this.getReadableDatabase();
	                copyDataBase();
	                db.close();
	                
	            } catch (IOException e) {
	                Log.e(this.getClass().toString(), "Copying error");
	                throw new Error("Error copying database!");
	            }
	        } else {
	            Log.i(this.getClass().toString(), "Database already exists");
	        }
	    }
	//Performing a database existence check
	    private boolean checkDataBase() {
	    	String path=DB_PATH+DB_NAME;
	    	File file=new File(path);
	    	if(file.exists())
	    	{
	    		return true;
	    	}
	        return false;
	    }
	    
	    public synchronized void close()
	    {
	    	if(database!=null)
	    	{
	    		database.close();
	    	}
	    }
	    public ArrayList<String> getAllLocations()
	    {
	    	ArrayList<String> locs=new ArrayList<String>();
	    	SQLiteDatabase db=this.getReadableDatabase();
	    	String col[]={"LOCATION_NAME"};
	    	Cursor c=db.query("pin_location", col, "LOCATION_ID > 1 AND LOCATION_ID<100", null, null, null,null);
	    	
	    	if(c!=null && c.getCount()>0)
			{
	    		c.moveToFirst();
	    		for(int i=0;i<c.getCount();i++)
				{
	    			String name=c.getString(c.getColumnIndex("LOCATION_NAME"));
	    			locs.add(name);
	    			c.moveToNext();
	    			
				}
	    		
			
			}
	    	c.close();
	    	db.close();
			
	    	return locs;
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
