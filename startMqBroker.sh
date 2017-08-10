docker run --name='activemq' -it --rm -e 'ACTIVEMQ_MIN_MEMORY=512' -e 'ACTIVEMQ_MAX_MEMORY=2048' -p 61616:61616 -p 8161:8161 -P webcenter/activemq:latest
