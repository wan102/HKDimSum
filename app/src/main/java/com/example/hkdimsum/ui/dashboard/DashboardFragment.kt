//package com.example.hkdimsum
package com.example.hkdimsum.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hkdimsum.DimsumData
import com.example.hkdimsum.R
import com.squareup.picasso.Picasso

//class DashboardFragment : Fragment() {
//
//    private var _binding: FragmentDashboardBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)
//
//        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

class DashboardFragment(private val userList: ArrayList<DimsumData>) :
    RecyclerView.Adapter<DashboardFragment.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.mt_name)
        val desc: TextView = itemView.findViewById(R.id.mt_desc)
        val img: ImageView = itemView.findViewById(R.id.mt_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycleview_rowitem,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.name.text = currentitem.name
        holder.desc.text = currentitem.desc
        val imageTarget = currentitem.img
        Picasso.get().load(imageTarget).into(holder.img)

//        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//            val name: TextView = itemView.findViewById(R.id.mt_name)
//            val desc: TextView = itemView.findViewById(R.id.mt_desc)
//            val img: ImageView = itemView.findViewById(R.id.mt_image)
//
//        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}