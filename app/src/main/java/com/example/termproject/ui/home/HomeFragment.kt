package com.example.termproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.termproject.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // RecyclerView 어댑터 생성 및 연결 (클릭 시 수정 다이얼로그)
        val adapter = RoutineAdapter(emptyList()) { position, routine ->
            val editText = android.widget.EditText(requireContext())
            editText.setText(routine.title)
            val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
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
                .create()
            dialog.show()
        }
        binding.recyclerRoutine.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.recyclerRoutine.adapter = adapter

        // LiveData 관찰하여 목록 갱신 + 로그
        homeViewModel.routines.observe(viewLifecycleOwner) { routines ->
            Log.d("RoutineTest", "routines size: ${routines.size}, data: $routines")
            adapter.submitList(routines)
        }

        // +버튼 클릭 시 다이얼로그 띄우기
        binding.fab.setOnClickListener {
            val editText = android.widget.EditText(requireContext())
            val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("루틴 추가")
                .setMessage("루틴 제목을 입력하세요.")
                .setView(editText)
                .setPositiveButton("추가") { _, _ ->
                    val title = editText.text.toString().trim()
                    Log.d("RoutineTest", "addRoutine called with title: $title")
                    if (title.isNotEmpty()) {
                        homeViewModel.addRoutine(title)
                    }
                }
                .setNegativeButton("취소", null)
                .create()
            dialog.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

