package com.example.jetpack.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.jetpack.R
import com.example.jetpack.util.getProgressDrawable
import com.example.jetpack.util.loadImage
import com.example.jetpack.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var detailId = 0
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        arguments?.let {
            detailId = DetailFragmentArgs.fromBundle(it).detailId
        }

        if(detailId != 0){
            detailViewModel.getItemBreed(detailId)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        detailViewModel.currentItem.observe(this, Observer { itemBread ->
            itemBread?.let {
                txtFirstTitle.text = it.ItemName
                txtSecondTitle.text = it.ItemLifeSpan
                itemImageView.loadImage(itemBread.ItemImageUrl, getProgressDrawable(context!!))
            }
        })
    }
}