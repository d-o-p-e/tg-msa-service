# build dockerfile for python project that runs main.py

FROM python:3.10-slim-buster

WORKDIR /app

COPY requirements.txt requirements.txt

RUN pip3 install -r requirements.txt

COPY src/ .

CMD ["python3", "main.py"]
