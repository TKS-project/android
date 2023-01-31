package com.example.tksproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.squareup.picasso.Picasso

data class  Data(
    var name: String? = null,
    var code: String? = null
)

class ListAdapter (val context: Context, val prefectureList : MutableList<prefecture>) : BaseAdapter() {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Any {
        return prefectureList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()    //元々はreturn 0
    }

    override fun getCount(): Int {
        return prefectureList.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = layoutInflater.inflate(R.layout.row_view, parent,false)
        val name = view.findViewById<TextView>(R.id.name)
        val code = view.findViewById<TextView>(R.id.code)
        val prefecture = prefectureList[position]


        //Picasso.get().load(user.videoThumbnail).into(view.imageIcon)
        name.text = prefecture.Name
        code.text = prefecture.Code

        return view
    }
}

class CityListAdapter (val context: Context, val cityList : MutableList<city>) : BaseAdapter() {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItem(position: Int): Any {
        return cityList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()    //元々はreturn 0
    }

    override fun getCount(): Int {
        return cityList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = layoutInflater.inflate(R.layout.row_view, parent,false)
        val name = view.findViewById<TextView>(R.id.name)
        val code = view.findViewById<TextView>(R.id.code)
        val city = cityList[position]


        //Picasso.get().load(user.videoThumbnail).into(view.imageIcon)
        name.text = city.Name
        code.text = city.Code

        return view
    }
}
class DetailedCityListAdapter (val context: Context, val dcityList : MutableList<detailedCity>) : BaseAdapter() {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItem(position: Int): Any {
        return dcityList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()    //元々はreturn 0
    }

    override fun getCount(): Int {
        return dcityList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = layoutInflater.inflate(R.layout.row_view, parent, false)
        val name = view.findViewById<TextView>(R.id.name)
        val code = view.findViewById<TextView>(R.id.code)
        val dcity = dcityList[position]


        //Picasso.get().load(user.videoThumbnail).into(view.imageIcon)
        name.text = dcity.Name
        code.text = dcity.Code

        return view
    }
}

class HotelListAdapter (val context: Context, val hotelList: MutableList<hotel>): BaseAdapter() {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getItem(position: Int): Any {
        return hotelList[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()    //元々はreturn 0
    }
    override fun getCount(): Int {
        return hotelList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //繰り返すリストの中身のXMLを指定
        val view: View = layoutInflater.inflate(R.layout.row_hotel_view, parent, false)
        val name = view.findViewById<TextView>(R.id.name)
        val no = view.findViewById<TextView>(R.id.no)
        val minCharge = view.findViewById<TextView>(R.id.minCharge)
        val nearStation = view.findViewById<TextView>(R.id.nearStation)
        val access = view.findViewById<TextView>(R.id.access)
        val reviewAvg = view.findViewById<TextView>(R.id.reviewAvg)

        val hotel = hotelList[position]
        //Picasso.get().load(user.videoThumbnail).into(view.imageIcon)//insert thumbnail
        name.text = hotel.hotelName
        no.text = hotel.hotelNo.toString()
        minCharge.text = hotel.hotelMinCharge.toString()
        nearStation.text = hotel.nearestStation
        access.text = hotel.access
        reviewAvg.text = hotel.reviewAverage.toString()

        return view
    }
}