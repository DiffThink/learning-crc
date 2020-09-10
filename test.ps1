name                               value
----                               -----
JGROUPS_PING_PROTOCOL              dns.DNS_PING
OPENSHIFT_DNS_PING_SERVICE_NAME    eap-app-ping
OPENSHIFT_DNS_PING_SERVICE_PORT    8888
ENV_FILES                          /etc/eap-environment/*
HTTPS_KEYSTORE_DIR                 /etc/eap-secret-volume
HTTPS_KEYSTORE                     keystore.jks
HTTPS_KEYSTORE_TYPE
HTTPS_NAME                         jboss
HTTPS_PASSWORD                     mykeystorepass
MQ_CLUSTER_PASSWORD                7NxMtwrH
MQ_QUEUES
MQ_TOPICS
JGROUPS_ENCRYPT_SECRET             eap-app-secret
JGROUPS_ENCRYPT_KEYSTORE_DIR       /etc/jgroups-encrypt-secret-volume
JGROUPS_ENCRYPT_KEYSTORE           jgroups.jceks
JGROUPS_ENCRYPT_NAME               secret-key
JGROUPS_ENCRYPT_PASSWORD           password
JGROUPS_CLUSTER_PASSWORD           7P2eLPeV
AUTO_DEPLOY_EXPLODED               false
ENABLE_GENERATE_DEFAULT_DATASOURCE false