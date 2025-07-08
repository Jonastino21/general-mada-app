package com.example.app.ui.house
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.repository.ServiceRepositoryImpl
import com.example.app.databinding.FragmentHouseListBinding
import com.example.app.ui.common.BaseFragment
import com.example.app.ui.house.HouseAdapter
import com.example.app.R


class HouseListFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_house_list
    private lateinit var binding: FragmentHouseListBinding
    private lateinit var viewModel: HouseViewModel
    private lateinit var adapter: HouseAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHouseListBinding.bind(view)

        setupViewModel()
        setupRecyclerView()
        setupSearch()
        loadHouses()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HouseViewModel(ServiceRepositoryImpl()) as T
            }
        })[HouseViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = HouseAdapter { house ->
            val action = HouseListFragmentDirections.actionHouseListToDetail(house.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@HouseListFragment.adapter
            addItemDecoration(GridSpacingItemDecoration(2, 16.dpToPx(context), true))
        }
    }

    private fun Int.dpToPx(context: Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchHouses(query, 1) // 1 guest par défaut
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun loadHouses() {
        viewModel.loadHouses()
        observeHouses()
    }

    private fun observeHouses() {
        viewModel.houses.observe(viewLifecycleOwner) { houses ->
            adapter.submitList(houses)
        }
    }
}

// Décoration pour espacement entre les éléments du GridLayout
class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: android.graphics.Rect,
        view: android.view.View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // position de l'item
        val column = position % spanCount // colonne

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // spacing - colonne * (spacing / spanCount)
            outRect.right = (column + 1) * spacing / spanCount // (colonne + 1) * (spacing / spanCount)

            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // bottom edge
        } else {
            outRect.left = column * spacing / spanCount // colonne * (spacing / spanCount)
            outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (colonne + 1) * (spacing / spanCount)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}
