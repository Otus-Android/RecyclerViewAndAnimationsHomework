package ru.otus.cryptosample.coins.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.otus.cryptosample.CoinsSampleApp
import ru.otus.cryptosample.coins.feature.categoryAdapter.CategoryAdapter
import ru.otus.cryptosample.coins.feature.di.DaggerCoinListComponent
import ru.otus.cryptosample.databinding.FragmentCoinListBinding
import javax.inject.Inject

class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: CoinListViewModelFactory

    private val viewModel: CoinListViewModel by viewModels { factory }

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComponent = (activity?.applicationContext as CoinsSampleApp).appComponent

        DaggerCoinListComponent.factory()
            .create(appComponent)
            .inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupChipToggle()
        subscribeUI()
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = categoryAdapter
        }
    }

    private fun setupChipToggle() {
        binding.highlightChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onHighlightMoversToggled(isChecked)
        }

        binding.showAllChip.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onShowAllToggled(isChecked)
        }
    }

    private fun subscribeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: CoinsScreenState) {
        val items = state.categories.map { category ->
            CategoryUiState(
                category = category,
                showAll = state.showAll
            )
        }

        categoryAdapter.submitList(items)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
