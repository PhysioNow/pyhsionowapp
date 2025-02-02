package crystal.physionow.datastore

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val reminderText = intent?.getStringExtra("reminder_text") ?: "Zeit für dein Training!"
        Toast.makeText(context, reminderText, Toast.LENGTH_LONG).show()
    }
}
