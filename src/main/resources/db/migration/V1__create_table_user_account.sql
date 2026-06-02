CREATE TABLE "UserAccount" (
    id UUID NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE
);