import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TalkDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: TalkDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.talkDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndRetrieveTalk() = runTest {
        val talk = Talk(message = "hello")
        val id = dao.insert(talk)
        val talks = dao.getAll().first()
        assertEquals(1, talks.size)
        assertEquals(id, talks[0].id)
        assertEquals("hello", talks[0].message)
    }

    @Test
    fun deleteTalkRemovesIt() = runTest {
        val talk = Talk(message = "bye")
        val id = dao.insert(talk)
        dao.delete(talk.copy(id = id))
        val talks = dao.getAll().first()
        assertTrue(talks.isEmpty())
    }
}
