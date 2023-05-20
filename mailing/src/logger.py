import logging
import sys

def get_logger():

    logging.basicConfig(
        filemode='w', 
        format='%(asctime)s %(levelname)s %(message)s',
        stream=sys.stdout,
        level=logging.DEBUG)
    
    logger = logging.getLogger()

    return logger