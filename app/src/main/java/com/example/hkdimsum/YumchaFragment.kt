package com.example.hkdimsum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hkdimsum.ui.dashboard.DashboardFragment
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YumchaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YumchaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var dbref: DatabaseReference
    lateinit var userRecyclerview: RecyclerView
    lateinit var userArrayList: ArrayList<DimsumData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yumcha, container, false)
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
                    userRecyclerview.adapter = DashboardFragment(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}