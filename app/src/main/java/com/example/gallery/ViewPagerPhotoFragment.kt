package com.example.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.gallery.data.PhotoItem
import kotlinx.android.synthetic.main.fragment_view_pager_photo.*


class ViewPagerPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val photoList = arguments?.getParcelableArrayList<PhotoItem>("PHOTO_LIST")
        PhotoAdapter().apply {
            viewpager2.adapter = this
            submitList(photoList)
        }

        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                photoNumber.text = "${position + 1}/${photoList?.size}"
            }
        })

        viewpager2.setCurrentItem(arguments?.getInt("PHOTO_POSITION") ?: 0,false)
    }
}