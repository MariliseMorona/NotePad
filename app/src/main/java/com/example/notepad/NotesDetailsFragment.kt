package com.example.notepad

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class NotesDetailsFragment: DialogFragment(), DialogInterface.OnClickListener {

private lateinit var noteEditTitle: EditText
private lateinit var noteEditDescription: EditText
private var id: Long = 0

        companion object{
            private const val EXTRA_ID = "id"

            fun newInstance(id: Long):NotesDetailsFragment{
                val bundle = Bundle()
                bundle.putLong(EXTRA_ID, id)

                val notesFragment = NotesDetailsFragment()
                notesFragment.arguments = bundle
                return notesFragment
            }
        }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
val view: View? = activity?.layoutInflater?.inflate(R.layout.note_detail, null)

        noteEditTitle = view?.findViewById(R.id.edt_notetitle) as EditText
        noteEditDescription = view.findViewById(R.id.edt_notedescription) as EditText

        return super.onCreateDialog(savedInstanceState)
    }


    override fun onClick(dialog: DialogInterface?, which: Int) {
        TODO("Not yet implemented")
    }

}