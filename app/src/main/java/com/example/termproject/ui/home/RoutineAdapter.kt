package com.example.termproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termproject.R
import com.example.termproject.model.Routine

class RoutineAdapter(
    private var routines: List<Routine>,
    private val onItemClick: (position: Int, routine: Routine) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {
    class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.tv_routine_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.routine_item, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.titleText.text = routine.title
        holder.itemView.setOnClickListener {
            onItemClick(position, routine)
        }
    }

    override fun getItemCount(): Int = routines.size

    fun submitList(newRoutines: List<Routine>) {
        routines = newRoutines
        notifyDataSetChanged()
    }
}

