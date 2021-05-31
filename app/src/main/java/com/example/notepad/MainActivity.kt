package com.example.notepad

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns._ID
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.NotePadProvider.Companion.URI_NOTES
import com.example.notepad.database.NotesDatabaseHelper.Companion.TITLE_NOTES
import com.google.android.material.floatingactionbutton.FloatingActionButton


//Loadermanager, faz a busca em segundo plano do cursor, evitando erros de trash
class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    lateinit var noteRecyclerView: RecyclerView
    lateinit var noteAdd: FloatingActionButton

    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteAdd = findViewById(R.id.btn_noteadd)
        noteAdd.setOnClickListener{    }

        adapter = NotesAdapter(object: NoteClickedListener{
            override fun noteClickedItem(cursor: Cursor){
                val id: Long = cursor?.getLong(cursor.getColumnIndex(_ID))

            }

            override fun noteRemoveItem(cursor: Cursor?) {
                val id: Long? = cursor?.getLong(cursor.getColumnIndex(_ID))
                //contentResolver é o objeto nativo da Activity de comunicação com o contentProvider
                contentResolver.delete(Uri.withAppendedPath(URI_NOTES, id.toString()), null, null)

            }
        })
        //para que não tenhamm set repetidos no adapter
        adapter.setHasStableIds(true)

        noteRecyclerView = findViewById(R.id.notes_recycler)
        noteRecyclerView.layoutManager = LinearLayoutManager(this)
        noteRecyclerView.adapter = adapter

    //instacia o que sera buscado, no caso a pesquisa do Content Provider
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        CursorLoader(this, URI_NOTES, null, null, null, TITLE_NOTES)

    }
    //pega os dados recebidos e manipula-os como for melhor
    override fun onLoaderFinished(loader: Loader<Cursor>, data: Cursor?){
        if(data != null){

        }
    }
    //acaba com a pesquisa em segundo plano do loadManager
    override fun onLoaderReset(loader: Loader<Cursor>){

    }
}