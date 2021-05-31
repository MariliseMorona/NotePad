package com.example.notepad.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID


class NotesDatabaseHelper(
    context: Context
): SQLiteOpenHelper(context, "databaseNotes", null, 1) {
    override fun onCreate(db: SQLiteDatabase?){
        db?.execSQL("CREATE TABLE_$TABLE_NOTES (" +
        "$_ID INTEGER NOT NULL PRIMARY KEY, "+
        "$TITLE_NOTES TEXT NOT NULL, "+
        "$DESCRIPTION_NOTES TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    //como boa pratica de projeto, o nome da tabela com primeira letra em maiuscula
    //e o nome das colunas e minusculo
    companion object{
        const val TABLE_NOTES: String = "Notes"
        const val TITLE_NOTES: String = "title"
        const val DESCRIPTION_NOTES: String = "description"
    }
}