import asyncio
from consumer import consume
from logger import get_logger

def main(logger = get_logger()):
    logger.debug("Starting Server...")
    loop = asyncio.get_event_loop()
    loop.run_until_complete(consume())
    loop.close()
    logger.debug("Server Stopped")

if __name__ == "__main__":
    main()
