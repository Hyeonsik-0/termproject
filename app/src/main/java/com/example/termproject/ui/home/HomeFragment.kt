package com.example.termproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.termproject.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RoutineAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // 오늘 날짜 표시
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.textToday.text = today

        // 어댑터 생성 (onAddClick 람다 전달)
        adapter = RoutineAdapter(
            emptyList(),
            onItemClick = { position, routine ->
                val editText = EditText(requireContext())
                editText.setText(routine.title)
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("루틴 수정")
                    .setMessage("루틴 제목을 수정하세요.")
                    .setView(editText)
                    .setPositiveButton("수정") { _, _ ->
                        val newTitle = editText.text.toString().trim()
                        if (newTitle.isNotEmpty()) {
                            homeViewModel.updateRoutine(position, newTitle)
                        }
                    }
                    .setNegativeButton("취소", null)
                    .setNeutralButton("삭제") { _, _ ->
                        homeViewModel.deleteRoutine(position)
                    }
                    .show()
            },
            onAddClick = {
                val editText = EditText(requireContext())
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("루틴 추가")
                    .setMessage("루틴 제목을 입력하세요.")
                    .setView(editText)
                    .setPositiveButton("추가") { _, _ ->
                        val title = editText.text.toString().trim()
                        if (title.isNotEmpty()) {
                            homeViewModel.addRoutine(title)
                        }
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // LiveData 관찰
        homeViewModel.routines.observe(viewLifecycleOwner) { routines ->
            adapter.submitList(routines)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}