package com.example.termproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.R
import com.example.termproject.model.Routine

class RoutineAdapter(
    private var routines: List<Routine>,
    private val onItemClick: (position: Int, routine: Routine) -> Unit,
    private val onAddClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_ADD_BUTTON = 1
    }

    override fun getItemCount(): Int = routines.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == routines.size) TYPE_ADD_BUTTON else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.routine_item, parent, false)
            RoutineViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_button, parent, false)
            AddButtonViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RoutineViewHolder && position < routines.size) {
            val routine = routines[position]
            holder.titleText.text = routine.title
            holder.itemView.setOnClickListener {
                onItemClick(position, routine)
            }
        } else if (holder is AddButtonViewHolder) {
            holder.addButton.setOnClickListener {
                onAddClick()
            }
        }
    }

    class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_routine_title)
    }

    class AddButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addButton: Button = itemView.findViewById(R.id.btnAddRoutine)
    }

    fun submitList(newRoutines: List<Routine>) {
        routines = newRoutines
        notifyDataSetChanged()
    }
}