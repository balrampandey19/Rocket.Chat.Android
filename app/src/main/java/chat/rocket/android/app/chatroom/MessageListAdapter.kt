package chat.rocket.android.app.chatroom

import DateTimeHelper
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import chat.rocket.android.R
import com.facebook.drawee.view.SimpleDraweeView
import com.stfalcon.frescoimageviewer.ImageViewer
import kotlinx.android.synthetic.main.item_message.view.*

class MessageListAdapter(private val context: Context, private var dataSet: MutableList<Message>, private val listener: (Message) -> Unit) : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = dataSet[position]

        holder.userAvatar.setImageURI(message.user.avatarUri)
        holder.userName.text = message.user.name
        holder.time.text = DateTimeHelper.getTime(message.localDatetime)

        val textContent = message.textContent
        if (textContent.isNullOrBlank()) {
            holder.textContent.visibility = View.GONE
        } else {
            holder.textContent.text = textContent
        }

        val imageAttachmentUri = message.imageAttachmentUri
        if (imageAttachmentUri.isNullOrBlank()) {
            holder.imageAttachment.visibility = View.GONE
        } else {
            holder.imageAttachment.setImageURI(imageAttachmentUri)
            holder.imageAttachment.setOnClickListener({
                ImageViewer.Builder(context, listOf(imageAttachmentUri))
                        .hideStatusBar(false)
                        .show()
            })
        }
    }

    override fun getItemCount(): Int = dataSet.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userAvatar: SimpleDraweeView = itemView.image_user_avatar
        val userName: TextView = itemView.text_user_name
        val time: TextView = itemView.text_message_time
        val textContent: TextView = itemView.text_content
        val imageAttachment: SimpleDraweeView = itemView.image_attachment


    }
}