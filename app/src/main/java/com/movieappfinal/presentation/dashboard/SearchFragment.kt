package com.movieappfinal.presentation.dashboard

import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.movieappfinal.R
import com.movieappfinal.adapter.SearchAdapter
import com.movieappfinal.core.utils.BaseFragment
import com.movieappfinal.core.utils.launchAndCollectIn
import com.movieappfinal.databinding.FragmentSearchBinding
import com.movieappfinal.utils.SpaceItemDecoration
import com.movieappfinal.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment :
    BaseFragment<FragmentSearchBinding, HomeViewModel>(FragmentSearchBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()
    private val searchAdapter by lazy {
        SearchAdapter { data ->
            val bundle = bundleOf("movieId" to data.id)
            findNavController().navigate(R.id.action_dashboardFragment_to_detailFragment, bundle)
        }
    }

    override fun initView() = with(binding) {
        tilSearchBar.hint = getString(R.string.search_title)
        rvGridItem.layoutManager = GridLayoutManager(requireContext(), 2)
        rvGridItem.adapter = searchAdapter
        val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        rvGridItem.addItemDecoration(SpaceItemDecoration(spaceInPixels))
    }

    override fun initListener() {
        binding.tietSearchBar.doAfterTextChanged { query ->
            viewModel.fetchSearch(query.toString()).launchAndCollectIn(viewLifecycleOwner) {
                searchAdapter.submitData(it)
            }
            /**
             * println still in use
             */
            println("MASUK INIT: $query")
        }
    }

    override fun observeData() {}
}
