package com.example.productslist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.productslist.domain.Product
import com.example.productslist.R
import com.example.productslist.databinding.ProductItemFragmentBinding
import javax.inject.Inject

class ProductItemFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as ProductApp).component
    }
    private lateinit var viewModelProductItemActivity: ProductItemViewModel

    @Inject
     lateinit var viewModelFactory:ViewModelFactory

    private var _binding: ProductItemFragmentBinding? = null
    private val binding: ProductItemFragmentBinding
        get() {
            return _binding ?: throw RuntimeException("binding is null")
        }

    private lateinit var onEndingOfEditingFragment: OnEndingOfEditingFragment

    private var screenMode: String = UNDEFINED_SCREEN_MODE
    private var productId: Int = Product.UNDEFINED_ID

    private var enteredtext: String? = null
    private var enteredCount: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEndingOfEditingFragment) {
            onEndingOfEditingFragment = context
            component.inject(this)
        } else {
            throw RuntimeException("Activity is not implementing the relevant interface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductItemFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelProductItemActivity =
            ViewModelProvider(this, viewModelFactory)[ProductItemViewModel::class.java]
        addErrorListeners()
        closingActivity()
        when (screenMode) {
            EDIT_MODE -> editProductModeLogic()
            ADD_MODE -> addProductModeLogic()
            else -> throw RuntimeException("Unknown screen mode")
        }
    }
    fun addProductModeLogic() {

        binding.buttonAddProduct.setOnClickListener {

            enteredtext = binding.tietName.text.toString()
            enteredCount = binding.tietCount.text.toString()
            viewModelProductItemActivity.addProductToList(enteredtext, enteredCount)

        }
    }

    fun editProductModeLogic() {
        viewModelProductItemActivity.getOunProductFromList(productId)
        viewModelProductItemActivity.currentProduct.observe(viewLifecycleOwner) {
            binding.tietName.setText(it.name)
            binding.tietCount.setText(it.count.toString())
        }

        binding.buttonAddProduct.setOnClickListener {
            enteredtext = binding.tietName.text.toString()
            enteredCount = binding.tietCount.text.toString()
            viewModelProductItemActivity.editProductToList(
                enteredtext, enteredCount
            )
        }

    }


    fun closingActivity() {
        viewModelProductItemActivity.shouldActivityBeFinished.observe(viewLifecycleOwner) {
            onEndingOfEditingFragment.endingOfEditingFragment()
        }
    }

    fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("no screenMode")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)

        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screenMode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(EXTRA_PRODUCT_ID)) {
                throw RuntimeException("Unknown productId")
            }
            productId = args.getInt(EXTRA_PRODUCT_ID)

        }
    }


    private fun addErrorListeners() {
        viewModelProductItemActivity.errorName.observe(viewLifecycleOwner) {
            if (it) {
                binding.tilName.error = getString(R.string.enterAgain)
            } else {
                binding.tilName.error = null
            }
        }
        viewModelProductItemActivity.errorCount.observe(viewLifecycleOwner) {
            if (it) {
                binding.tilCount.error = getString(R.string.enterAgain)
            } else {
                binding.tilCount.error = null
            }
        }

        binding.tietName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelProductItemActivity.resetNameError()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.tietCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModelProductItemActivity.resetCountError()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val EXTRA_PRODUCT_ID = "Product_Id"
        private const val EXTRA_SCREEN_MODE = "EXTRA_EDIT_MODE"
        private const val EDIT_MODE = "EDIT_MODE"
        private const val ADD_MODE = "ADD_MODE"
        private const val UNDEFINED_SCREEN_MODE = ""

        fun addFragmentInAddMode(): ProductItemFragment {
            return ProductItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun addFragmentInEditMode(productId: Int): ProductItemFragment {
            return ProductItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, EDIT_MODE)
                    putInt(EXTRA_PRODUCT_ID, productId)
                }
            }
        }
    }
}
