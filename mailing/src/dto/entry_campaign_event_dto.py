from pydantic import BaseModel

class EntryCampaignEventDto(BaseModel):
    userId: int
    nickname: str
    email: str