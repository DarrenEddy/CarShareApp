package com.de.carshare.rvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.de.carshare.R
import com.de.carshare.models.Request
import java.text.SimpleDateFormat
import java.util.Calendar

class RequestAdapter(var items: List<Request>) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>(){
    inner class RequestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.row_requests,parent,false)
        return RequestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = items.get(position)

        val tvDepart = holder.itemView.findViewById<TextView>(R.id.tvDepart)
        val tvArival = holder.itemView.findViewById<TextView>(R.id.tvArrival)
        val tvDate = holder.itemView.findViewById<TextView>(R.id.tvDate)
        val connector = holder.itemView.findViewById<TextView>(R.id.tvDepartArrivalConnector)

        tvDepart.setText(request.departCity)
        tvArival.setText(request.arrivalCity)

        if (request.stops.isEmpty())
        {
            connector.setText("->")
        }
        else
        {
            connector.setText("...")
        }



        val sdf:SimpleDateFormat = SimpleDateFormat("MMM dd, yyyy")

        tvDate.setText(request.departDate)



    }
}