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
POST http://localhost:8000/course


POST http://localhost:8000/course


POST http://localhost:8000/course

// LessonControler


// CategoriesControler


