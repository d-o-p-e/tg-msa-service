from enum import Enum

class MailType(str, Enum):
    CAMPAIGN_ENTRY: str = "campaign-entry"
    CAMPAIGN_DRAW: str = "campaign-draw"
    USER_CREATION: str = "user-creation"