package fr.cdudit.playgroundfinder.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.cdudit.playgroundfinder.databinding.PlaygroundListFiltersBinding

class PlaygroundListFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: PlaygroundListFiltersBinding
    private var onSubmit: ((Int?, Int?, String?) -> Unit)? = null

    companion object {
        const val TAG = "PlaygroundListFilterBottomSheetFragment"

        fun newInstance(onSubmit: (Int?, Int?, String?) -> Unit): PlaygroundListFilterBottomSheetFragment {
            return PlaygroundListFilterBottomSheetFragment().apply {
                this.onSubmit = onSubmit
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = PlaygroundListFiltersBinding.inflate(layoutInflater, container, false)
        this.initListener()
        return this.binding.root
    }

    private fun initListener() {
        this.binding.buttonSearch.setOnClickListener {
            this.onSubmit?.invoke(
                this.binding.editTextMinAge.getIntValueOrNull(),
                this.binding.editTextMaxAge.getIntValueOrNull(),
                this.binding.editTextSearch.getStringValueOrNull()
            )
            this.dismiss()
        }
    }

    private fun EditText.getStringValueOrNull(): String? {
        return if (this.editableText.isNotBlank())
            this.editableText.toString()
        else
            null
    }

    private fun EditText.getIntValueOrNull(): Int? {
        return if (this.editableText.isNotBlank())
            this.editableText.toString().toInt()
        else
            null
    }
}