package com.example.hkdimsum.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hkdimsum.DimsumData
import com.example.hkdimsum.R
import com.example.hkdimsum.YumchaAdpater
import com.example.hkdimsum.databinding.FragmentDashboardBinding
import com.google.firebase.database.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    lateinit var dbref: DatabaseReference
    lateinit var userRecyclerview: RecyclerView
    lateinit var userArrayList: ArrayList<DimsumData>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
//        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecyclerview = view.findViewById(R.id.showList)
        userRecyclerview.layoutManager = LinearLayoutManager(context)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()

    }

    private fun getUserData() {

        dbref =
            FirebaseDatabase.getInstance("https://hkdimsum-default-rtdb.firebaseio.com/")
                .getReference("cantoneserestaurants")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val cafe = dataSnapshot.getValue(DimsumData::class.java)
                        userArrayList.add(cafe!!)
                    }
                    userRecyclerview.adapter = YumchaAdpater(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}