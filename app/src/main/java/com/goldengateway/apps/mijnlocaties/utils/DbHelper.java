package com.goldengateway.apps.mijnlocaties.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.goldengateway.apps.mijnlocaties.model.Locatie;

/**
 * Created by Rob on 04-10-15.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MIJNLOCATIES.DB";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "CREATE TABLE LOCATIES ( LOCATIEID INTEGER PRIMARY KEY, NAAMKORT TEXT, OMSCHRIJVING TEXT, ADRES TEXT, POSTCODE TEXT, LGRAAD REAL, BGRAAD REAL)";
        db.execSQL(strSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LOCATIES");
        onCreate(db);
    }

    // voor test doeleinden:
    public void emptyTable( String strTabel ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strQuery = "DELETE FROM " + strTabel;
        db.execSQL(strQuery);
        db.close();//TEST
    }

    public boolean isTableLocatieSEmpty() {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        int intAantal = 0;
        String sel = "SELECT COUNT(*) AS AANTAL FROM LOCATIES";
        Cursor cur = db.rawQuery(sel, null);
        cur.moveToFirst();
        if (cur.isAfterLast() == false) {
            intAantal = cur.getInt(0);
            if ( intAantal == 0) {
                result = true;
            }
        }
        cur.close();

        return result;
    }

    public void insertLocatie( Locatie iLoc) {
        SQLiteDatabase db = this.getWritableDatabase();

        String strQuery = "INSERT INTO LOCATIES ( OMSCHRIJVING, NAAMKORT, ADRES, POSTCODE, BGRAAD, LGRAAD ) VALUES ( " +
                StringFunc.QuotedString( iLoc.getOmschrijving()) + "," +
                StringFunc.QuotedString( iLoc.getNaamkort()) + "," +
                StringFunc.QuotedString( iLoc.getAdres()) + "," +
                StringFunc.QuotedString( iLoc.getPostcode()) + "," +
                iLoc.getBgraad() + "," +
                iLoc.getLgraad() + ")";
        db.execSQL(strQuery);

        db.close();
    }

    public void saveLocatie( Locatie loc) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strQuery = "UPDATE LOCATIES SET " +
                "OMSCHRIJVING = " + StringFunc.getSQLString(loc.getOmschrijving()) + ", " +
                "LGRAAD      = " + StringFunc.getSQLString(loc.getLgraad()) + ", " +
                "BGRAAD      = " + StringFunc.getSQLString(loc.getBgraad()) + " " +
                "WHERE LOCATIEID = " + StringFunc.getSQLString(loc.getLocatieid());
        db.execSQL(strQuery);
        db.close();
    }

    public void deleteLocatie( String strId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String strQuery = "DELETE FROM LOCATIES WHERE LOCATIEID = " + strId;
        db.execSQL(strQuery);
        db.close();
    }


    public Locatie getLocatieById( String strId ) {
        SQLiteDatabase db = this.getReadableDatabase();
        Locatie locatie = new Locatie();

        String sel = "SELECT * FROM LOCATIES WHERE LOCATIEID = " + strId;
        Cursor cur = db.rawQuery(sel, null);
        cur.moveToFirst();
        if (cur.isAfterLast() == false) {
            locatie.setLocatieid(cur.getInt(0));
            locatie.setNaamkort(cur.getString(1));
            locatie.setOmschrijving(cur.getString(2));
            locatie.setAdres(cur.getString(3));
            locatie.setPostcode(cur.getString(4));
            locatie.setLgraad(cur.getDouble(5));
            locatie.setBgraad(cur.getDouble(6));
        }
        cur.close();
        return locatie;
    }

}
