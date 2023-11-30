package club.mobile.d21.personalassistant_ver2_kotlin.ui.home

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import club.mobile.d21.personalassistant_ver2_kotlin.R
import club.mobile.d21.personalassistant_ver2_kotlin.data.DailyTask
import club.mobile.d21.personalassistant_ver2_kotlin.databinding.FragmentAddDailyTaskBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

interface CallBack{
    fun add(dailyTask: DailyTask)
}
class AddDailyTaskFragment: Fragment() {
    private var _binding: FragmentAddDailyTaskBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddDailyTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var selectedTime: LocalTime = LocalTime.now()
        binding.chooseTime.setOnClickListener {
            val currentTime = LocalTime.now()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hours, minutes ->
                    selectedTime = LocalTime.of(hours,minutes)
                    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
                    binding.chooseTime.text = selectedTime.format(formatter)
                },
                currentTime.hour,
                currentTime.minute,
                true // True để hiển thị 24 giờ, false để hiển thị AM/PM
            )
            timePickerDialog.show()
        }
        binding.createButton.setOnClickListener {
            if(binding.chooseTime.text.equals("Choose your time here")) {
                return@setOnClickListener
            }
            homeViewModel.addDailyTask(DailyTask(0,
                    binding.chooseTitle.text.toString(),selectedTime,
                    binding.chooseDescription.text.toString())
            )
            parentFragmentManager.popBackStack()
        }
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNavigationView = activity?.
        findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.visibility = View.VISIBLE
        _binding = null
    }
}