CREATE TABLE "UserRoles" (
    user_id UUID NOT NULL REFERENCES "UserAccount"(id),
    role_id INT NOT NULL REFERENCES "Role"(id),

    PRIMARY KEY (user_id, role_id)
);