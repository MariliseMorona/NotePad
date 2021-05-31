package com.example.notepad

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns._ID
import com.example.notepad.database.NotesDatabaseHelper
import com.example.notepad.database.NotesDatabaseHelper.Companion.TABLE_NOTES
import java.lang.UnsupportedOperationException

class NotePadProvider : ContentProvider() {


    //objeto responsavel por fazer a validação da url de requisição do content provider
    private lateinit var mUriMatcher: UriMatcher
    private lateinit var dbHelper: NotesDatabaseHelper

    override fun onCreate(): Boolean {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        //sempre que for requisitado o endereço/notes ele deve trazer todas as notas
        //e o resultado dela sempre tem que ser 1
        mUriMatcher.addURI(AUTHORITY, "notes", NOTES)
        mUriMatcher.addURI(AUTHORITY, "notes/#", NOTES_BY_ID)
        if (context != null) {
            dbHelper = NotesDatabaseHelper(context as Context)
        }
        return true


    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        if (mUriMatcher.match(uri) == NOTES_BY_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linesAffect: Int = db.delete(TABLE_NOTES, "$_ID", arrayOf(uri.lastPathSegment))
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linesAffect
        } else {
            throw UnsupportedSchemeException("Uri inválida para exclusão")
        }
    }

    override fun getType(uri: Uri): String? =
        throw UnsupportedSchemeException("Uri não implementada")


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (mUriMatcher.match(uri) == NOTES) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id = db.insert(TABLE_NOTES, null, values)
            val insertUrl: Uri! = Uri.withAppendedPath(BASE_URI, id.toString())
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertUrl
        } else {
            throw UnsupportedOperationException("Uri inválida para inserção")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when {
            mUriMatcher.match(uri) == NOTES -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor = db.query(TABLE_NOTES, projection, selectionArgs, null, null, sortOrder)
                cursor.setNotificationUri(context?.contentResolver, uri)
                cursor
            }
            mUriMatcher.match(uri) == NOTES_BY_ID -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor = db.query(
                    TABLE_NOTES,
                    projection,
                    "$_ID = ?",
                    arrayOf(uri.lastPathSegment),
                    null,
                    null,
                    sortOrder
                )
                cursor.setNotificationUri((context as Context).contentResolver, uri)
                cursor
            }
            else -> {
                throw UnsupportedOperationException("Uri não implementada")
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        if (mUriMatcher.match(uri) == NOTES_BY_ID){
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val lineAffect = db.update(TABLE_NOTES, values, "$_ID = ?", arrayOf(uri.lastPathSegment))
        db.close()
        context?.contentResolver?.notifyChange(uri, null)
        return lineAffect
    }else {
        throw UnsupportedOperationException("Uri não implementada")
    }
}

    companion object{

        const val AUTHORITY = "com.example.notepad.notepadcontentprovider"
val BASE_URI = Uri.parse("content://$AUTHORITY")
        val URI_NOTES = Uri.withAppendedPath(BASE_URI, "notes")

        //content://com.example.notepad.notepadcontentprovider/notes
        // retorna a string

        const val NOTES = 1

        const val NOTES_BY_ID = 2
    }
}