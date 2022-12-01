package fr.cdudit.playgroundfinder.features.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.cdudit.playgroundfinder.R
import fr.cdudit.playgroundfinder.databinding.PlaygroundListItemBinding
import fr.cdudit.playgroundfinder.models.Record

class PlaygroundListAdapter(
    private val context: Context,
    private val dataSet: ArrayList<Record>,
    private val onClick: (Record) -> Unit
) : RecyclerView.Adapter<PlaygroundListAdapter.ViewHolder>() {
    private lateinit var binding: PlaygroundListItemBinding

    class ViewHolder(
        private val context: Context,
        private val binding: PlaygroundListItemBinding,
        private val onClick: (Record) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record) {
            binding.textViewItemTitle.text = item.fields.siteName
            binding.textViewItemDesc.text = context.getString(
                R.string.playground_list_item_desc,
                item.fields.surface.toInt().toString(),
                item.fields.gameNumber.toString()
            )
            binding.itemContainer.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        this.binding = PlaygroundListItemBinding.inflate(layoutInflater, viewGroup, false)
        return ViewHolder(context, binding, onClick)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}