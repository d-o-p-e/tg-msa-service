from pydantic import BaseModel

class UserCreateEventDto(BaseModel):
    userId: int
    nickname: str
    email: str
    profileImageUrl: str