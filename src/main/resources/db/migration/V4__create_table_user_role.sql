CREATE TABLE "UserRoles" (
    "userID" UUID NOT NULL REFERENCES "UserAccount"(id),
    "roleID" INT NOT NULL REFERENCES "Role"(id),

    PRIMARY KEY ("userID", "roleID")
);