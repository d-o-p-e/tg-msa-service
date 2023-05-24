from aiokafka import AIOKafkaConsumer
from logger import get_logger
import os
from mail import Mail
from utils.mail_type import MailType
from dto.user_create_event_dto import UserCreateEventDto
from dto.entry_campaign_event_dto import EntryCampaignEventDto

async def consume(logger = get_logger(), kafka_host: str = os.getenv("KAFKA_HOST"), kafka_group: str = os.getenv("KAFKA_GROUP_ID")):
    consumer = AIOKafkaConsumer(
        "user-creation",
        "campaign-entry",
        "campaign-draw",
        bootstrap_servers=kafka_host,
        group_id=kafka_group,
        # bootstrap_servers='localhost:9092',
        # group_id='mailing'
        )
    
    mail = Mail()
    await consumer.start()
    try:
        async for msg in consumer:
            logger.debug(f"Consumed: {msg.value.decode('utf-8')}")
            # logic separated by topic type
            if msg.topic == "user-creation":
                msg_json: UserCreateEventDto = UserCreateEventDto.parse_raw(msg.value.decode('utf-8'))
            elif msg.topic == "campaign-entry":
                msg_json: EntryCampaignEventDto = EntryCampaignEventDto.parse_raw(msg.value.decode('utf-8'))
            mail.send(msg_json, MailType(msg.topic))
    finally:
        await consumer.stop()
        logger.debug("Consumer stopped")

