package com.example.gallery

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gallery.data.GalleryViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : Fragment() {

    lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        val galleryAdapter = GalleryAdapter()
        recycleview.apply {
            adapter = galleryAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }

        galleryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GalleryViewModel::class.java)
        galleryViewModel.photoListLive.observe(this, Observer {
            galleryAdapter.submitList(it)
            swipeRefresh.isRefreshing = false
        })

        galleryViewModel.photoListLive.value ?: galleryViewModel.fetData()
        swipeRefresh.setOnRefreshListener {
            galleryViewModel.fetData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                swipeRefresh?.isRefreshing = true
                Handler().postDelayed({ galleryViewModel.fetData() }, 1000)

            }
        }

        return super.onOptionsItemSelected(item)
    }
}

