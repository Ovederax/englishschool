package repo

import com.example.model.Course
import com.example.model.Lesson
import com.example.model.User
import com.example.service.*
import org.junit.Test
import kotlin.test.assertEquals

class ServicesTest {
    @Test
    fun userServiceTest() {
        DatabaseFactory.init()
        val userService = UserService()
        val token = userService.registerClient(User(
                "login",
                "password",
                "firstname",
                "lastname",
                "email",
        ))
        if(token === null) {
            assert(false)
            return
        }
        val user = token.let { userService.getUserByToken(it) }
        assertEquals("login", user?.login)
        assertEquals("email", user?.email)

        userService.logout(token)
        val user_two = token.let { userService.getUserByToken(it) }
        assertEquals(null, user_two)

        val newToken = userService.login("login", "password")
        if(newToken === null) {
            assert(false)
            return
        }

        assertEquals(1, userService.getList().size)
    }

    @Test
    fun courseServiceTest() {
        DatabaseFactory.init()
        val userService = UserService()
        val courseService = CourseService()
        val token = userService.registerTutor(User(
                "login",
                "password",
                "firstname",
                "lastname",
                "email",
        ))
        if(token === null) {
            assert(false)
            return
        }
        assertEquals(0, courseService.getList().size)
        val id = courseService.create(Course(
            -1,
            "English",
            100
        ))
        val id_two = courseService.create(Course(
                -1,
                "Math",
                100
        ))
        assertEquals("English", courseService.getList().first().name)
        courseService.update(Course(
            id,
            "Spanish",
            100
        ))
        assertEquals("Spanish", courseService.getList().first().name)
        assertEquals("Math", courseService.get(id_two)?.name)
    }

    @Test
    fun lessonServiceTest() {
        DatabaseFactory.init()
        val userService = UserService()
        val courseService = CourseService()
        val lessonService = LessonService()

        val token = userService.registerTutor(User(
                "login",
                "password",
                "firstname",
                "lastname",
                "email",
        ))
        if(token === null) {
            assert(false)
            return
        }
        val courseId = courseService.create(Course(
                -1,
                "English",
                100
        ))
        assertEquals(0, courseService.get(courseId)?.lessons?.size)
        lessonService.create(Lesson(
            0,
            "Lesson 1",
            "Content",
            -1,
            courseId,
            -1
        ))
        lessonService.create(Lesson(
                1,
                "Lesson 2",
                "Content",
                -1,
                courseId,
                -1
        ))
        assertEquals(2, lessonService.getList().size)
        assertEquals(2, courseService.get(courseId)?.lessons?.size)
        assertEquals("Lesson 1", courseService.get(courseId)?.lessons?.first()?.title)
    }
}