package com.example.notepad

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.database.NotesDatabaseHelper.Companion.DESCRIPTION_NOTES
import com.example.notepad.database.NotesDatabaseHelper.Companion.TITLE_NOTES

class NotesAdapter(private val listener: NoteClickedListener): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        )


    override fun getItemCount(): Int = if (mCursor != null) mCursor?.count as Int else 0


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        mCursor?.moveToPosition(position)

        holder.noteTitle.text = mCursor?.getString(mCursor?.getColumnIndex(TITLE_NOTES) as Int)
        holder.noteDescription.text =
            mCursor?.getString(mCursor?.getColumnIndex(DESCRIPTION_NOTES) as Int)
        holder.noteButtonRemove.setOnClickListener {
            mCursor?.moveToPosition(position)
            listener.noteRemoveItem(mCursor)
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            listener.noClickedItem(mCursor as Cursor)

        }
    }

        fun setCursor(newCursor: Cursor?) {
            //notifica a activity que o nov cursor tem um novo valor
            mCursor = newCursor
            notifyDataSetChanged()
        }


    class NotesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var noteTitle = itemView?.findViewById(R.id.txt_notetitle) as TextView
        var noteDescription = itemView?.findViewById(R.id.txt_notedescription) as TextView
        var noteButtonRemove = itemView?.findViewById(R.id.btn_noteremove) as Button

    }

}