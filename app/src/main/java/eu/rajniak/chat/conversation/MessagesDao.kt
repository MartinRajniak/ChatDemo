package eu.rajniak.chat.conversation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    @Insert(onConflict = REPLACE)
    fun save(message: Message)

    @Query("SELECT * FROM messages")
    fun loadAll(): Flow<List<Message>>
}
