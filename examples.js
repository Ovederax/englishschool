// UserController

POST http://localhost:8000/user/client
=> Cookie
{
    "firstname": "Name",
    "lastname": "lastName",
    "email": "email",
    "login": "Client",
    "password": "password",
    "otherContacts": {}
}

POST http://localhost:8000/user/tutor
=> Cookie
{
    "firstname": "Name",
    "lastname": "lastName",
    "email": "email",
    "login": "Tutor",
    "password": "password",
    "otherContacts": {}
}

POST http://localhost:8000/user/login
=> Cookie
{
    "login": "Tutor",
    "password": "password"
}

POST http://localhost:8000/user/logout
<= Cookie
=> 200

// CourseControler
POST http://localhost:8000/course
{
    "name": "CourseTwo",
    "amount": 100
}
GET http://localhost:8000/course/1
{
    "id": 1,
    "name": "Course one",
    "amount": 120,
    "lessons": [
        {
            "title": "Lesson One",
            "order": 1,
            "content": "Text",
            "category": {
                "order": 1,
                "title": "Beginner",
                "description": "",
                "id": 1
            }
        }
    ]
}
GET http://localhost:8000/course/
[
    {
        "id": 1,
        "name": "Course one",
        "amount": 120
    },
    {
        "id": 2,
        "name": "Course two",
        "amount": 220
    }
]
// LessonControler
GET localhost:8000/lesson
[
    {
        "order": 1,
        "title": "Lesson One",
        "content": "Text",
        "id": 1,
        "courseId": 1,
        "categoryId": -1
    }
]
GET http://localhost:8000/lesson/1
{
    "title": "Lesson One",
    "order": 1,
    "content": "Text",
    "category": {
        "order": 1,
        "title": "Beginner",
        "description": "",
        "id": 1
    }
}
POST localhost:8000/lesson
{
    "order": 2,
    "title": "'Lesson Two'",
    "content": "content",
    "courseId": 1,
    "categoryId": -1
}
// CategoriesControler
GET localhost:8000/category
[
    {
        "order": 1,
        "title": "Beginner",
        "description": "",
        "id": 1
    }
]

