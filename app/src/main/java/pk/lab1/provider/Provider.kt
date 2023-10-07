package pk.lab1.provider


import android.content.*
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import pk.lab1.Const
import pk.lab1.Const.PROVIDER_NAME
import pk.lab1.database.TimeDatabase

class Provider : ContentProvider() {
    companion object {
        // defining authority so that other application can access it
        // defining content URI
        const val URL = "content://$PROVIDER_NAME/time"

        const val uriCode = 1
        var uriMatcher: UriMatcher? = null
        const val TABLE_NAME = "Time"
        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "time",
                uriCode
            )

            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "time/*",
                uriCode
            )
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    /**
     * How to fetch sample code
     *
     *   val cursor = contentResolver.query(
     *       Uri.parse("content://pk.lab1.provider/time"),
     *       null,
     *       null,
     *       null,
     *       null
     *   )
     *
     *   if (cursor!!.moveToFirst()) {
     *       while (!cursor.isAfterLast) {
     *           Log.i("ContentProvider",  "Username: ${cursor.getString(cursor.getColumnIndex("username"))}, number: ${cursor.getInt(cursor.getColumnIndex("number"))}")
     *           cursor.moveToNext()
     *       }
     *   } else {
     *       Log.d("ContentProvider",  "No records found")
     *   }
     *
     */
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val db = Room.databaseBuilder(
            context!!.applicationContext,
            TimeDatabase::class.java, Const.DATABASE_NAME
        ).allowMainThreadQueries().build()

        val cursor = db.query("SELECT username, number FROM time", null)

        cursor.setNotificationUri(context!!.contentResolver, uri)

        return cursor
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}