package com.example.termproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.termproject.R
import com.example.termproject.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

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

        // 현재 날짜 표시
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.textToday.text = today


        // 루틴 목록이 변경될 때마다 카드 뷰를 동적으로 생성/갱신
        homeViewModel.routines.observe(viewLifecycleOwner) { routines ->
            binding.cardContainer.removeAllViews()
            for ((index, routine) in routines.withIndex()) {
                val cardView = layoutInflater.inflate(R.layout.routine_item, binding.cardContainer, false)
                val titleView = cardView.findViewById<TextView>(R.id.text_routine_title)
                titleView.text = routine.title

                cardView.setOnClickListener {
                    val editText = android.widget.EditText(requireContext())
                    editText.setText(routine.title)
                    val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("루틴 수정")
                        .setMessage("루틴 제목을 수정하세요.")
                        .setView(editText)
                        .setPositiveButton("수정") { _, _ ->
                            val newTitle = editText.text.toString().trim()
                            if (newTitle.isNotEmpty()) {
                                homeViewModel.updateRoutine(index, newTitle)
                            }
                        }
                        .setNegativeButton("취소", null)
                        .create()
                    dialog.show()
                }

                binding.cardContainer.addView(cardView)
            }
            // 버튼을 항상 마지막에 추가
            binding.cardContainer.addView(binding.btnAddRoutine)
        }

        // +버튼 클릭 시 다이얼로그 띄우기
        binding.btnAddRoutine.setOnClickListener {
            val editText = android.widget.EditText(requireContext())
            val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
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

