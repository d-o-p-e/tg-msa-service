import os
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from dto.mail_dto import MailType

class MailContent():
    def __init__(self, type: MailType):
        self.sender_email = os.getenv("MAIL_USERNAME")
        if (type == MailType.APPLIED):
            self.subject = "[갓생] 상품에 응모해주셔서 감사합니다!"
            self.content = """\
                <html>
                <body>
                    <p>
                        안녕하세요. 갓생입니다.
                        상품에 응모해주셔서 감사합니다.
                    </p>
                </body>
                </html>
                """

    def get_content(self, receiver_mail: str):
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