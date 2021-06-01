package com.example.notepad

import android.database.Cursor

//essa interface é responsavel por implementar as ações de click de dentro do adapter
interface NoteClickedListener {


    fun noClickedItem(cursor: Cursor) {

    }

    fun noteRemoveItem(cursor: Cursor?) {

    }
}

