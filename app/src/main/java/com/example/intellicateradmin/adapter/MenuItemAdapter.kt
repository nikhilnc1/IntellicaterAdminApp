import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intellicateradmin.databinding.ItemItemBinding
import com.example.intellicateradmin.model.AllMenu
import com.google.firebase.database.DatabaseReference


class MenuItemAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference,
    private val onDeleteClickListener:(position :Int) ->Unit
) : RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {
    private val itemQuantities = IntArray(menuList.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class AddItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                foodNameTextView.text = menuItem.foodName
                priceTextView.text = menuItem.foodPrice
                quantityTextVIew.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    onDeleteClickListener(position)
                }
                pluseButton.setOnClickListener {
                    increaseQuantity(position)
                }
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.quantityTextVIew.text = itemQuantities[position].toString()
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.quantityTextVIew.text = itemQuantities[position].toString()
            }
        }

        private fun deleteQuantity(position: Int) {
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }
    }

}
