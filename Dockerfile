FROM python:3.10.2

WORKDIR /code

COPY . .

COPY ./requirements.txt /code/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

COPY . /code/app

EXPOSE 14045:14045

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "14045"]
