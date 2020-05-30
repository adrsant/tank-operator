#!/bin/sh

# queues
awslocal sqs create-queue --queue-name QUEQUE_TESTE

# topics
awslocal sns create-topic --name TOPIC_TESTE
