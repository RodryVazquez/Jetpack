package com.example.jetpack.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpack.R
import com.example.jetpack.view.adapters.ItemAdapter
import com.example.jetpack.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var itemViewModel: ItemViewModel
    private val itemAdapter = ItemAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)
        itemViewModel.refresh()

        mainList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = itemAdapter
        }

        refreshLayout.setOnRefreshListener {
            mainList.visibility = View.GONE
            txtError.visibility = View.GONE
            pgrLoadingView.visibility = View.VISIBLE
            itemViewModel.refresh()

            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.action_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings ->{
                val action  = ListFragmentDirections.actionListFragmentToSettingsFragment()
                Navigation.findNavController(view!!).navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        itemViewModel.itemList.observe(this, Observer { items ->
            items?.let {
                mainList.visibility = View.VISIBLE
                itemAdapter.updateItemList(items)
            }
        })

        itemViewModel.loadError.observe(this, Observer { isError ->
            isError?.let {
                txtError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        itemViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                pgrLoadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    txtError.visibility = View.GONE
                    mainList.visibility = View.GONE
                }
            }
        })
    }


}