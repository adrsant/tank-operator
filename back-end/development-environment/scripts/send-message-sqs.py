import localstack_client.session

queue = 'http://localhost:4576/queue/QUQUE_TESTE'
session = localstack_client.session.Session()
sqs = session.client('sqs')
response = sqs.send_message( QueueUrl=queue, MessageBody='TEST')
print(response)
