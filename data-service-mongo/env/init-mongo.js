db.createUser({
    user: "tomek",
    pwd: "password",
    roles: [
        {
            role: "readWrite",
            db: "todolist"
        }
    ]
});