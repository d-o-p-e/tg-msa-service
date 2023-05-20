import smtplib, ssl
import os
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from logger import get_logger
from dto.mail_dto import MailDto
from content.apply import MailContent

class Mail:
    def __init__(self):
        self.smtp_host = os.getenv("MAIL_HOST")
        self.smtp_port = os.getenv("MAIL_PORT")
        self.sender_email = os.getenv("MAIL_USERNAME")
        self.password = os.getenv("MAIL_PASSWORD")
        self.ssl_context = ssl.create_default_context()
        self.logger = get_logger()

    def send(self, kafka_event: MailDto):
        message = MailContent(kafka_event.mail_type).get_content(kafka_event.mail_receiver)

        # Create secure connection with server and send email
        with smtplib.SMTP_SSL(self.smtp_host, self.smtp_port, context=self.ssl_context) as server:
            server.login(self.sender_email, self.password)
            server.sendmail(
                self.sender_email, kafka_event.mail_receiver, message.as_string()
            )
            self.logger.debug(f"Mail sent to: {kafka_event.mail_receiver}")
