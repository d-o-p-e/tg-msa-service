from aiokafka import AIOKafkaConsumer
from logger import get_logger
import os
from mail import Mail
from dto.mail_dto import MailDto

async def consume(logger = get_logger(), kafka_host: str = os.getenv("KAFKA_HOST"), kafka_group: str = os.getenv("KAFKA_GROUP_ID"), kafka_topic: str = os.getenv("KAFKA_TOPIC")):
    consumer = AIOKafkaConsumer(
        kafka_topic,
        bootstrap_servers=kafka_host,
        group_id=kafka_group,
        # "mailing",
        # bootstrap_servers='localhost:9092',
        # group_id='mailiing'
        )
    
    mail = Mail()
    await consumer.start()
    try:
        async for msg in consumer:
            print(f"""{msg.value.decode('utf-8')}""")
            logger.debug(f"Consumed: {msg.value.decode('utf-8')}")
            msg_json: MailDto = MailDto.parse_raw(msg.value.decode('utf-8'))
            mail.send(msg_json)
    finally:
        await consumer.stop()
        logger.debug("Consumer stopped")

