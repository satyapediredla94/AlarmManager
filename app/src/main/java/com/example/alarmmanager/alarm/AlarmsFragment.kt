package com.example.alarmmanager.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmmanager.adapter.AlarmAdapter
import com.example.alarmmanager.databinding.FragmentAlarmsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmsFragment : Fragment() {

    private lateinit var binding: FragmentAlarmsBinding
    private val viewModel: AlarmsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAlarmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setAlarmAdapter()
        binding.fab.setOnClickListener {
            navigateToScheduleAlarmFragment()
        }
    }

    private fun setAlarmAdapter() {
        binding.alarms.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AlarmAdapter(viewModel)
            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val alarm = (adapter as AlarmAdapter).currentList[viewHolder.adapterPosition]
                    viewModel.deleteAlarm(alarm)
                }

            })
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun navigateToScheduleAlarmFragment() {
        val action = AlarmsFragmentDirections.actionAlarmsFragmentToAlarmSchedulerFragment()
        findNavController().navigate(action)
    }

}