from pydantic import BaseModel

class DrawCampaignEventDto(BaseModel):
    userId: int
    nickname: str
    email: str