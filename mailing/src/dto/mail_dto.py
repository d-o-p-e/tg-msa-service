from pydantic import BaseModel
from enum import Enum

class MailType(str, Enum):
    APPLIED = "APPLIED"

class MailDto(BaseModel):
    mail_receiver: str
    mail_type: MailType