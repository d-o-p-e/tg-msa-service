import smtplib, ssl
import os
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from logger import get_logger
from utils.mail_type import MailType

from dto.user_create_event_dto import UserCreateEventDto
from dto.entry_campaign_event_dto import EntryCampaignEventDto
from dto.draw_campaign_event_dto import DrawCampaignEventDto
class Mail:
    def __init__(self):
        self.smtp_host = os.getenv("MAIL_HOST")
        self.smtp_port = os.getenv("MAIL_PORT")
        self.sender_email = os.getenv("MAIL_USERNAME")
        self.password = os.getenv("MAIL_PASSWORD")

        self.ssl_context = ssl.create_default_context()
        self.logger = get_logger()

    def generate_message(self, receiver_mail: str):
        message = MIMEMultipart("alternative")
        message["Subject"] = self.subject
        message["From"] = self.sender_email
        message["To"] = receiver_mail

        # Turn these into plain/html MIMEText objects
        html_mime = MIMEText(self.content, "html")

        # Add HTML/plain-text parts to MIMEMultipart message
        # The email client will try to render the last part first
        message.attach(html_mime)

        return message

    def send(self, kafka_event: UserCreateEventDto | EntryCampaignEventDto | DrawCampaignEventDto, event_type: MailType):
        self.get_content(kafka_event, event_type)
        message = self.generate_message(kafka_event.email)

        # Create secure connection with server and send email
        with smtplib.SMTP_SSL(self.smtp_host, self.smtp_port, context=self.ssl_context) as server:
            server.login(self.sender_email, self.password)
            server.sendmail(
                self.sender_email, kafka_event.email, message.as_string()
            )
            self.logger.debug(f"Mail sent to: {kafka_event.email}")

    def get_content(self, kafka_event: UserCreateEventDto | EntryCampaignEventDto | DrawCampaignEventDto, event_type: MailType):
        if (event_type == MailType.CAMPAIGN_ENTRY):
            kafka_event: EntryCampaignEventDto
            with open("content/campaign_entry.html", "r") as f:
                self.subject = "[갓생] 상품에 응모해주셔서 감사합니다!"
                self.content = f.read().format(
                    username=kafka_event.nickname
                )
        elif (type == MailType.CAMPAIGN_DRAW):
            kafka_event: DrawCampaignEventDto
            with open("content/campaign_draw.html", "r") as f:
                self.subject = "[갓생] 상품 당첨을 축하드립니다!"
                self.content = f.read().format(
                    username=kafka_event.nickname
                )
        elif (event_type == MailType.USER_CREATION):
            kafka_event: UserCreateEventDto
            with open("content/user_creation.html", "r") as f:
                self.subject = "[갓생] 회원가입을 축하드립니다!"
                self.content = f.read().format(
                    username=kafka_event.nickname
                )