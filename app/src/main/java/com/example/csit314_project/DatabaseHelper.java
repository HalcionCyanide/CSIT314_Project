package com.example.csit314_project;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME ="appDB.db";// Database name
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);// 1? Its database Version
        this.mContext = context;
        DB_PATH = mContext.getDatabasePath(DB_NAME).getPath();
    }

    public void createDataBase() throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            //this.getReadableDatabase(); //<<<<<<<<<< REMOVED (commented out)
            //this.close(); //<<<<<<<<<< REMOVED ()commented out
            try
            {
                //Copy the database from assets
                copyDataBase();
                Log.e(TAG, "createDatabase database created");
            }
            catch (IOException mIOException)
            {
                mIOException.printStackTrace(); //<<<<<<<<<< might as well include the actual cause in the log
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH); //<<<<<<<<<< just the path used
        if (dbFile.exists()) return true; //<<<<<<<<<< return true of the db exists (see NOTE001)
        if (!dbFile.getParentFile().exists()) dbFile.getParentFile().mkdirs();
        if (new File(DB_PATH + "-shm").exists())
            new File(DB_PATH + "-shm").delete();
        if ((new File(DB_PATH + "-wal")).exists())
            new File(DB_PATH + "-wal").delete();
        return false;
    }

    /** NOTE001
     *  Just checking the file does leave scope for a non sqlite file to be copied from the assets folder
     *  and be copied resulting in an exception. The above could be extended to apply additional checks
     *  if considered required e.g. checking the first sixteen bytes for The header string: "SQLite format 3\000"
     */

    //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH; //<<<<<<<<<< just the path used
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException
    {
        String mPath = DB_PATH;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    /**
     * Note this can be added and the line uncommented (see below) to disable WAL logging which
     * from Android 9 (Pie) is the default
     */
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // db.disableWriteAheadLogging(); //<<<<<<<<<< uncomment if you want to not use WAL but use the less efficient journal mode.
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}