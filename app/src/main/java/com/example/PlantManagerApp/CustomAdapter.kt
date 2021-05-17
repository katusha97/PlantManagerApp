import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import app.com.q3.DataModel
import com.example.PlantManagerApp.R
import dataBaseModule.DatabaseHandlerImpl
import dataBaseModule.WorkType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CustomAdapter(private val dataSet: ArrayList<*>, val work_type: WorkType, mContext: Context) :
        ArrayAdapter<Any?>(mContext, R.layout.row_item, dataSet) {
    private class ViewHolder {
        lateinit var txtName: TextView
        lateinit var checkBox: CheckBox
    }
    override fun getCount(): Int {
        return dataSet.size
    }
    override fun getItem(position: Int): DataModel {
        return dataSet[position] as DataModel
    }
    override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
    ): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        val result: View
        if (convertView == null) {
            viewHolder = ViewHolder()
            convertView =
                    LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
            viewHolder.txtName =
                    convertView.findViewById(R.id.txtName)
            viewHolder.checkBox =
                    convertView.findViewById(R.id.checkBox)
            viewHolder.checkBox.setOnClickListener {
                val handler = DatabaseHandlerImpl(context)
                val dataModel: DataModel = getItem(position)
                dataModel.checked = !dataModel.checked
                val dtime = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                if (dataModel.checked) {
                    handler.removeWork(dtime, dataModel.name, work_type)
                } else {
                    handler.addWork(dtime, dataModel.name, work_type)
                }
            }
            result = convertView
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }
        val item: DataModel = getItem(position)
        viewHolder.txtName.text = item.name
        viewHolder.checkBox.isChecked = item.checked
        return result
    }
}